package hu.csaszi.gameengine.test;

import java.awt.event.KeyEvent;

import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.game.GameState;
import hu.csaszi.gameengine.input.Input;
import hu.csaszi.gameengine.input.Mouse;
import hu.csaszi.gameengine.render.core.Drawer;
import hu.csaszi.gameengine.render.core.Window;
import hu.csaszi.gameengine.render.graphics.Image;

public class TestPlay extends GameState {

	Image img;
	
	private int y = 300;
	private int x = 200;
	
	private String coords = "0, 0";
	
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
		
		drawer.drawString("Coords: " + coords, 100, 300);
		if(img != null){
			drawer.drawImage(img, x, y);
		}
	}

	@Override
	public void update(Window window, GameManager gameManager) {

		Input input = window.getInput();
		Mouse mouse = window.getMouse();
		
		coords = mouse.getX() + ", " + mouse.getY();
		
		if(input.isKeyDown(KeyEvent.VK_W)){
			y -= 1;
		}
		if(input.isKeyDown(KeyEvent.VK_S)){
			y += 1;
		}
		if(input.isKeyDown(KeyEvent.VK_A)){
			x -= 1;
		}
		if(input.isKeyDown(KeyEvent.VK_D)){
			x += 1;
		}
		
		if(input.isKeyDown(KeyEvent.VK_ESCAPE)){
			window.close();
		}
	}

}
