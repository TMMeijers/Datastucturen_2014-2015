package game.units;
/**
 * Class which represents an orc soldier.
 */
public class OrcSoldier extends Unit {
	/*
	 * Stats of the unit.
	 */
	static final boolean IS_ORC = true;
	static final int ATT = 4;
	static final int PWR = 1;
	static final int HP = 3;
	static final int SUP = 1;
	static final int RNG = 1;
	static final int MV = 1;
	
	/**
	 * Constructor for orc soldier.
	 */
	public OrcSoldier() {
		super(IS_ORC, ATT, PWR, HP, SUP, RNG, MV);
	}
}