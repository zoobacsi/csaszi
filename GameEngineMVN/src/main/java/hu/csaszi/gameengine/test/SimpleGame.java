package hu.csaszi.gameengine.test;

import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.render.core.Window;
import hu.csaszi.gameengine.render.core.gl.GLFWWindow;
import hu.csaszi.gameengine.test.states.TestSimpleGamePlayState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleGame extends GameManager {

    private static Window window;
    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;
    private static final String TITLE = "Simple Game Test";

    private static final int GAME_ID = 0;

    private static Logger logger = LoggerFactory.getLogger(SimpleGame.class);

    public SimpleGame() {
        window = createWindow(TITLE, WIDTH, HEIGHT, false);

    }

    public static void main(String[] args) {

        logger.info("test");

        final SimpleGame main = new SimpleGame();

        main.addState(new TestSimpleGamePlayState(GAME_ID));
        main.enterState(GAME_ID, true);

        ((GLFWWindow)window)
                .setSize(640, 480)
                .setFullscrean(false);
        ((GLFWWindow)window).setCallbacks();

        window.run();
    }

}
