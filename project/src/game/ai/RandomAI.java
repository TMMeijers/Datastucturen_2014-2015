package game.ai;

//import game.LegendsOfArborea;
import game.LegendsOfArborea;
import game.board.Tile;
import game.players.ComputerPlayer;
import game.units.Unit;

import java.util.ArrayList;

public class RandomAI extends Ai {

	public RandomAI(ComputerPlayer cp) {
		super(cp);
	}

	@Override
	public ArrayList<AIMove> doThinking() {
		System.out.println("think in random ai");
		
		ArrayList<AIMove> moves = new ArrayList<AIMove>();
		
		for (Unit u : this.computerPlayer.get().GetUnits()) {
			// get Tile unit is standing on
			Tile t = u.getPosition();
			if (t != null) {
				ArrayList<Tile> attackableTiles = 
						LegendsOfArborea.GAME.board.getSurroundingEnemyTiles(t, u.rng, u.race);
				ArrayList<Tile> reachableTiles = 
						LegendsOfArborea.GAME.board.getSurroundingEmptyTiles(t, u.spd);
				
				// do something with it
			}
		}
		
		return moves;
	}

}
