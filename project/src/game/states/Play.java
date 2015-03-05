package game.states;

import game.LegendsOfArborea;
import game.Mechanics;
import game.board.Tile;
import game.mode.GameMode;
import game.players.Player;
import game.units.Unit;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Play extends BasicGameState {

	static final int WIDTH = LegendsOfArborea.WIDTH;
	static final int HEIGHT = LegendsOfArborea.HEIGHT;
	static final float POLY_SIDE = 50.0f;
	static final float POLY_HORSIDE = POLY_SIDE * 1.5f;
	static final float POLY_XPAD = 2;
	static final float POLY_YPAD = 2;
	
	// The current gamemode
	public static GameMode game;
	
	// Array for graphical tiles
	private Polygon[][] gTiles;
	
	// Textures
	private Image[][] tileTextures;
	private Image[][] unitTextures;
	private Image attackIconTexture;
	private Image moveIconTexture;
	
	// Game variables for playing
	private Tile selectedTile;
	private Unit selectedUnit;
	private Tile goal;
	private ArrayList<Tile> reachableTiles;
	private ArrayList<Tile> attackableTiles;
	
	// Input device
	Mouse mouse;
	
	private Player activePlayer;
	private Player inactivePlayer;
	
	// Display strings
	private String winMessage;
	private String playerTurn;
	private String options;
	
	public Play(int state) {
		
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// Initialise variables
		selectedTile = null;
		goal = null;
		
		// Set active player (Player playing human) and initialise units
		if (!game.getPlayer(1).playsOrc()) {
			activePlayer = game.getPlayer(1);
			inactivePlayer = game.getPlayer(2);
		} else {
			activePlayer = game.getPlayer(2);
			inactivePlayer = game.getPlayer(1);
		}
		activePlayer.initTurn();
		
		// Initialise display strings
		playerTurn = activePlayer.getName() + "'s turn";
		options = "(Esc) - Deselect               (E) - End turn";
		
		// Load tile textures
		tileTextures = new Image[LegendsOfArborea.TILE_TYPES][];
		for (int i = 0; i < LegendsOfArborea.TILE_TYPES; i++) {
			tileTextures[i] = new Image[LegendsOfArborea.TILE_STATES];	
			for (int j = 0; j < LegendsOfArborea.TILE_STATES; j++) {
				String location = "res/tiles/" + LegendsOfArborea.TILE_NAMES[i] + j + ".png";
				tileTextures[i][j]= new Image(location);
			}
		}
		// Load unit textures (2 races, 2 types per race)
		unitTextures = new Image[LegendsOfArborea.RACES][];
		unitTextures[0] = new Image[2];
		unitTextures[1] = new Image[2];
		unitTextures[0][0] = new Image("res/units/static/h_general.png");
		unitTextures[0][1] = new Image("res/units/static/h_soldier.png");
		unitTextures[1][0] = new Image("res/units/static/o_general.png");
		unitTextures[1][1] = new Image("res/units/static/o_soldier.png");
		// Load icon textures (move and attack)
		attackIconTexture = new Image("res/icons/active_attack.png");
		moveIconTexture = new Image("res/icons/active_move.png");
		
		// Polygon sizes
		float horSpace = 1.5f * POLY_HORSIDE;
		float polyHeight = (float)Math.sqrt(3) * POLY_SIDE;
		float vertSpace = polyHeight;

		float[] points = {0, polyHeight/2, POLY_HORSIDE/2f, 0, POLY_HORSIDE*1.5f, 0, POLY_HORSIDE*2f, 
						  polyHeight/2, POLY_HORSIDE*1.5f, polyHeight, POLY_HORSIDE/2f, polyHeight};

		// Polygon array
		int dim = game.board.getDimension();
		int totalCols = (dim * 2) - 1;
		gTiles = new Polygon[totalCols][];
		
		// Polygon positioning
		int length;
		float x;
		float y;
		float startX = WIDTH/2 - (dim*2) * horSpace/2;
		float startY = (HEIGHT - ((dim*2 - 1) * (vertSpace+POLY_YPAD))) / 2;
		
		// Make all polygons and set position
		for (int i = 0; i < totalCols; i++) {
			x = startX + i * (horSpace+POLY_XPAD);
			// Increasing length columns
			if (i < dim) {
				y = startY + vertSpace/2 * (dim - (i + 1));
				length = game.board.getColLength(i);
				gTiles[i] = new Polygon[length];
				for (int j = 0; j < length; j++) {
					// Columns with increasing tile count
					gTiles[i][j] = new Polygon(points);
					gTiles[i][j].setX(x);
					gTiles[i][j].setY(y + j * (polyHeight+POLY_YPAD));
				}
			// Decreasing length columns
			} else {
				y = startY + vertSpace/2 * ((i + 1) % dim); 
				length = game.board.getColLength(i);
				gTiles[i] = new Polygon[length];
				for (int j = 0; j < length; j++) {
					// From the middle column start decreasing size
					gTiles[i][j] = new Polygon(points);
					gTiles[i][j].setX(x);
					gTiles[i][j].setY(y + j * (polyHeight+POLY_YPAD));
				}
			}
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// Render all tiles and units
		for (int i = 0; i < gTiles.length; i++) {
			for (int j = 0; j < gTiles[i].length; j++) {		
				// Render tile
				Tile tile = game.board.getTile(i, j);
				g.texture(gTiles[i][j], tileTextures[tile.getType()][tile.getStatus()]);
				
				// Render unit
				if (!tile.empty()) {
					Unit unit = tile.getUnit();
					float tileX = gTiles[i][j].getCenterX();
					float tileY = gTiles[i][j].getCenterY();
					float unitX = tileX - unitTextures[unit.race][unit.type].getWidth()/1.5f;
					float unitY = tileY - unitTextures[unit.race][unit.type].getHeight()/1.25f;
					unitTextures[unit.race][unit.type].draw(unitX, unitY, 1.5f);
					float iconY = tileY - POLY_SIDE;
					float attIconX = tileX + POLY_HORSIDE/6f;
					float moveIconX = tileX - POLY_HORSIDE/3f;
					if (unit.canAttack()) {
						attackIconTexture.draw(attIconX, iconY, 1.5f);
					} if (unit.canMove()) {
						moveIconTexture.draw(moveIconX, iconY, 1.5f);
					}
				}
			}
		}
		
		// Render strings
		Menu.FONT_NORMAL.drawString(30, 30, playerTurn);
		Menu.FONT_SMALL.drawString(WIDTH / 2 - 260, 30, options);
		if (game.isFinished()) {
			Menu.FONT_LARGE.drawString(WIDTH / 2 - 225, HEIGHT / 4, winMessage);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		// Get input
		Input input = gc.getInput();
		boolean leftMousePressed = input.isMousePressed(0);
//		System.out.println("Unit: " + selectedUnit + ", Tile: " + selectedTile);
		// Check if game ends
		if (!game.getPlayer(1).anyAlive() || !game.getPlayer(2).anyAlive()) {
			winMessage = activePlayer.getName() + " wins the game!";
			game.gameOver();
		}
		// Check if turn ends and give turn to other player
		if (!activePlayer.anyActive(game.board)) {
			if (activePlayer.playsOrc()) {
				if (game.getPlayer(1).playsOrc()) {
					activePlayer = game.getPlayer(2);
					inactivePlayer = game.getPlayer(1);
				} else {
					activePlayer = game.getPlayer(1);
					inactivePlayer = game.getPlayer(2);
				}
			} else if (game.getPlayer(1).playsOrc()) {
				activePlayer = game.getPlayer(1);
				inactivePlayer = game.getPlayer(2);
			} else {
				activePlayer = game.getPlayer(2);
				inactivePlayer = game.getPlayer(1);
			}
			playerTurn = activePlayer.getName() + "'s turn";
			activePlayer.initTurn();
		}
		
		// Select active tile
		if (leftMousePressed && selectedTile == null) {
			selectUnit();
		} else if (leftMousePressed) {
			unitAction();
		}
		
		// Deselect tile
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			deselect();
		}
		
		// End turn
		if (input.isKeyPressed(Input.KEY_E)) {
			deselect();
			activePlayer.endTurn();
		}
	}
	
	@Override
	public int getID() {
		return LegendsOfArborea.PLAY;
	}
	
	private void selectUnit() {
		selectedTile = xyToTile(Mouse.getX(), Mouse.getY());
		// Check if action can be performed
		if (selectedTile != null && !selectedTile.empty() && selectedTile.getUnit().active()) {
			selectedUnit = selectedTile.getUnit();
			// Get attackable tiles
			if (selectedUnit.canAttack() && selectedUnit.anyAttackable(game.board)) {
				System.out.println("I can still attack!?: " + selectedUnit.anyAttackable(game.board));
				selectedTile.setStatus(Tile.ACTIVE);
				attackableTiles = game.board.getSurroundingEnemyTiles(selectedTile, selectedUnit.rng, selectedUnit.race);
				System.out.println(attackableTiles);
				for (Tile t : attackableTiles) {
					t.setStatus(Tile.ATTACKABLE);
				}
			}
			// Get reachable tiles
			if (selectedUnit.canMove() && selectedUnit.anyMovable(game.board)) {
				selectedTile.setStatus(Tile.ACTIVE);
				reachableTiles = game.board.getSurroundingEmptyTiles(selectedTile, selectedUnit.spd);
				for (Tile t : reachableTiles) {
					t.setStatus(Tile.REACHABLE);
				}
			}
		} 
		if (reachableTiles == null && attackableTiles == null) {
			selectedUnit = null;
			selectedTile = null;
		}
	}
	
	private void unitAction() {
		goal = xyToTile(Mouse.getX(), Mouse.getY());
		// If Tile is empty or unit can't do anything nothing to do
		if (reachableTiles != null && reachableTiles.contains(goal)) {
			Mechanics.move(game.board, selectedUnit, goal);
			deselect();
		} else if (attackableTiles != null && attackableTiles.contains(goal)) {
			if (Mechanics.hit(game.board, selectedUnit, goal.getUnit())) {
				goal.getUnit().getPosition().removeUnit();
				inactivePlayer.removeUnit(goal.getUnit());
			}
			deselect();
		}
	}
	
	private void deselect() {
		if (reachableTiles != null) {
			for (Tile t : reachableTiles) {
				t.setStatus(Tile.DEFAULT);
			}
		}
		if (attackableTiles != null) {
			for (Tile t : attackableTiles) {
				t.setStatus(Tile.DEFAULT);
			}
		}
		if (selectedTile != null) {
			selectedTile.setStatus(Tile.DEFAULT);
		}
		reachableTiles = null;
		attackableTiles = null;
		selectedTile = null;
		selectedUnit = null;
		goal = null;
	}
	
	private Tile xyToTile(int mx, int my) {
		// Invert mouse Y so 0 is at the top
		my = HEIGHT - my;
		// Get all polygon sizes
		int dim = game.board.getDimension();
		float horSpace = 1.5f * POLY_HORSIDE;
		float polyHeight = (float)Math.sqrt(3) * POLY_SIDE;
		float vertSpace = polyHeight;
		
		// Remove borders for coords
		float xBorder = WIDTH/2f - (dim*2f) * horSpace/2f;
		float yBorder = (HEIGHT - ((dim*2 - 1) * (vertSpace+POLY_YPAD))) / 2f;
		float x = mx - xBorder - POLY_HORSIDE/4f;
		float y = my - yBorder;
		
		// Now get estimate for column and row from that
		int col = (int) Math.floor(x / (horSpace + POLY_XPAD));
		// If column not on the board return no tile
		if (col < 0 || col >= dim*2 - 1) {
			return null;
		}
		int length = game.board.getColLength(col);
		int row = (int) Math.floor((y - ((dim*2 - 1 - length) * vertSpace/2)) / (vertSpace + POLY_YPAD));
		
		return game.board.getTile(col, row);
	}

}
