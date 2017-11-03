package hu.csaszi.gameengine.test;

import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.render.core.Window;
import hu.csaszi.gameengine.test.states.TestSimpleGamePlayState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleGame extends GameManager {

	private static Window window;
	private static final int WIDTH = 700;
	private static final int HEIGHT = 500;
	private static final String TITLE = "Simple Game Test";
	
	private static final int GAME_ID = 0;

	private static Logger logger = LoggerFactory.getLogger(SimpleGame.class);

	public SimpleGame() {
		
		window = createWindow(TITLE, WIDTH, HEIGHT, true);
		window.setFullscreen(true);
		window.show();
	}

	public static void main(String[] args) {

		logger.info("test");

		logger.debug("debug", new Exception("gdgfd"));
		TestMain main = new TestMain();
		main.addState(new TestSimpleGamePlayState(GAME_ID));
		
		main.enterState(GAME_ID, true);
	}
	
}
