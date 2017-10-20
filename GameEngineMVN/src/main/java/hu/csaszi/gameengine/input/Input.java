package hu.csaszi.gameengine.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class Input implements KeyListener {

	private List<Integer> pressedKeys = new ArrayList<>();
	private List<Integer> downKeys = new ArrayList<>();

	@Override
	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();
		if (downKeys.indexOf(key) == -1) {
			pressedKeys.add(key);
			downKeys.add(key);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

		int key = e.getKeyCode();
		if (pressedKeys.indexOf(key) != -1) {
			pressedKeys.remove(pressedKeys.indexOf(key));

		}
		if (downKeys.indexOf(key) != -1) {
			downKeys.remove(downKeys.indexOf(key));
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	public boolean isKeyPressed(int key) {
		
		if (pressedKeys.indexOf(key) != -1) {
			pressedKeys.remove(pressedKeys.indexOf(key));
			return true;
		}
		return false;
	}
	
	public boolean isKeyDown(int key) {
		
		return downKeys.indexOf(key) != -1;
	}
}
