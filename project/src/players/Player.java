package players;

import java.util.ArrayList;
import units.Unit;

public abstract class Player {
	/**
	 * Array list holding all the units of the player
	 */
	ArrayList<Unit> units;

	/**
	 * Name of the player.
	 */
	String name;
	
	/**
	 * Default constructor passing default name.
	 */
	public Player() {
		this("DEFAULT");
	}
	
	/**
	 * Constructor for a player.
	 * @param name
	 */
	public Player(String name) {
		this.name = name;
		units = new ArrayList<Unit>();
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
	public boolean anyActive() {
		for (Unit u : units) {
			if (u.active()) {
				return true;
			}
		}
		return false;
	}
}
