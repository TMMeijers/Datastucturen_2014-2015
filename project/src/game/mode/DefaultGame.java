package game.mode;

public class DefaultGame extends GameMode {

	/**
	 * Array holding values for all orc units.
	 */
	private static final int[] ORC_UNITS = {8, 2};
	
	/**
	 * Positions for the orcs in this setup.
	 */
	private static final int[][] ORC_POSITIONS = {{0,1},{1,1},{1,2},{1,3},{1,4},{1,5},{2,1},{2,6}, // Soldiers
										  		  {0,4},{1,0}};									   // Generals
	
	/**
	 * Array holding values for all human units.
	 */
	private static final int[] HUMAN_UNITS = {6, 3};
	
	/**
	 * Positions for the humans in this setup.
	 */
	private static final int[][] HUMAN_POSITIONS = {{7,0},{7,1},{7,2},{7,3},{7,4},{8,1}, // Soldiers
										    		{7,5},{8,0},{8,3}};					 // Generals
	
	/**
	 * Size of the board
	 */
	private static final int SIZE = 5;
	
	/**
	 * Constructor for a game with default setup. Human versus computer.
	 * @param nameP1 the name of player 1
	 * @param orcP1  true if player 1 plays orc, false if human
	 */
	public DefaultGame(String nameP1, boolean orcP1) {
		super(SIZE);
		initPlayers(nameP1, orcP1);
		initUnits(ORC_UNITS, HUMAN_UNITS);
		placeUnits(ORC_POSITIONS, HUMAN_POSITIONS);
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
		placeUnits(ORC_POSITIONS, HUMAN_POSITIONS);
	}
}
