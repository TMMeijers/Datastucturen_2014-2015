package game.states;

import game.LegendsOfArborea;
import game.mode.DefaultGame;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Menu extends BasicGameState {

	private int width = LegendsOfArborea.WIDTH;
	private int height = LegendsOfArborea.HEIGHT;
	private String match;
	
	// Fonts
	UnicodeFont fontSmall;
	UnicodeFont fontNormal;
	UnicodeFont fontLarge;
	
	// Buttons
	Image OptionButton;
	Image PlayButton;
	
	// Input device
	Mouse mouse;
	
	public Menu(int state) {
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		// Load custom font	with different sizes
		fontSmall = new UnicodeFont("res/ui/fonts/DARK11__.ttf", 25, false, false);
		fontNormal = new UnicodeFont("res/ui/fonts/DARK11__.ttf", 40, false, false);
		fontLarge = new UnicodeFont("res/ui/fonts/DARK11__.ttf", 60, false, false);
		fontSmall.addAsciiGlyphs();
		fontSmall.getEffects().add(new ColorEffect());
		fontSmall.loadGlyphs();
		fontNormal.addAsciiGlyphs();
		fontNormal.getEffects().add(new ColorEffect());
		fontNormal.loadGlyphs();
		fontLarge.addAsciiGlyphs();
		fontLarge.getEffects().add(new ColorEffect());
		fontLarge.loadGlyphs();
		
		PlayButton = new Image("res/ui/png/red_button11.png");
		match = LegendsOfArborea.NAME_P1 + " VS " + LegendsOfArborea.NAME_P2;
		
		Play.gameMode = new DefaultGame(LegendsOfArborea.NAME_P1, LegendsOfArborea.NAME_P2, true);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		fontLarge.drawString(width / 2 - 225, height / 10, "Legends of Arborea");
		fontNormal.drawString(width / 2 - 225, height / 4, match);
		g.drawImage(PlayButton, width / 2 - PlayButton.getWidth() / 2, height / 2 - PlayButton.getHeight() / 2);
		fontSmall.drawString(width / 2 - 30, height / 2 - 15, "PLAY");
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		Input input = gc.getInput();
		int xPos = Mouse.getX();
		int yPos = Mouse.getY();
		if ((xPos > width / 2 - PlayButton.getWidth() / 2 && xPos < width / 2 + PlayButton.getWidth() / 2) &&
			(yPos > height / 2 - PlayButton.getHeight() / 2 && yPos < height / 2 + PlayButton.getHeight() / 2)) {
			if (input.isMousePressed(0)) {
				game.enterState(LegendsOfArborea.PLAY);
			}
		}
	}

	@Override
	public int getID() {
		return LegendsOfArborea.MENU;
	}

}
