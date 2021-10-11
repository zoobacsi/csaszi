package hu.csaszi.gameengine.physics.objects;

import hu.csaszi.gameengine.example.states.TestSimpleGamePlayState;
import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.physics.Gravity;
import hu.csaszi.gameengine.physics.objects.ai.BulletMoveCommand;
import hu.csaszi.gameengine.physics.world.World;
import hu.csaszi.gameengine.render.core.gl.Animation;
import hu.csaszi.gameengine.render.core.gl.GLFWWindow;
import hu.csaszi.gameengine.render.core.gl.Sprite;
import hu.csaszi.gameengine.render.core.gl.TextureSheet;
import hu.csaszi.gameengine.render.core.gl.renderer.Camera;
import hu.csaszi.gameengine.render.graphics.AnimationKeys;
import hu.csaszi.gameengine.util.MathUtil;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Player extends Entity {

    public static final int ANIM_IDLE = 0;
    public static final int ANIM_SIZE = 4;
    public static final int ANIM_WALK = 1;
    public static final int ANIM_JUMP = 2;
    public static final int ANIM_SHOOT = 3;

    public static Player instance;

    public Player(Transform transform) {
        super(ANIM_SIZE, transform, "tall");
        this.tag = "player";



//        this.setSprite(ANIM_WALK, new Animation(4, 5, "soldier"));
        TextureSheet sheet = new TextureSheet("sheets/Soldier3", 32, 48);
        this.setSprite(ANIM_IDLE, new Animation(5, sheet, AnimationKeys.DEFAULT_CHARSET_IDLE_FRAMES));
        this.setSprite(ANIM_WALK, new Animation(5, sheet, AnimationKeys.DEFAULT_CHARSET_WALKING_FRAMES));

        instance = this;
    }

    @Override
    public void update(float delta, GLFWWindow window, Camera camera, World world) {

        Vector2f movement = new Vector2f();
        if (window.getInput() != null) {

            if (window.getInput().isKeyDown(GLFW_KEY_A)) {
                movement.add(Gravity.getGravity().getDirectedMovement(false).mul(delta));
            }
            if (window.getInput().isKeyDown(GLFW_KEY_D)) {
                movement.add(Gravity.getGravity().getDirectedMovement(true).mul(delta));
            }
//            if (window.getInput().isKeyDown(GLFW_KEY_W)) {
//                movement.add(0, 5 * delta);
//            }
//            if (window.getInput().isKeyDown(GLFW_KEY_S)) {
//                movement.add(0, -5 * delta);
//            }
            if (window.getInput().isKeyDown(GLFW_KEY_SPACE) && isOnGround() && !jumping) {
//                movement.add(0, 10 * delta);
                jump();
            }
//            if (window.getInput().isKeyDown(GLFW_KEY_UP) && !isOnGround() && jumping) {
//                secondJump();
//            }

            if(window.getInput().isKeyDown(GLFW_KEY_ENTER)) {
                GameObject gameObject = EntityManager.getFrontObject();
                if(gameObject != null) {
                    gameObject.onInteract(this);
                }
            }
            if (window.getInput().isKeyPressed(GLFW_KEY_R)) {
                camera.rotateCamera(MathUtil.RAD_90);
            }
            if (window.getInput().isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
                Vector2f targetDir = Gravity.getGravity().getDirectedMovement(Gravity.getGravity().isRight(direction));

                BulletEntity bulletEntity = new BulletEntity(2, new Transform(new Vector3f(this.transform.pos).add(targetDir.x, targetDir.y, 0)), "tall");
                TextureSheet sheet = new TextureSheet("sheets/bullet", 135, 147);
                bulletEntity.setSprite(Entity.ANIM_IDLE, new Animation(20, sheet, AnimationKeys.PLATFORMER_CHARSET_WALKING_FRAMES));
                bulletEntity.setSprite(Entity.ANIM_WALK, new Animation(20, sheet, AnimationKeys.PLATFORMER_CHARSET_WALKING_FRAMES));
                bulletEntity.setCommand(new BulletMoveCommand(bulletEntity, targetDir));
                bulletEntity.getVelocity().mul(40);

                EntityManager.getEntityManager(GameManager.getInstance().getCurrentState()).addObject(bulletEntity);
            }

        }

        velocity = movement;

        //move(movement);
        if((movement.x != 0 || movement.y != 0) && !jumping && onGround){
            useAnimation(ANIM_WALK);
        } else {
            useAnimation(ANIM_IDLE);
        }

        super.update(delta, window, camera, world);

        camera.getPosition().lerp(transform.pos.mul(-world.getScale(), new Vector3f()), 0.05f);
//				camera.setPosition(transform.getPosition().mul(-world.getScale(), new Vector3f()));

        TestSimpleGamePlayState gameState = ((TestSimpleGamePlayState) GameManager.getInstance().getCurrentState());

        gameState.putDebugInfo("player.onGround", String.valueOf(onGround));
        gameState.putDebugInfo("player.jump", String.valueOf(jumping));
        gameState.putDebugInfo("grav.vector", Gravity.getGravity().getGravityVector().toString());
        gameState.putDebugInfo("player.gravVelocity", gravityVelocity.toString());
    }
}
