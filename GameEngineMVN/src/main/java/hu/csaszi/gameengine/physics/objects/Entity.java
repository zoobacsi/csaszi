package hu.csaszi.gameengine.physics.objects;

import hu.csaszi.gameengine.audio.OggPlayer;
import hu.csaszi.gameengine.example.states.TestSimpleGamePlayState;
import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.physics.Direction;
import hu.csaszi.gameengine.physics.Gravity;
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
import hu.csaszi.gameengine.util.IOUtil;
import hu.csaszi.gameengine.util.PropsUtil;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.Sys;

import java.io.File;

public class Entity extends GameObject {

    public static final int ANIM_IDLE = 0;
    public static final int ANIM_WALK = 1;

    private static File biteSound = IOUtil.getFile("bite.ogg");
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

            if (data.intersects) {
                boundingBox.correctPosition(box, data);
                transform.pos.set(boundingBox.getCenter(), 0);

                return true;
            }
        }
        return false;
    }

    public boolean collideWithGround(World world){

        Vector2f gravVector = Gravity.getGravity().getGravityVector().mul(0.1f, new Vector2f());

        Tile tile = world.getTileByPosition(
                getPositionX() + gravVector.x,
                getPositionY() + gravVector.y);

        if (tile != null) {
           return tile.isSolid();
        }
        return false;
    }

    public boolean jump() {
        int scale = PropsUtil.getProperties().getScale();
        float jumpHeight = PropsUtil.getProperties().getJumpHeight();

        Vector2f gravVector = new Vector2f(Gravity.getGravity().getGravityVector());

        gravityVelocity.set(gravVector.mul(-scale * jumpHeight));
        onGround = false;
        jumping = true;

        return true;
    }

    public boolean secondJump() {
        if(jumping && !secondJumpUsed) {
            int scale = PropsUtil.getProperties().getScale();
            float jumpHeight = PropsUtil.getProperties().getJumpHeight();

            gravityVelocity.set(Gravity.getGravity().getGravityVector().mul(-scale * jumpHeight));

            secondJumpUsed = true;
            return true;
        }
        return false;
    }

    private boolean isStuck(World world) {
//        float deltaX = direction.equals(Direction.WEST) ? -2f : 2f;
        int deltaX = direction.equals(Direction.WEST) ? -1 : 1;
        Tile tile = world.getTileByPosition(getPositionX(), getPositionY(), deltaX, 0);
        if (tile != null) {
            stuck = tile.isSolid();
        } else {
            stuck = true;
        }

        return stuck;
    }

    private boolean isAbyssAhead(World world) {
        int deltaX = direction.equals(Direction.WEST) ? -1 : 1;
        Tile tile = world.getTileByPosition(getPositionX(), getPositionY(), deltaX, 1);
        if (tile != null) {
            abyssAhead = !tile.isSolid();
        } else {
            abyssAhead = true;
        }

        return abyssAhead;
    }

    @Override
    public void update(float delta, GLFWWindow window, Camera camera, World world) {

        onGround = collideWithGround(world);

        if(!"player".equalsIgnoreCase(tag)){
            command.execute(delta);
        }

        isStuck(world);
        isAbyssAhead(world);

        if("entity_1".equalsIgnoreCase(tag)){
            TestSimpleGamePlayState gameState = ((TestSimpleGamePlayState)GameManager.getInstance().getCurrentState());
            Player player = gameState.getPlayer();

            gameState.putDebugInfo("distance", String.valueOf(player.getTransform().pos.distance(this.getTransform().pos)));
            gameState.putDebugInfo("ent1stuck", String.valueOf(stuck));
            gameState.putDebugInfo("ent1ahead", String.valueOf(abyssAhead));
        }

        if(onGround) {
            gravityVelocity.set(0, 0);
            jumping = false;
            secondJumpUsed = false;
        } else {
            if(Math.abs(gravityVelocity.x) <= 5f) {
                gravityVelocity.x += Gravity.getGravity().getGravityVector().x;
            }
            if(Math.abs(gravityVelocity.y) <= 5f) {
                gravityVelocity.y += Gravity.getGravity().getGravityVector().y;
            }
        }
        if(Gravity.getGravity().isChangeInProgress()){
            gravityVelocity.set(0, 0);
            Gravity.getGravity().setChangeInProgress(false);
            onGround = false;
        }

        if((velocity.x != 0 || velocity.y != 0) && !jumping && onGround){
            useAnimation(ANIM_WALK);
        } else {
            useAnimation(ANIM_IDLE);
        }

        move(new Vector2f((gravityVelocity.x * delta) + velocity.x, (gravityVelocity.y * delta) + velocity.y));

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

        //OggPlayer.runOgg(biteSound);
        System.out.println("TÁMADÁS: " + tag + " őt: " + target.getTag());
    }

    @Override
    public boolean isAwareAbout(GameObject target) {

        if(target == null) {
            return false;
        }

        Direction targetDir = (getPositionX()-target.getPositionX() < 0) ? Direction.EAST : Direction.WEST;

        return targetDir.equals(direction);
    }

    public void setAttackInterval(float attackInterval) {
        this.attackInterval = attackInterval;
    }

    @Override
    public float getAttackInterval() {
        return attackInterval;
    }

    private float attackInterval = 1f;

    public boolean isAlive() {
        return isAlive;
    }
}
