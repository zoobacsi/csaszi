package hu.csaszi.gameengine.game;

import hu.csaszi.gameengine.input.AWTInput;
import hu.csaszi.gameengine.input.Input;
import hu.csaszi.gameengine.physics.objects.ObjectManager;
import hu.csaszi.gameengine.render.core.Drawer;
import hu.csaszi.gameengine.render.core.Window;
import hu.csaszi.gameengine.render.core.gl.GLFWWindow;
import hu.csaszi.gameengine.render.core.gl.GLInput;
import hu.csaszi.gameengine.render.core.gl.Timer;
import hu.csaszi.gameengine.render.core.software.SoftwareWindow;
import hu.csaszi.gameengine.render.graphics.gui.GUIManager;
import org.joml.Vector3f;

import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class GameManager {

	private static Set<Window> windowPool = new HashSet<>();

	private Set<GameState> states = new HashSet<>();
	private GameState currentState;
	private Window window;
	private boolean pause;
	private boolean softwareRender;
	private Input input;

	public GameManager(){
		this.softwareRender = true;
	}

	public GameManager(boolean softwareRender){
		this.softwareRender = softwareRender;
	}

	public boolean isPause() {
		return pause;
	}

	public boolean isSoftwareRender(){
		return softwareRender;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}

	// Game Access
	public void addState(GameState state) {

		states.add(state);
	}

	public void flushStates(GameState state) {
		states.clear();
	}

	public void enterState(int stateId, boolean doInit) {
		boolean stateFound = false;
		for (GameState state : states) {
			if (state.getStateId() == stateId) {
				currentState = state;
				stateFound = true;
				currentState.init(window, this);
				break;
			}
		}
		if (!stateFound) {
			throw new Error("No such state found: " + stateId);
		}
	}

	public Window createWindow(String title, int width, int height,
			boolean softwareRender) {

		if(softwareRender){

			window = new SoftwareWindow(title, width, height, 2, this);
			input = new AWTInput();

		} else {

			window = new GLFWWindow(title, width, height, this);
			input = new GLInput((GLFWWindow)window);
		}

		windowPool.add(window);

		return window;
	}

	// Window Access
	public void init() {

		if (isStateOpen()) {
			currentState.init(window, this);
		}
	}

	public void render() {
		if (isStateOpen()) {
			if (!pause) {

				currentState.render(window, window.getDrawer(), this);

//				ObjectManager.render(window, window.getDrawer());
//
//				GUIManager.render(window, window.getDrawer());
			}

//			window.increaseFrames();
		}
	}

	public void update() {

		if (isStateOpen()) {
			if (!pause) {
				currentState.update(window, this);

				ObjectManager.update(window, this);

				GUIManager.update(window);
			}
//			window.increaseTicks();
		}
	}

	private boolean isStateOpen() {

		if (currentState == null) {
			return false;
		}

		return true;
	}

	public Window getWindow(){

		return window;
	}

	public static Window getWindowFromPool(){

		if(windowPool != null && !windowPool.isEmpty()){
			return  windowPool.iterator().next();
		}

		return null;
	}

	public GameState getCurrentState(){
		return currentState;
	}

	public Input getInput() {
		return input;
	}

}
