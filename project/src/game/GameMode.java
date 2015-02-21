package game;

import players.*;
import board.*;

public abstract class GameMode {
	
	/**
	 * Array holding the players
	 */
	Player[] players;
	
	/**
	 * Boolean for finished game state or not.
	 */
	boolean finished;

	/**
	 * The board of this specific game.
	 */
	Board board;
	
	public GameMode() {
		players = new Player[2];
		finished = false;
	}
	
	public void initPlayers(String nameP1) {
		players[0] = new Player(nameP1);
	}
	
	public void initPlayers(String nameP1, String nameP2) {
		
	}
}