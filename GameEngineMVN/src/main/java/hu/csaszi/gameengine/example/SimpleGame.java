package hu.csaszi.gameengine.example;

import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.render.core.Window;
import hu.csaszi.gameengine.render.core.gl.GLFWWindow;
import hu.csaszi.gameengine.example.states.TestSimpleGamePlayState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleGame extends GameManager {

    private static Window window;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final String TITLE = "Simple Game Test";

    private static final int GAME_ID = 0;

    private static Logger logger = LoggerFactory.getLogger(SimpleGame.class);

    public SimpleGame() {
        instance = this;
        window = createWindow(TITLE, WIDTH, HEIGHT);
    }

    public static void main(String[] args) {

        logger.info("example");

        final SimpleGame main = new SimpleGame();

        main.addState(new TestSimpleGamePlayState(GAME_ID));
        main.enterState(GAME_ID, false);


        ((GLFWWindow)window)
                .setSize(WIDTH, HEIGHT)
                .setFullscreen(false);
        ((GLFWWindow)window).setCallbacks();

        window.run();

    }

}
