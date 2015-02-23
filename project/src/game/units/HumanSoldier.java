package game.units;
/**
 * Class which represents an orc soldier.
 */
public class HumanSoldier extends Unit {
	/*
	 * Stats of the unit.
	 */
	static final boolean IS_ORC = false;
	static final int ATT = 6;
	static final int PWR = 1;
	static final int HP = 4;
	static final int SUP = 1;
	static final int RNG = 1;
	static final int MV = 1;
	
	/**
	 * Constructor for human soldier.
	 */
	public HumanSoldier() {
		super(IS_ORC, ATT, PWR, HP, SUP, RNG, MV);
	}
}