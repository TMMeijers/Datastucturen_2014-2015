/**
 * 
 */
public class Board {

	/**
	 * Array filled with all the board columns.
	 */
	private Column[] columns;

	/**
	 * Generates the default board (5 x 5 x 5) with default units: 6 human soldiers,
	 * 3 human generals, 8 orc soldiers and 2 orc generals.
	 */
	public Board() {
		this(5);
	}

	/**
	 * Generates the board with specified dimensions with default units: 6 human soldiers,
	 * 3 human generals, 8 orc soldiers and 2 orc generals.
	 * @param  dimension the dimension of the board (dim x dim x dim)
	 */
	public Board(int dimension) {
		int totalCols = (dimension * 2) - 1;
		columns = new Column[totalCols];

		for (int i = 0; i < totalCols; i++) {
			if (i < dimension) {
				// Columns with increasing tile count
				columns[i] = new Column(dimension + i, i);
			} else {
				// From the middle column start decreasing size
				columns[i] = new Column(-i % dimension + 2 * dimension - 2, i);
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
		// Cannot move more than 1 column
		if (Math.abs(start.getCol() - goal.getCol()) > 1 ||
			Math.abs(start.getRow() - goal.getRow()) > 1) {
			return false;
		}
		// Goal tile needs to be on the board
		if (goal.getRow() < 1 || goal.getRow() > goal.maxRow()) {
			return false;
		}
		return true;
	}

	public Tile getTile(int col, int row) throws IndexOutOfBoundsException {
		if (col >= columns.length) {
			throw new IndexOutOfBoundsException("Column doesn't exist.");
		}
		if (row >= columns[col].length()) {
			throw new IndexOutOfBoundsException("Tile doesn't exist.");
		}
		return columns[col].getTile(row);
	}
}