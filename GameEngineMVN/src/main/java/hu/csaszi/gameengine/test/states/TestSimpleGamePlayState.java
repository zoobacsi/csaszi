package hu.csaszi.gameengine.test.states;

import hu.csaszi.gameengine.audio.AudioClip;
import hu.csaszi.gameengine.audio.AudioPlayer;
import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.game.GameState;
import hu.csaszi.gameengine.input.Input;
import hu.csaszi.gameengine.physics.objects.ObjectManager;
import hu.csaszi.gameengine.render.core.Drawer;
import hu.csaszi.gameengine.render.core.Window;
import hu.csaszi.gameengine.test.objects.BlockSpawner;
import hu.csaszi.gameengine.test.objects.Player;

import java.awt.event.KeyEvent;

public class TestSimpleGamePlayState extends GameState {

	AudioClip clip;

	public TestSimpleGamePlayState(int stateId) {
		this.stateId = stateId;
	}

	@Override
	public void init(Window window, GameManager gameManager) {

		clip = new AudioClip("src/main/resources/gate.wav");

		for(int i = 0; i < 6; i++){
			ObjectManager.addObject(new BlockSpawner(window, i*64+64));
		}
		
		ObjectManager.addObject(new Player(window));
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

		if(input.isKeyPressed(KeyEvent.VK_SPACE)){
			AudioPlayer.playSound(clip);
		}
		if(input.isKeyDown(KeyEvent.VK_ESCAPE)){
			window.close();
		}

	}

}
