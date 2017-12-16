package hu.csaszi.gameengine.physics.collission;

import hu.csaszi.gameengine.physics.objects.GameObject;
import org.joml.Vector2f;

import java.awt.Rectangle;

public class Collision {

	public Vector2f distance;
	public boolean intersects;

	public Collision(Vector2f distance, boolean intersects){
		this.distance = distance;
		this.intersects = intersects;
	}
	public static boolean isColliding(GameObject gameObject1, GameObject gameObject2){
		
		Rectangle r1 = new Rectangle(gameObject1.getX(), gameObject1.getY(), gameObject1.getSx(), gameObject1.getSy());
		Rectangle r2 = new Rectangle(gameObject2.getX(), gameObject2.getY(), gameObject2.getSx(), gameObject2.getSy());
		
		return r1.intersects(r2);
	}
}
