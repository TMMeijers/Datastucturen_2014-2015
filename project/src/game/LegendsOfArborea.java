package game;

import game.mode.DefaultGame;
import game.mode.GameMode;
import game.states.Menu;
import game.states.Play;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class LegendsOfArborea extends StateBasedGame {
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final boolean FULLSCREEN = false;
	public static final int MENU = 0;
	public static final int PLAY = 1;
	
	public static String NAME_P1;
	public static String NAME_P2;
	
	private static final String NAME = "Legends of Arobrea";
	
	
	public LegendsOfArborea(String gameName) {
		super(gameName);
		this.addState(new Menu(MENU));
		this.addState(new Play(PLAY));
	}
	
	public void initStatesList(GameContainer app) throws SlickException {
		this.getState(MENU).init(app, this);
		this.getState(PLAY).init(app, this);
		this.enterState(MENU);
	}
	
	public static void main(String[] args) {
		NAME_P1 = "Truub Deluxe";
		NAME_P2 = "Crappy AI";
		
		AppGameContainer app;
		try {
			app = new AppGameContainer(new LegendsOfArborea(NAME));
			app.setDisplayMode(WIDTH, HEIGHT, FULLSCREEN);
			app.setTargetFrameRate(60);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		
		
		// Initialize game. Choose gamemode (Default board, custom/other boards later?)
		// Choose versus human or AI.
		// Give in name(s) and who is playing which side
		boolean orcP1 = true;
		GameMode game = new DefaultGame(NAME_P1, NAME_P2, orcP1);

		// GraphicsEngine engine = new GraphicsEngine(game)
		
		// Now play game
		while (!game.isFinished()) {
			
//			switch (game.state()) {
//			case game.MAIN_MENU:
//				game.handleMenu();
//			case game.RUNNING:
//				game.mainLoop()
//			}
			
			// draw game (graphicsengine->render())
			
			// get active user (ManualUser, AIUser)
			// activeUser = game->getActiveUser()
			
			// get input from user
			// Action userAction = activeUser->handleInput() // by AI Is this automatic
			// put user input on action queue
			
			
			// game->executeAction(userAction)
		}
		
//		new Thread() { 
//			listenForUserINput()
//			game->putOnInputQueue(input)
//		}
//		
//		while (!stop) {
//			graphicsengine->render();
//		
//			input = game->pollInput()
//			if (input) {
//				game->executeInput();
//			}
	//		if (graphicsengine->elapsedTime < 24fps) wait();
//		}
	}
}
