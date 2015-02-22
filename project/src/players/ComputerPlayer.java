package players;

import ai.Ai;

public class ComputerPlayer extends Player {
	/**
	 * The ai module of the computer player
	 */
	Ai ai;

	/**
	 * Constructor for a computer player specifying only race.
	 * @param orc  true if player is going to play orc, false if human
	 */
	public ComputerPlayer(boolean orc) {
		super("1337AI", orc);
		ai = new Ai(); // Doesn't do anything yet
	}
}
