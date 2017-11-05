package hu.csaszi.gameengine.render.core.gl;

public class Timer {

    public static double getTime() {
        return (double)System.nanoTime() / 1000000000.0;
    }
}
