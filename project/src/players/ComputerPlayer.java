package players;

import ai.Ai;

public class ComputerPlayer extends Player {
	/**
	 * The ai module of the computer player
	 */
	Ai ai;
	
	public ComputerPlayer() {
		super("1337AI");
		ai = new Ai();
	}
}
