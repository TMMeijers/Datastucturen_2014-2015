package game.datastructures;

public class HitText {

	public final static int DURATION = 500;
	private int timer;
	private String text;
	private float x;
	private float y;
	
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
	
	public boolean stopRender() {
		if (timer > DURATION) {
			return true;
		}
		return false;
	}
	
	public void updateTimer(int delta) {
		timer += delta;
		y--;
	}
	
	public String getText() {
		return text;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
}
