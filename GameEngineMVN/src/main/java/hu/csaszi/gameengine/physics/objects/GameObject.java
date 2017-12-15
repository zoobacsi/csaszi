package hu.csaszi.gameengine.physics.objects;

import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.physics.world.Tile;
import hu.csaszi.gameengine.render.core.Drawer;
import hu.csaszi.gameengine.render.core.Window;
import hu.csaszi.gameengine.render.core.gl.Animation;
import hu.csaszi.gameengine.render.core.gl.Sprite;
import hu.csaszi.gameengine.render.core.gl.Texture;
import hu.csaszi.gameengine.render.core.gl.models.Model;
import hu.csaszi.gameengine.render.core.gl.renderer.Camera;
import hu.csaszi.gameengine.render.core.gl.shaders.Shader;
import hu.csaszi.gameengine.render.graphics.imaging.Image;
import org.joml.Vector3f;

import java.awt.*;
import java.util.HashMap;

public abstract class GameObject {

	protected int x;
	protected int y;
	protected int sx;
	protected int sy;
	
	protected boolean doDraw = true;
	protected boolean didDraw;
	protected boolean hasImage;
	protected boolean isDestroyed;
	
	protected Color color = Color.white;
	protected String tag;
	
	protected Image image;
	protected Model model;
	//protected Texture texture;
	protected Sprite texture;
	protected Transform transform;

	public GameObject(Sprite texture){

		float ratio = texture.getRatio() + (texture.getRatio() - 1.0f);
		System.out.println(ratio);
		float[] vertices = new float[]{
				-1f, ratio, 0, // TOP LEFT      0
				1f, ratio, 0, // TOP RIGHT     1
				1f, -1f, 0, // BOTTOM RIGHT  2
				-1f, -1f, 0 // BOTTOM LEFT 3
		};

		float[] texCoords = new float[]{
				0, 0,
				1, 0,
				1, 1,
				0, 1
		};

		int[] indices = new int[]{
				0, 1, 2,
				2, 3, 0
		};

		model = new Model(vertices, texCoords, indices);
		this.texture = texture;

		transform = new Transform();
		transform.scale = new Vector3f(32, 32, 2);
	}
	public void render(Shader shader, Camera camera){

		shader.bind();
		shader.setUniform("sampler",0);
		shader.setUniform("projection", transform.getProjection(camera.getProjection()));
		texture.bind(0);
		model.render();
//		if(doDraw){
//
//			if(hasImage){
//				drawer.drawImage(image.getLoadedImage(), x, y);
//			} else {
//
//				drawer.fillRect(x, y, sx, sy, color);
//			}
//			didDraw = true;
//		}
	}
	
	public boolean isDestroyed() {
		return isDestroyed;
	}

	public void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	public abstract void update(float delta, GameManager gameManager);

	public int getX(){
		return x;
	}

	public int getY() {
		return y;
	}

	public int getSx() {
		return sx;
	}

	public int getSy() {
		return sy;
	}

	public boolean isDidDraw() {
		return didDraw;
	}
	
	public String getTag(){
		return tag;
	}
}
