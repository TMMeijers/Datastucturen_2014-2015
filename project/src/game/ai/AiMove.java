package game.ai;

import game.board.Tile;
import game.units.Unit;

import java.util.ArrayList;

public class AiMove {
	public enum TYPE {
		ATTACK,
		MOVE
	}
	
	public Tile tile;
	public Unit unit;
	public TYPE type;
	public ArrayList<Tile> attackableTiles;
	public ArrayList<Tile> reachableTiles;
	
	public AiMove(Tile t, Unit u, TYPE mt, ArrayList<Tile> attackableTiles, ArrayList<Tile> reachableTiles) {
		this.tile = t;
		this.unit = u;
		this.type = mt;
		this.attackableTiles = attackableTiles;
		this.reachableTiles = reachableTiles;
	}
}