package hu.csaszi.gameengine.physics.objects;

import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.game.GameState;
import hu.csaszi.gameengine.physics.world.World;
import hu.csaszi.gameengine.render.core.gl.renderer.Camera;
import hu.csaszi.gameengine.render.core.gl.shaders.Shader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityManager {

	private static final Map<GameState, EntityManager> entityManagers = new HashMap<>();

	private GameState gameState;
	private List<GameObject> gameObjects = new ArrayList<>();

	private EntityManager(GameState gameState){

		this.gameState = gameState;
	}

	public static EntityManager createEntityManager(GameState gameState){

		EntityManager entityManager = new EntityManager(gameState);
		entityManagers.put(gameState, entityManager);
		return entityManager;
	}

	public static EntityManager getEntityManager(GameState gameState){
		return entityManagers.get(gameState);
	}

	//GameOject Methods
	public void addObject(GameObject gameObject){
		gameObjects.add(gameObject);
	}
	
	public void removeObject(GameObject gameObject){
		gameObjects.remove(gameObject);
	}
	
	public void clearGameObjects(){
		gameObjects.clear();
	}
	
	//Update Methods
	public void update(float delta, GameManager gameManager){
		
		List<GameObject> objects = new ArrayList<>();
		objects.addAll(gameObjects);
		
		for(GameObject gameObject : objects) {
			if(gameObject != null ){
				if(gameObject.isDestroyed()){
					gameObjects.remove(gameObject);
				} else {
					gameObject.update(delta, gameManager);
				}
			}
		}
	}
	
	public void render(Shader shader, Camera camera, World world){

		List<GameObject> objects = new ArrayList<>();
		objects.addAll(gameObjects);
		
		for(GameObject gameObject : objects){
			gameObject.render(shader, camera, world);
		}
	}
	
	public List<GameObject> getObjects(){
		
		List<GameObject> results = new ArrayList<>();
		
		results.addAll(gameObjects);
		
		return results;
		
	}
}
