package game.states;

import game.Helpers;
import game.LegendsOfArborea;
import game.Mechanics;
import game.ai.AiMove;
import game.board.Tile;
import game.datastructures.AnimationLinkedList;
import game.datastructures.HitText;
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
import org.newdawn.slick.UnicodeFont;
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
	
	// Graphics
	private Polygon[][] gTiles;
	private Animation[][][][] unitAnimations;
	
	// Textures
	private Image[][] tileTextures;
	private Image attackIcon;
	private Image moveIcon;
	private Image healthPoint;
	private Image infoBoxHuman;
	private Image infoBoxOrc;
	private Image infoIcons;
	
	// Game variables for playing
	private Tile selectedTile;
	private Unit selectedUnit;
	private ArrayList<Tile> reachableTiles;
	private ArrayList<Tile> attackableTiles;
	
	// AI Variables
	private LinkedList<AiMove> aiMoves;
	private int aiPauseTimer;
	private final int aiPause = 200;
	private AiMove m;
	private boolean startTurn;
	
	// Unit for hover information
	private Unit hoverUnit;
	
	// Players
	private Player activePlayer;
	private Player inactivePlayer;
	
	// Display strings
	private String winMessage;
	private String playerTurn;
	private String options;
	private String hoverInfo;
	
	// Animations
	private AnimationLinkedList animatedUnits;
	private HitText hitText;
	
	private UnicodeFont font24;
	
	public Play(int state) {
		
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// Initialise variables
		startTurn = true;
		selectedTile = null;
		selectedUnit = null;
		animatedUnits = new AnimationLinkedList();
		hoverUnit = null;
		aiMoves = new LinkedList<AiMove>();
		aiPauseTimer = 0;
		m = null;
		hitText = null;
		hoverInfo = "";
		
		// Load fonts
		font24 = Helpers.getFont("Knights_Quest", 24);
		
		// Resizing for larger boards, works up to 19 x 19 x 19. Default is 5 x 5 x 5
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
		attackIcon = new Image("res/icons/active_attack.png");
		moveIcon = new Image("res/icons/active_move.png");
		healthPoint = new Image("res/icons/health_point.png");
		infoBoxHuman = new Image("res/ui/png/blue_panel.png");	
		infoBoxOrc = new Image("res/ui/png/red_panel.png");	
		infoIcons = new Image("res/icons/info_icons.png");
		
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

				if (!tile.empty()) {
					// Render unit animations
					Unit unit = tile.getUnit();
					float tileX = gTiles[i][j].getCenterX();
					float tileY = gTiles[i][j].getCenterY();
					float unitX = tileX - Unit.SPRITE_SIZE*1.25f / 2f * resize;
					float unitY = tileY - Unit.SPRITE_SIZE*1.25f / 2f * resize;
					if (unit.getState() == Unit.ANIM_DYING) {
						// Temporary because of only one dying direction
						unit.setDirection(0);
					}
					unitAnimations[unit.race][unit.type][unit.getState()][unit.getDirection()].draw(unitX, unitY, resize*Unit.SPRITE_SIZE*1.25f, resize*Unit.SPRITE_SIZE*1.25f);
					
					// Render active icons
					float attackIconY = tileY - attackIcon.getHeight()/2;
					float moveIconY = tileY - moveIcon.getHeight()/1.5f;
					float attIconX = tileX + POLY_HORSIDE/2f;
					float moveIconX = tileX - POLY_HORSIDE/2f - moveIcon.getWidth()*1.5f;
					if (unit.canAttack()) {
						attackIcon.draw(attIconX, attackIconY, resize*1.5f);
					} if (unit.canMove()) {
						moveIcon.draw(moveIconX, moveIconY, resize*1.5f);
					}
					
					// Render remaining hitpoints
					int health = unit.getHp();
					int pointsInRow = 5;
					float offSet;
					if (health >= pointsInRow) {
						offSet = pointsInRow;
					} else {
						offSet = health % pointsInRow;
					}
					float healthX = tileX - (offSet/2f) * healthPoint.getWidth();
					float healthY = tileY+4 - (float)Math.sqrt(3) * POLY_SIDE/2;
					int remaining = 0;
					for (int k = 0; k < health/pointsInRow+1; k++) {
						health -= k*pointsInRow;
						if (health >= pointsInRow) {
							remaining = pointsInRow;
						} else {
							remaining = health % pointsInRow;
						}
						for (int l = 0; l < remaining; l++) {
							healthPoint.draw(healthX + l*(healthPoint.getWidth()+1*resize), healthY + k*(healthPoint.getHeight()+1*resize), 1*resize);
						}
					}
				}
			}
		}
		
		if (hoverUnit != null) {
			int offSet = 30;
			float size = 2f;
			float x = Mouse.getX()+offSet;
			Image infoBox = infoBoxHuman;
			if (hoverUnit.race == Unit.ORC) {
				infoBox = infoBoxOrc;
			}
			if (Mouse.getX() > 3*WIDTH/4) {
				x -= size*infoBox.getWidth()+2*offSet;
			}
			float y = HEIGHT-Mouse.getY()+offSet;
			if (HEIGHT-Mouse.getY() > HEIGHT/4) {
				y -= size*infoBox.getHeight()+2*offSet;
			}
			infoBox.draw(x, y, size);
			int textOffset = 23;
			UnicodeFont font = Helpers.getFont("Knights_Quest", 26);
			font.drawString(x+textOffset, y+textOffset-8, hoverUnit.toString());
			infoIcons.draw(x+textOffset, y+2*textOffset, 1.1f);
			font24.drawString(x+textOffset*3, y+2*textOffset, hoverInfo);
			if (selectedUnit != null && attackableTiles != null && attackableTiles.contains(hoverUnit.getPosition())) {
				double chance = ((double)Math.round(Mechanics.hitChance(LegendsOfArborea.GAME.board, selectedUnit, hoverUnit)*100))/100;
				font24.drawString(x+textOffset, y+textOffset*7.1f, "hit chance: " + chance);
			}
		}
		
		if (hitText != null) {
			font24.drawString(hitText.getX(), hitText.getY(), hitText.getText());
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
		Input input = gc.getInput();
		
		// Check if game ends
		if (!LegendsOfArborea.GAME.getPlayer(1).anyAlive() || !LegendsOfArborea.GAME.getPlayer(2).anyAlive()) {
			winMessage = activePlayer.getName() + " wins the game!";
			LegendsOfArborea.GAME.gameOver();
		}
		
		// Check if turn ends and give turn to other player
		if (!activePlayer.anyActive(LegendsOfArborea.GAME.board)) {
			switchTurn();
		}
		
		// Update animation timers and check if animation done
		if (!animatedUnits.isEmpty()) {
			animatedUnits.updateTime(delta);
			if (animatedUnits.stopAnimation()) {
				Unit u = animatedUnits.removeFirst();
				if (u.getState() == Unit.ANIM_DYING) {
					u.getPosition().removeUnit();
					u = null;
				} else {
					u.setState(Unit.ANIM_WALKING);
				}
			}
		}
		
		// Check if we need to draw hit/miss string
		if (hitText != null) {
			hitText.updateTimer(delta);
			if (hitText.stopRender()) {
				hitText = null;
			}
		}
		
		// Get unit for hover information
		if (input.isKeyDown(Input.KEY_LCONTROL)) {
			Tile hover = xyToTile(Mouse.getX(), Mouse.getY());
			if (hover != null) {
				if (!hover.empty()) {
					hoverUnit = hover.getUnit();
					hoverInfo = Integer.toString(hoverUnit.getHp()) + '\n' +
								Integer.toString(hoverUnit.att) + '\n' +
								Integer.toString(hoverUnit.sup) + '\n' +
								Integer.toString(hoverUnit.rng) + '\n' +
								Integer.toString(hoverUnit.spd);
				} else {
					hoverUnit = null;
				}
			} else {
				hoverUnit = null;
			}
		} else {
			hoverUnit = null;
		}		
		
		// Reset tile if unit just died and we already selected new unit
		if (attackableTiles != null) {
			for (Tile t : attackableTiles) {
				if (t.empty()) {
					if (selectedUnit != null && selectedUnit.canMove()) {
						if (reachableTiles != null) {
							reachableTiles.add(t);
						} else {
							reachableTiles = new ArrayList<Tile>(1);
							reachableTiles.add(t);
						}
						t.setStatus(Tile.REACHABLE);
					} else {
						t.setStatus(Tile.DEFAULT);
					}
					attackableTiles.remove(t);
					break;
				}
			}
		}
		
		if (activePlayer instanceof HumanPlayer) {
			// Get input
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
		// AI Player
		} else {
			boolean aiMustThink = false;
			synchronized(aiMoves) {
				// no moves stored, AI Must think (further down)
		        aiPauseTimer += delta;
				if (startTurn) {
					aiMustThink = true;
					startTurn = false;
				} else {
					if (m == null && !aiMoves.isEmpty() && aiPauseTimer > aiPause) {
				        m = aiMoves.removeFirst();
					}
					if (m != null) {
						selectedTile = m.unit.getPosition();
						selectedTile.setStatus(Tile.ACTIVE);
						reachableTiles = m.reachableTiles;
						attackableTiles = m.attackableTiles;
				        for (Tile t : m.attackableTiles) {
				        	t.setStatus(Tile.ATTACKABLE);
				        }
				        for (Tile t : m.reachableTiles) {
				        	t.setStatus(Tile.REACHABLE);
				        }
				        if (aiPauseTimer > aiPause*2) {
				        	aiPauseTimer = 0;
				        	aiAction(m);
				        	m = null;
				        	if (aiMoves.isEmpty()) {
				        		deselect();
				        		activePlayer.endTurn();
				        	}
				        }
					}
				}
			}
			
			// ai must think, thus tart thinking. on callback we fill aiMoves
			if (aiMustThink) {
				ComputerPlayer cp = (ComputerPlayer)activePlayer;
				cp.getAI().think((moves) -> {
					System.out.println("Ai finished thinking");
	
					// fill move queue
					synchronized(aiMoves) {
						for (AiMove m : moves) {
							aiMoves.add(m);
						}
					}
				});
			}
		}
	}
	
	private void aiAction(AiMove m) {
		Tile goal = m.tile;
		if (m.type == AiMove.TYPE.MOVE) {
			Mechanics.move(LegendsOfArborea.GAME.board, m.unit, goal);
			deselect();
		} else if (m.type == AiMove.TYPE.ATTACK) {			
			handleAttack(goal, m.unit);
		}
	}
	
	private void handleAttack(Tile goal, Unit attacker) {			
		Unit target = goal.getUnit();
		attacker.setState(Unit.ANIM_ATTACKING);
		animatedUnits.add(attacker, Unit.ATT_FRAMES*Unit.ATT_DURATION);
		float x = gTiles[goal.getCol()][goal.getRow()].getCenterX() - 20;
		float y = gTiles[goal.getCol()][goal.getRow()].getCenterY() - 10;
		if (Mechanics.hit(LegendsOfArborea.GAME.board, attacker, target)) {
			hitText = new HitText(true, x, y);
			if (target.getHp() <= 0) {
				handleDying(target);
			}
		} else {
			hitText = new HitText(false, x, y);
		}
		deselect();
	}
	
	private void unitAction(int delta) {
		Tile goal = xyToTile(Mouse.getX(), Mouse.getY());
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
			} else if (attackableTiles != null && attackableTiles.contains(goal) && !goal.empty() &&goal.getUnit().getState() != Unit.ANIM_DYING) {
				handleAttack(goal, selectedUnit);
			}
		}
	}
	
	@Override
	public int getID() {
		return LegendsOfArborea.PLAY;
	}
	
	public void handleDying(Unit u) {
		u.setState(Unit.ANIM_DYING);
		inactivePlayer.removeUnit(u);
		animatedUnits.add(u, u.deathFrames*Unit.DIE_DURATION);
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
		startTurn = true;
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
		return LegendsOfArborea.GAME.board.getTile(col, row);
	}

}