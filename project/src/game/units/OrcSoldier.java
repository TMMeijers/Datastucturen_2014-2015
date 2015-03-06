package game.units;

public class OrcSoldier extends Unit {
	
	public static final int DIE_DURATION = 6;
	
	/**
	 * Constructor for orc soldier.
	 */
	public OrcSoldier() {
		// Race, type, att, pwr, sup, hp, rng, spd
		super(1, 1, 4, 1, 1, 3, 1, 1);
		name = "Orc Soldier";
	}
}