package hu.csaszi.gameengine.test.objects;

import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.physics.objects.GameObject;
import hu.csaszi.gameengine.render.core.Window;

import java.awt.Color;

public class EnemyBlock extends GameObject {

	private static int velocity = 3;
	
	public EnemyBlock(int x, int y) {
		this.sx = 32;
		this.sy = 32;
		
		this.x = x;
		this.y = y;
		this.color = Color.RED;
		
		this.tag = "block";
	}

	@Override
	public void update(Window window, GameManager gameManager) {
		this.x -= velocity;
		
		if(this.x < 0 - sx) {
			this.isDestroyed = true;
		}
	}

}
