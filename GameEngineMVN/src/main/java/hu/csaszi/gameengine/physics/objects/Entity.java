package hu.csaszi.gameengine.physics.objects;

import hu.csaszi.gameengine.physics.collission.AABB;
import hu.csaszi.gameengine.physics.collission.Collision;
import hu.csaszi.gameengine.physics.world.World;
import hu.csaszi.gameengine.render.core.gl.Animation;
import hu.csaszi.gameengine.render.core.gl.GLFWWindow;
import hu.csaszi.gameengine.render.core.gl.Sprite;
import hu.csaszi.gameengine.render.core.gl.TextureSheet;
import hu.csaszi.gameengine.render.core.gl.renderer.Camera;
import hu.csaszi.gameengine.render.graphics.AnimationKeys;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Entity extends GameObject {

    public static final int ANIM_IDLE = 0;
    public static final int ANIM_WALK = 1;

    private static long maxEntityId = 0;

    private boolean isAlive = true;
    private long entityId = 0;
    boolean collide = false;

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



    public boolean collideWithTile(World world){

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
                return true;
            }
        }
        return false;
    }

    @Override
    public void update(float delta, GLFWWindow window, Camera camera, World world) {
        if(!"player".equalsIgnoreCase(tag)){
            velocity.x = 1;
        }
        velocity.add(GRAVITY);

        move(velocity.mul(delta));
        collide = collideWithTile(world);
        System.out.println(tag + " Collide: " + collide);
    }

    @Override
    public void onInteract(GameObject actor) {
        System.out.println("elotte "+this.useSprite);
        this.useAnimation(Entity.ANIM_IDLE);
        System.out.println("interract " + this.tag + " "+ this.useSprite);
    }

    public boolean isAlive() {
        return isAlive;
    }
}
