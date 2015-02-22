package units;
/**
 * Class which represents an orc soldier.
 */
public class OrcSoldier extends Unit {
	/**
	 * Attack stat of the Unit.
	 */
	static final int ATT = 4;

	/**
	 * Health stat of the Unit.
	 */
	static final int HP = 3;

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
	 * Constructor for orc soldier.
	 */
	public OrcSoldier() {
		super(ATT, HP, SUP, RNG, MV);
	}
}