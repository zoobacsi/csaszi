package hu.csaszi.gameengine.input;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class AWTInput implements KeyListener, Input, MouseListener, MouseMotionListener,
		MouseWheelListener {

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

	@Override
	public boolean isKeyReleased(int key) {
		return false;
	}

	private static List<Integer> pressedButtons = new ArrayList<>();
	private static List<Integer> downButtons = new ArrayList<>();

	private static int x;
	private static int y;

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {

		int button = e.getButton();

		if (pressedButtons.indexOf(button) == -1) {
			pressedButtons.add(button);
			downButtons.add(button);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		int button = e.getButton();

		if (pressedButtons.indexOf(button) != -1) {
			pressedButtons.add(pressedButtons.indexOf(button));
		}
		if (downButtons.indexOf(button) != -1) {
			downButtons.add(downButtons.indexOf(button));
		}
	}

	public boolean isMouseButtonPressed(int button) {

		if (pressedButtons.indexOf(button) != -1) {
			pressedButtons.remove(pressedButtons.indexOf(button));
			return true;
		}

		return false;
	}

	public boolean isMouseButtonDown(int button) {

		return downButtons.indexOf(button) != -1;
	}

	@Override
	public boolean isMouseButtonReleased(int button) {
		return false;
	}

	public int getMouseX(){
		return x;
	}

	public int getMouseY(){
		return y;
	}
}
