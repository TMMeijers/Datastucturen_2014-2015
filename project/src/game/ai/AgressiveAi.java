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
		ArrayList<Unit> enemyUnits = enemy.GetUnits();
		ArrayList<Unit> targets = new ArrayList<Unit>();
		
		// Get targets to attack
		int nrTargets = 1;
		if (enemyUnits.size() < 2) {
			nrTargets = 1;
		}
		while (targets.size() < nrTargets) {
			int lowestHp = 100;
			int bestEmpty = 0;
			Unit bestTarget = null;
			for (Unit u : enemyUnits) {
				if (!targets.contains(u)) {
					int emptyTiles = board.getSurroundingEmptyTiles(u.getPosition(), 1).size();
					if (u.getHp() < lowestHp && emptyTiles > bestEmpty) {
						// Check if we can move to unit
						lowestHp = u.getHp();
						bestTarget = u;
						bestEmpty = emptyTiles;
					}
				}
			}
			System.out.println(true);
			targets.add(bestTarget);
		}
		ArrayList<ArrayList<Unit>> clusters = new ArrayList<ArrayList<Unit>>();
		for (Unit u : targets) {
			clusters.add(new ArrayList<Unit>());
			int bestDist = 1000;
			Unit bestAttacker = null;
			for (Unit a : myUnits) {
				int dist = Math.abs(u.getCol() - a.getCol()) + Math.abs(u.getRow() - a.getRow());
				if (dist < bestDist) {
					bestDist = dist;
					bestAttacker = a;
				}
			}
			System.out.println("Trying to find move");
			findMovesAttacker(moves, bestAttacker, u);
		}
		for (AiMove m : moves) {
			System.out.println(m.type);
		}
		return moves;		
	}
	
	private void findMovesAttacker(ArrayList<AiMove> moves, Unit attacker, Unit target) {
		if (attacker.adjecentTo(target)) {
			moves.add(getMove(target.getPosition(), attacker, AiMove.TYPE.ATTACK));
		} else {
			// Make distance smaller
			Tile next = Astar.aStar(board, attacker.getPosition(), target.getPosition());
			moves.add(getMove(next, attacker, AiMove.TYPE.MOVE));
			if (next.adjecentTo(target.getPosition())) {
				moves.add(getMove(target.getPosition(), attacker, AiMove.TYPE.ATTACK));
			}
		}
	}
	
	private AiMove getMove(Tile target, Unit u, AiMove.TYPE move) {
		ArrayList<Tile> attackableTiles = board.getSurroundingEnemyTiles(target, u.rng, u.race);
		ArrayList<Tile> reachableTiles = board.getSurroundingEmptyTiles(target, u.spd);
		return new AiMove(target, u, move, attackableTiles, reachableTiles);
	}
}
