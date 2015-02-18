/**
 * Abstract class for all the units on the board.
 */
public abstract class Unit {

	/**
	 * Attack value of the unit.
	 */
	protected int attack;

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
	protected int move;

	/**
	 * Constructor for a Unit which sets its stats
	 * @param  attack    the attack weapon skill of the unit
	 * @param  hitpoints the hitpoints of the unit
	 * @param  support   the amount of weapon skill added as support
	 * @param  range     the range of the unit
	 * @param  move      the move speed of a unit
	 */
	public Unit(int attack, int hitpoints, int support, int range, int move) {
		this.attack = attack;
		this.hitpoints = hitpoints;
		this.support = support;
		this.range = range;
		this.move = move;
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

}