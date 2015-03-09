package game.ai;

import java.util.ArrayList;


public class Ai {
	
	private boolean isThinking;
	
	public Ai() {
		this.isThinking = false;
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
		System.out.println("Start thinking");
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
