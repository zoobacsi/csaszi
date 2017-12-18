package hu.csaszi.gameengine.game;

import hu.csaszi.gameengine.render.core.Window;
import hu.csaszi.gameengine.render.core.gl.GLFWWindow;
import hu.csaszi.gameengine.render.core.software.SoftwareWindow;

import java.util.HashSet;
import java.util.Set;

public class GameManager {

    private static Set<GLFWWindow> windowPool = new HashSet<>();

    private Set<GameState> states = new HashSet<>();
    private GameState currentState;
    private GLFWWindow window;
    private boolean pause;
    private boolean softwareRender;
    private static GameManager instance;

    protected GameManager() {
        this.softwareRender = false;
    }

    protected GameManager(boolean softwareRender) {
        this.softwareRender = softwareRender;
    }

    public boolean isPause() {
        return pause;
    }

    public boolean isSoftwareRender() {
        return softwareRender;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
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
                if (doInit) {
                    currentState.init(window, this);
                }
                break;
            }
        }
        if (!stateFound) {
            throw new Error("No such state found: " + stateId);
        }
    }

    public GLFWWindow createWindow(String title, int width, int height) {

        window = new GLFWWindow(title, width, height, this);

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
//
//				GUIManager.render(window, window.getDrawer());
            }

//			window.increaseFrames();
        }
    }

    public void update(float delta) {

        if (isStateOpen()) {
            if (!pause) {
                currentState.update(delta, this);

//				GUIManager.update(window);
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

    public GLFWWindow getWindow() {

        return window;
    }

    public static GLFWWindow getWindowFromPool() {

        if (windowPool != null && !windowPool.isEmpty()) {
            return windowPool.iterator().next();
        }

        return null;
    }

    public GameState getCurrentState() {
        return currentState;
    }

}
