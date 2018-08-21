package hu.csaszi.gameengine.physics;

import hu.csaszi.gameengine.util.MathUtil;
import org.joml.Vector2f;

public enum Gravity {

    DOWN(0, MathUtil.RAD_360, new Vector2f(0, -1)),
    LEFT(90, MathUtil.RAD_90, new Vector2f(-1, 0)),
    UP(180, MathUtil.RAD_180, new Vector2f(0, 1)),
    RIGHT(270, MathUtil.RAD_270, new Vector2f(1, 0));

    protected static final float GRAVITY_SPEED_CONSTANT = 1.0f;

    private Gravity(int angle, double radian, Vector2f gravityVector){
        this.angle = angle;
        this.radian = radian;
        this.gravityVector = gravityVector.mul(GRAVITY_SPEED_CONSTANT);
    }

    public static Gravity getGravity(){
        return gravity;
    }

    public static void setGravity(float radian){

        if((MathUtil.RAD_270 + MathUtil.RAD_45 < radian) && (radian < MathUtil.RAD_45 )) {
            gravity = Gravity.DOWN;
        } else if((MathUtil.RAD_45 < radian) && (radian < MathUtil.RAD_90 + MathUtil.RAD_45 )) {
            gravity = Gravity.LEFT;
        } else if((MathUtil.RAD_90 + MathUtil.RAD_45 < radian) && (radian < MathUtil.RAD_180 + MathUtil.RAD_45 )) {
            gravity = Gravity.UP;
        } else if((MathUtil.RAD_180 + MathUtil.RAD_45 < radian) && (radian < MathUtil.RAD_270 + MathUtil.RAD_45 )) {
            gravity = Gravity.RIGHT;
        }
    }

    public int getAngle() {
        return angle;
    }

    public float getRadian() {
        return (float)radian;
    }

    private int angle;
    private double radian;

    public Vector2f getGravityVector() {
        return gravityVector;
    }

    private Vector2f gravityVector;


    private static Gravity gravity = Gravity.DOWN;
}
