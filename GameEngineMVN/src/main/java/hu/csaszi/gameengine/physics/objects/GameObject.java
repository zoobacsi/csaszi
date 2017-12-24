package hu.csaszi.gameengine.physics.objects;

import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.physics.Direction;
import hu.csaszi.gameengine.physics.collission.AABB;
import hu.csaszi.gameengine.physics.collission.Collision;
import hu.csaszi.gameengine.physics.world.World;
import hu.csaszi.gameengine.render.core.gl.Animation;
import hu.csaszi.gameengine.render.core.gl.GLFWWindow;
import hu.csaszi.gameengine.render.core.gl.Sprite;
import hu.csaszi.gameengine.render.core.gl.TileSheet;
import hu.csaszi.gameengine.render.core.gl.models.Model;
import hu.csaszi.gameengine.render.core.gl.renderer.Camera;
import hu.csaszi.gameengine.render.core.gl.shaders.Shader;
import hu.csaszi.gameengine.render.graphics.assets.Assets;
import hu.csaszi.gameengine.render.graphics.imaging.Image;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.awt.*;
import java.nio.FloatBuffer;
import java.util.Comparator;

public abstract class GameObject implements Comparable<GameObject>{

	protected boolean isDestroyed;
	
	protected String tag;
	protected final Model model;
	protected AABB boundingBox;
	protected Image image;

	protected Sprite[] sprites;
	protected Transform transform;
	protected int useSprite;
	protected Direction direction = Direction.NORTH;

	public GameObject(Sprite[] sprites, Transform transform, String model){

		this.sprites = sprites;
		this.model = Assets.getModel(model);
		this.transform = transform;
		this.boundingBox = new AABB(new Vector2f(transform.pos.x, transform.pos.y), new Vector2f(transform.scale.x, transform.scale.y));
	}

	public GameObject(Sprite sprite, Transform transform, String model){
		this(new Sprite[]{ sprite}, transform, model);
	}

	@Override
	public int compareTo(GameObject o) {
		return Float.compare(this.getTransform().pos.y, o.getTransform().pos.y);
	}

	public void render(Shader shader, Camera camera, World world){

		Matrix4f target = camera.getProjection();
		target.mul(world.getWorldMatrix());
		shader.bind();
		shader.setUniform("sampler",0);
		shader.setUniform("projection", transform.getProjection(target));
		if(sprites != null && sprites.length > useSprite){
			sprites[useSprite].bind(direction.getId(), 0);
		}
		model.render();
	}

	public void collideWithEntity(GameObject entity) {
		Collision collision = boundingBox.getCollision(entity.boundingBox);

		if (collision.intersects) {
			collision.distance.x /= 2;
			collision.distance.y /= 2;

			boundingBox.correctPosition(entity.boundingBox, collision);
			transform.pos.set(boundingBox.getCenter().x, boundingBox.getCenter().y, 0);

			entity.boundingBox.correctPosition(boundingBox, collision);
			entity.transform.pos.set(entity.boundingBox.getCenter().x, entity.boundingBox.getCenter().y, 0);
		}
	}

	public void move(Vector2f direction) {

		Direction moveDir = Direction.getByVector(direction);
		if(moveDir != null){
			this.direction = moveDir;
		}

		if (transform.pos.x + direction.x < 0){
			transform.pos.x = 0 - direction.x;
		} else if (transform.pos.x + direction.x > Transform.getMaxWidth()){
			transform.pos.x = Transform.getMaxWidth() - direction.x;
		}

		if (transform.pos.y + direction.y > 0){
			transform.pos.y = 0 - direction.y;
		} else if (transform.pos.y + direction.y < -Transform.getMaxHeigth()){
			transform.pos.y = -Transform.getMaxHeigth() - direction.y;
		}

		transform.pos.add(new Vector3f(direction, 0));
		boundingBox.getCenter().set(transform.pos.x, transform.pos.y);
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}

	public void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
		this.transform = null;
		this.sprites = null;
	}

	public abstract void update(float delta, GLFWWindow window, Camera camera, World world);

	public void setSprite(int index, Sprite sprite){
		sprites[index] = sprite;
	}

	public void useAnimation(int index){
		this.useSprite = index;
	}

	public Direction getDirection() {
		return direction;
	}

	public Transform getTransform() {
		return transform;
	}

	public float getPositionX(){
		return transform.pos.x;
	}

	public float getPositionY(){
		return transform.pos.y;
	}

	public abstract void onInteract(GameObject actor);

	public String getTag(){
		return tag;
	}
}
