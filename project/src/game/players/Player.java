package game.players;

import game.board.Board;
import game.units.Unit;

import java.util.ArrayList;

public abstract class Player {
	/**
	 * Array list holding all the units of the player
	 */
	private ArrayList<Unit> units;

	/**
	 * Name of the player.
	 */
	private String name;
	
	/**
	 * The side the Player is playing, orc = true, human = false.
	 */
	private boolean orc;
	
	/**
	 * Constructor for player specifying both name and race
	 * @param name the name of the player
	 * @param race the race of the player
	 */
	public Player(String name, boolean orc) {
		this.name = name;
		this.orc = orc;
		units = new ArrayList<Unit>();
	}
	
	/**
	 * Initializes this players turn, setting all units move and attack
	 * status to active.
	 */
	public void initTurn() {
		for (Unit u : units) {
			u.activate();
		}
	}
	
	public void endTurn() {
		for (Unit u : units) {
			u.deactivate();
		}
	}
	
	public Unit getUnit(int index) {
		return units.get(index);
	}
	
	public ArrayList<Unit> GetUnits() {
		return units;
	}
	
	public int unitsAlive() {
		return units.size();
	}
	
	public boolean anyAlive() {
		return unitsAlive() != 0;
	}
	
	/**
	 * Adds the specified unit to the players units.
	 * @param unit the unit to be added to this players units
	 */
	public void addUnit(Unit unit) {
		units.add(unit);
	}
	
	/**
	 * Removes the specified unit from the players units.
	 * @param unit the unit to be removed from this players units
	 */
	public void removeUnit(Unit unit) {
		units.remove(unit);
	}
	
	/**
	 * Checks whether the player has any units that can still move or attack.
	 * @return true if Player has any unit that can still move or attack
	 */
	public boolean anyActive(Board board) {
		for (Unit u : units) {
			if (u.canMove()) {
				return true;
			}
		}
		for (Unit u : units) {
			if (u.canAttack() && u.anyAttackable(board)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the race of the player.
	 * @return the race of the player
	 */
	public boolean playsOrc() {
		return orc;
	}
	
	public String getName() {
		return name;
	}
}
