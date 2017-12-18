package hu.csaszi.gameengine.physics.objects;

import hu.csaszi.gameengine.physics.collission.AABB;
import hu.csaszi.gameengine.physics.collission.Collision;
import hu.csaszi.gameengine.physics.world.World;
import hu.csaszi.gameengine.render.core.gl.Animation;
import hu.csaszi.gameengine.render.core.gl.GLFWWindow;
import hu.csaszi.gameengine.render.core.gl.Sprite;
import hu.csaszi.gameengine.render.core.gl.renderer.Camera;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Entity extends GameObject {

    private static long maxEntityId = 0;

    private boolean isAlive = true;
    private long entityId = 0;

    public Entity(int maxAnimations, Transform transform, String model) {
        super(new Sprite[maxAnimations], transform, model);
        entityId = maxEntityId++;

        this.tag = "entity_" + entityId;
    }

    public Entity(Sprite sprite, Transform transform) {
        super(sprite, transform, "tall");
        entityId = maxEntityId++;

        this.tag = "entity_" + entityId;
    }


    public void collideWithTile(World world){

        AABB[] boxes = new AABB[25];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                boxes[i + j * 5] = world.getTileBoundingBox((int) (((transform.pos.x / 2) + 0.5f) - (5 / 2)) + i,
                        (int) (((-transform.pos.y / 2) + 0.5f) - (5 / 2)) + j);
            }
        }

        AABB box = null;
        for (int i = 0; i < boxes.length; i++) {
            if (boxes[i] != null) {
                if (box == null) {
                    box = boxes[i];
                }

                Vector2f length1 = box.getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
                Vector2f length2 = boxes[i].getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());

                if (length1.lengthSquared() > length2.lengthSquared()) {
                    box = boxes[i];
                }
            }
        }
        if (box != null) {
            Collision data = boundingBox.getCollision(box);

            if (data.intersects) {
                boundingBox.correctPosition(box, data);
                transform.pos.set(boundingBox.getCenter(), 0);
            }

            for (int i = 0; i < boxes.length; i++) {
                if (boxes[i] != null) {
                    if (box == null) box = boxes[i];

                    Vector2f length1 = box.getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
                    Vector2f length2 = boxes[i].getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());

                    if (length1.lengthSquared() > length2.lengthSquared()) {
                        box = boxes[i];
                    }
                }
            }

            data = boundingBox.getCollision(box);
            if (data.intersects) {
                boundingBox.correctPosition(box, data);
                transform.pos.set(boundingBox.getCenter(), 0);
            }
        }
    }

//    @Override
//    public void update(float delta, GLFWWindow window, Camera camera, World world) {
//
//        collideWithTile(world);
//    }

    @Override
    public void update(float delta, GLFWWindow window, Camera camera, World world) {
        if(!"player".equalsIgnoreCase(tag)){
            move(new Vector2f(5*delta, 0));
        }
        collideWithTile(world);
    }

    public boolean isAlive() {
        return isAlive;
    }
}
