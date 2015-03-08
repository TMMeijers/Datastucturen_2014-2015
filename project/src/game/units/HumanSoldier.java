package game.units;
/**
 * Class which represents a human soldier.
 */
public class HumanSoldier extends Unit {
	
	public static final int DEATH_FRAMES = 3;
	
	/**
	 * Constructor for human soldier.
	 */
	public HumanSoldier() {
		// Race, type, att, pwr, sup, hp, rng, spd, death frames
		super(0, 1, 6, 1, 1, 4, 1, 1, DEATH_FRAMES);
		name = "Human Soldier";
	}
}