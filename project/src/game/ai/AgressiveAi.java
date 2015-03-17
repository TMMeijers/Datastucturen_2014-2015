package game.ai;

import game.LegendsOfArborea;
import game.Mechanics;
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
		int nrTargets = 3;
		if (enemy.GetUnits().size() < 3) {
			nrTargets = enemy.GetUnits().size();
		}
		while (targets.size() < nrTargets) {
			int lowestHp = 100;
			int bestEmpty = 0;
			int bestDist = 100;
			Unit bestTarget = null;
			for (Unit u : enemy.GetUnits()) {
				if (!targets.contains(u) && !unreachable.contains(u)) {
					int dist = 100;
					for (Unit m : myUnits) {
						if (getDistance(u, m) < dist) {
							dist = getDistance(u, m);
						}
					}
					// Find best target (low hp + space around the unit)
					int emptyTiles = board.getSurroundingEmptyTiles(u.getPosition(), 1).size();
					emptyTiles += board.getSurroundingEnemies(u.getPosition(), 1, u.race).size();
					if (u.getHp() <= lowestHp && emptyTiles > bestEmpty && dist < bestDist) {
						bestDist = dist;
						lowestHp = u.getHp();
						bestTarget = u;
						bestEmpty = emptyTiles;
					}
				}
			}
			targets.add(bestTarget);
		}
		
		for (Unit t : targets) {
			if (myUnits.size() - exhaustedUnits.size() <= 0) {
				// If we don't have any units left
				break;
			}
			System.out.println(t + " at c" + t.getCol() + " r" + t.getRow());
			System.out.println("Units left: " + (myUnits.size() - exhaustedUnits.size()));
			// Now find a suitable attacker
			int bestDist = 1000;
			Unit bestAttacker = null;
			for (Unit a : myUnits) {
				int dist = getDistance(t, a);
				if (dist < bestDist && !exhaustedUnits.contains(a)) {
					bestDist = dist;
					bestAttacker = a;
				}
			}
			findMovesAttacker(moves, bestAttacker, t);
			exhaustedUnits.add(bestAttacker);
			Tile support = moves.get(moves.size()-1).tile;
			int unitsNeeded = board.getSurroundingEmptyTiles(support, 1).size() + 1;
			System.out.println("Empty tiles: " + unitsNeeded);
			if (myUnits.size() - exhaustedUnits.size() < unitsNeeded) {
				unitsNeeded = myUnits.size() - exhaustedUnits.size();
			}
			System.out.println(unitsNeeded);
			for (int i = 0; i < unitsNeeded; i++) {
				bestDist = 1000;
				Unit bestSupport = null;
				for (Unit u : myUnits) {
					int dist = getDistance(u, support);
					if (dist < bestDist && !exhaustedUnits.contains(u)) {
						bestDist = dist;
						bestSupport = u;
					}
				}
				findMovesSupporter(moves, bestSupport, support);
				exhaustedUnits.add(bestSupport);
			}
		}
		return moves;
	}
	
	private boolean findMovesSupporter(ArrayList<AiMove> moves, Unit supporter, Tile target) {
		// Make distance smaller
		Tile next = Astar.aStar(board, supporter.getPosition(), target);
		if (next == null) {
			// If we didn't find a suitable path/move
			return false;
		}
		moves.add(getMove(next, supporter, AiMove.TYPE.MOVE, supporter.getPosition()));
		ArrayList<Unit> surroundingEnemies = 
				LegendsOfArborea.GAME.board.getSurroundingEnemies(next, supporter.rng, supporter.race);
		Unit bestSurrounding = null;
		double bestChance = 0;
		for (Unit e : surroundingEnemies) {
			double chance = Mechanics.hitChance(board, supporter, e);
			if (chance > bestChance) {
				bestSurrounding = e;
				bestChance = chance;
			}
		}
		if (bestSurrounding != null) {
			moves.add(getMove(bestSurrounding.getPosition(), supporter, AiMove.TYPE.ATTACK, next));
		}
		return true;
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
		if (move == AiMove.TYPE.MOVE) {
			target.switchTaken();
			u.getPosition().switchTaken();
		}
		return new AiMove(target, u, move, attackableTiles, reachableTiles);
	}
	
	private int getDistance(Unit u, Tile t) {
		return Math.max(Math.abs(u.getCol() - t.getCol()), Math.abs(u.getRow() - t.getRow()));
	}
	
	private int getDistance(Unit u, Unit t) {
		return getDistance(u, t.getPosition());
	}
}
