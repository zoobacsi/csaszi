package hu.csaszi.gameengine.physics.objects;

import hu.csaszi.gameengine.example.states.TestSimpleGamePlayState;
import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.physics.Direction;
import hu.csaszi.gameengine.physics.Gravity;
import hu.csaszi.gameengine.physics.collission.AABB;
import hu.csaszi.gameengine.physics.collission.Collision;
import hu.csaszi.gameengine.physics.world.Tile;
import hu.csaszi.gameengine.physics.world.World;
import hu.csaszi.gameengine.render.core.gl.GLFWWindow;
import hu.csaszi.gameengine.render.core.gl.Sprite;
import hu.csaszi.gameengine.render.core.gl.renderer.Camera;
import org.joml.Vector2f;

public class BulletEntity extends GameObject {

    public static final int ANIM_WALK = 1;

    private long entityId = 0;
    private static long maxEntityId = 0;

    public BulletEntity(int maxAnimations, Transform transform, String model) {
        super(new Sprite[maxAnimations], transform, model);
        entityId = maxEntityId++;

        boundingBox = new AABB(new Vector2f(transform.pos.x, transform.pos.y), new Vector2f(transform.scale.x - 0.1f, transform.scale.y - 0.1f));
        this.tag = "bulletEntity_" + entityId;
    }

    public BulletEntity(Sprite sprite, Transform transform) {
        super(sprite, transform, "tall2");
        entityId = maxEntityId++;

        this.tag = "bulletEntity_" + entityId;
    }

    public boolean collideWithTile(World world){

        if(transform == null) {
            return false;
        }

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
                if(Gravity.isAbove(boundingBox.getCenter(), box.getCenter())){
                    onGround = true;
                } else if(Gravity.isBelow(boundingBox.getCenter(), box.getCenter())){
                    gravityVelocity.set(0, 0);
                }
                boundingBox.correctPosition(box, data);
                transform.pos.set(boundingBox.getCenter(), 0);

                return true;
            }
        }
        onGround = false;
        return false;
    }

    public boolean canMoveForward() {
        if(collideWith != null) {
            attack(collideWith);
            return false;
        }
        if (stuck) {
            return false;
        }
        return true;
    }

    private boolean isStuck(World world) {

        if (collideWithTile(world)) {
            stuck = true;
        } else {
            stuck = false;
        }

        return stuck;
    }

    @Override
    public void update(float delta, GLFWWindow window, Camera camera, World world) {
        command.execute(delta);
        if (!isDestroyed) {
            isStuck(world);
            useAnimation(ANIM_WALK);

            collideWithTile(world);

            move(velocity);
        }
    }

    @Override
    public void onInteract(GameObject actor) {

    }

    @Override
    public void attack(GameObject target) {
        System.out.println("BUMM: " + tag + " őt: " + target.getTag());
    }

    @Override
    public boolean isAwareAbout(GameObject target) {
        return false;
    }

    @Override
    public float getAttackInterval() {
        return 0;
    }
}
