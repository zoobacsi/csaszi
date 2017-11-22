package hu.csaszi.gameengine.test.objects;

import hu.csaszi.gameengine.audio.AudioPlayer;
import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.physics.collission.Collission;
import hu.csaszi.gameengine.physics.objects.GameObject;
import hu.csaszi.gameengine.physics.objects.ObjectManager;
import hu.csaszi.gameengine.physics.world.World;
import hu.csaszi.gameengine.render.core.Window;
import hu.csaszi.gameengine.render.core.gl.GLFWWindow;
import hu.csaszi.gameengine.render.core.gl.renderer.Camera;
import hu.csaszi.gameengine.test.states.TestSimpleGamePlayState;

public class Player extends GameObject {

	private static boolean isAlive = true;
	private static int score;
	private String hitSound;
	
	public Player(Window window, String hitSound){
		
		this.sx = 32;
		this.sy = sx;
		
		this.x = 100;
		this.y = window.getHeight()/2- sy/2;
		
		this.tag = "player";
		this.hitSound = hitSound;
	}

	@Override
	public void update(float delta, GameManager gameManager) {

		if(gameManager.isSoftwareRender()) {

		} else {
			GLFWWindow window = (GLFWWindow)gameManager.getWindow();
			Camera camera = ((TestSimpleGamePlayState)gameManager.getCurrentState()).getCamera();
			World world = ((TestSimpleGamePlayState)gameManager.getCurrentState()).getWorld();
		}


//		AWTInput AWTInput = window.getInput();
//
//		if(!isAlive){
//			if(AWTInput.isKeyDown(KeyEvent.VK_SPACE)){
//				isAlive = true;
//				score = 0;
//				ObjectManager.clearGameObjects();
//				gameManager.enterState(0, true);
//			}
//			return;
//		}
//		score++;
//
//		checkCollission(gameManager);
//
//		if(AWTInput.isKeyDown(KeyEvent.VK_W)){
//			y -= 5;
//		} else if(AWTInput.isKeyDown(KeyEvent.VK_S)){
//			y += 5;
//		}
//
//		if(y > window.getHeight() - sy){
//			y = window.getHeight() - sy;
//		} else if( y <= 0){
//			y = 0;
//		}
		
	}

	public static int getScore() {
		return score;
	}

	private void checkCollission(GameManager gameManager) {
		
		for(GameObject gameObject : ObjectManager.getObjects()){
			if("block".equals(gameObject.getTag())){
				if(Collission.isColliding(this, gameObject)){

					if(hitSound != null && !hitSound.isEmpty()){
						AudioPlayer.playSound(hitSound);
					}

					System.out.println("You Died!");
					isAlive = false;
				}
			}
		}
	}
	
	public static boolean isPlayerAlive(){
		return isAlive;
	}
}
