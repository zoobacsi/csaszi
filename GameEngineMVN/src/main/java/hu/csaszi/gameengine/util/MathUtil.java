package hu.csaszi.gameengine.util;

import hu.csaszi.gameengine.physics.Point;

public class MathUtil {

    public static int convert(float number){
        return (int)Math.round(Math.floor(number));
    }

    public static Point convertCoordsToIsometric(int x, int y, float ratio) {
        float xPoint = (x - y) * 1.0f  * 2f/*/ 2.0f*/;
        float yPoint = (x + y) * ratio * 1f/*/ 2.0f*/;
        return new Point(xPoint, yPoint);
    }

    public static Point convertCoordsToIsometric(float x, float y, float ratio) {
        float xPoint = (x - y) * 1 / 2;
        float yPoint = (x + y) * ratio / 2;
        return new Point(xPoint, yPoint);
    }
}
