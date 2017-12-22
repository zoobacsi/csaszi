package hu.csaszi.gameengine.render.core.gl;

import hu.csaszi.gameengine.physics.Direction;

public class Animation implements Sprite{

    private Texture[][] frames;
    private int pointer;

    private double elapsedTime;
    private double currentTime;
    private double lastTime;
    private double fps;
    private Direction currentDirection;

    public Animation(int fps, Texture[][] frames) {
        this(fps);
        this.frames = frames;
    }
    public Animation(int fps, TextureSheet sheet, int[][] tileNums) {
        this(fps);

        this.frames = new Texture[Direction.values().length][tileNums[0].length];
        for (Direction direction : Direction.values()) {
            for (int i = 0; i < tileNums[direction.getId()].length; i++) {
                this.frames[direction.getId()][i] = sheet.getTexture(tileNums[direction.getId()][i]);
            }
        }
    }
    public Animation(int amount, int fps, String filename) {
        this(fps);
        this.frames = new Texture[1][amount];
        for (int i = 0; i < amount; i++){
            this.frames[0][i] = new Texture("anim/" + filename + "_" + i);
        }
    }
    private Animation(int fps) {
        this.pointer = 0;
        this.elapsedTime = 0;
        this.currentTime = 0;
        this.lastTime = Timer.getTime();
        this.fps = 1.0/(double)fps;
    }

    @Override
    public void bind(int directionId, int sampler) {
        this.currentTime = Timer.getTime();
        this.elapsedTime += currentTime - lastTime;

        if(elapsedTime >= fps) {
//            elapsedTime -= fps;
            elapsedTime = 0;
            pointer++;
        }

        if(directionId > frames.length){
            directionId = 0;
        }
        Texture[] directedFrames = frames[directionId];

        if(pointer >= directedFrames.length) {
            pointer = 0;
        }

        this.lastTime = currentTime;

        frames[directionId][pointer].bind(directionId, sampler);
    }

    @Override
    public float getRatio() {
        return frames[0][0].getRatio();
    }
}
