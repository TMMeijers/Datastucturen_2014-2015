package game.board;

import game.units.Unit;

/**
 * Abstract class for the tiles on the board.
 */
public class Tile {
	
	public static final int DEFAULT = 0;
	public static final int ACTIVE = 1;
	public static final int REACHABLE = 2;
	public static final int ATTACKABLE = 3;
	

	/**
	 * Column the tile is in.
	 */
	private int col;

	/**
	 * Row the tile is in.
	 */
	private int row;

	/**
	 * Unit on the tile.
	 */
	private Unit unit;

	/**
	 * The height of the tile, provides bonuses to ranged units
	 */
	private int height;

	/**
	 * The bonus provided to AWS or DWS.
	 */
	private int bonus;

	/**
	 * The type of the tile (e.g. grass, forest).
	 */
	private String name;
	
	private int type;

	private int status;
	
	/**
	 * Boolean to indicate if tile is taken, added for AI so it doesn't have to modify the current state.
	 */
	private boolean taken;
	
	/**
	 * Constructor which specifies column and row, uses explicit constructor invocation
	 * to construct default field (a grass field).
	 * @param  col column for this tile
	 * @param  row row for this tile
	 */
	public Tile(int col, int row) {
		this(col, row, "grass");
	}

	/**
	 * Constructor for a tile which specifies type, this hen defines the bonuses the field
	 * provides.
	 * @param  col  column for this tile
	 * @param  row  row for this tile
	 * @param  type the type of the field which determines the bonuses
	 */
	public Tile(int col, int row, String name) throws IllegalArgumentException {
		this.col = col;
		this.row = row;
		this.name = name;
		taken = false;
		status = 0;

		// Maybe change this, first thought about abstract class Tile, and child classes
		// GrassTile, WoodTile etc. But too difficult for only things that differ are three
		// variables?
		switch (name) {
			case "grass":
				height = 0;
				bonus = 0;
				type = 0;
				break;
			case "wood":
				height = 0;
				bonus = 1;
				type = 1;
				break;
			case "swamp":
				height = 0;
				bonus = -1;
				type = 2;
				break;
			case "boulder":
				height = 1;
				bonus = 1;
				type = 3;
				break;
			default:
				throw new IllegalArgumentException("Type: " + name + " doesn't exist.");
		}
	}
	
	/**
	 * Returns the column of the tile
	 * @return the column
	 */
	public int getCol() {
		return col;
	}

	/**
	 * Returns the row of the tile
	 * @return the row
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Returns the height of the tile
	 * @return the height of the tile
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Returns the bonus of the tile
	 * @return the bonus of the tile
	 */
	public int getBonus() {
		return bonus;
	}

	/**
	 * Checks if the tile is empty
	 * @return true of tile doesn't contain a unit, false otherwise
	 */
	public boolean empty() {
		return unit == null;
	}

	/**
	 * Fills the tile with the specified unit.
	 * @param  unit the unit to be placed on the tile
	 * @return      false if the tile was occupied, true otherwise
	 */
	public boolean fill(Unit unit) {
		if (!empty()) {
			return false;
		}
		this.unit = unit;
		taken = true;
		return true;
	}

	/**
	 * Returns the unit at the tile.
	 * @return the unit at the tile
	 */
	public Unit getUnit() {
		return unit;
	}
	
	public boolean isTaken() {
		return taken;
	}
	
	public void switchTaken() {
		taken = !taken;
	}
	
	/**
	 * Removes the unit from the tile
	 */
	public void removeUnit() {
		taken = false;
		unit = null;
	}
	
	public int getType() {
		return type;
	}

	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public boolean adjecentTo(Tile t) {
		return (Math.abs(col - t.getCol()) <= 1 && Math.abs(row - t.getRow()) <= 1);
	}

	/**
	 * Overrides toString from Object, prints the type of the unit
	 * @return the String containing type
	 */
	public String toString() {
		return name;
	}
	
	/**
	 * Overrides the equals method.
	 */
	@Override
	public boolean equals(Object tile) {
        if (tile instanceof Tile){
            Tile other = (Tile) tile;
    	    if ((this.col == other.col && this.row == other.row)) {
    	        return true;
    	    }
        }
        return false;
	}
	
	/**
	 * Support boards up to dimension of 20 x 20 x 20
	 */
    @Override
    public int hashCode() {
        return 41 * col + 2 * row;
    }
}