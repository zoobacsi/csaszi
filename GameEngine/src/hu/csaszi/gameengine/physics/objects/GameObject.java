package hu.csaszi.gameengine.physics.objects;

import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.render.core.Drawer;
import hu.csaszi.gameengine.render.core.Window;
import hu.csaszi.gameengine.render.graphics.Image;

import java.awt.Color;

public abstract class GameObject {

	protected int x;
	protected int y;
	protected int sx;
	protected int sy;
	
	protected boolean doDraw = true;
	protected boolean didDraw;
	protected boolean hasImage;
	protected boolean isDestroyed;
	
	protected Color color = Color.WHITE;
	protected String tag;
	
	protected Image image;
	
	public void render(Window window, Drawer drawer){
		
		if(doDraw){
			if(hasImage){
				drawer.drawImage(image, x, y);
			} else {
				
				drawer.fillRect(x, y, sx, sy, color);
			}
			didDraw = true;
		}
	}
	
	public boolean isDestroyed() {
		return isDestroyed;
	}

	public void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	public abstract void update(Window window, GameManager gameManager);

	public int getX(){
		return x;
	}

	public int getY() {
		return y;
	}

	public int getSx() {
		return sx;
	}

	public int getSy() {
		return sy;
	}

	public boolean isDidDraw() {
		return didDraw;
	}
	
	public String getTag(){
		return tag;
	}
}
