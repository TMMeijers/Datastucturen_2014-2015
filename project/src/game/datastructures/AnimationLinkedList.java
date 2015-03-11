package game.datastructures;

import game.units.Unit;

import java.util.LinkedList;

public class AnimationLinkedList {
	
	private LinkedList<Unit> units;
	private LinkedList<Integer> timers;
	private LinkedList<Integer> durations;
	
	public AnimationLinkedList() {
		units = new LinkedList<Unit>();
		timers = new LinkedList<Integer>();
		durations = new LinkedList<Integer>();
	}
	
	public void add(Unit u, int d) {
		units.add(u);
		timers.add(0);
		durations.add(d);
	}
	
	public Unit removeFirst() {
		timers.removeFirst();
		return units.removeFirst();
	}
	
	public void updateTime(int delta) {
		int val = 0;
		for (int i = 0; i < timers.size(); i++) {
			val = timers.get(i);
			timers.set(i, val+delta);
		}
	}
	
	public int getTimer() {
		return timers.getFirst();
	}
	
	public int getDuration() {
		return durations.getFirst();
	}
	
	public boolean isEmpty() {
		return units.size() == 0;
	}
}
