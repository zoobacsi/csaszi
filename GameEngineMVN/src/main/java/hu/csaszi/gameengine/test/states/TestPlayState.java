package hu.csaszi.gameengine.test.states;

import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.game.GameState;
import hu.csaszi.gameengine.input.Input;
import hu.csaszi.gameengine.input.Mouse;
import hu.csaszi.gameengine.physics.objects.ObjectManager;
import hu.csaszi.gameengine.render.core.Drawer;
import hu.csaszi.gameengine.render.core.Window;
import hu.csaszi.gameengine.render.graphics.imaging.SpriteSheet;
import hu.csaszi.gameengine.test.objects.Block;

import java.awt.event.KeyEvent;

public class TestPlayState extends GameState {

	private SpriteSheet spriteSheet;
	
	private int y = 300;
	private int x = 200;
	private int cx = 0;
	private int cy = 0;
	
	private Block block;
	private String coords = "0, 0";
	
	public TestPlayState(int stateId){
	
		this.stateId = stateId;
	}
	
	@Override
	public void init(Window window, GameManager gameManager) {
		//spriteSheet = new SpriteSheet(new Image("res/spritesheet.png"), 64, 64);
		
		int y = 32;
		for(int i = 0; i < 20; i++){
			Block block = new Block(32, y, 32);
			ObjectManager.addObject(block);
			y += 34;
		}
		
		System.out.println("Init completed");
	}

	@Override
	public void render(Window window, Drawer drawer, GameManager gameManager) {
		
		drawer.drawString("Coords: " + coords, 100, 300);
//		if(spriteSheet != null){
//			drawer.drawImage(spriteSheet.getSprite(1, 0), x, y);
//		}
		if(block != null){
			block.render(window, drawer);
		}
		
		drawer.setCameraPos(cx, cy);
	}

	@Override
	public void update(Window window, GameManager gameManager) {

		Input input = window.getInput();
		Mouse mouse = window.getMouse();
		if(block != null){
			block.update(window, gameManager);
		}
		
		coords = mouse.getX() + ", " + mouse.getY();
		
		if(input.isKeyDown(KeyEvent.VK_W)){
			cy--;
		}
		if(input.isKeyDown(KeyEvent.VK_S)){
			cy++;
		}
		if(input.isKeyDown(KeyEvent.VK_A)){
			cx--;
		}
		if(input.isKeyDown(KeyEvent.VK_D)){
			cx++;
		}
		
		if(input.isKeyDown(KeyEvent.VK_ESCAPE)){
			window.close();
		}

		x = mouse.getX() - 50 - cx;
		y = mouse.getY() - 50 - cy;
	}

}
