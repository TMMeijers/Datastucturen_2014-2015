package game.datastructures;

import game.units.Unit;

import java.util.LinkedList;

// Keeps track of all animated units (dying and attacking) and corresponding timers and durations
public class AnimationLinkedList {
	
	private LinkedList<Unit> units;
	private LinkedList<Integer> timers;
	private LinkedList<Integer> durations;
	
	public AnimationLinkedList() {
		units = new LinkedList<Unit>();
		timers = new LinkedList<Integer>();
		durations = new LinkedList<Integer>();
	}
	
	// Adds unit to the list and initialise timer and set duration
	public void add(Unit u, int d) {
		units.add(u);
		timers.add(0);
		durations.add(d);
	}
	
	// removes the unit and corresponding timers
	public Unit removeFirst() {
		timers.removeFirst();
		durations.removeFirst();
		return units.removeFirst();
	}
	
	// Updates all the timers
	public void updateTime(int delta) {
		int val = 0;
		for (int i = 0; i < timers.size(); i++) {
			val = timers.get(i);
			timers.set(i, val+delta);
		}
	}
	
	// Checks if we need to stop an animation
	public boolean stopAnimation() {
		if (timers.getFirst() > durations.getFirst()) {
			return true;
		}
		return false;
	}
	
	// Returns wether we have any animations playing or not
	public boolean isEmpty() {
		return units.size() == 0;
	}
}
