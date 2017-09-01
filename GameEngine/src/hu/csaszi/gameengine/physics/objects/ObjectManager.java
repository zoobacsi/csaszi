package hu.csaszi.gameengine.physics.objects;

import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.render.core.Drawer;
import hu.csaszi.gameengine.render.core.Window;

import java.util.ArrayList;
import java.util.List;

public class ObjectManager {

	private static List<GameObject> gameObjects = new ArrayList<>();
	
	
	//GameOject Methods
	public static void addObject(GameObject gameObject){
		gameObjects.add(gameObject);
	}
	
	public static void removeObject(GameObject gameObject){
		gameObjects.remove(gameObject);
	}
	
	public static void clearGameObjects(){
		gameObjects.clear();
	}
	
	//Update Methods
	public static void update(Window window, GameManager gameManager){
		
		List<GameObject> objects = new ArrayList<>();
		objects.addAll(gameObjects);
		
		for(GameObject gameObject : objects) {
			if(gameObject != null ){
				if(gameObject.isDestroyed()){
					gameObjects.remove(gameObject);
				} else {
					gameObject.update(window, gameManager);
				}
			}
		}
	}
	
	public static void render(Window window, Drawer drawer){
		
		List<GameObject> objects = new ArrayList<>();
		objects.addAll(gameObjects);
		
		for(GameObject gameObject : objects){
			gameObject.render(window, drawer);
		}
	}
	
	public static List<GameObject> getObjects(){
		
		List<GameObject> results = new ArrayList<>();
		
		results.addAll(gameObjects);
		
		return results;
		
	}
}
