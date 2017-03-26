package hu.csaszi.twodee.input;

import hu.csaszi.twodee.util.Direction;

import org.newdawn.slick.Input;


public class InputUtil {
	
	private static InputUtil instance;
	
	private InputUtil(){
		
	}
	
	public static InputUtil getInstance(){
		if(instance == null){
			instance = new InputUtil();
		}
		return instance;
	}
	
	public static Direction getDirection(Input input){
		return getInstance().getDirectionImpl(input);
	}
	
	public Direction getDirectionImpl(Input input) {

		int direction = -1;

		boolean upDir = input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W);
		boolean downDir = input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S);
		boolean leftDir = input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A);
		boolean rightDir = input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D);

		if (upDir) {
			if (leftDir && !rightDir) {
				direction = 7;
			}
			else if (!leftDir && rightDir) {
				direction = 1;
			}
			else if ((!leftDir && !rightDir) || (leftDir && rightDir)) {
				direction = 0;
			}
		}
		else if (downDir) {
			if (leftDir && !rightDir) {
				direction = 5;
			}
			else if (!leftDir && rightDir) {
				direction = 3;
			}
			else if ((!leftDir && !rightDir) || (leftDir && rightDir)) {
				direction = 4;
			}
		}
		else {
			if (leftDir && !rightDir) {
				direction = 6;
			}
			else if (!leftDir && rightDir) {
				direction = 2;
			}
		}
		return Direction.getById(direction);
	}

}
