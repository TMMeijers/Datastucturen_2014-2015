package game.states;

import game.LegendsOfArborea;
import game.Mechanics;
import game.ai.AIMove;
import game.board.Tile;
import game.players.ComputerPlayer;
import game.players.HumanPlayer;
import game.players.Player;
import game.units.HumanGeneral;
import game.units.HumanSoldier;
import game.units.OrcGeneral;
import game.units.OrcSoldier;
import game.units.Unit;

import java.util.ArrayList;
import java.util.LinkedList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Play extends BasicGameState {

	static final int WIDTH = LegendsOfArborea.WIDTH;
	static final int HEIGHT = LegendsOfArborea.HEIGHT;
	static float POLY_SIDE = 50.0f;
	static float POLY_HORSIDE = POLY_SIDE * 1.5f;
	static final float POLY_XPAD = 2;
	static final float POLY_YPAD = 2;
	
	// Array for graphical tiles
	private Polygon[][] gTiles;
	private Animation[][][][] unitAnimations;
	
	// Textures
	private Image[][] tileTextures;
	private Image attackIconTexture;
	private Image moveIconTexture;
	
	// Game variables for playing
	private Tile selectedTile;
	private Unit selectedUnit;
	private Tile goal;
	private ArrayList<Tile> reachableTiles;
	private ArrayList<Tile> attackableTiles;
	private LinkedList<AIMove> aiMoves;
	
	// Input device
	Mouse mouse;
	
	// Players
	private Player activePlayer;
	private Player inactivePlayer;
	
	// Display strings
	private String winMessage;
	private String playerTurn;
	private String options;
	
	// Check animations needed
	private boolean attackingActive;
	private Unit attackingUnit;
	private int attTime;
	private int attElapsed;
	private boolean dyingActive;
	private Unit dyingUnit;
	private int dieTime;
	private int dieElapsed;
	private Player ownerDyingUnit;
	
	
	
	public Play(int state) {
		
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// Initialise variables
		attackingActive = false;
		attackingUnit = null;
		attElapsed = 0;
		dyingActive = false;
		dyingUnit = null;
		dieElapsed = 0;
		ownerDyingUnit = null;
		selectedTile = null;
		goal = null;
		aiMoves = new LinkedList<AIMove>();
		POLY_SIDE = 50f / (LegendsOfArborea.GAME.board.getDimension() / 5f);
		POLY_HORSIDE = POLY_SIDE * 1.5f;
		
		// Set active player (Player playing human) and initialise units
		if (!LegendsOfArborea.GAME.getPlayer(1).playsOrc()) {
			activePlayer = LegendsOfArborea.GAME.getPlayer(1);
			inactivePlayer = LegendsOfArborea.GAME.getPlayer(2);
		} else {
			activePlayer = LegendsOfArborea.GAME.getPlayer(2);
			inactivePlayer = LegendsOfArborea.GAME.getPlayer(1);
		}
		activePlayer.initTurn();
		
		// Initialise display strings
		playerTurn = activePlayer.getName() + "'s turn";
		options = "(Esc) - Deselect               (E) - End turn";
		
		// Load tile textures (Specified in LegendsOfArborea)
		tileTextures = new Image[LegendsOfArborea.TILE_TYPES][];
		String tilesRootLoc = "res/tiles/";
		for (int i = 0; i < LegendsOfArborea.TILE_TYPES; i++) {
			tileTextures[i] = new Image[LegendsOfArborea.TILE_STATES];	
			for (int j = 0; j < LegendsOfArborea.TILE_STATES; j++) {
				tileTextures[i][j] = new Image(tilesRootLoc + LegendsOfArborea.TILE_NAMES[i] + j + ".png");
			}
		}
		
		// Load unit movement animations		
		unitAnimations = new Animation[LegendsOfArborea.RACES][][][];
		String unitRootLoc = "res/units/";
		SpriteSheet sheet = null;
		for (int i = 0; i < LegendsOfArborea.RACES; i++) {
			unitAnimations[i] = new Animation[LegendsOfArborea.UNIT_TYPES][][];
			for (int j = 0; j < LegendsOfArborea.UNIT_TYPES; j++) {
				unitAnimations[i][j] = new Animation[Unit.ANIM_STATES][];
				for (int k = 0; k < Unit.ANIM_STATES; k++) {
					// Walking animations
					if (k == Unit.ANIM_WALKING) {
						unitAnimations[i][j][k] = new Animation[Unit.ORIENTATIONS];
						sheet = new SpriteSheet(unitRootLoc + LegendsOfArborea.RACE_NAMES[i] + "_" + LegendsOfArborea.UNIT_NAMES[j] + "_move.png", 
												Unit.SPRITE_SIZE, Unit.SPRITE_SIZE);
						for (int l = 0; l < Unit.ORIENTATIONS; l++) {
							unitAnimations[i][j][k][l] = new Animation(sheet, l, 0, l, Unit.WALK_FRAMES-1, false, Unit.WALK_DURATION, true);
						}
					// Attacking animations
					} else if (k == Unit.ANIM_ATTACKING) {
						unitAnimations[i][j][k] = new Animation[Unit.ORIENTATIONS];
						sheet = new SpriteSheet(unitRootLoc + LegendsOfArborea.RACE_NAMES[i] + "_" + LegendsOfArborea.UNIT_NAMES[j] + "_attack.png", 
												Unit.SPRITE_SIZE, Unit.SPRITE_SIZE);
						for (int l = 0; l < Unit.ORIENTATIONS; l++) {
							unitAnimations[i][j][k][l] = new Animation(sheet, l, 0, l, Unit.ATT_FRAMES-1, false, Unit.ATT_DURATION, true);
						}
					// Dying animations (only 1 direction yet)
					} else {
						int dyingOrientations = 1;
						unitAnimations[i][j][k] = new Animation[dyingOrientations];
						sheet = new SpriteSheet(unitRootLoc + LegendsOfArborea.RACE_NAMES[i] + "_" + LegendsOfArborea.UNIT_NAMES[j] + "_die.png", 
												Unit.SPRITE_SIZE, Unit.SPRITE_SIZE);
						for (int l = 0; l < dyingOrientations; l++) {
							int frames = 0;
							if (i == 0 && j == 0) {
								frames = HumanGeneral.DEATH_FRAMES;
							} else if (i == 0 && j == 1) {
								frames = HumanSoldier.DEATH_FRAMES;
							} else if (i == 1 && j == 0) {
								frames = OrcGeneral.DEATH_FRAMES;
							} else if (i == 1 && j == 1) {
								frames = OrcSoldier.DEATH_FRAMES;
							}
							unitAnimations[i][j][k][l] = new Animation(sheet, 0, 0, frames-1, 0, true, Unit.DIE_DURATION, true);
						}
					}
				}
			}
		}
		
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
		int dim = LegendsOfArborea.GAME.board.getDimension();
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
				length = LegendsOfArborea.GAME.board.getColLength(i);
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
				length = LegendsOfArborea.GAME.board.getColLength(i);
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
				Tile tile = LegendsOfArborea.GAME.board.getTile(i, j);
				g.texture(gTiles[i][j], tileTextures[tile.getType()][tile.getStatus()]);
				float resize = 1 / (LegendsOfArborea.GAME.board.getDimension() / 5f);
				// Render unit
				if (!tile.empty()) {
					Unit unit = tile.getUnit();
					float tileX = gTiles[i][j].getCenterX();
					float tileY = gTiles[i][j].getCenterY();
					float unitX = tileX - Unit.SPRITE_SIZE / 1.5f * resize;
					float unitY = tileY - Unit.SPRITE_SIZE / 1.5f * resize;
					if (unit.getState() == Unit.ANIM_DYING) {
						// Temporary because of only one dying direction
						unit.setDirection(0);
					}
					unitAnimations[unit.race][unit.type][unit.getState()][unit.getDirection()].draw(unitX, unitY, resize*Unit.SPRITE_SIZE*1.25f, resize*Unit.SPRITE_SIZE*1.25f);
					float iconY = tileY - POLY_SIDE;
					float attIconX = tileX + POLY_HORSIDE/6f;
					float moveIconX = tileX - POLY_HORSIDE/3f;
					if (unit.canAttack()) {
						attackIconTexture.draw(attIconX, iconY, resize*1.5f);
					} if (unit.canMove()) {
						moveIconTexture.draw(moveIconX, iconY, resize*1.5f);
					}
				}
			}
		}
		
		// Render strings
		Menu.FONT_NORMAL.drawString(30, 30, playerTurn);
		Menu.FONT_SMALL.drawString(WIDTH / 2 - 260, 30, options);
		if (LegendsOfArborea.GAME.isFinished()) {
			Menu.FONT_LARGE.drawString(WIDTH / 2 - 225, HEIGHT / 4, winMessage);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		// Check if game ends
		if (!LegendsOfArborea.GAME.getPlayer(1).anyAlive() || !LegendsOfArborea.GAME.getPlayer(2).anyAlive()) {
			winMessage = activePlayer.getName() + " wins the game!";
			LegendsOfArborea.GAME.gameOver();
		}
		
		// Check if turn ends and give turn to other player
		if (!activePlayer.anyActive(LegendsOfArborea.GAME.board)) {
			switchTurn();
		}
		
		if (activePlayer instanceof HumanPlayer) {
			
			// Get input
			Input input = gc.getInput();
			boolean leftMousePressed = input.isMousePressed(0);
			
			// Select active tile
			if (leftMousePressed && selectedTile == null) {
				selectUnit();
			} else if (leftMousePressed) {
				unitAction(delta);
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
		} else {
			
			boolean aiMustThink = false;
			synchronized(aiMoves) {
				// no moves stored, AI Must think (further down)
				if (aiMoves.size() == 0) {
					aiMustThink = true;
				} else {
					// pop all moves that are stored and execute them
					// THIS is not nice yet. it does everything and then redraws
					// hence NO animations get played
					// but it  works :P
					while (!aiMoves.isEmpty()) {
				        AIMove m = aiMoves.removeFirst();
				        System.out.println("Do move");
				        if (m.type == AIMove.TYPE.MOVE) {
				        	// this should be checked in the ai itself, but here we check again
				        	// 'voor de zekerheid'
				        	if (m.tile.empty()) {
				        		Mechanics.move(LegendsOfArborea.GAME.board, m.unit, m.tile);
				        	}
				        } else if (m.type == AIMove.TYPE.ATTACK) {
				        	// should also be checked in the ai itself
				        	if (m.tile.getUnit().getHp() > 0) {
					        	if (Mechanics.hit(LegendsOfArborea.GAME.board, m.unit, m.tile.getUnit())) {
									handleDying();
								}
				        	}
				        }
				    }
					// ai is done with moves
					activePlayer.endTurn();
				}
			}
			
			// ai must think, thus tart thinking. on callback we fill aiMoves
			if (aiMustThink) {
				ComputerPlayer cp = (ComputerPlayer)activePlayer;
				cp.getAI().think((moves) -> {
					System.out.println("Ai finished thinking");
	
					// fill move queue
					synchronized(aiMoves) {
						for (AIMove m : moves) {
							aiMoves.add(m);
						}
					}
				});
			}
		}
			
			
		// Check if dying or attacking animations need to be stopped
		if (attackingActive) {
			attElapsed += delta;
			if (attElapsed > attTime) {
				attackingUnit.setState(Unit.ANIM_WALKING);
				attackingUnit = null;
				attackingActive = false;
				attElapsed = 0;
			}
		}
		if (dyingActive) {
			dieElapsed += delta;
			System.out.println(dieElapsed);
			if (dieElapsed > dieTime) {
				ownerDyingUnit.removeUnit(dyingUnit);
				dyingUnit.getPosition().removeUnit();
				System.out.println(dyingUnit);
				dyingUnit = null;
				dyingActive = false;
				dieElapsed = 0;
			}
		}
	}
	
	@Override
	public int getID() {
		return LegendsOfArborea.PLAY;
	}
	
	public void handleDying() {
		dyingActive = true;
		ownerDyingUnit = inactivePlayer;
		dyingUnit = goal.getUnit();
		dyingUnit.setState(Unit.ANIM_DYING);
		dieTime = dyingUnit.deathFrames * Unit.DIE_DURATION;
		System.out.println(dieTime);
	}
	
	private void switchTurn() {
		if (activePlayer.playsOrc()) {
			if (LegendsOfArborea.GAME.getPlayer(1).playsOrc()) {
				activePlayer = LegendsOfArborea.GAME.getPlayer(2);
				inactivePlayer = LegendsOfArborea.GAME.getPlayer(1);
			} else {
				activePlayer = LegendsOfArborea.GAME.getPlayer(1);
				inactivePlayer = LegendsOfArborea.GAME.getPlayer(2);
			}
		} else if (LegendsOfArborea.GAME.getPlayer(1).playsOrc()) {
			activePlayer = LegendsOfArborea.GAME.getPlayer(1);
			inactivePlayer = LegendsOfArborea.GAME.getPlayer(2);
		} else {
			activePlayer = LegendsOfArborea.GAME.getPlayer(2);
			inactivePlayer = LegendsOfArborea.GAME.getPlayer(1);
		}
		playerTurn = activePlayer.getName() + "'s turn";
		activePlayer.initTurn();
	}
	
	private void selectUnit() {
		selectedTile = xyToTile(Mouse.getX(), Mouse.getY());
		// Check if action can be performed
		if (selectedTile != null && !selectedTile.empty() && selectedTile.getUnit().active()) {
			selectedUnit = selectedTile.getUnit();
			// Get attackable tiles
			if (selectedUnit.canAttack() && selectedUnit.anyAttackable(LegendsOfArborea.GAME.board)) {
				selectedTile.setStatus(Tile.ACTIVE);
				attackableTiles = LegendsOfArborea.GAME.board.getSurroundingEnemyTiles(selectedTile, selectedUnit.rng, selectedUnit.race);
				for (Tile t : attackableTiles) {
					t.setStatus(Tile.ATTACKABLE);
				}
			}
			// Get reachable tiles
			if (selectedUnit.canMove() && selectedUnit.anyMovable(LegendsOfArborea.GAME.board)) {
				selectedTile.setStatus(Tile.ACTIVE);
				reachableTiles = LegendsOfArborea.GAME.board.getSurroundingEmptyTiles(selectedTile, selectedUnit.spd);
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
	
	private void unitAction(int delta) {
		goal = xyToTile(Mouse.getX(), Mouse.getY());
		// Maybe we want to make the switch to a friendly active unit
		if (goal != null && !goal.empty() && goal.getUnit().race == selectedUnit.race && goal.getUnit().active()) {
			deselect();
			selectUnit();
		} else {
			// If we can move to the goal
			if (reachableTiles != null && reachableTiles.contains(goal)) {
				Mechanics.move(LegendsOfArborea.GAME.board, selectedUnit, goal);
				deselect();
			// If we can attack the goal
			} else if (attackingUnit == null && attackableTiles != null && attackableTiles.contains(goal) && goal.getUnit() != dyingUnit) {
				attackingActive = true;
				attackingUnit = selectedUnit;
				attackingUnit.setState(Unit.ANIM_ATTACKING);
				attTime = Unit.ATT_FRAMES * Unit.ATT_DURATION;
				if (Mechanics.hit(LegendsOfArborea.GAME.board, attackingUnit, goal.getUnit())) {
					handleDying();
				}
				deselect();
			}
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
		int dim = LegendsOfArborea.GAME.board.getDimension();
		float horSpace = 1.5f * POLY_HORSIDE;
		float polyHeight = (float)Math.sqrt(3) * POLY_SIDE;
		float vertSpace = polyHeight;
		
		// Remove borders for coords
		float xBorder = WIDTH/2f - (dim*2f) * horSpace/2f;
		float yBorder = (HEIGHT - ((dim*2 - 1) * (vertSpace+POLY_YPAD))) / 2f;
		float x = mx - xBorder - POLY_HORSIDE/4f;
		float y = my - yBorder;
		
		// Now get estimate for column and row from that
		x /= (horSpace+POLY_XPAD);
		int col = (int) Math.floor(x);
		// If column not on the board return no tile (saves triangle computation)
		if (col < 0 || col >= dim*2 - 1) {
			return null;
		}
		int length = LegendsOfArborea.GAME.board.getColLength(col);
		y = (y - ((dim*2 - 1 - length) * vertSpace/2)) / (vertSpace + POLY_YPAD);
		int row = (int) Math.floor(y);
		// If row not in column return no tile (saves triangle computation)
		if (row < 0 || row >= length) {
			return null;
		}
		
		// ARGHHHH MARKUS HELP
//		// Now check if we are in the left upper or lower triangle section (different tile)
//		// First get triangle vertices
//		x -= col;
//		y -= row;
//		System.out.println("X: " + x + ", Y: " + y);
//		float p0X= 0;
//		float p0Y = vertSpace/2f;
//		float p1X = POLY_HORSIDE/2f;
//		float p1Y = 0;
//		float p2X = 0;
//		float p2Y = 0;
//		
//		// If we need to check if were on one of the triangles
//		if (x < POLY_HORSIDE/2f) {
//			System.out.println(true);
//			// If we're checking lower triangle
//			if (y > vertSpace/2) {
//				p1Y = vertSpace;
//				p2Y = vertSpace;
//			}
//			float sign = 1;
//			float r = 0.5f * (-p1Y * p2X + p0Y * (-p1X + p2X) + p0X * (p1Y - p2Y) + p1X * p2Y);
//			if (r < 0) {
//				sign = -1;
//			}
//			float s = (p0Y * p2X - p0X * p2Y + (p2Y - p0Y) * x + (p0X - p2X) * y) * sign;
//			float t = (p0X * p1Y - p0Y * p1X + (p0Y - p1Y) * x + (p1X - p0X) * y) * sign;
//			// If were in a triangle
//			if (s > 0 && t > 0 && (s+t) < 2 * r * sign) {
//				col--;
//				// Left side of the board and middle column
//				if (col < dim) {
//					if (y <= vertSpace/2) {
//						row--;
//					}
//				// Right side of the board
//				} else {
//					if (y > vertSpace/2) {
//						row++;
//					}
//				}
//			}
//		}
		return LegendsOfArborea.GAME.board.getTile(col, row);
	}

}