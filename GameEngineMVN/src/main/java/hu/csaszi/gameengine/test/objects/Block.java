package hu.csaszi.gameengine.test.objects;

import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.physics.objects.GameObject;
import hu.csaszi.gameengine.render.core.Window;
import hu.csaszi.gameengine.render.core.gl.Texture;

import java.awt.*;

public class Block extends GameObject {

	public Block(int x, int y, int size){
		super(new Texture("checker"));
		this.x = x;
		this.y = y;
		this.sx = size;
		this.sy = size;
	}
	public Block(int x, int y, int size, Color color) {
		this(x, y, size);
		this.color = color;
	}
//
//	@Override
//	public void update(Window window, GameManager gameManager) {
//		this.x += 1;
//	}

	@Override
	public void update(float delta, GameManager gameManager) {

	}
}
