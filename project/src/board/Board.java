package board;

import java.util.ArrayList;
import units.Unit;

/**
 * 
 */
public class Board {

	/**
	 * Two dimensional array with all the tiles, first dimension are columns second the tile
	 */
	private Tile[][] tiles;

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
	
	/**
	 * Returns all the Tiles surrounding tile
	 * @param tile the tile for which the surrounding tiles are obtained
	 * @return ArrayList with all the surrounding tiles
	 */
	public ArrayList<Tile> getSurroundingTiles(Tile tile, int range) {
		ArrayList<Tile> surroundingTiles = new ArrayList<Tile>(6);
		// Go through all surrounding tiles
		for (int i = -range; i < range + 1; i++) {
			for (int j = -range; j < range + 1; j++) {
				// If on board add to list
				if (tileOnBoard(tile.getCol() + i, tile.getRow() + j)) {
					surroundingTiles.add(getTile(tile.getCol() + i, tile.getRow() + j));
				}
			}
		}
		return surroundingTiles;
	}
	
	/**
	 * Returns all units surrounding a specific tile
	 * @param tile the tile for which the surrounding units are obtained
	 * @return 	   ArrayList with all the surrounding units
	 */
	public ArrayList<Unit> getSurroundingUnits(Tile tile, int range) {
		ArrayList<Tile> surroundingTiles = getSurroundingTiles(tile, range);
		ArrayList<Unit> surroundingUnits = new ArrayList<Unit>(6);
		// Loop through tiles, if not empty add unit
		for (Tile t : surroundingTiles) {
			if (!t.empty()) {
				surroundingUnits.add(t.getUnit());
			}
		}
		return surroundingUnits;
	}
	
	/**
	 * Checks wether a given column and row correspond to a tile on the board
	 * @param col the column of the tile
	 * @param row the row of the tile
	 * @return true if tile is on the board, false otherwise
	 */
	public boolean tileOnBoard(int col, int row) {
		if (col >= tiles.length || row >= tiles[col].length) {
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
		return tiles[col][row];
	}
}