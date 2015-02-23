package game.units;
/**
 * Class which represents an orc soldier.
 */
public class OrcGeneral extends Unit {
	/*
	 * Stats of the unit.
	 */
	static final boolean IS_ORC = true;
	static final int ATT = 8;
	static final int PWR = 1;
	static final int HP = 10;
	static final int SUP = 2;
	static final int RNG = 1;
	static final int MV = 1;
	
	public OrcGeneral() {
		super(IS_ORC, ATT, PWR, HP, SUP, RNG, MV);
	}
}