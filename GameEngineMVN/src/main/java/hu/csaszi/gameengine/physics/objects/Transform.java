package hu.csaszi.gameengine.physics.objects;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform {

    public Vector3f pos;
    public Vector3f scale;

    public static float maxWidth;
    public static float maxHeigth;

    public Transform() {
        this.pos = new Vector3f();
        this.scale = new Vector3f(1, 1, 1);
    }

    public Matrix4f getProjection(Matrix4f target) {
        target.translate(pos);
        target.scale(scale);
        return target;
    }

    public Vector3f getPosition() {
        return pos;
    }

    public static void setWorldBoundaries(int width, int height){
        maxWidth = (width - 1) * 2;
        maxHeigth = (height - 1) * 2;
    }

    public static float getMaxWidth(){
        return maxWidth;
    }

    public static float getMaxHeigth() {
        return maxHeigth;
    }
}
