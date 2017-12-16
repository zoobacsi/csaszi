package hu.csaszi.gameengine.render.core.software;

import hu.csaszi.gameengine.render.core.Drawer;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class SoftwareDrawer implements Drawer{

	private hu.csaszi.gameengine.render.core.Window window;
	private BufferStrategy bufferStrategy;
	private Graphics g;

	private int cameraX;
	private int cameraY;

	public SoftwareDrawer(SoftwareWindow window) {

		this.window = window;
		this.bufferStrategy = window.getBufferStrategy();
		this.g = bufferStrategy.getDrawGraphics();
		g.setColor(Color.white);
	}

	public void drawImage(java.awt.Image img, int x, int y) {
		g.drawImage(img, x, y, null);
	}

	public void drawString(String string, int x, int y) {
		g.drawString(string, x, y);
	}

	public void drawString(String string, int x, int y, Color color) {
		Color oldColor = g.getColor();
		setColor(color);
		g.drawString(string, x, y);
		setColor(oldColor);
	}

	public void drawString(String string, Font font, int x, int y, Color color) {

		Font oldFont = g.getFont();
		g.setFont(font);
		drawString(string, x, y, color);
		g.setFont(oldFont);
	}

	public void drawRect(int x, int y, int sx, int sy){
		g.drawRect(x, y, sx, sy);
	}

	public void drawRect(int x, int y, int sx, int sy, Color color){
		Color oldColor = g.getColor();
		setColor(color);
		drawRect(x, y, sx, sy);
		setColor(oldColor);
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
		g.setColor(Color.white);
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
