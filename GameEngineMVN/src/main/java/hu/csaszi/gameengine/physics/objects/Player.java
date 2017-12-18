package hu.csaszi.gameengine.physics.objects;

import hu.csaszi.gameengine.physics.world.World;
import hu.csaszi.gameengine.render.core.gl.Animation;
import hu.csaszi.gameengine.render.core.gl.GLFWWindow;
import hu.csaszi.gameengine.render.core.gl.Texture;
import hu.csaszi.gameengine.render.core.gl.renderer.Camera;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Player extends Entity {

    public static final int ANIM_IDLE = 0;
    public static final int ANIM_WALK = 1;
    public static final int ANIM_SIZE = 2;

    public Player(Transform transform) {
        super(ANIM_SIZE, transform, "tall");
        this.tag = "player";
        this.setSprite(ANIM_IDLE, new Texture("anim/soldier_0"));
        this.setSprite(ANIM_WALK, new Animation(4, 5, "soldier"));
    }

    @Override
    public void update(float delta, GLFWWindow window, Camera camera, World world) {

        Vector2f movement = new Vector2f();
        if (window.getInput() != null) {

            if (window.getInput().isKeyDown(GLFW_KEY_A)) {
                movement.add(-5 * delta, 0);
            }
            if (window.getInput().isKeyDown(GLFW_KEY_D)) {
                movement.add(5 * delta, 0);
            }
            if (window.getInput().isKeyDown(GLFW_KEY_W)) {
                movement.add(0, 5 * delta);
            }
            if (window.getInput().isKeyDown(GLFW_KEY_S)) {
                movement.add(0, -5 * delta);
            }
        }

        move(movement);
        if(movement.x != 0 || movement.y != 0){
            useAnimation(ANIM_WALK);
        } else {
            useAnimation(ANIM_IDLE);
        }

        super.update(delta, window, camera, world);

        camera.getPosition().lerp(transform.pos.mul(-world.getScale(), new Vector3f()), 0.05f);
//				camera.setPosition(transform.getPosition().mul(-world.getScale(), new Vector3f()));
    }
}
