package hu.csaszi.gameengine.physics.objects;

import hu.csaszi.gameengine.physics.Direction;
import hu.csaszi.gameengine.physics.collission.Collision;
import hu.csaszi.gameengine.physics.objects.ai.Command;
import hu.csaszi.gameengine.physics.world.World;
import hu.csaszi.gameengine.render.core.gl.GLFWWindow;
import hu.csaszi.gameengine.render.core.gl.Sprite;
import hu.csaszi.gameengine.render.core.gl.renderer.Camera;
import hu.csaszi.gameengine.render.core.gl.shaders.Shader;
import org.joml.Matrix4f;
import org.joml.Vector2f;

public abstract class GameObject extends RenderObject {


    protected float speed = 1f;
    protected String tag;
    protected boolean stuck;
    protected boolean abyssAhead;
    protected Vector2f velocity = new Vector2f();
    protected Vector2f gravityVelocity = new Vector2f();

    protected boolean jumping;
    protected boolean secondJumpUsed;
    protected Command command;
    protected boolean onGround;
    protected GameObject collideWith;

    public GameObject(Sprite[] sprites, Transform transform, String model) {
        super(sprites, transform, model);
    }

    public GameObject(Sprite sprite, Transform transform, String model) {
        this(new Sprite[]{sprite}, transform, model);
    }


    public Vector2f getVelocity() {
        return velocity;
    }

    /**
     * @param velocity
     * @deprecated use other implementation instead
     */
    @Deprecated
    public void setVelocity(Vector2f velocity) {
        this.velocity.x = velocity.x;
        this.velocity.y = velocity.y;
    }

    public void setVelocity(float x, float y) {
        this.velocity.x = x;
        this.velocity.y = y;
    }


    public boolean isOnGround() {
        return onGround;
    }


    @Override
    public void render(Shader shader, Camera camera, World world) {

        if (isDestroyed) {
            return;
        }

        Matrix4f target = camera.getProjection();
        target.mul(world.getWorldMatrix());
        shader.bind();
        shader.setUniform("sampler", 1);
        shader.setUniform("projection", transform.getProjection(target).rotateZ(-camera.getTargetRotate()));

        if (sprites != null && sprites.length > useSprite) {
            sprites[useSprite].bind(direction.getId(), 1);
        }
        model.render();
    }

    public boolean collideWithEntity(GameObject entity) {
        Collision collision = boundingBox.getCollision(entity.boundingBox);

        if (collision.intersects) {
            collision.distance.x /= 2;
            collision.distance.y /= 2;

            boundingBox.correctPosition(entity.boundingBox, collision);
            transform.pos.set(boundingBox.getCenter().x, boundingBox.getCenter().y, 0);

            entity.boundingBox.correctPosition(boundingBox, collision);
            entity.transform.pos.set(entity.boundingBox.getCenter().x, entity.boundingBox.getCenter().y, 0);

            collideWith = entity;
            return true;
        }

        return false;
    }

    public abstract void update(float delta, GLFWWindow window, Camera camera, World world);


    public abstract void onInteract(GameObject actor);

    public String getTag() {
        return tag;
    }

    public abstract void attack(GameObject target);

    public void setCommand(Command command) {
        this.command = command;
    }

    public boolean canMoveForward() {
        if (stuck || abyssAhead) {
            return false;
        }

        if (Direction.WEST.equals(direction)) {

        } else if (Direction.EAST.equals(direction)) {
            //TODO
        }

        return true;
    }

    public float getSpeed() {
        return speed;
    }

    public abstract boolean isAwareAbout(GameObject target);


    public abstract float getAttackInterval();
}
