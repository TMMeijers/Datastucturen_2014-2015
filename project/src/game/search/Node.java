package game.search;

import game.board.Tile;

public class Node {
	
	private int cost;
	private Tile tile;
	public int col;
	public int row;
	private Node previous;
	
	// Constructor for a node in the A* path
	public Node(Node previous, int cost, Tile tile, int increment) {
		this.cost = cost + increment;
		this.tile = tile;
		col = tile.getCol();
		row = tile.getRow();
		this.previous = previous;
	}
	
	public Node(Node previous, int cost, Tile tile) {
		this(previous, cost, tile, 1);
	}

	public Node(Tile tile) {
		this(null, 0, tile, 0);
	}
	
	// Return previous node in path
	public Node getPrevious() {
		return previous;
	}
	
	// Get the current actual cost
	public int getCost() {
		return cost;
	}
	
	// return corresponding tile
	public Tile getTile() {
		return tile;
	}

	// equals method for checking if object contained in list
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
	
	// Really simple hash functions, works with board up to 20 tiles.
    @Override
    public int hashCode() {
        return 41 * col + 2 * row;
    }
}
