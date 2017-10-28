package hu.csaszi.gameengine.test.states;

import hu.csaszi.gameengine.audio.AudioClip;
import hu.csaszi.gameengine.audio.AudioPlayer;
import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.game.GameState;
import hu.csaszi.gameengine.input.Input;
import hu.csaszi.gameengine.physics.objects.ObjectManager;
import hu.csaszi.gameengine.render.core.Drawer;
import hu.csaszi.gameengine.render.core.Window;
import hu.csaszi.gameengine.render.graphics.gui.GUIManager;
import hu.csaszi.gameengine.render.graphics.gui.Label;
import hu.csaszi.gameengine.test.objects.BlockSpawner;
import hu.csaszi.gameengine.test.objects.Player;

import java.awt.event.KeyEvent;

public class TestSimpleGamePlayState extends GameState {

	public TestSimpleGamePlayState(int stateId) {
		this.stateId = stateId;
	}

	@Override
	public void init(Window window, GameManager gameManager) {

		AudioPlayer.addClip("playerHit", new AudioClip("src/main/resources/phit.wav"));

		for(int i = 0; i < 6; i++){
			ObjectManager.addObject(new BlockSpawner(window, i*64+64));
		}
		
		ObjectManager.addObject(new Player(window, "playerHit"));

		GUIManager.add(new Label("Volume: " + Math.round(AudioPlayer.getVolume() * 100f),"Apple LiGothic",16,0,window.getHeight()));
	}

	@Override
	public void render(Window window, Drawer drawer, GameManager gameManager) {
		drawer.drawString("Score: " + Player.getScore(), window.getWidth()/2 - 34, 10);
	
		if(!Player.isPlayerAlive()){
			drawer.drawString("Press Space To Restart, Score: " + Player.getScore(), window.getWidth()/2-34, window.getHeight()/2 - 30);
		}
	}

	@Override
	public void update(Window window, GameManager gameManager) {
		
		Input input = window.getInput();

		if(input.isKeyDown(KeyEvent.VK_Y)){
			AudioPlayer.decreaseVolume();
			GUIManager.getObjects().get(0).setLabelText("Volume: " + Math.round(AudioPlayer.getVolume() * 100f));
		}
		if(input.isKeyDown(KeyEvent.VK_X)){
			AudioPlayer.increaseVolume();
			GUIManager.getObjects().get(0).setLabelText("Volume: " + Math.round(AudioPlayer.getVolume() * 100f));
		}

		if(input.isKeyDown(KeyEvent.VK_ESCAPE)){
			window.close();
		}
	}
}
