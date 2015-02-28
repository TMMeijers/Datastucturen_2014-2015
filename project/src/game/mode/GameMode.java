package game.mode;

import game.board.Board;
import game.board.Tile;
import game.players.*;
import game.units.*;

public abstract class GameMode {
	
	/**
	 * Array holding the players
	 */
	private Player[] players;
	
	/**
	 * Boolean for finished game state or not.
	 */
	private boolean finished;

	/**
	 * The board of this specific game.
	 */
	public final Board board;


	public GameMode(int size) {
		players = new Player[2];
		board = new Board(size);
		finished = false;
	}

	/**
	 * Initializes the players, adds race and names. Makes a human and a computer player
	 * @param nameP1 name of human player 1
	 * @param raceP1 race of human player 1
	 * @param raceP2 race of computer player 2
	 */
	public void initPlayers(String nameP1, boolean orcP1) {
		players[0] = new HumanPlayer(nameP1, orcP1);
		players[1] = new ComputerPlayer(!orcP1);
	}
	
	/**
	 * Initializes the players, adds race and names. Makes two human players
	 * @param nameP1 name of human player 1
	 * @param raceP1 race of human player 1
	 * @param nameP2 name of human player 2
	 * @param raceP2 race of human player 2
	 */
	public void initPlayers(String nameP1, String nameP2, boolean orcP1) {
		players[0] = new HumanPlayer(nameP1, orcP1);
		players[1] = new HumanPlayer(nameP2, !orcP1);		
	}
	
	/**
	 * Returns player based on index passed.
	 * @param i index for player, 1 or 2
	 * @return  Player 1 or 2
	 */
	public Player getPlayer(int i) {
		return players[i - 1];
	}
	
	/**
	 * Initializes all the units for the players
	 * @param orcUnits   array holding number of units to be added for player playing orc
	 * @param humanUnits array holding number of units to be added for player playing human
	 */
	public void initUnits(int[] orcUnits, int[] humanUnits) {
		int orc = 2;
		int human = 1;
		if (getPlayer(1).playsOrc()) {
			orc = 1;
			human = 2;
		}
		for (int i = 0; i < orcUnits[0]; i++) {
			getPlayer(orc).addUnit(new OrcSoldier());
		}
		for (int i = 0; i < orcUnits[1]; i++) {
			getPlayer(orc).addUnit(new OrcGeneral());
		}
		for (int i = 0; i < humanUnits[0]; i++) {
			getPlayer(human).addUnit(new HumanSoldier());
		}
		for (int i = 0; i < humanUnits[1]; i++) {
			getPlayer(human).addUnit(new HumanGeneral());
		}	
	}

	/**
	 * Places the units on the board according to the positions passed
	 * as arguments. 
	 * @param orcPositions    integer array with [Unit][col, row] for orcs
	 * @param humanPositions  integer array with [Unit][col, row] for humans
	 */
	public void placeUnits(int[][] orcPositions, int[][] humanPositions) {
		int orc = 2;
		int human = 1;
		if (getPlayer(1).playsOrc()) {
			orc = 1;
			human = 2;
		}
		Unit u;
		Tile t;
		
		// Loop through orc and human units and place on field
		for (int i = 0; i < getPlayer(orc).unitsAlive(); i++) {
			u = getPlayer(orc).getUnit(i);
			t = board.getTile(orcPositions[i][0], orcPositions[i][1]);
			u.moveTo(t);
			t.fill(u);
		}
		for (int i = 0; i < getPlayer(human).unitsAlive(); i++) {
			u = getPlayer(human).getUnit(i);
			t = board.getTile(humanPositions[i][0], humanPositions[i][1]);
			u.moveTo(t);
			t.fill(u);
		}
	}
	
	/**
	 * Returns whether game is finished or not.
	 * @return true if finished, false otherwise
	 */
	public boolean isFinished() {
		return finished;
	}
	
	/**
	 * Sets game state to finished.
	 */
	public void gameOver() {
		finished = true;
	}
}