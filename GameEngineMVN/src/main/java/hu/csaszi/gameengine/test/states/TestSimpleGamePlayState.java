package hu.csaszi.gameengine.test.states;

import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.game.GameState;
import hu.csaszi.gameengine.render.core.Drawer;
import hu.csaszi.gameengine.render.core.Window;

public class TestSimpleGamePlayState extends GameState {

	public TestSimpleGamePlayState(int stateId) {
		this.stateId = stateId;
	}

	@Override
	public void init(Window window, GameManager gameManager) {
//
//		AudioPlayer.addClip("playerHit", new AudioClip("src/main/resources/phit.wav"));
//
//		for(int i = 0; i < 6; i++){
//			ObjectManager.addObject(new BlockSpawner(window, i*64+64));
//		}
//
//		ObjectManager.addObject(new Player(window, "playerHit"));
	}

	@Override
	public void render(Window window, Drawer drawer, GameManager gameManager) {
//		drawer.drawString("Score: " + Player.getScore(), window.getWidth()/2 - 34, 10);
//
//		if(!Player.isPlayerAlive()){
//			drawer.drawString("Press Space To Restart, Score: " + Player.getScore(), window.getWidth()/2-34, window.getHeight()/2 - 30);
//		}
	}

	@Override
	public void update(Window window, GameManager gameManager) {
		
//		AWTInput AWTInput = window.getInput();
//
//		if(AWTInput.isKeyDown(KeyEvent.VK_Y)){
//			AudioPlayer.decreaseVolume();
//			GUIManager.getObjects().get(0).setLabelText("Volume: " + Math.round(AudioPlayer.getVolume() * 100f));
//		}
//		if(AWTInput.isKeyDown(KeyEvent.VK_X)){
//			AudioPlayer.increaseVolume();
//			GUIManager.getObjects().get(0).setLabelText("Volume: " + Math.round(AudioPlayer.getVolume() * 100f));
//		}
//
//		if(AWTInput.isKeyDown(KeyEvent.VK_ESCAPE)){
//			window.close();
//		}
	}
}
