package game.ai;

import game.LegendsOfArborea;
import game.board.Board;
import game.board.Tile;
import game.players.ComputerPlayer;
import game.players.Player;
import game.search.Astar;
import game.units.Unit;

import java.util.ArrayList;


public class AgressiveAi extends Ai {
	
	Player enemy;
	ArrayList<Unit> myUnits;
	Board board;

	public AgressiveAi(ComputerPlayer cp, Player enemy) {
		super(cp);
		this.enemy = enemy;
		myUnits = this.computerPlayer.get().GetUnits();
	}

	@Override
	public ArrayList<AiMove> doThinking() {
		this.board = LegendsOfArborea.GAME.board;
		ArrayList<AiMove> moves = new ArrayList<AiMove>();
		ArrayList<Unit> targets = new ArrayList<Unit>();
		ArrayList<Unit> unreachable = new ArrayList<Unit>();
		ArrayList<Unit> exhaustedUnits = new ArrayList<Unit>();
		
		// Get targets to attack
		int nrTargets = 1;
		if (enemy.GetUnits().size() < 2) {
			nrTargets = 1;
		}
		while (targets.size() < nrTargets) {
			int lowestHp = 100;
			int bestEmpty = 0;
			Unit bestTarget = null;
			for (Unit u : enemy.GetUnits()) {
				if (!targets.contains(u) && !unreachable.contains(u)) {
					// Find best target (low hp + space around the unit)
					int emptyTiles = board.getSurroundingEmptyTiles(u.getPosition(), 1).size();
					emptyTiles += board.getSurroundingEnemies(u.getPosition(), 1, u.race).size();
					if (u.getHp() <= lowestHp && emptyTiles > bestEmpty) {
						lowestHp = u.getHp();
						bestTarget = u;
						bestEmpty = emptyTiles;
					}
				}
			}
			targets.add(bestTarget);
			// Now find a suitable attacker
			int bestDist = 1000;
			Unit bestAttacker = null;
			for (Unit a : myUnits) {
				int dist = Math.abs(bestTarget.getCol() - a.getCol()) + Math.abs(bestTarget.getRow() - a.getRow());
				if (dist < bestDist && !exhaustedUnits.contains(a)) {
					bestDist = dist;
					bestAttacker = a;
				}
			}
			// If we couldn't find a target it must be blocked, remove and search new
			if (!findMovesAttacker(moves, bestAttacker, bestTarget)) {
				targets.remove(bestTarget);
				unreachable.add(bestTarget);
			} else {
				exhaustedUnits.add(bestAttacker);
				// Get cluster going
			}
		}
		return moves;		
	}
	
	private boolean findMovesAttacker(ArrayList<AiMove> moves, Unit attacker, Unit target) {
		if (attacker.adjecentTo(target)) {
			moves.add(getMove(target.getPosition(), attacker, AiMove.TYPE.ATTACK, attacker.getPosition()));
		} else {
			// Make distance smaller
			Tile next = Astar.aStar(board, attacker.getPosition(), target.getPosition());
			if (next == null) {
				// If we didn't find a suitable path/move
				return false;
			}
			moves.add(getMove(next, attacker, AiMove.TYPE.MOVE, attacker.getPosition()));
			if (next.adjecentTo(target.getPosition())) {
				moves.add(getMove(target.getPosition(), attacker, AiMove.TYPE.ATTACK, next));
			}
		}
		return true;
	}
	
	private AiMove getMove(Tile target, Unit u, AiMove.TYPE move, Tile uPosition) {
		ArrayList<Tile> attackableTiles = board.getSurroundingEnemyTiles(uPosition, u.rng, u.race);
		ArrayList<Tile> reachableTiles = board.getSurroundingEmptyTiles(uPosition, u.spd);
		return new AiMove(target, u, move, attackableTiles, reachableTiles);
	}
}
