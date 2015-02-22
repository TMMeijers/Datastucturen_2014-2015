package units;
/**
 * Class which represents an orc soldier.
 */
public class HumanSoldier extends Unit {
	/**
	 * Attack stat of the Unit.
	 */
	static final int ATT = 6;

	/**
	 * Health stat of the Unit.
	 */
	static final int HP = 4;

	/**
	 * Support stat of the Unit.
	 */
	static final int SUP = 1;

	/**
	 * Ranged stat of the Unit.
	 */
	static final int RNG = 1;
	
	/**
	 * Movement stat of the Unit.
	 */
	static final int MV = 1;
	
	/**
	 * Constructor for human soldier.
	 */
	public HumanSoldier() {
		super(ATT, HP, SUP, RNG, MV);
	}
}