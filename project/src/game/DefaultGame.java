package game;

public class DefaultGame extends GameMode {

	/**
	 * Array holding values for all orc units.
	 */
	static final int[] ORC_UNITS = {8, 2};
	
	/**
	 * Array holding values for all human units.
	 */
	static final int[] HUMAN_UNITS = {6, 3};
	
	/**
	 * Size of the board
	 */
	static final int SIZE = 5;
	
	/**
	 * Constructor for a game with default setup. Human versus computer.
	 * @param nameP1 the name of player 1
	 * @param orcP1  true if player 1 plays orc, false if human
	 */
	public DefaultGame(String nameP1, boolean orcP1) {
		super(SIZE);
		initPlayers(nameP1, orcP1);
	}
	
	/**
	 * Constructor for a game with default setup. Human vs Human.
	 * @param nameP1 the name of player 1
	 * @param nameP2 the name of player 2
	 * @param orcP1  true if player 1 plays orc, false if human
	 */
	public DefaultGame(String nameP1, String nameP2, boolean orcP1) {
		super(SIZE);
		initPlayers(nameP1, nameP2, orcP1);
		initUnits(ORC_UNITS, HUMAN_UNITS);
	}
}
