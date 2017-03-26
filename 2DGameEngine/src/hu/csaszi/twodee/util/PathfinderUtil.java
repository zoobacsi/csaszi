package hu.csaszi.twodee.util;

public class PathfinderUtil {

	public static Direction getDirection(float startX, float startY, float destinationX, float destinationY) {
		float disX = startX - destinationX;
		float disY = startY - destinationY;
		double atan = Math.atan(disY / disX);
		double angle = Math.toDegrees(atan);
		int direction = -1;

		if (disX < 0 && disY < 0) {
			if (angle > 0 && angle <= 25) {
				direction = 2;
			} else if (angle > 25 && angle <= 75) {
				direction = 3;
			} else if (angle > 75) {
				direction = 4;
			}
		} else if (disX < 0 && disY > 0) {
			if (angle < -75) {
				direction = 0;
			} else if (angle >= -75 && angle < -25) {
				direction = 1;
			} else if (angle >= -25) {
				direction = 2;
			}
		} else if (disX > 0 && disY < 0) {
			if (angle < -75) {
				direction = 4;
			} else if (angle >= -75 && angle < -25) {
				direction = 5;
			} else if (angle >= -25) {
				direction = 6;
			}
		} else if (disX > 0 && disY > 0) {
			if (angle > 0 && angle <= 25) {
				direction = 6;
			} else if (angle > 25 && angle <= 75) {
				direction = 7;
			} else if (angle > 75) {
				direction = 0;
			}
		}

		return Direction.getById(direction);

		// System.out.println("x: " + disX);
		// System.out.println("y: " + disY);
		// System.out.println("angle: " + angle);
	}
}
