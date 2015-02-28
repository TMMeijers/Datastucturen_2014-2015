package game.states;

import game.LegendsOfArborea;

import java.awt.Font;
import java.io.InputStream;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

public class Menu extends BasicGameState {

	UnicodeFont fontSmall;
	UnicodeFont fontLarge;

	private int width = LegendsOfArborea.WIDTH;
	private int height = LegendsOfArborea.HEIGHT;
	
	Image OptionButton;
	Image PlayButton;
	
	public Menu(int state) {
		
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		// Load custom font	
		fontSmall = new UnicodeFont("sprites/ui/fonts/DARK11__.ttf", 27, false, false);
		fontLarge = new UnicodeFont("sprites/ui/fonts/DARK11__.ttf", 50, false, false);
		fontSmall.addAsciiGlyphs();
		fontSmall.getEffects().add(new ColorEffect());
		fontSmall.loadGlyphs();
		fontLarge.addAsciiGlyphs();
		fontLarge.getEffects().add(new ColorEffect());
		fontLarge.loadGlyphs();
		
		PlayButton = new Image("sprites/ui/PNG/red_button11.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		fontLarge.drawString(width / 2 -150, height / 10, "Legends of Arborea");
		g.drawImage(PlayButton, width / 2 - PlayButton.getWidth() / 2, height / 2 - PlayButton.getHeight() / 2);
		fontSmall.drawString(width / 2 - 30, height / 2 - 10, "PLAY");
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		
	}

	@Override
	public int getID() {
		return LegendsOfArborea.MENU;
	}

}
