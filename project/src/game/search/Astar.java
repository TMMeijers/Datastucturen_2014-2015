package game.search;

import game.board.Board;
import game.board.Tile;

import java.util.ArrayList;

public class Astar {
	
	public static Tile aStar(Board board, Tile start, Tile goal) {
		ArrayList<Tile> goals = board.getSurroundingEmptyTiles(goal, 1);
		ArrayList<Node> visited = new ArrayList<Node>();
		ArrayList<Node> open = new ArrayList<Node>();
		
		Node goalNode = new Node(goal);
		Node current = new Node(start);;
		Node next = null;
		open.add(current);
		visited.add(current);
		
		// While we still have open nodes
		while (open.size() > 0) {
			// Get path with lowest cost, add to visited Nodes
			current = open.get(0);
			
			// Get lowest cost path
			for (Node n : open) {
				if (n.getCost()+heuristic(n, goalNode) <= current.getCost()+heuristic(current, goalNode)) {
					current = n;
				}
			}

			// If we are at goal
			if (goals.contains(current.getTile())) {
				// No unblocked path found
				if (current.getCost() >= 1000) {
					return null;
				}
				return getNextTile(current);
			}
			// Keep track of visisted nodes, remove current from pen nodes
			visited.add(current);
			open.remove(current);
			
			// Open new nodes
			for (Tile t : board.getSurroundingTiles(current.getTile(), 1)) {
				// If node not taken add at normal cost
				if (!t.isTaken()) {
					next = new Node(current, current.getCost(), t);
				} else {
					// Else expensive
					next = new Node(current, current.getCost(), t, 1000);
				}
				// If not visisted yet or in open, add to open nodes
				if (!visited.contains(next) && !open.contains(next)) {
					open.add(next);
				}
			}
		}
		// Didn't find a path
		return null;
	}
	
	// Very simple heuristic which returns the max between columns and rows
	private static int heuristic(Node s, Node g) {
		return Math.max(Math.abs(s.col - g.col), Math.abs(s.row - g.row));
	}
	
	// Goes through the path and returns the next tile the unit needs to move to
	private static Tile getNextTile(Node n) {
		Node cur = n;
		Node prev = n.getPrevious();
		while (prev.getPrevious() != null) {
			cur = prev;
			prev = cur.getPrevious();
		}
		return cur.getTile();
	}
}
