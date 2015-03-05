package game.units;
/**
 * Class which represents a human soldier.
 */
public class HumanSoldier extends Unit {
	/**
	 * Constructor for human soldier.
	 */
	public HumanSoldier() {
		// Race, type, att, pwr, sup, hp, rng, spd
		super(0, 1, 6, 1, 1, 4, 1, 1);
		name = "Human Soldier";
	}
}