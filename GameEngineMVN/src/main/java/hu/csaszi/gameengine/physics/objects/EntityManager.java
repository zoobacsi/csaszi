package hu.csaszi.gameengine.physics.objects;

import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.game.GameState;
import hu.csaszi.gameengine.physics.world.World;
import hu.csaszi.gameengine.render.core.gl.GLFWWindow;
import hu.csaszi.gameengine.render.core.gl.renderer.Camera;
import hu.csaszi.gameengine.render.core.gl.shaders.Shader;
import hu.csaszi.gameengine.example.states.TestSimpleGamePlayState;

import java.util.*;

public class EntityManager {

	private static final Map<GameState, EntityManager> entityManagers = new HashMap<>();

	private GameState gameState;
	private List<GameObject> gameObjects = new ArrayList<>();
	private static
	Set<GameObject> nearestObjects = new HashSet<>();

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

		GLFWWindow window = (GLFWWindow) gameManager.getWindow();
        Camera camera = ((TestSimpleGamePlayState) gameManager.getCurrentState()).getCamera();
        World world = ((TestSimpleGamePlayState) gameManager.getCurrentState()).getWorld();

		List<GameObject> objects = new ArrayList<>();
		objects.addAll(gameObjects);

		objects.sort(new Comparator<GameObject>() {
			@Override
			public int compare(GameObject o1, GameObject o2) {
				if(o1 instanceof Player){
					float x = o1.getTransform().pos.x + o1.getDirection().getX();
					float y = o1.getTransform().pos.y + o1.getDirection().getY();

					if(Math.abs(x - o2.getPositionX()) < 2f && Math.abs(y - o2.getPositionY()) < 2f){
						nearestObjects.add(o2);
						//System.out.println(o2 + " added");
					} else {
						nearestObjects.remove(o2);
					}
				} else if(o2 instanceof Player){
					float x = o2.getTransform().pos.x + o2.getDirection().getX();
					float y = o2.getTransform().pos.y + o2.getDirection().getY();

					if(Math.abs(x - o1.getPositionX()) < 2f && Math.abs(y - o1.getPositionY()) < 2f){
						nearestObjects.add(o1);
						//System.out.println(o1 + " added");
					} else {
						nearestObjects.remove(o1);
					}
				}
				if (o1 == null){
					return -1;
				} else if (o2 == null) {
					return 1;
				}
				return Float.compare(o2.getTransform().pos.y, o1.getTransform().pos.y);
			}
		});

		int i = 0;
		for(GameObject gameObject : objects) {
			if(gameObject != null ){
				if(gameObject.isDestroyed()){
					gameObjects.remove(gameObject);
				} else {
					gameObject.update(delta, window, camera, world);
				}
			}
			for(int j = i + 1; j < objects.size(); j++){
				if(gameObject != null && !gameObject.isDestroyed()) {
					gameObject.collideWithEntity(objects.get(j));
				}
			}
			i++;
		}

	}
	
	public void render(Shader shader, Camera camera, World world){

		List<GameObject> objects = new ArrayList<>();
		objects.addAll(gameObjects);

		objects.sort(new Comparator<GameObject>() {
			@Override
			public int compare(GameObject o1, GameObject o2) {
				if (o1 == null || o1.isDestroyed()){
					return -1;
				} else if (o2 == null || o2.isDestroyed()) {
					return 1;
				}
				return Float.compare(o2.getTransform().pos.y, o1.getTransform().pos.y);
			}
		});

		for(GameObject gameObject : objects){
			if (gameObject != null) {
				gameObject.render(shader, camera, world);
			}
		}
	}
	
	public List<GameObject> getObjects(){
		
		List<GameObject> results = new ArrayList<>();
		
		results.addAll(gameObjects);
		
		return results;
	}

	public static GameObject getFrontObject(){
		if(nearestObjects.iterator().hasNext()){
			return nearestObjects.iterator().next();
		}
		return null;
	}
}
