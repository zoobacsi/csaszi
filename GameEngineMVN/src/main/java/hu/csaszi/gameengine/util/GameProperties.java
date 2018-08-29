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
        clone.setEnemyTurnTime(enemyTurnTime);
        clone.setPlayerSpeed(playerSpeed);

        return clone;
    }

    private float jumpHeight;

    public float getEnemyTurnTime() {
        return enemyTurnTime;
    }

    public void setEnemyTurnTime(float enemyTurnTime) {
        this.enemyTurnTime = enemyTurnTime;
    }

    private float enemyTurnTime;

    public float getPlayerSpeed() {
        return playerSpeed;
    }

    public void setPlayerSpeed(float playerSpeed) {
        this.playerSpeed = playerSpeed;
    }

    private float playerSpeed;

//
//    GameProperties(int scale, int jumpHeight) {
//
//        this.scale = scale;
//        this.jumpHeight = jumpHeight;
//    }
}
