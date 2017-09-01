package hu.csaszi.gameengine.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

public class Mouse implements MouseListener, MouseMotionListener,
		MouseWheelListener {

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

	public boolean isButtonPressed(int button) {

		if (pressedButtons.indexOf(button) != -1) {
			pressedButtons.remove(pressedButtons.indexOf(button));
			return true;
		}

		return false;
	}

	public boolean isButtonDown(int button) {

		return downButtons.indexOf(button) != -1;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
}
