package game;

import game.board.Board;
import game.board.Tile;
import game.units.Unit;

import java.util.ArrayList;

/**
 * The mechanics for the game Legends of Arborea, calculates hit chances and handles movement.
 * @author T.M. Meijers and M. Pfundstein
 */
public class Mechanics {
	/**
	 * Calculates whether attacker can hit the defender, and if so deals the damage. Also 
	 * sets attacking status to inactive.
	 * @param board     the current state of the board
	 * @param attacker  the attacking Unit
	 * @param defender  the defending Unit
	 * @see 			hitChance
	 * @return 			true if hit was succesful, false otherwise
	 */
	public static boolean hit(Board board, Unit attacker, Unit defender) {
		// If P(hit) is larger than value generated deal damage
		attacker.setDirection(findNewDirection(board.getDimension(), attacker.getPosition(), defender.getPosition()));
		defender.setDirection(findNewDirection(board.getDimension(), defender.getPosition(), attacker.getPosition()));
		if (Math.random() < hitChance(board, attacker, defender)) {
			attacker.hasAttacked();
			defender.damaged(attacker.pwr);
			System.out.println("HIT! HP remaining: " + defender.getHp());
			return true;
		}
		attacker.hasAttacked();
		System.out.println("MISS! HP remaining: " + defender.getHp());
		return false;
	}
	
	/**
	 * Calculates the hit chance of the attacking Unit
	 * @param board     the current state of the board
	 * @param attacker  the attacking Unit
	 * @param defender  the defending Unit
	 * @return 			double with 0 < value < 1 
	 */
	public static double hitChance(Board board, Unit attacker, Unit defender) {
		int aws = attacker.att;
		int dws = defender.att;
		
		// Get surrounding units and calculate final aws
		ArrayList<Unit> surrounding = board.getSurroundingUnits(attacker.getPosition(), 
																attacker.rng);
		for (Unit u : surrounding) {
			if (attacker.race == u.race) {
				aws += u.sup;
			} else {
				aws -= u.sup;
			}
		}
		// Return P(hit)
		return 1.0 / (1.0 + Math.exp(-0.4 * (aws - dws)));
	}
	
	/**
	 * Moves unit to new Tile, updates all positioning accordingly.
	 * @param unit  the Unit to be moved
	 * @param goal  the Tile the unit is moving to
	 */
	public static void move(Board board, Unit unit, Tile goal) {
		Tile current = unit.getPosition();
		if (!current.empty()) {
			// AI already empties and fills tiles
			current.removeUnit();
			goal.fill(unit);
		}
		unit.moveTo(goal);
		unit.setDirection(findNewDirection(board.getDimension(), current, goal));
		if (!unit.anyAttackable(board)) {
			unit.hasAttacked();
		}
		unit.hasMoved();
	}
	
	public static int findNewDirection(int dim, Tile current, Tile goal) {
		int curCol = current.getCol();
		int curRow = current.getRow();
		int goalCol = goal.getCol();
		int goalRow = goal.getRow();
		// If we're moving right
		if (curCol < goalCol) {
			if (curCol < dim-1) {
				if (curRow == goalRow) {
					return Unit.FACE_UR;
				} else {
					return Unit.FACE_DR;
				}
			} else {
				if (curRow == goalRow) {
					return Unit.FACE_DR;
				} else {
					return Unit.FACE_UR;
				}
			}
		// If we're moving left
		} else if (curCol > goalCol) {
			if (curCol < dim) {
				if (curRow == goalRow) {
					return Unit.FACE_DL;
				} else {
					return Unit.FACE_UL;
				}
			} else {
				if (curRow == goalRow) {
					return Unit.FACE_UL;
				} else {
					return Unit.FACE_DL;
				}
			}
		// If we're moving vertical
		} else {
			if (curRow > goalRow) {
				return Unit.FACE_UP;
			} else {
				return Unit.FACE_DOWN;
			}
		}
	}
}
