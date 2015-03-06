package game.units;

public class OrcGeneral extends Unit {
	
	public static final int DIE_DURATION = 4;
	
	/**
	 * Constructor for orc general.
	 */
	public OrcGeneral() {
		// Race, type, att, pwr, sup, hp, rng, spd
		super(1, 0, 8, 1, 2, 10, 1, 1);
		name = "Orc General";
	}
}