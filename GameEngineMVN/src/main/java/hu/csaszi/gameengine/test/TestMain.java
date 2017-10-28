package hu.csaszi.gameengine.test;

import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.render.core.Window;
import hu.csaszi.gameengine.test.states.TestPlayState;

public class TestMain extends GameManager {

	private static Window window;
	private static final int WIDTH = 700;
	private static final int HEIGHT = 500;
	private static final String TITLE = "GameEngineTest";
	
	private static final int GAME_ID = 0;
	
	public TestMain(){
		
		window = createWindow(TITLE, WIDTH, HEIGHT, false);
		window.show();
	}
	
	public static void main(String[] args) {

		TestMain main = new TestMain();
		main.addState(new TestPlayState(GAME_ID));
		
		main.enterState(GAME_ID, true);
		
		//Start Game
//		
//		while (window.isRunning()){
//			
//			window.clear(Color.BLACK);
//			window.update();
//		}
//		window.close();
	}

}
