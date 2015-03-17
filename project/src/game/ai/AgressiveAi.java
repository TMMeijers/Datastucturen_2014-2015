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
		int nrTargets = 5;
		if (enemy.GetUnits().size() < nrTargets) {
			nrTargets = enemy.GetUnits().size();
		}
		while (targets.size() < nrTargets) {
			// Get target based on hp and distance to enemy units
			int lowestHp = 100;
			int bestEmpty = 0;
			int bestDist = 100;
			Unit bestTarget = null;
			for (Unit u : enemy.GetUnits()) {
				if (!targets.contains(u) && !unreachable.contains(u)) {
					int dist = 100;
					// Check distances to enemy units
					for (Unit m : myUnits) {
						if (getDistance(u, m) < dist) {
							dist = getDistance(u, m);
						}
					}
					// Check if target is a better target than the current
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
		// Now get attackers
		for (Unit t : targets) {
			// If target died in the meantime
			if (t == null) {
				continue;
			}
			if (myUnits.size() - exhaustedUnits.size() <= 0) {
				// If we don't have any units left
				break;
			}
			// Find the best attacker based on distance
			int bestDist = 1000;
			Unit bestAttacker = null;
			for (Unit a : myUnits) {
				int dist = getDistance(t, a);
				if (dist < bestDist && !exhaustedUnits.contains(a)) {
					bestDist = dist;
					bestAttacker = a;
				}
			}
			// See if we can move towards target, also handles attacking
			if (findMovesAttacker(moves, bestAttacker, t)) {
				// If so get support for attacker based on empty tiles around new spot
				exhaustedUnits.add(bestAttacker);
				Tile support = moves.get(moves.size()-1).tile;
				int unitsNeeded = board.getSurroundingEmptyTiles(support, 1).size() + 1;
				if (myUnits.size() - exhaustedUnits.size() < unitsNeeded) {
					unitsNeeded = myUnits.size() - exhaustedUnits.size();
				}
				// Get supporters based on distance
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
					// Check if we can find a move, also handles attacking
					if (findMovesSupporter(moves, bestSupport, support)) {
						exhaustedUnits.add(bestSupport);
					}
				}
			}
		}
		
		// Now let all units move and attack that haven't done so
		for (Unit u : myUnits) {
			if (!exhaustedUnits.contains(u)) {
				moveCloser(moves, u, exhaustedUnits);
			}
		}
		return moves;
	}
	
	// moves a unit closer to the closest unit which is either attacking or supporting
	private void moveCloser(ArrayList<AiMove> moves, Unit u, ArrayList<Unit> exhaustedUnits) {
		// Find best goal unit based on distance
		int bestDist = 100;
		Tile bestTile = null;
		for (Unit e : exhaustedUnits) {
			int dist = getDistance(u, e);
			if (dist < bestDist) {
				Tile next = Astar.aStar(board, u.getPosition(), e.getPosition());
				if (next != null) {
					bestDist = dist;
					bestTile = next;
				}
			}
		}
		if (bestTile != null) {
			moves.add(getMove(bestTile, u, AiMove.TYPE.MOVE, u.getPosition(), true));
			attackLowest(moves, u, bestTile, false);
		} else {
			attackLowest(moves, u, u.getPosition(), true);
		}
	}
	
	// Attacks the unit with the lowest HP
	private boolean attackLowest(ArrayList<AiMove> moves, Unit u, Tile t, boolean canMove) {
		ArrayList<Unit> surroundingEnemies = 
			LegendsOfArborea.GAME.board.getSurroundingEnemies(t, u.rng, u.race);
		Unit bestSurrounding = null;
		int lowestHp = 100;
		for (Unit e : surroundingEnemies) {
			int hp = e.getHp();
			if (hp < lowestHp) {
				lowestHp = hp;
				bestSurrounding = e;
			}
		}
		if (bestSurrounding != null) {
			moves.add(getMove(bestSurrounding.getPosition(), u, AiMove.TYPE.ATTACK, t, canMove));
			return true;
		}
		return false;
	}
	
	// Find the best move for a supporter, attacks if possible
	private boolean findMovesSupporter(ArrayList<AiMove> moves, Unit supporter, Tile target) {
		// Make distance smaller
		Tile next = Astar.aStar(board, supporter.getPosition(), target);
		if (next == null) {
			if (attackLowest(moves, supporter, supporter.getPosition(), true)) {
				return true;
			}
			// If we didn't find a suitable path/move
			return false;
		}
		moves.add(getMove(next, supporter, AiMove.TYPE.MOVE, supporter.getPosition(), true));
		attackLowest(moves, supporter, next, false);
		return true;
	}
	
	// Handles the move for the main attackers, attacks and moves
	private boolean findMovesAttacker(ArrayList<AiMove> moves, Unit attacker, Unit target) {
		// If we're already there attack
		if (attacker.adjecentTo(target)) {
			moves.add(getMove(target.getPosition(), attacker, AiMove.TYPE.ATTACK, attacker.getPosition(), true));
		} else {
			// Make distance smaller
			Tile next = Astar.aStar(board, attacker.getPosition(), target.getPosition());
			if (next == null) {
				// If we didn't find a suitable path/move
				if (attackLowest(moves, attacker, attacker.getPosition(), true)) {
					return true;
				}
				return false;
			}
			// Add move and attack if possible
			moves.add(getMove(next, attacker, AiMove.TYPE.MOVE, attacker.getPosition(), true));
			if (next.adjecentTo(target.getPosition())) {
				moves.add(getMove(target.getPosition(), attacker, AiMove.TYPE.ATTACK, next, false));
			} else {
				// Else attack other unit if possible
				attackLowest(moves, attacker, next, false);
			}
		}
		return true;
	}
	
	// Converts tile and goal unit to an AiMove
	private AiMove getMove(Tile target, Unit u, AiMove.TYPE move, Tile uPosition, boolean canMove) {
		// Tiles for highlighting
		ArrayList<Tile> attackableTiles = board.getSurroundingEnemyTiles(uPosition, u.rng, u.race);
		ArrayList<Tile> reachableTiles = new ArrayList<Tile>();
		if (canMove) {
			reachableTiles = board.getSurroundingTiles(uPosition, u.spd);
			ArrayList<Tile> remove = new ArrayList<Tile>();
			for (Tile t : reachableTiles) {
				if (t.isTaken()) {
					remove.add(t);
				}
			}
			// Two loops to prevent ConcurrentModificationException
			for (Tile t : remove) {
				reachableTiles.remove(t);
			}
		}
		if (move == AiMove.TYPE.MOVE) {
			target.switchTaken();
			u.getPosition().switchTaken();
		}
		return new AiMove(target, u, move, attackableTiles, reachableTiles);
	}
	
	// Calculates distance based on unit and tiles
	private int getDistance(Unit u, Tile t) {
		return Math.max(Math.abs(u.getCol() - t.getCol()), Math.abs(u.getRow() - t.getRow()));
	}
	
	// We can't always call with tiles so we need this method as well.
	private int getDistance(Unit u, Unit t) {
		return getDistance(u, t.getPosition());
	}
}
