package game.ai;

import game.board.Tile;
import game.units.Unit;

public class AIMove {
	public enum TYPE {
		ATTACK,
		MOVE
	}
	
	public Tile tile;
	public Unit unit;
	public TYPE type;
	
	public AIMove(Tile t, Unit u, TYPE mt) {
		this.tile = t;
		this.unit = u;
		this.type = mt;
	}
}
