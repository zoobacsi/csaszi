package hu.csaszi.gameengine.render.core;

import hu.csaszi.gameengine.render.graphics.Image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Drawer {

	private Window window;
	private BufferStrategy bufferStrategy;
	private Graphics g;

	private int cameraX;
	private int cameraY;

	public Drawer(Window window) {

		this.window = window;
		this.bufferStrategy = window.getBufferStrategy();
		this.g = bufferStrategy.getDrawGraphics();
		g.setColor(Color.WHITE);
	}

	public void drawImage(Image img, int x, int y) {
		g.drawImage(img.getRawImage(), x, y, null);
	}

	public void drawString(String string, int x, int y) {
		g.drawString(string, x, y);
	}

	public void fillRect(int x, int y, int sx, int sy, Color color) {
		
		Color oldColor = g.getColor();
		setColor(color);
		g.fillRect(x, y, sx, sy);
		setColor(oldColor);
	}
	
	public void fillRect(int x, int y, int sx, int sy) {
		g.fillRect(x, y, sx, sy);
	}

	public void setColor(Color color) {
		g.setColor(color);
	}

	public void setCameraPos(int cx, int cy) {
		g.translate(-cameraX, -cameraY);
		cameraX = cx;
		cameraY = cy;
		g.translate(cameraX, cameraY);
	}

	public void moveCamera(int mx, int my) {

		cameraX += mx;
		cameraY += my;
		g.translate(mx, my);
	}
	
	public int getCameraX(){
		return cameraX;
	}
	
	public int getCameraY(){
		return cameraY;
	}
}
