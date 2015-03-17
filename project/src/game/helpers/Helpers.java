package game.helpers;

import java.util.Random;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class Helpers {
	public static int randInt(int min, int max) {

	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	// Returns font based on name and font size
	@SuppressWarnings("unchecked") // We need one effect for UnicodeFonts
	public static UnicodeFont getFont(String name, int size) throws SlickException {
		UnicodeFont font = new UnicodeFont("res/ui/fonts/" + name + ".ttf", size, false, false);
		font.addAsciiGlyphs();
		font.getEffects().add(new ColorEffect());
		font.loadGlyphs();
		return font;
	}
}