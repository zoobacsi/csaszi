package hu.csaszi.gameengine.game;

import hu.csaszi.gameengine.physics.objects.ObjectManager;
import hu.csaszi.gameengine.render.core.Window;
import hu.csaszi.gameengine.render.core.gl.GLFWWindow;
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
		} else {

			window = new GLFWWindow(title, width, height, this);
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
//			if (!pause) {
//				window.clear();
//
//				currentState.render(window, window.getDrawer(), this);
//
//				ObjectManager.render(window, window.getDrawer());
//
//				GUIManager.render(window, window.getDrawer());
//			}
			window.update();
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

	public void loop(){
		double frameCap = 1.0 / 60.0;

		double frameTime = 0;
		int frames = 0;

		double time = Timer.getTime();
		double unprocessed = 0.0;
		// Set the clear color
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while (!window.shouldClose()) {

			boolean canRender = false;

			double time2 = Timer.getTime();
			double passed = time2 - time;
			unprocessed += passed;

			frameTime += passed;
			time = time2;

			while (unprocessed >= frameCap) {

				unprocessed -= frameCap;

				canRender = true;

//                target = scale;

				if (window.input.isKeyDown(GLFW_KEY_ESCAPE)) {
					glfwSetWindowShouldClose(window, true);
				}


				if(input.isKeyDown(GLFW_KEY_A)) {
					camera.getPosition().sub(new Vector3f(-5,0,0));
				}
				if(input.isKeyDown(GLFW_KEY_D)) {
					camera.getPosition().sub(new Vector3f(5,0,0));
				}
				if(input.isKeyDown(GLFW_KEY_W)) {
					camera.getPosition().sub(new Vector3f(0,5,0));
				}
				if(input.isKeyDown(GLFW_KEY_S)) {
					camera.getPosition().sub(new Vector3f(0,-5,0));
				}

				world.correctCamera(camera, this);

				update();

				if (frameTime >= 1.0) {
					frameTime = 0.0;
					currentFPS = frames;
					System.out.println("FPS: " + currentFPS);
					frames = 0;
				}
			}

			if (canRender) {

				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

//                shader.bind();
//                shader.setUniform("sampler", 0);
//                shader.setUniform("projection", camera.getProjection().mul(target));
				//texture.bind(0);

				world.render(tileRenderer, shader, camera);

				glfwSwapBuffers(window); // swap the color buffers
				frames++;
			}
		}

	}
}
