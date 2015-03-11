package game.ai;

//import game.LegendsOfArborea;
import game.Helpers;
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
		
		ArrayList<AIMove> moves = new ArrayList<AIMove>();
		ArrayList<Tile> takenTiles = new ArrayList<Tile>();
		
		for (Unit u : this.computerPlayer.get().GetUnits()) {
			// get Tile unit is standing on
			Tile t = u.getPosition();
			if (t != null) {
				ArrayList<Tile> attackableTiles = 
						LegendsOfArborea.GAME.board.getSurroundingEnemyTiles(t, u.rng, u.race);
				ArrayList<Tile> reachableTiles = 
						LegendsOfArborea.GAME.board.getSurroundingEmptyTiles(t, u.spd);
				for (Tile taken : takenTiles) {
					reachableTiles.remove(taken);
				}
				int attackableTileSize = attackableTiles.size();
				int reachableTileSize = reachableTiles.size();
				
				int max = attackableTileSize + reachableTileSize - 1;
				
				AIMove.TYPE mt = null;
				
				if (max > 0) {
					int targetTileIndex = Helpers.randInt(0, max);
					Tile targetTile = null;
					if (targetTileIndex < attackableTileSize) {
						targetTile = attackableTiles.get(targetTileIndex);
						mt = AIMove.TYPE.ATTACK;
					} else {
						mt = AIMove.TYPE.MOVE;
						targetTile = reachableTiles.get(Math.abs(targetTileIndex - attackableTileSize));
					}
					if (targetTile != null && mt != null) {
						moves.add(new AIMove(targetTile, u, mt, attackableTiles, reachableTiles));
						takenTiles.add(targetTile);
					}
				}
			}
		}
		return moves;
	}

}
