package hu.csaszi.gameengine.test;

import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.render.core.Window;

public class Main extends GameManager {

	private static Window window;
	private static final int WIDTH = 700;
	private static final int HEIGHT = 500;
	private static final int BUFFER_SIZE = 2;
	private static final String TITLE = "Game Engine Test";
	
	private static final int GAME_ID = 0;
	
	public Main(){
		
		window = createWindow(TITLE, WIDTH, HEIGHT, BUFFER_SIZE);
		window.show();
	}
	
	public static void main(String[] args) {

		Main main = new Main();
		main.addState(new TestPlay(GAME_ID));
		
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
