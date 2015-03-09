package game.units;

public class OrcSoldier extends Unit {
	
	public static final int DEATH_FRAMES = 3;
	
	/**
	 * Constructor for orc soldier.
	 */
	public OrcSoldier() {
		// Race, type, att, pwr, sup, hp, rng, spd, death frames
		super(1, 1, 4, 1, 1, 3, 1, 1, DIE_DURATION);
		name = "Orc Soldier";
	}
}