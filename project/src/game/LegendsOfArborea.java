package game;

import game.mode.GameMode;
import game.states.Menu;
import game.states.Play;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class LegendsOfArborea extends StateBasedGame {
	
	// Resolutation and state variables
	public static final int WIDTH = 1600;
	public static final int HEIGHT = 900;
	public static final boolean FULLSCREEN = false;
	public static final int MENU = 0;
	public static final int PLAY = 1;
	
	// Types count variables
	public static final int TILE_TYPES = 1;
	public static final int TILE_STATES = 4;
	public static final String[] TILE_NAMES = {"grass"};
	public static final int RACES = 2;
	public static final String[] RACE_NAMES = {"h", "o"};
	public static final int UNIT_TYPES = 2;
	public static final String[] UNIT_NAMES = {"general", "soldier"};
	
	// Strings
	public static String NAME_P1;
	public static String NAME_P2;
	private static final String NAME = "Legends of Arobrea";

	// The game mode loaded
	public static GameMode GAME;

	public LegendsOfArborea(String gameName) {
		super(gameName);
		this.addState(new Menu(MENU));
		this.addState(new Play(PLAY));
	}
	
	public void initStatesList(GameContainer app) throws SlickException {
		this.getState(MENU).init(app, this);
		this.getState(PLAY).init(app, this);
		this.enterState(PLAY);
	}
	
	public static void main(String[] args) {
		NAME_P1 = "Truub Deluxe";
		NAME_P2 = "Jonas sukkellll";
		GAME = null;
		
		try {
			AppGameContainer app = new AppGameContainer(new LegendsOfArborea(NAME));
			app.setDisplayMode(WIDTH, HEIGHT, FULLSCREEN);
			app.setTargetFrameRate(60);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
