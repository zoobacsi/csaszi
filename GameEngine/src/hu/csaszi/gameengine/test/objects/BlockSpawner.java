package hu.csaszi.gameengine.test.objects;

import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.physics.objects.GameObject;
import hu.csaszi.gameengine.physics.objects.ObjectManager;
import hu.csaszi.gameengine.render.core.Window;

import java.util.Random;

public class BlockSpawner extends GameObject {

	private int ticksPassed;
	private int timer = 32;
	
	private boolean movingDown = true;

	public BlockSpawner(Window window, int y) {

		this.sx = 1;
		this.sy = 1;

		this.x = window.getWidth() + 64;
		this.y = y;

		Random random = new Random();
		timer += random.nextInt(64);
		
		this.tag = "blockSpawner";
	}

	@Override
	public void update(Window window, GameManager gameManager) {

		ticksPassed++;

		if (ticksPassed > timer) {

			spawn();
			ticksPassed = 0;
		}
		
		if(movingDown){
			y +=5;
			if(y > window.getHeight()-1) {
				movingDown = !movingDown;
			}
		} else {
			y -= 5;
			
			if(y<32){
				movingDown = !movingDown;
			}
		}
	}

	private void spawn() {

		if(Player.isPlayerAlive()){
			ObjectManager.addObject(new EnemyBlock(x, y));
		}
	}

}
