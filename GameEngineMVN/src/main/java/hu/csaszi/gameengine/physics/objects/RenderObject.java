package hu.csaszi.gameengine.physics.objects;

import hu.csaszi.gameengine.physics.Direction;
import hu.csaszi.gameengine.physics.collission.AABB;
import hu.csaszi.gameengine.physics.world.World;
import hu.csaszi.gameengine.render.core.gl.Sprite;
import hu.csaszi.gameengine.render.core.gl.models.Model;
import hu.csaszi.gameengine.render.core.gl.renderer.Camera;
import hu.csaszi.gameengine.render.core.gl.shaders.Shader;
import hu.csaszi.gameengine.render.graphics.assets.Assets;
import hu.csaszi.gameengine.render.graphics.imaging.Image;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public abstract class RenderObject implements Comparable<RenderObject> {

    protected boolean isDestroyed;
    protected final Model model;
    protected AABB boundingBox;
    protected AABB gravityBox;
    protected Image image;

    public Sprite[] getSprites() {
        return sprites;
    }

    protected Sprite[] sprites;
    protected Transform transform;
    protected int useSprite;
    protected Direction direction = Direction.WEST;

    public RenderObject(Sprite[] sprites, Transform transform, String model) {

        this.sprites = sprites;
        this.model = Assets.getModel(model);
        this.transform = transform;
        this.boundingBox = new AABB(new Vector2f(transform.pos.x, transform.pos.y), new Vector2f(transform.scale.x, transform.scale.y));
        this.gravityBox = new AABB(new Vector2f(transform.pos.x + 0.2f, transform.pos.y), new Vector2f(transform.scale.x - 0.2f, transform.scale.y));
    }

    public Direction getDirection() {
        return direction;
    }

    public void changeFacing() {
        if (Direction.WEST.equals(direction)) {
            direction = Direction.EAST;
        } else if (Direction.EAST.equals(direction)) {
            direction = Direction.WEST;
        } else if (Direction.NORTH.equals(direction)) {
            direction = Direction.SOUTH;
        } else if (Direction.SOUTH.equals(direction)) {
            direction = Direction.NORTH;
        }
    }
    public AABB getGravityBox() {
        return gravityBox;
    }

    @Override
    public int compareTo(RenderObject o) {
        return Float.compare(this.getTransform().pos.y, o.getTransform().pos.y);
    }

    public void destroy() {
        setDestroyed(true);
    }

    public abstract void render(Shader shader, Camera camera, World world);

    public void move(Vector2f direction) {

        Direction moveDir = Direction.getByVector(direction, this.direction);
        if (moveDir != null) {
            this.direction = moveDir;
        }

        if (transform.pos.x + direction.x < 0) {
            transform.pos.x = 0 - direction.x;
        } else if (transform.pos.x + direction.x > Transform.getMaxWidth()) {
            transform.pos.x = Transform.getMaxWidth() - direction.x;
        }

        if (transform.pos.y + direction.y > 0) {
            transform.pos.y = 0 - direction.y;
        } else if (transform.pos.y + direction.y < -Transform.getMaxHeigth()) {
            transform.pos.y = -Transform.getMaxHeigth() - direction.y;
        }

        transform.pos.add(new Vector3f(direction, 0));
        boundingBox.getCenter().set(transform.pos.x, transform.pos.y);
        gravityBox.getCenter().set(transform.pos.x, transform.pos.y);
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean isDestroyed) {
        this.isDestroyed = isDestroyed;
//        this.transform = null;
        this.sprites = null;
    }

    public void setSprite(int index, Sprite sprite) {
        sprites[index] = sprite;
    }

    public void useAnimation(int index) {
        this.useSprite = index;
    }

    public Transform getTransform() {
        return transform;
    }

    public float getPositionX() {
        return transform.pos.x;
    }

    public float getPositionY() {
        return transform.pos.y;
    }

}
