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
}
