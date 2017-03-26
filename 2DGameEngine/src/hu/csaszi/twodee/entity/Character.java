package hu.csaszi.twodee.entity;

import hu.csaszi.twodee.commands.interfaces.Command;
import hu.csaszi.twodee.entity.interfaces.Collidable;
import hu.csaszi.twodee.entity.interfaces.Drawable;
import hu.csaszi.twodee.entity.interfaces.Movable;
import hu.csaszi.twodee.util.Direction;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Shape;

public interface Character extends Drawable, Movable, Comparable<Drawable>, Collidable {

	public int getId();
	public void draw();
	public Direction moveTowardsDirection(Character character);
	public float distanceFromSq(Character character);
	public float getX();
	public float getY();
	public float getCharOrtoPosX();
	public void setCharOrtoPosX(float charOrtoPosX);
	public float getCharOrtoPosY();
	public void setCharOrtoPosY(float charOrtoPosY);
	public void setLocation(float ortoX, float ortoY);
	public void moveCharacter(int delta, Direction direction);
	public void moveToTile(int x, int y, int delta);
	public void attack();
	public void update(int delta);
	public boolean isAlive();
	public boolean isAttack();
	public Animation getCharAnim();
	public void setCharAnim(Animation charAnim);
	public String getName();
	public void setName(String name);
	public int getHp();
	public void setHp(int hp);
	public float getSpeed();
	public void setSpeed(float speed);
	public Shape getCharShape();
	public void setCharShape(Shape charShape);
	public int compareTo(Drawable o);
	public Character setCommand(Command command);
}
