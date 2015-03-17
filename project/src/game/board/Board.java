package game.board;

import game.units.Unit;

import java.util.ArrayList;

/**
 * 
 */
public class Board {

	/**
	 * Two dimensional array with all the tiles, first dimension are columns second the tile
	 */
	private Tile[][] tiles;
	
	/**
	 * The dimension of the board.
	 */
	private int dimension;

	/**
	 * Generates the default board (dimensions: 5 x 5 x 5)
	 */
	public Board() {
		this(5);
	}

	/**
	 * Generates the board with specified dimensions.
	 * @param  dimension the dimension of the board (dim x dim x dim)
	 */
	public Board(int dimension) {
		this.dimension = dimension;
		int totalCols = (dimension * 2) - 1;
		tiles = new Tile[totalCols][];
		int length;
		
		for (int i = 0; i < totalCols; i++) {
			if (i < dimension) {
				length = dimension + i;
				tiles[i] = new Tile[length];
				for (int j = 0; j < length; j++) {
					// Columns with increasing tile count
					tiles[i][j] = new Tile(i, j);
				}
			} else {
				length = -i % dimension + 2 * dimension - 2;
				tiles[i] = new Tile[length];
				for (int j = 0; j < length; j++) {
					// From the middle column start decreasing size
					tiles[i][j] = new Tile(i, j);
				}
			}
		}
	}

	/**
	 * Checks if goal Tile is empty and within reach.
	 * @param  start the tile the unit is standing on
	 * @param  goal  the tile the units wants to move to
	 * @return       true if within reach and empty, false otherwise
	 */
	public boolean legalMove(Tile start, Tile goal) {
		// Goal tile needs to be empty
		if (!goal.empty()) {
			return false;
		}
		// Goal tile needs to be on the board
		if (!tileOnBoard(goal.getCol(), goal.getRow())) {
			return false;
		}
		// Cannot move more than 1 column
		if (Math.abs(start.getCol() - goal.getCol()) > 1 ||
			Math.abs(start.getRow() - goal.getRow()) > 1) {
			return false;
		}
		return true;
	}
	
	// Returns all tiles around a certain tile
	public ArrayList<Tile> getSurroundingTiles(Tile tile, int speed) {
		int col = tile.getCol();
		int row = tile.getRow();
		ArrayList<Tile> surroundingTiles = new ArrayList<Tile>();
		// Go through all surrounding tiles
		for (int i = -speed; i < speed + 1; i++) {
			for (int j = -speed; j < speed + 1; j++) {
				// Don't add tiles out of reach
				if (col < dimension-speed && ((i == -speed && j == speed) || i == speed && j == -speed)) {
					continue;
				} else if (col > dimension-speed && ((i == speed && j == speed) || i == -speed && j == -speed)) {
					continue;
				} else if (col == dimension-speed && j == speed && (i == -speed || i == speed)) {
					continue;
				}
				// If on board add to list, don't add self
				if (tileOnBoard(col+i, row+j) && tile != getTile(col+i, row+j)) {
					surroundingTiles.add(getTile(col+i, row+j));
				}
			}
		}
		return surroundingTiles;
	}

	
	/**
	 * Returns all the Tiles surrounding this tile and are empty
	 * @param tile the tile for which the surrounding empty tiles are obtained
	 * @return ArrayList with all the surrounding empty tiles
	 */
	public ArrayList<Tile> getSurroundingEmptyTiles(Tile tile, int speed) {
		int col = tile.getCol();
		int row = tile.getRow();
		ArrayList<Tile> surroundingEmptyTiles = new ArrayList<Tile>();
		// Go through all surrounding tiles
		for (int i = -speed; i < speed + 1; i++) {
			for (int j = -speed; j < speed + 1; j++) {
				// Don't add tiles out of reach
				if (col < dimension-speed && ((i == -speed && j == speed) || i == speed && j == -speed)) {
					continue;
				} else if (col > dimension-speed && ((i == speed && j == speed) || i == -speed && j == -speed)) {
					continue;
				} else if (col == dimension-speed && j == speed && (i == -speed || i == speed)) {
					continue;
				}
				// If on board and empty add to list, don't add self
				if (tileOnBoard(col+i, row+j) && getTile(col+i, row+j).empty() &&
					tile != getTile(col+i, row+j)) {
						surroundingEmptyTiles.add(getTile(col+i, row+j));
				}
			}
		}
		return surroundingEmptyTiles;
	}
	
	/**
	 * Returns all the Tiles surrounding this tile and have a unit
	 * @param tile the tile for which the surrounding filled tiles are obtained
	 * @return ArrayList with all the surrounding filled tiles
	 */
	public ArrayList<Tile> getSurroundingUnitTiles(Tile tile, int range) {
		int col = tile.getCol();
		int row = tile.getRow();
		ArrayList<Tile> surroundingUnitTiles = new ArrayList<Tile>();
		// Go through all surrounding tiles
		for (int i = -range; i < range + 1; i++) {
			for (int j = -range; j < range + 1; j++) {
				// Don't add tiles out of reach
				if (col < dimension-range && ((i == -range && j == range) || i == range && j == -range)) {
					continue;
				} else if (col > dimension-range && ((i == range && j == range) || i == -range && j == -range)) {
					continue;
				} else if (col == dimension-range && j == range && (i == -range || i == range)) {
					continue;
				}
				// If on board and not empty add to list, don't add self
				if (tileOnBoard(col+i, row+j) && !getTile(col+i, row+j).empty() &&
					tile != getTile(col+i, row+j)) {
						surroundingUnitTiles.add(getTile(col+i, row+j));
				}
			}
		}
		return surroundingUnitTiles;
	}
	
	// Returns all surrounding tiles which contain an enemy
	public ArrayList<Tile> getSurroundingEnemyTiles(Tile tile, int range, int race) {
		int col = tile.getCol();
		int row = tile.getRow();
		ArrayList<Tile> surroundingUnitTiles = new ArrayList<Tile>();
		// Go through all surrounding tiles
		for (int i = -range; i < range + 1; i++) {
			for (int j = -range; j < range + 1; j++) {
				// Don't add tiles out of reach
				if (col < dimension-range && ((i == -range && j == range) || i == range && j == -range)) {
					continue;
				} else if (col > dimension-range && ((i == range && j == range) || i == -range && j == -range)) {
					continue;
				} else if (col == dimension-range && j == range && (i == -range || i == range)) {
					continue;
				}
				// If on board and tile contains enemy unit add, don't add self
				if (tileOnBoard(col+i, row+j) && !getTile(col+i, row+j).empty() &&
					tile != getTile(col+i, row+j) && getTile(col+i, row+j).getUnit().race != race) {
						surroundingUnitTiles.add(getTile(col+i, row+j));
				}
			}
		}
		return surroundingUnitTiles;
	}
	
	/**
	 * Returns all units surrounding a specific tile
	 * @param tile the tile for which the surrounding units are obtained
	 * @return 	   ArrayList with all the surrounding units
	 */
	public ArrayList<Unit> getSurroundingUnits(Tile tile, int range) {
		ArrayList<Tile> surroundingTiles = getSurroundingUnitTiles(tile, range);
		ArrayList<Unit> surroundingUnits = new ArrayList<Unit>();
		// Loop through tiles and add units
		for (Tile t : surroundingTiles) {
			surroundingUnits.add(t.getUnit());
		}
		return surroundingUnits;
	}
	
	// Returns all enemies surrounding a tile, calls other method which relies on race as an int
	public ArrayList<Unit> getSurroundingEnemies(Tile tile, int range, boolean race) {
		if (race) {
			return getSurroundingEnemies(tile, range, 1);
		}
		return getSurroundingEnemies(tile, range, 0);
	}
	
	// Returns all enemies surroudning a tile.
	public ArrayList<Unit> getSurroundingEnemies(Tile tile, int range, int race) {
		ArrayList<Tile> surroundingTiles = getSurroundingUnitTiles(tile, range);
		ArrayList<Unit> surroundingUnits = new ArrayList<Unit>();
		// Loop through tiles and add units
		for (Tile t : surroundingTiles) {
			if (t.getUnit().race != race) {
				surroundingUnits.add(t.getUnit());
			}
		}
		return surroundingUnits;
	}
	
	/**
	 * Checks whether a given column and row correspond to a tile on the board
	 * @param col the column of the tile
	 * @param row the row of the tile
	 * @return true if tile is on the board, false otherwise
	 */
	public boolean tileOnBoard(int col, int row) {
		if (col < 0 || row < 0 || col >= tiles.length || row >= tiles[col].length) {
			return false;
		}
		return true;
	}

	/**
	 * Returns the tile corresponding to col and row. Always call tileOnBoard(col, row)
	 * before calling this method.
	 * @param col the column of the tile
	 * @param row the row of the tile
	 * @return the corresponding Tile
	 */
	public Tile getTile(int col, int row)  {
		if (tileOnBoard(col, row)) {
			return tiles[col][row];
		}
		return null;
	}
	
	/**
	 * Returns the dimension of the board
	 * @return the dimension of the board
	 */
	public int getDimension() {
		return dimension;
	}
	
	/**
	 * Returns the number of Tiles in the specified column.
	 * @param col the specified column
	 * @return    the number of tiles in the specified column
	 */
	public int getColLength(int col) {
		return tiles[col].length;
	}
	
	// Returns all tiles
	public Tile[][] getTiles() {
		return tiles;
	}
}