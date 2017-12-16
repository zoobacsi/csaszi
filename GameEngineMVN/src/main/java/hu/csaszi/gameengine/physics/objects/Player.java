package hu.csaszi.gameengine.physics.objects;

import hu.csaszi.gameengine.audio.AudioPlayer;
import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.physics.collission.AABB;
import hu.csaszi.gameengine.physics.collission.Collision;
import hu.csaszi.gameengine.physics.objects.GameObject;
import hu.csaszi.gameengine.physics.world.World;
import hu.csaszi.gameengine.render.core.Window;
import hu.csaszi.gameengine.render.core.gl.Animation;
import hu.csaszi.gameengine.render.core.gl.GLFWWindow;
import hu.csaszi.gameengine.render.core.gl.renderer.Camera;
import hu.csaszi.gameengine.test.states.TestSimpleGamePlayState;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Player extends GameObject {

	private static boolean isAlive = true;
	
	public Player(Transform transform){
		super(new Animation(4, 5,"soldier"),transform);
		this.sx = 16;
		this.sy = 16;
		
		this.x = 100;
		this.y = 100;
		
		this.tag = "player";
	}

	@Override
	public void update(float delta, GameManager gameManager) {

		if(gameManager.isSoftwareRender()) {
			//		AWTInput AWTInput = window.getInput();
//
//		if(!isAlive){
//			if(AWTInput.isKeyDown(KeyEvent.VK_SPACE)){
//				isAlive = true;
//				score = 0;
//				ObjectManager.clearGameObjects();
//				gameManager.enterState(0, true);
//			}
//			return;
//		}
//		score++;
//
//		checkCollission(gameManager);
//
//		if(AWTInput.isKeyDown(KeyEvent.VK_W)){
//			y -= 5;
//		} else if(AWTInput.isKeyDown(KeyEvent.VK_S)){
//			y += 5;
//		}
//
//		if(y > window.getHeight() - sy){
//			y = window.getHeight() - sy;
//		} else if( y <= 0){
//			y = 0;
//		}

		} else {
			GLFWWindow window = (GLFWWindow)gameManager.getWindow();
			Camera camera = ((TestSimpleGamePlayState)gameManager.getCurrentState()).getCamera();
			World world = ((TestSimpleGamePlayState)gameManager.getCurrentState()).getWorld();

			if(window.getInput() != null){

				if(window.getInput().isKeyDown(GLFW_KEY_A)) {
					transform.getPosition().add(new Vector3f(-5 * delta,0,0));
				}
				if(window.getInput().isKeyDown(GLFW_KEY_D)) {
					transform.getPosition().add(new Vector3f(5 * delta,0,0));
				}
				if(window.getInput().isKeyDown(GLFW_KEY_W)) {
					transform.getPosition().add(new Vector3f(0,5 * delta,0));
				}
				if(window.getInput().isKeyDown(GLFW_KEY_S)) {
					transform.getPosition().add(new Vector3f(0,-5 * delta,0));
				}

				AABB[] boxes = new AABB[25];
				for(int i = 0; i < 5; i++){
					for(int j = 0; j < 5; j++){
						boxes[i+j*5] = world.getTileBoundingBox((int)(((transform.pos.x / 2) + 0.5f) - (5/2)) + i,
																(int)(((-transform.pos.y / 2) + 0.5f) - (5/2)) + j);
					}
				}

				AABB box = null;
				for(int i = 0; i < boxes.length; i++){
					if(boxes[i] != null){
						if(box == null){
							box = boxes[i];
						}

						Vector2f length1 = box.getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
						Vector2f length2 = boxes[i].getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());

						if(length1.lengthSquared() > length2.lengthSquared()) {
							box = boxes[i];
						}
					}
				}
				if(box != null) {
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
					}
				}
				camera.getPosition().lerp(transform.pos.mul(-world.getScale(), new Vector3f()), 0.05f);
//				camera.setPosition(transform.getPosition().mul(-world.getScale(), new Vector3f()));
			}
		}
	}
	
	public static boolean isPlayerAlive(){
		return isAlive;
	}
}
