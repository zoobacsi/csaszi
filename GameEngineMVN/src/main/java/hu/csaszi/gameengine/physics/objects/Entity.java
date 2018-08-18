package hu.csaszi.gameengine.physics.objects;

import hu.csaszi.gameengine.physics.Direction;
import hu.csaszi.gameengine.physics.collission.AABB;
import hu.csaszi.gameengine.physics.collission.Collision;
import hu.csaszi.gameengine.physics.world.Tile;
import hu.csaszi.gameengine.physics.world.World;
import hu.csaszi.gameengine.render.core.gl.Animation;
import hu.csaszi.gameengine.render.core.gl.GLFWWindow;
import hu.csaszi.gameengine.render.core.gl.Sprite;
import hu.csaszi.gameengine.render.core.gl.TextureSheet;
import hu.csaszi.gameengine.render.core.gl.renderer.Camera;
import hu.csaszi.gameengine.render.graphics.AnimationKeys;
import hu.csaszi.gameengine.util.PropsUtil;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.Sys;

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
        super(sprite, transform, "tall2");
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
            Collision gravityData = gravityBox.getCollision(box);

            if (gravityData.intersects) {
                onGround = true;
            } else {
                onGround = false;
            }
//            System.out.println(tag + " onGround: " + onGround);
            if (data.intersects) {
                boundingBox.correctPosition(box, data);
                transform.pos.set(boundingBox.getCenter(), 0);

                return true;
            }
        }
        return false;
    }

    public boolean jump() {
        int scale = PropsUtil.getProperties().getScale();
        float jumpHeight = PropsUtil.getProperties().getJumpHeight();

        gravityVelocity.y = scale * jumpHeight;
        onGround = false;
        jumping = true;

        return true;
    }

    public boolean secondJump() {
        if(jumping && !secondJumpUsed) {
            int scale = PropsUtil.getProperties().getScale();
            float jumpHeight = PropsUtil.getProperties().getJumpHeight();

            gravityVelocity.y = scale * jumpHeight;

            secondJumpUsed = true;
            return true;
        }
        return false;
    }

    @Override
    public void update(float delta, GLFWWindow window, Camera camera, World world) {
        if(!"player".equalsIgnoreCase(tag)){
            command.execute(delta);
        }

        if(onGround) {

            float deltaX = direction.equals(Direction.WEST) ? -2f : 2f;
            Tile tile = world.getTileByPosition(getPositionX() + deltaX, getPositionY());
            if (tile != null) {
                stuck = tile.isSolid();
            } else {
                stuck = true;
            }

            gravityVelocity.set(0);
            jumping = false;
            secondJumpUsed = false;
        } else {
            gravityVelocity.y += GRAVITY;
        }
//        if("player".equalsIgnoreCase(tag) && gravityVelocity.y != 0){
//            System.out.println("gravityVelocity " + gravityVelocity);
//        }

        if((velocity.x != 0 || velocity.y != 0) && !jumping && onGround){
            useAnimation(ANIM_WALK);
        } else {
            useAnimation(ANIM_IDLE);
        }

        move(new Vector2f(velocity.x, (gravityVelocity.y * delta) + velocity.y));
        collide = collideWithTile(world);
    }

    @Override
    public void onInteract(GameObject actor) {
        System.out.println("elotte "+this.useSprite);
        this.useAnimation(Entity.ANIM_IDLE);
        System.out.println("interract " + this.tag + " "+ this.useSprite);
    }

    @Override
    public void attack(GameObject target) {
        System.out.println("TÁMADÁS: " + tag + " őt: " + target.getTag());
    }

    @Override
    public boolean isAwareAbout(GameObject target) {

        Direction targetDir = (getPositionX()-target.getPositionX() < 0) ? Direction.EAST : Direction.WEST;

        return targetDir.equals(direction);
    }

    public boolean isAlive() {
        return isAlive;
    }
}
