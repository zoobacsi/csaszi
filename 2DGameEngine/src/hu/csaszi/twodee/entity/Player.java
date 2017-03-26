
package hu.csaszi.twodee.entity;

import hu.csaszi.twodee.graphics.CharGraphics;
import hu.csaszi.twodee.graphics.CharacterSet;
import hu.csaszi.twodee.runtime.MapState;

import org.newdawn.slick.geom.Shape;

public class Player extends NPC {

	public Player(int id, MapState parent, java.awt.Image moveImg, java.awt.Image attackImg, java.awt.Image deadImg, String name, int hp, float speed, Shape shape, float ortoX, float ortoY) {
		super(id, parent, moveImg, attackImg, deadImg, name, hp, speed, shape, ortoX, ortoY);
	}

	public Player(int id, MapState parent, CharGraphics charGraphics, String name, int hp, float speed, float ortoX, float ortoY) {
	    super(id, parent, charGraphics, name, hp, speed, ortoX, ortoY);
	}
	
	public Player(int id, MapState parent, CharacterSet characterSet, int duration, Shape charShape, String name, int hp, float speed, float ortoX, float ortoY) {
	    super(id, parent, characterSet, duration, charShape, name, hp, speed, ortoX, ortoY);
	}

}
