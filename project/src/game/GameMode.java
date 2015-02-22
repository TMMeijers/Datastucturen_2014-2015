package game;

import players.*;
import units.*;
import board.Board;

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
		players[0] = new HumanPlayer(nameP2, !orcP1);		
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
		int[] p1Units;
		int[] p2Units;
		if (getPlayer(1).equals("Orc")) {
			p1Units = orcUnits;
			p2Units = humanUnits;
		} else {
			p1Units = humanUnits;
			p2Units = orcUnits;
		}
		for (int i = 0; i < p1Units[0]; i++) {
			getPlayer(1).addUnit(new OrcSoldier());
		}
		for (int i = 0; i < p1Units[1]; i++) {
			getPlayer(1).addUnit(new OrcGeneral());
		}
		for (int i = 0; i < p2Units[0]; i++) {
			getPlayer(2).addUnit(new HumanSoldier());
		}
		for (int i = 0; i < p2Units[1]; i++) {
			getPlayer(2).addUnit(new HumanGeneral());
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