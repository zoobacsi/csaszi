package hu.csaszi.gameengine.physics.objects;

import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.physics.collission.AABB;
import hu.csaszi.gameengine.physics.collission.Collision;
import hu.csaszi.gameengine.physics.world.World;
import hu.csaszi.gameengine.render.core.gl.Animation;
import hu.csaszi.gameengine.render.core.gl.GLFWWindow;
import hu.csaszi.gameengine.render.core.gl.renderer.Camera;
import hu.csaszi.gameengine.test.states.TestSimpleGamePlayState;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Player extends Entity {

    public Player(Transform transform) {
        super(new Animation(4, 5, "soldier"), transform);
        this.tag = "player";
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

        super.update(delta, window, camera, world);

        camera.getPosition().lerp(transform.pos.mul(-world.getScale(), new Vector3f()), 0.05f);
//				camera.setPosition(transform.getPosition().mul(-world.getScale(), new Vector3f()));
    }
}
