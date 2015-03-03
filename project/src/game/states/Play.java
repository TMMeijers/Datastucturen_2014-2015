package game.states;

import game.LegendsOfArborea;
import game.mode.GameMode;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Play extends BasicGameState {

	private int width = LegendsOfArborea.WIDTH;
	private int height = LegendsOfArborea.HEIGHT;
	
	public static GameMode gameMode;
	
	private Polygon[][] gTiles;
	private Animation[] gUnits;
	
	private Image grassTexture;
	
	public Play(int state) {
		
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		grassTexture = new Image("res/tiles/grass.png");
		
		// Make Polygon array
		float side = 50.0f;
		float horDist = 1.5f * side;
		float height = (float)Math.sqrt(3) * side;
		float vertDist = height;

		float[] points = {0, height/2, side/2, 0, side*1.5f, 0, side*2, height/2, side*1.5f, height, side/2, height};

		int dim = gameMode.board.getDimension();
		int totalCols = (dim * 2) - 1;
		gTiles = new Polygon[totalCols][];
		int length;
		float x;
		float y;
		float startX = 100.0f;
		float startY = 100.0f;
		
		for (int i = 0; i < totalCols; i++) {
			x = startX + i * horDist;
			System.out.println(x);
			// Increasing length columns
			if (i < dim) {
				y = startY + side * vertDist/2 * (dim - i);
				
				length = dim + i;
				gTiles[i] = new Polygon[length];
				for (int j = 0; j < length; j++) {
					// Columns with increasing tile count
					gTiles[i][j] = new Polygon(points);
					gTiles[i][j].setX(x);
					gTiles[i][j].setY(startY + j * height);
				}

			// Decreasing length columns
			} else {
				y = startY + side * vertDist/2 * (i % dim); 
				length = -i % dim + 2 * dim - 2;
				gTiles[i] = new Polygon[length];
				for (int j = 0; j < length; j++) {
					// From the middle column start decreasing size
					gTiles[i][j] = new Polygon(points);
					gTiles[i][j].setX(x);
					gTiles[i][j].setY(y + j * height);
				}
			}
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		for (Polygon[] pArray : gTiles) {
			for (Polygon p : pArray) {
				g.texture(p, grassTexture);
			}
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {

	}

	@Override
	public int getID() {
		return LegendsOfArborea.PLAY;
	}

}
