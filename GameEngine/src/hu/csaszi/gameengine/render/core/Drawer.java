package hu.csaszi.gameengine.render.core;

import hu.csaszi.gameengine.render.graphics.Image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Drawer {

	private Window window;
	private BufferStrategy bufferStrategy;
	private Graphics g;
	
	public Drawer(Window window){
		
		this.window = window;
		this.bufferStrategy = window.getBufferStrategy();
		this.g = bufferStrategy.getDrawGraphics();
		g.setColor(Color.BLUE);
	}
	
	public void drawImage(Image img, int x, int y) {
		g.drawImage(img.getRawImage(), x, y, null);
	}
	
	public void drawString(String string, int x, int y){
		g.drawString(string, x, y);
	}
	
	public void fillRect(int x, int y, int sx, int sy){
		g.fillRect(x, y, sx, sy);
	}
	
	public void setColor(Color color){
		g.setColor(color);
	}
}
