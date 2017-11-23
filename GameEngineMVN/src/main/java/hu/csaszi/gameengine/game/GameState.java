package hu.csaszi.gameengine.game;

import hu.csaszi.gameengine.render.core.Drawer;
import hu.csaszi.gameengine.render.core.Window;

public abstract class GameState {

	protected int stateId;
	
	public abstract void init(Window window, GameManager gameManager);
	
	public abstract void render(Window window, Drawer drawer, GameManager gameManager);

	public abstract void update(float delta, GameManager gameManager);
	
	public int getStateId(){
		return stateId;
	}
}
