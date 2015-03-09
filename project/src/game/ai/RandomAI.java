package game.ai;

import java.util.ArrayList;

public class RandomAI extends Ai {

	@Override
	public ArrayList<AIMove> doThinking() {
		System.out.println("think in random ai");
		
		ArrayList<AIMove> moves = new ArrayList<AIMove>();
		
		try {
			Thread.sleep(4000);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return moves;
	}

}
