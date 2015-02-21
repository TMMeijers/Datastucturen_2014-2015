package board;

import units.Unit;

/**
 * Abstract class for the tiles on the board.
 */
public class Tile {

	/**
	 * Column the tile is in.
	 */
	private int col;

	/**
	 * Row the tile is in.
	 */
	private int row;

	/**
	 * Max row for the column the tile is in.
	 */
	private int maxRow;

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
	private String type;

	/**
	 * Constructor which specifies column and row, uses explicit constructor invocation
	 * to construct default field (a grass field).
	 * @param  col column for this tile
	 * @param  row row for this tile
	 */
	public Tile(int col, int row, int maxRow) {
		this(col, row, maxRow, "grass");
	}

	/**
	 * Constructor for a tile which specifies type, this hen defines the bonuses the field
	 * provides.
	 * @param  col  column for this tile
	 * @param  row  row for this tile
	 * @param  type the type of the field which determines the bonuses
	 */
	public Tile(int col, int row, int maxRow, String type) throws IllegalArgumentException {
		this.col = col;
		this.row = row;
		this.maxRow = maxRow;
		this.type = type;

		// Maybe change this, first thought about abstract class Tile, and child classes
		// GrassTile, WoodTile etc. But too difficult for only things that differ are three
		// variables?
		switch (type) {
			case "grass":
				height = 0;
				bonus = 0;
				break;
			case "wood":
				height = 0;
				bonus = 1;
				break;
			case "swamp":
				height = 0;
				bonus = -1;
				break;
			case "boulder":
				height = 1;
				bonus = 1;
				break;
			default:
				throw new IllegalArgumentException("Type: " + type + " doesn't exist.");
		}
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
		return true;
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
	 * Returns the maximum row possible for the column the tile is in
	 * @return the maximum row for the column the tile is in
	 */
	public int maxRow() {
		return maxRow;
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
	 * Returns the unit at the tile.
	 * @return the unit at the tile
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * Overrides toString from Object, prints the type of the unit
	 * @return the String containing type
	 */
	public String toString() {
		return type;
	}
}