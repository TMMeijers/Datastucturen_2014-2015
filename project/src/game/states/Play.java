package game.states;

import game.LegendsOfArborea;
import game.board.Tile;
import game.mode.GameMode;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
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
	
	static final float POLY_SIDE = 50.0f;
	static final float POLY_HORSIDE = POLY_SIDE * 1.5f;
	static final float POLY_XPAD = 3;
	static final float POLY_YPAD = 5;
	
	// Input device
	Mouse mouse;
	
	public Play(int state) {
		
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		// Load textures
		grassTexture = new Image("res/tiles/grass.png");
		
		// Make Polygon array
		float horSpace = 1.5f * POLY_HORSIDE;
		float height = (float)Math.sqrt(3) * POLY_SIDE;
		float vertSpace = height;

		float[] points = {0, height/2, POLY_HORSIDE/2f, 0, POLY_HORSIDE*1.5f, 0, POLY_HORSIDE*2f, height/2, POLY_HORSIDE*1.5f, height, POLY_HORSIDE/2f, height};

		int dim = gameMode.board.getDimension();
		int totalCols = (dim * 2) - 1;
		gTiles = new Polygon[totalCols][];
		int length;
		float x;
		float y;
		float startX = width/2 - (dim*2) * horSpace/2;
		float startY = height/2;
		
		for (int i = 0; i < totalCols; i++) {
			x = startX + i * (horSpace+POLY_XPAD);
			// Increasing length columns
			if (i < dim) {
				y = startY + vertSpace/2 * (dim - (i + 1));

				System.out.println(y);
				System.out.println(true);
				length = dim + i;
				gTiles[i] = new Polygon[length];
				for (int j = 0; j < length; j++) {
					// Columns with increasing tile count
					gTiles[i][j] = new Polygon(points);
					gTiles[i][j].setX(x);
					gTiles[i][j].setY(y + j * (height+POLY_YPAD));
				}

			// Decreasing length columns
			} else {
				y = startY + vertSpace/2 * ((i + 1) % dim); 
				System.out.println(y);
				length = -i % dim + 2 * dim - 2;
				gTiles[i] = new Polygon[length];
				for (int j = 0; j < length; j++) {
					// From the middle column start decreasing size
					gTiles[i][j] = new Polygon(points);
					gTiles[i][j].setX(x);
					gTiles[i][j].setY(y + j * (height+POLY_YPAD));
				}
			}
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException, IllegalArgumentException {
		for (int i = 0; i < gTiles.length; i++) {
			for (int j = 0; j < gTiles[i].length; j++) {		
				switch (gameMode.board.getTile(i, j).getType()) {
				case "grass":	
					g.texture(gTiles[i][j], grassTexture);
					break;
				case "wood":
					break;
				case "swamp":
					break;
				case "boulder":
					break;
				default:
					throw new IllegalArgumentException("Type: " + gameMode.board.getTile(i, j).getType() + " doesn't exist.");
			}
			}
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		int xPos = Mouse.getX();
		int yPos = Mouse.getY();
		
		Tile test = xyToTile(xPos, yPos);
		System.out.println(test);
		if (test != null) {
			System.out.println("Col: " + test.getCol() + ", Row: " + test.getRow());
		}
	}

	@Override
	public int getID() {
		return LegendsOfArborea.PLAY;
	}
	
	public Tile xyToTile(int mx, int my) {
		int dim = gameMode.board.getDimension();
		float horSpace = 1.5f * POLY_HORSIDE;
		float polyHeight = (float)Math.sqrt(3) * POLY_SIDE;
		float vertSpace = height;
		
		// Remove padding
		float xBorder = width/2f - (dim*2f) * (horSpace + POLY_XPAD)/2f;
		float yBorder = (height - ((dim*2f - 1) * (vertSpace + POLY_YPAD))) / 2f; // HHMmmmm
		float x = mx - xBorder;
		float y = my - yBorder;
		
		System.out.println("X: " + x + ", Y: " + y);
		float temp1 = width - 2*xBorder;
		float temp2 = height - 2*yBorder;
		System.out.println("X max: " + temp1 + ", Y max: " + temp2);
		
		// If we didn't click on the field
		if (x < 0 || y < 0 || x > width - 2*xBorder || y > height - 2*yBorder) {
			return null;
		}
		int col = (int)(x / (horSpace + POLY_XPAD));
		int row = (int)(y / (vertSpace + POLY_YPAD));
		if (col >= dim * 2 - 1) {
			return null;
		}
		
		return gameMode.board.getTile(col, row);
	}

}
