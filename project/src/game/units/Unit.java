package game.units;

import game.board.Board;
import game.board.Tile;

public abstract class Unit {
	
	/**
	 * Number of stances unit has (up, upright, upleft, downright, downleft, down)
	 */
	public final static int ORIENTATIONS = 6;
	
	public final static int WALK_DURATION = 5;
	
	public final static int ATT_DURATION = 4;
	
	public final static int SPRITE_SIZE = 80;
	
	/**
	 * 
	 */
	
	/**
	 * Race of the unit, orc is true (1), human is false (0).
	 */
	public final int race;

	/**
	 * The type of the unit, i.e. General, Soldier etc.
	 */
	public final int type;
	
	/**
	 * Attack stat of the unit.
	 */
	public final int att;
	
	/**
	 * Power stat of the unit, determines damage dealed.
	 */
	public final int pwr;
	
	/**
	 * Support stat of the unit, determines weapon skill assist.
	 */
	public final int sup;
	
	/**
	 * Range stat of the unit, how many tiles the Unit can reach with an attack.
	 */
	public final int rng;
	
	/**
	 * Speed stat of the unit, how many tiles the unit can move.
	 */
	public final int spd;
	
	/**
	 * Health stat for the unit.
	 */
	private int hitpoints;
	
	/**
	 * If unit can still move.
	 */
	private boolean activeMove;
	
	/**
	 * If unit can still attack.
	 */
	private boolean activeAttack;
	
	/**
	 * The position of the Unit.
	 */
	private Tile position;
	
	/**
	 * The name of the unit.
	 */
	protected String name;
	
	/**
	 * Constructor for Unit, sets status to inactive, no position specified.
	 */
	public Unit(int race, int type, int att, int pwr, int sup, int hitpoints, int rng, int spd) {
		this.race = race;
		this.type = type;
		this.att = att;
		this.pwr = pwr;
		this.sup = sup;
		this.hitpoints = hitpoints;
		this.rng = rng;
		this.spd = spd;
		activeMove = false;
		activeAttack = false;
		position = null;
	}
	
	/**
	 * Returns the hitpoints of the Unit.
	 * @return the hitpoints of the Unit
	 */
	public int getHp() {
		return hitpoints;
	}
	
	/**
	 * Returns the position of the Unit.
	 * @return the Tile the unit is positioned on
	 */
	public Tile getPosition() {
		return position;
	}
	
	/**
	 * Sets the position for the Unit.
	 * @param tile the new position
	 */
	public void moveTo(Tile tile) {
		position = tile;
	}

	/**
	 * When unit gets damaged, subtract a hitpoint.
	 * @param  dmg the damage taken by the unit
	 * @return     true if the unit gets killed by the damage, false otherwise
	 */
	public boolean damaged(int dmg) {
		hitpoints -= dmg;
		if (hitpoints <= 0) {
			// Unit has 0 hp, is killed
			return true;
		}
		return false;
	}
	
	public void activate() {
		activeMove = true;
		activeAttack = true;
	}
	
	public void deactivate() {
		activeMove = false;
		activeAttack = false;
	}
	
	public boolean active() {
		return activeAttack || activeMove;
	}
	
	/**
	 * Checks if Unit can still make an attack.
	 * @return true if Unit can still attack, false otherwise
	 */
	public boolean canAttack() {
		return activeAttack;
	}
	
	public boolean anyMovable(Board board) {
		return board.getSurroundingEmptyTiles(position, spd).size() > 0;
	}
	
	public boolean anyAttackable(Board board) {
		if (board.getSurroundingEnemies(position, rng, race).size() == 0) {
			// If we can't move set attack to false anyway
			if (!canMove()) {
				activeAttack = false;
			}
			return false;
		}
		return true;
	}
	
	/**
	 * Sets attack status to inactive.
	 */
	public void hasAttacked() {
		activeAttack = false;
	}

	
	/**
	 * Checks if Unit can still move
	 * @return true if Unit can still make a move, false otherwise
	 */
	public boolean canMove() {
		return activeMove;
	}
	
	/**
	 * Sets move status to inactive.
	 */
	public void hasMoved() {
		activeMove = false;
	}

	public String toString() {
		return name;
	}

}