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
		if (Math.random() < hitChance(board, attacker, defender)) {
			defender.damaged(attacker.pwr);
			attacker.hasAttacked();
			return true;
		}
		attacker.hasAttacked();
		return false;
	}
	
	/**
	 * Calculates the hit chance of the attacking Unit
	 * @param board     the current state of the board
	 * @param attacker  the attacking Unit
	 * @param defender  the defending Unit
	 * @return 			double with 0 < value < 1 
	 */
	private static double hitChance(Board board, Unit attacker, Unit defender) {
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
	public static void move(Unit unit, Tile goal) {
		unit.getPosition().removeUnit();
		unit.moveTo(goal);
		goal.fill(unit);
	}
}
