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
	 * Value for support lended to attacking/attacked adjecent units.
	 */
	protected int support;

	public Unit(int attack, int hitpoints, int support) {
		this.attack = attack;
		this.hitpoints = hitpoints;
		this.support = support;
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