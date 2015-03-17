package game.players;

import game.ai.AgressiveAi;
import game.ai.Ai;
import game.ai.RandomAi;

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
		ai = new RandomAi(this);
	}
	
	public ComputerPlayer(boolean orc, Player other) {
		super("1337AI", orc);
		ai = new AgressiveAi(this, other);
	}
	
	public Ai getAI() {
		return ai;
	}
}
