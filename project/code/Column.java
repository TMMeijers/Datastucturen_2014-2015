/**
 *
 */
public class Column {

	/**
	 * Array filled with all the tiles in this column.
	 */
	private Tile[] tiles;

	/**
	 * The column number.
	 */
	private int colNumber;

	/**
	 * Length of the column.
	 */
	private int length;

	/**
	 * Constructor for a column on the board
	 * @param  length    the number of Fields the column should have
	 * @param  colNumber the number of the column
	 */
	public Column(int length, int colNumber) {
		tiles = new Tile[length];
		this.colNumber = colNumber;
		this.length = length;
		for (int i = 0; i < length; i++) {
			tiles[i] = new Tile(colNumber, i, length);
		}
	}

	public int length() {
		return tiles.length;
	}

	public Tile getTile(int index) {
		return tiles[index];
	}
}