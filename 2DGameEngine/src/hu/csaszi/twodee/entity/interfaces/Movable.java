package hu.csaszi.twodee.entity.interfaces;

import hu.csaszi.twodee.util.Direction;

import org.newdawn.slick.util.pathfinding.Mover;

public interface Movable extends Mover {

	public void moveCharacter(int delta, Direction direction);
	public void moveToTile(int x, int y, int delta);
}
