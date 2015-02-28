package game.states;

import game.LegendsOfArborea;

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

	UnicodeFont fontSmall;
	UnicodeFont fontNormal;
	UnicodeFont fontLarge;

	private int width = LegendsOfArborea.WIDTH;
	private int height = LegendsOfArborea.HEIGHT;
	
	private String match;
	
	Image OptionButton;
	Image PlayButton;
	
	Mouse mouse;
	
	public Menu(int state) {
		
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		// Load custom font	
		fontSmall = new UnicodeFont("sprites/ui/fonts/DARK11__.ttf", 25, false, false);
		fontNormal = new UnicodeFont("sprites/ui/fonts/DARK11__.ttf", 40, false, false);
		fontLarge = new UnicodeFont("sprites/ui/fonts/DARK11__.ttf", 60, false, false);
		fontSmall.addAsciiGlyphs();
		fontSmall.getEffects().add(new ColorEffect());
		fontSmall.loadGlyphs();
		fontNormal.addAsciiGlyphs();
		fontNormal.getEffects().add(new ColorEffect());
		fontNormal.loadGlyphs();
		fontLarge.addAsciiGlyphs();
		fontLarge.getEffects().add(new ColorEffect());
		fontLarge.loadGlyphs();
		
		PlayButton = new Image("sprites/ui/PNG/red_button11.png");
		match = LegendsOfArborea.NAME_P1 + " VS " + LegendsOfArborea.NAME_P2;
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
