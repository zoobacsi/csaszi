package hu.csaszi.gameengine.test;

import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.game.GameState;
import hu.csaszi.gameengine.render.core.Drawer;
import hu.csaszi.gameengine.render.core.Window;
import hu.csaszi.gameengine.render.graphics.Image;

public class TestPlay extends GameState {

	Image img;
	
	public TestPlay(int stateId){
	
		this.stateId = stateId;
	}
	
	@Override
	public void init(Window window, GameManager gameManager) {
		img = new Image("res/urgay.png");
		System.out.println("Init completed");
	}

	@Override
	public void render(Window window, Drawer drawer, GameManager gameManager) {
		
		drawer.drawString("Hello world!", 100, 300);
		if(img != null){
			drawer.drawImage(img, 200, 300);
		}
	}

	@Override
	public void update(Window window, GameManager gameManager) {
		// TODO Auto-generated method stub

	}

}
