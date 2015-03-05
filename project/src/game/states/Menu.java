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
	public static UnicodeFont FONT_SMALL;
	public static UnicodeFont FONT_NORMAL;
	public static UnicodeFont FONT_LARGE;
	
	// Buttons
	Image OptionButton;
	Image PlayButton;
	
	// Input device
	Mouse mouse;
	
	public Menu(int state) {
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// Load custom font	with different sizes
		FONT_SMALL = new UnicodeFont("res/ui/fonts/DARK11__.ttf", 30, false, false);
		FONT_NORMAL = new UnicodeFont("res/ui/fonts/DARK11__.ttf", 40, false, false);
		FONT_LARGE = new UnicodeFont("res/ui/fonts/DARK11__.ttf", 60, false, false);
		FONT_SMALL.addAsciiGlyphs();
		FONT_SMALL.getEffects().add(new ColorEffect());
		FONT_SMALL.loadGlyphs();
		FONT_NORMAL.addAsciiGlyphs();
		FONT_NORMAL.getEffects().add(new ColorEffect());
		FONT_NORMAL.loadGlyphs();
		FONT_LARGE.addAsciiGlyphs();
		FONT_LARGE.getEffects().add(new ColorEffect());
		FONT_LARGE.loadGlyphs();
		
		PlayButton = new Image("res/ui/png/red_button11.png");
		match = LegendsOfArborea.NAME_P1 + " VS " + LegendsOfArborea.NAME_P2;
		
		Play.game = new DefaultGame(LegendsOfArborea.NAME_P1, LegendsOfArborea.NAME_P2, true);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		FONT_LARGE.drawString(width / 2 - 225, height / 10, "Legends of Arborea");
		FONT_NORMAL.drawString(width / 2 - 225, height / 4, match);
		g.drawImage(PlayButton, width / 2 - PlayButton.getWidth() / 2, height / 2 - PlayButton.getHeight() / 2);
		FONT_SMALL.drawString(width / 2 - 30, height / 2 - 15, "PLAY");
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		int xPos = Mouse.getX();
		int yPos = Mouse.getY();
		if ((xPos > width / 2 - PlayButton.getWidth() / 2 && xPos < width / 2 + PlayButton.getWidth() / 2) &&
			(yPos > height / 2 - PlayButton.getHeight() / 2 && yPos < height / 2 + PlayButton.getHeight() / 2)) {
			if (input.isMousePressed(0)) {
				sbg.enterState(LegendsOfArborea.PLAY);
			}
		}
	}

	@Override
	public int getID() {
		return LegendsOfArborea.MENU;
	}

}
