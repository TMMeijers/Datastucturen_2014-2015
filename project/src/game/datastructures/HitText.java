package game.datastructures;

public class HitText {

	public final static int DURATION = 700;
	private int timer;
	private String text;
	private float x;
	private float y;
	
	// Floating text for hits or misses
	public HitText(boolean hit, float x, float y) {
		if (hit) {
			text = "HIT!";
		} else {
			text = "MISS!";
		}
		this.x = x;
		this.y = y;
		timer = 0;
	}
	
	// Checks if we need to stop rendering
	public boolean stopRender() {
		if (timer > DURATION) {
			return true;
		}
		return false;
	}
	
	// Updates timers
	public void updateTimer(int delta) {
		timer += delta;
		y--;
	}
	
	// Returns the corresponding text
	public String getText() {
		return text;
	}
	
	// x coordinate
	public float getX() {
		return x;
	}
	
	// y coordinate
	public float getY() {
		return y;
	}
}
