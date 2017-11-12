package hu.csaszi.gameengine.render.core;

import hu.csaszi.gameengine.game.GameManager;

public interface Window  {

	public void show();

	public void update();

	public void clear();

	public void close();

	public int getWidth();
	public int getHeight();

	public void run();

	public String getTitle();

	public boolean shouldClose();
	
	public void setFullscreen(boolean fullscreen);
//
//	public boolean isRunning();
//
//	public void increaseFrames();
//
//	public void increaseTicks();
//
//	public void increaseTime();
//
	public Drawer getDrawer();
//
//	public AWTInput getInput();

	public GameManager getGameManager();
}
