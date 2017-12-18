package hu.csaszi.gameengine.game;

import hu.csaszi.gameengine.physics.world.World;
import hu.csaszi.gameengine.render.core.Drawer;
import hu.csaszi.gameengine.render.core.Window;
import hu.csaszi.gameengine.render.core.gl.GLFWWindow;
import hu.csaszi.gameengine.render.core.gl.renderer.Camera;
import hu.csaszi.gameengine.render.graphics.gui.GUI;

public abstract class GameState {

	protected int stateId;
	
	public abstract void init(GLFWWindow window, GameManager gameManager);
	
	public abstract void render(GLFWWindow window, Drawer drawer, GameManager gameManager);

	public abstract void update(float delta, GameManager gameManager);

	public abstract Camera getCamera();

	public abstract World getWorld();

	public abstract GUI getGUI();

	public int getStateId(){
		return stateId;
	}
}
