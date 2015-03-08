package game.units;

public class HumanGeneral extends Unit {
	
	public static final int DEATH_FRAMES = 5;
	
	/**
	 * Constructor for human general.
	 */
	public HumanGeneral() {
		// Race, type, att, pwr, sup, hp, rng, spd, death frames
		super(0, 0, 8, 1, 2, 5, 1, 1, DEATH_FRAMES);
		name = "Human General";
	}

}