package game.units;

import game.board.Tile;

/**
 * Abstract class for all the units on the board.
 */
public abstract class Unit {
	
	/**
	 * Race of the unit, true for orc, false for human.
	 */
	protected boolean race;

	/**
	 * Attack value of the unit.
	 */
	protected int attack;
	
	/**
	 * Damage value of the unit.
	 */
	protected int power;

	/**
	 * The hitpoints of the unit.
	 */
	protected int hitpoints;

	/**
	 * The range of the unit.
	 */
	protected int range;

	/**
	 * Value for support lended to attacking/attacked adjecent units.
	 */
	protected int support;

	/**
	 * Maximum tiles a unit can move.
	 */
	protected int speed;
	
	/**
	 * If unit can still move.
	 */
	protected boolean activeMove;
	
	/**
	 * If unit can still attack.
	 */
	protected boolean activeAttack;
	
	/**
	 * The position of the Unit.
	 */
	protected Tile position;
	
	/**
	 * Constructor for a Unit which sets its stats.
	 * @param  race 	 the race of the unit, true for orc, false for human
	 * @param  attack    the attack weapon skill of the unit
	 * @param  hitpoints the hitpoints of the unit
	 * @param  support   the amount of weapon skill added as support
	 * @param  range     the range of the unit
	 * @param  speed     the movement speed of a unit
	 */
	public Unit(boolean race, int attack, int power, int hitpoints, int support, int range, int speed) {
		this.race = race;
		this.attack = attack;
		this.power = power;
		this.hitpoints = hitpoints;
		this.support = support;
		this.range = range;
		this.speed = speed;
		activeMove = false;
		activeAttack = false;
		position = null;
	}
	
	/**
	 * Sets the position for the Unit.
	 * @param tile the new position
	 */
	public void moveTo(Tile tile) {
		position = tile;
	}
	
	/**
	 * Returns the race of the unit.
	 * @return true for orc, false for human
	 */
	public boolean getRace() {
		return race;
	}
	
	/**
	 * Returns the attack stat of the Unit.
	 * @return the attack stat
	 */
	public int getAttack() {
		return attack;
	}
	
	/**
	 * Returns the power stat of the Unit.
	 * @return the power that
	 */
	public int getPower() {
		return power;
	}
	
	/**
	 * Returns the health stat of the Unit.
	 * @return the health stat
	 */
	public int getHitpoints() {
		return hitpoints;
	}
	
	/**
	 * Returns the support stat of the Unit.
	 * @return the support stat
	 */
	public int getSupport() {
		return support;
	}
	
	/**
	 * Returns the range stat of the Unit.
	 * @return the range stat
	 */
	public int getRange() {
		return range;
	}
	
	/**
	 * Returns the movement speed stat of the Unit.
	 * @return the movement speed stat
	 */
	public int getSpeed() {
		return speed;
	}
	
	/**
	 * Returns the position of the Unit.
	 * @return the Tile the unit is positioned on
	 */
	public Tile getPosition() {
		return position;
	}

	/**
	 * When unit gets damaged, subtract a hitpoint.
	 * @param  dmg the damage taken by the unit
	 * @return     true if the unit gets killed by the damage, false otherwise
	 */
	public boolean damaged(int dmg) {
		hitpoints -= dmg;
		if (hitpoints < 1) {
			// Unit has 0 hp, is killed
			return true;
		}
		return false;
	}
	
	public void activate() {
		activeMove = true;
		activeAttack = true;
	}
	
	/**
	 * Checks if Unit can still make an attack.
	 * @return true if Unit can still attack, false otherwise
	 */
	public boolean canAttack() {
		return activeAttack;
	}
	
	/**
	 * Sets attack status to inactive.
	 */
	public void hasAttacked() {
		activeAttack = true;
	}

	
	/**
	 * Checks if Unit can still move
	 * @return true if Unit can still make a move, false otherwise
	 */
	public boolean canMove() {
		return activeMove;
	}
	
	/**
	 * Sets move status to inactive.
	 */
	public void hasMoved() {
		activeMove = true;
	}

}