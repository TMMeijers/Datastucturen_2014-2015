package game.units;
/**
 * Class which represents an orc soldier.
 */
public class HumanGeneral extends Unit {
	/*
	 * Stats of the unit.
	 */
	static final boolean IS_ORC = false;
	static final int ATT = 8;
	static final int PWR = 1;
	static final int HP = 5;
	static final int SUP = 2;
	static final int RNG = 1;
	static final int MV = 1;
	
	/**
	 * Constructor for human general.
	 */
	public HumanGeneral() {
		super(IS_ORC, ATT, PWR, HP, SUP, RNG, MV);
	}

}