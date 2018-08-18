package hu.csaszi.gameengine.util;

public class GameProperties implements Cloneable{

    private int scale;

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public float getJumpHeight() {
        return jumpHeight;
    }

    public void setJumpHeight(double jumpHeight) {
        this.jumpHeight = (float)jumpHeight;
    }

    @Override
    protected GameProperties clone() {

        GameProperties clone = new GameProperties();

        clone.setScale(scale);
        clone.setJumpHeight(jumpHeight);

        return clone;
    }

    private float jumpHeight;
//
//    GameProperties(int scale, int jumpHeight) {
//
//        this.scale = scale;
//        this.jumpHeight = jumpHeight;
//    }
}
