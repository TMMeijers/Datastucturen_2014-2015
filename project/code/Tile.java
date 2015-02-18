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
	 * Unit on the tile.
	 */
	private Unit unit;

	/**
	 * The height of the tile.
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
	 * Constructor which specifies column and row, other values are default.
	 * @param  col column for this tile
	 * @param  row row for this tile
	 */
	public Tile(int col, int row) {
		this.col = col;
		this.row = row;
	}

	/**
	 * [tile description]
	 * @param  col  [description]
	 * @param  row  [description]
	 * @param  type [description]
	 */
	public Tile(int col, int row, String type) throws IllegalArgumentException {
		this.col = col;
		this.row = row;
		this.type = type;

		// Maybe change this, first thought about abstract class Tile, and child classes
		// GrassTile, WoodTile etc. But too difficult for only things that differ are three
		// variables?
		switch (type) {
			case "grass":
				height = 0;
				bonus = 0;
			case "wood":
				height = 0;
				bonus = 1;
			case "swamp":
				height = 0;
				bonus = -1;
			case "boulder":
				height = 1;
				bonus = 1;
			default:
				throw new IllegalArgumentException("Invalid tile type: " + type);
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
	 * Checks if the tile is empty
	 * @return true of tile doesn't contain a unit, false otherwise
	 */
	public boolean empty() {
		return unit == null;
	}

	public String toString() {
		return "type";
	}
}