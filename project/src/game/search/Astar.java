package game.search;

import game.board.Board;
import game.board.Tile;

import java.util.ArrayList;

public class Astar {
	
	public static Tile aStar(Board board, Tile start, Tile goal) {
		ArrayList<Tile> goals = board.getSurroundingEmptyTiles(goal, 1);
		ArrayList<Node> visited = new ArrayList<Node>();
		ArrayList<Node> open = new ArrayList<Node>();
		int bestCost = Integer.MAX_VALUE;
		
		Node startNode = new Node(start);
		Node goalNode = new Node(goal);
		Node current = startNode;
		Node next = null;
		open.add(startNode);
		visited.add(startNode);
		System.out.println("Goal tile, col: " + goal.getCol() + " row: " + goal.getRow());
		
		while (open.size() > 0) {
			// Get path with lowest cost, add to visited Nodes
			for (Node n : open) {
				System.out.println("Next, col: " + n.col + " row: " + n.row);
				if (n.getCost()+heuristic(n, goalNode) < current.getCost()+heuristic(current, goalNode)) {
					current = n;
					System.out.println("Next, col: " + n.col + " row: " + n.row);
					bestCost = n.getCost()+heuristic(n, goalNode);
				}
			}
			
			if (goals.contains(current.getTile())) {
				return getNextTile(current);
			}
			visited.add(current);
			open.remove(current);
			
			// Open new nodes
			for (Tile t : board.getSurroundingEmptyTiles(current.getTile(), 1)) {
				next = new Node(current, current.getCost(), t);
				if (!visited.contains(next)) {
					open.add(next);
				}
			}
		}
		// Didn't find a path
		return null;
	}
	
	private static int heuristic(Node s, Node g) {
		return Math.max(Math.abs(s.col - g.col), Math.abs(s.row - g.row));
	}
	
	private static Tile getNextTile(Node n) {
		Node cur = n;
		Node prev = n.getPrevious();
		while (prev != null) {
			cur = prev;
			prev = cur.getPrevious();
		}
		return cur.getTile();
	}
}
