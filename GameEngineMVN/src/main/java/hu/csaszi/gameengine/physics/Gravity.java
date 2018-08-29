package hu.csaszi.gameengine.physics;

import hu.csaszi.gameengine.util.GameProperties;
import hu.csaszi.gameengine.util.MathUtil;
import hu.csaszi.gameengine.util.PropsUtil;
import org.joml.Vector2f;
import org.lwjgl.Sys;

import java.net.URL;

public enum Gravity {

    DOWN(0, MathUtil.RAD_360, new Vector2f(0, -1)),
    LEFT(90, MathUtil.RAD_90, new Vector2f(-1, 0)),
    UP(180, MathUtil.RAD_180, new Vector2f(0, 1)),
    RIGHT(270, MathUtil.RAD_270, new Vector2f(1, 0));

    protected static final float GRAVITY_SPEED_CONSTANT = 1.0f;

    protected boolean changeInProgress;

    private Gravity(int angle, double radian, Vector2f gravityVector){
        this.angle = angle;
        this.radian = radian;
        this.gravityVector = gravityVector.mul(GRAVITY_SPEED_CONSTANT);
    }

    public static Gravity getGravity(){
        return gravity;
    }

    public boolean isChangeInProgress() {
        return changeInProgress;
    }

    public void setChangeInProgress(boolean changeInProgress) {
        this.changeInProgress = changeInProgress;
    }

    public static void setGravity(float radian){

        if((MathUtil.RAD_270 + MathUtil.RAD_45 < radian) || (radian < MathUtil.RAD_45 )) {
            gravity = Gravity.DOWN;
        } else if((MathUtil.RAD_45 < radian) && (radian < MathUtil.RAD_90 + MathUtil.RAD_45 )) {
            gravity = Gravity.LEFT;
        } else if((MathUtil.RAD_90 + MathUtil.RAD_45 < radian) && (radian < MathUtil.RAD_180 + MathUtil.RAD_45 )) {
            gravity = Gravity.UP;
        } else if((MathUtil.RAD_180 + MathUtil.RAD_45 < radian) && (radian < MathUtil.RAD_270 + MathUtil.RAD_45 )) {
            gravity = Gravity.RIGHT;
        }
        System.out.println(gravity.toString());
        gravity.changeInProgress = true;
    }

    @Override
    public String toString() {
        return this.name();
    }

    public Vector2f getDirectedMovement(boolean right) {
        Vector2f movement = new Vector2f();

        switch (getGravity()){
            case DOWN:
                movement.set((right ? 1: -1) * PropsUtil.getProperties().getPlayerSpeed(), 0);
                break;
            case LEFT:
                movement.set(0, (right ? -1: 1) * PropsUtil.getProperties().getPlayerSpeed());
                break;
            case UP:
                movement.set((right ? -1: 1) * PropsUtil.getProperties().getPlayerSpeed(), 0);
                break;
            case RIGHT:
                movement.set(0, (right ? 1: -1) * PropsUtil.getProperties().getPlayerSpeed());
                break;
        }

        return movement;
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
