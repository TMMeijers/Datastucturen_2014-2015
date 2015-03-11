package game.ai;

import game.players.ComputerPlayer;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class Ai {
	
	private boolean isThinking;
	// weak ref to avoid leaking
	protected WeakReference<ComputerPlayer> computerPlayer;
	
	public Ai(ComputerPlayer cp) {
		this.isThinking = false;
		this.computerPlayer = new WeakReference<ComputerPlayer>(cp);
	}
	
	public ArrayList<AIMove> doThinking() {
		// nothing in here for generic ai class
		return new ArrayList<AIMove>();
	}
	
	public void think(IThinking thinkingDoneCallback) {
		// TODO Auto-generated method stub
		if (this.isThinking) {
			// return if we still think
			return;
		}
		
		this.isThinking = true;
		
		// if not start Thread
		Runnable thinkingTask =() -> {
			// think
			ArrayList<AIMove> moves = doThinking();
			// done with thinking. call callback
			thinkingDoneCallback.thinkingDone(moves);
			this.isThinking = false;
		};
		
		new Thread(thinkingTask).start();
		
	}
}
