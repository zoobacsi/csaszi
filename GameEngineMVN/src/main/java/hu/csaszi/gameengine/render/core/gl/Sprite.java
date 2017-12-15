package hu.csaszi.gameengine.render.core.gl;

public interface Sprite {

    default void bind(){
        bind(0);
    }

    void bind(int sample);

    float getRatio();
}
