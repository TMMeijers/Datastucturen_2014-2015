package game.states;

import game.LegendsOfArborea;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Play extends BasicGameState {

	private int width = LegendsOfArborea.WIDTH;
	private int height = LegendsOfArborea.HEIGHT;
	
	public Play(int state) {
		
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {

	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {

	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {

	}

	@Override
	public int getID() {
		return LegendsOfArborea.PLAY;
	}

}
