package hu.csaszi.gameengine.render.core.gl;

public class Animation implements Sprite{

    private Texture[] frames;
    private int pointer;

    private double elapsedTime;
    private double currentTime;
    private double lastTime;
    private double fps;

    public Animation(int amount, int fps, String filename) {
        this.pointer = 0;
        this.elapsedTime = 0;
        this.currentTime = 0;
        this.lastTime = Timer.getTime();
        this.fps = 1.0/(double)fps;

        this.frames = new Texture[amount];
        for (int i = 0; i < amount; i++){
            this.frames[i] = new Texture("anim/" + filename + "_" + i);
        }
    }

    @Override
    public void bind(){
        bind(0);
    }

    @Override
    public void bind(int sampler) {
        this.currentTime = Timer.getTime();
        this.elapsedTime += currentTime - lastTime;

        if(elapsedTime >= fps) {
            elapsedTime -= fps;
            pointer++;
        }

        if(pointer >= frames.length) {
            pointer = 0;
        }

        this.lastTime = currentTime;

        frames[pointer].bind(sampler);
    }

    @Override
    public float getRatio() {
        return frames[0].getRatio();
    }
}
