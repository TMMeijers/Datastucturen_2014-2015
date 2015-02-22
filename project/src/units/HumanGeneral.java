package units;
/**
 * Class which represents an orc soldier.
 */
public class HumanGeneral extends Unit {
	/**
	 * Attack stat of the Unit.
	 */
	static final int ATT = 8;

	/**
	 * Health stat of the Unit.
	 */
	static final int HP = 5;

	/**
	 * Support stat of the Unit.
	 */
	static final int SUP = 2;

	/**
	 * Ranged stat of the Unit.
	 */
	static final int RNG = 1;
	
	/**
	 * Movement stat of the Unit.
	 */
	static final int MV = 1;
	
	/**
	 * Constructor for human general.
	 */
	public HumanGeneral() {
		super(ATT, HP, SUP, RNG, MV);
	}

}