package board;
public class Game {

	public static void main(String[] args) {
		Field board = new Field(30);

		Tile current = board.getTile(30, 30);
		Tile next = null;
		int nextCol;
		int nextRow;
		while (true) {
			System.out.println("Currently at col: " + current.getCol() + " and row: " + current.getRow());
			System.out.println("Give in goal column: ");
			nextCol = Integer.parseInt(System.console().readLine());
			System.out.println("Give in goal row: ");
			nextRow = Integer.parseInt(System.console().readLine());
			try {
				next = board.getTile(nextCol, nextRow);
			} catch (IndexOutOfBoundsException e) {
				System.out.println(e);
			}
			if (!board.legalMove(current, next)) {
				System.out.println("Illegal move!");
			} else {
				current = next;
			}
		}
	}
}