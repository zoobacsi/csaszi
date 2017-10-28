package hu.csaszi.gameengine.render.core;

import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.input.Input;
import hu.csaszi.gameengine.input.Mouse;
import hu.csaszi.gameengine.render.core.Drawer;

import java.awt.Canvas;

import javax.swing.JFrame;

public abstract class Window extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected static String TITLE;
	protected static int WIDTH;
	protected static int HEIGHT;
	protected static int BUFFER_SIZE;
	protected static JFrame FRAME;

	protected GameManager gameManager;
	protected Drawer drawer;

	protected Input input = new Input();
	protected Mouse mouse = new Mouse();

	protected int frames, ticks, time;
	protected int lastFrames, lastTicks;

	protected Thread loop;
	protected final double UPDATE_SPEED = 60;
	protected static boolean isRunning;



	public abstract void show();
	
//	private void startInputListeners(){
//		this.addKeyListener(input);
//		this.addMouseListener(mouse);
//		this.addMouseMotionListener(mouse);
//		this.addMouseWheelListener(mouse);
//
//	}

	public abstract void update();

	public abstract void clear();

	public abstract void close();
	
	public abstract void setFullscreen(boolean fullscreen);

	public boolean isRunning() {
		return isRunning;
	}

	protected void gameLoop() {

		loop = new Thread(){
		
			public void run(){
				double lastTime = System.nanoTime();
				double delta = 0;
				final double ns = 1e9/UPDATE_SPEED;
				
				double start = System.currentTimeMillis();
				long next = 1L;
				
				while(isRunning()){
					
					double nowTime = System.nanoTime();
					double now = (System.currentTimeMillis() - start)/1000;
					delta += (nowTime - lastTime)/ns;
					
					lastTime = nowTime;
					
					while(delta >= 1){
						
						gameManager.update();
						
						gameManager.render();
						delta--;
					}
					
					if(now >= next){
						
						next++;
						time++;
						lastFrames = frames;
						lastTicks = ticks;
						System.out.println("FPS: " + lastFrames + " UPS: " + lastTicks);
						frames = 0;
						ticks = 0;
					}
				}
			}
		}; loop.start();
	}

	public void increaseFrames() {
		frames++;
	}

	public void increaseTicks() {
		ticks++;
	}

	public void increaseTime() {
		time++;
	}
	
	public Drawer getDrawer(){
		return drawer;
	}
	
	public Input getInput() {
		return input;
	}
	
	public Mouse getMouse(){
		return mouse;
	}
}
