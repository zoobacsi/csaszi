package hu.csaszi.gameengine.physics.collission;

import hu.csaszi.gameengine.physics.Gravity;
import org.joml.Vector2f;

public class AABB {

    private Vector2f center;
    private Vector2f halfExtent;

    public AABB(Vector2f center, Vector2f halfExtent){
        this.center = center;
        this.halfExtent = halfExtent;
    }

    public Collision getCollision(AABB box2){
        Vector2f distance = new Vector2f(box2.center).sub(center);
        distance.x = Math.abs(distance.x);
        distance.y = Math.abs(distance.y);
//        Vector2f gravityVector = Gravity.getGravity().getGravityVector().normalize();
//        distance.sub(halfExtent.mul(gravityVector).add(box2.halfExtent.mul(gravityVector.mul(-1)), new Vector2f()));
        distance.sub(new Vector2f(halfExtent).add(new Vector2f(box2.halfExtent)));

        return new Collision(distance, distance.x <= 0 && distance.y <= 0);
    }

    public void correctPosition(AABB box2, Collision data){
        Vector2f correctionDistance = box2.center.sub(center, new Vector2f());
        if (data.distance.x > data.distance.y) {
            if (correctionDistance.x > 0) {
                center.add(data.distance.x, 0);
            }
            else {
                center.add(-data.distance.x, 0);
            }
        }
        else {
            if (correctionDistance.y > 0) {
                center.add(0, data.distance.y);
            }
            else {
                center.add(0, -data.distance.y);
            }
        }
    }

    public Vector2f getCenter() {
        return center;
    }

    public Vector2f getHalfExtent() {
        return halfExtent;
    }
}
