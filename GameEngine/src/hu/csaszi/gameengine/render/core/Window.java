package hu.csaszi.gameengine.render.core;

import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.input.Input;
import hu.csaszi.gameengine.input.Mouse;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Window extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static String TITLE;
	private static int WIDTH;
	private static int HEIGHT;
	private static int BUFFER_SIZE;
	private static JFrame FRAME;

	private GameManager gameManager;
	private Drawer drawer;
	
	private Input input = new Input();
	private Mouse mouse = new Mouse();
	
	private int frames, ticks, time;
	private int lastFrames, lastTicks;

	private Thread loop;
	private final double UPDATE_SPEED = 60;
	private static boolean isRunning;

	public Window(String title, int width, int height, int bufferSize, GameManager gameManager) {

		this.gameManager = gameManager;
		
		Window.TITLE = title;
		Window.WIDTH = width;
		Window.HEIGHT = height;
		Window.BUFFER_SIZE = bufferSize;

		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size);
		setSize(size);
		
		setFocusable(true);

		FRAME = new JFrame(TITLE);
		FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FRAME.setSize(size);
		FRAME.setPreferredSize(size);
		FRAME.add(this);
		FRAME.pack();
		FRAME.setResizable(false);
		FRAME.setLocationRelativeTo(null);
	}

	public void show() {

		this.createBufferStrategy(BUFFER_SIZE);

		isRunning = true;
		FRAME.setVisible(true);
		
		drawer = new Drawer(this);
		
		startInputListeners();
		
		gameLoop();
	}
	
	private void startInputListeners(){
		this.addKeyListener(input);
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);
		this.addMouseWheelListener(mouse);
	}

	public void update() {
		if (!isRunning()) {
			System.out.println("WINDOW NOT INITIALIZED!");
		}
		this.getBufferStrategy().show();
	}

	public void clear(Color clearColor) {

		if (!isRunning()) {
			System.out.println("WINDOW NOT INITIALIZED!");
		}

		BufferStrategy bufferStrategy = this.getBufferStrategy();

		Graphics g = bufferStrategy.getDrawGraphics();
		g.setColor(clearColor);
		g.fillRect(0, 0, WIDTH, HEIGHT);

	}

	public void close() {
		System.out.println("Closing");
		FRAME.dispose();
		loop.interrupt();
		System.exit(0);
	}
	
	public void setFullscreen(boolean fullscreen){
		if(fullscreen && !isRunning){
			System.out.println("miafasz");
			FRAME.dispose();
			FRAME.setUndecorated(true);
			FRAME.setExtendedState(JFrame.MAXIMIZED_BOTH);
			FRAME.setVisible(true);
		}
		WIDTH = FRAME.getWidth();
		HEIGHT = FRAME.getHeight();
	}

	public boolean isRunning() {
		return isRunning;
	}

	private void gameLoop() {

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
