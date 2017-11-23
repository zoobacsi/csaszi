package hu.csaszi.gameengine.physics.objects;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform {

    public Vector3f pos;
    public Vector3f scale;

    public Transform() {
        this.pos = new Vector3f();
        this.scale = new Vector3f();
    }

    public Matrix4f getProjection(Matrix4f target) {
        target.scale(scale);
        target.translate(pos);
        return target;
    }
}
