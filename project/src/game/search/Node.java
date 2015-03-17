package game.search;

import game.board.Tile;

public class Node {
	
	private int cost;
	private Tile tile;
	public int col;
	public int row;
	private Node previous;
	
	public Node(Node previous, int cost, Tile tile) {
		this.cost = cost++;
		this.tile = tile;
		col = tile.getCol();
		row = tile.getRow();
		this.previous = previous;
	}

	public Node(Tile tile) {
		this.tile = tile;
		col = tile.getCol();
		row = tile.getRow();
		cost = 0;
		previous = null;
	}
	
	public Node getPrevious() {
		return previous;
	}
	
	public int getCost() {
		return cost;
	}
	
	public Tile getTile() {
		return tile;
	}

	@Override
	public boolean equals(Object node) {
        if (node instanceof Node){
            Node other = (Node) node;
    	    if (this.col == other.col && this.row == other.row) {
    	        return true;
    	    }
        }
        return false;
	}
	
    @Override
    public int hashCode() {
        return 41 * col + 2 * row;
    }
}
