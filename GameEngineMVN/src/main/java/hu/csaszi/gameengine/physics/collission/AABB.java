package hu.csaszi.gameengine.physics.collission;

import org.joml.Vector2f;

public class AABB {

    private Vector2f center;
    private Vector2f halfExtent;

    public AABB(Vector2f center, Vector2f halfExtent){
        this.center = center;
        this.halfExtent = halfExtent;
    }

    public boolean isIntersecting(AABB box2){
        Vector2f distance = box2.getCenter().sub(center, new Vector2f());
        distance.x = (float)Math.abs(distance.x);
        distance.y = (float)Math.abs(distance.y);

        distance.sub(halfExtent.add(box2.halfExtent), new Vector2f());

        return (distance.x < 0 && distance.y < 0);
    }

    public Vector2f getCenter() {
        return center;
    }

    public Vector2f getHalfExtent() {
        return halfExtent;
    }
}
