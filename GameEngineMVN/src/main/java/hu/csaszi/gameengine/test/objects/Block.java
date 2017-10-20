package hu.csaszi.gameengine.test.objects;

import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.physics.objects.GameObject;
import hu.csaszi.gameengine.render.core.Window;

import java.awt.Color;

public class Block extends GameObject {

	public Block(int x, int y, int size){
		this.x = x;
		this.y = y;
		this.sx = size;
		this.sy = size;
	}
	public Block(int x, int y, int size, Color color) {
		this(x, y, size);
		this.color = color;
	}

	@Override
	public void update(Window window, GameManager gameManager) {
		this.x += 1;
	}

}
