package hu.csaszi.twodee.entity;

import hu.csaszi.twodee.commands.interfaces.Command;
import hu.csaszi.twodee.entity.interfaces.Drawable;
import hu.csaszi.twodee.gamestates.DefaultPlay;
import hu.csaszi.twodee.graphics.CharGraphics;
import hu.csaszi.twodee.graphics.CharacterSet;
import hu.csaszi.twodee.graphics.ImageUtil;
import hu.csaszi.twodee.map.TileUtil;
import hu.csaszi.twodee.map.interfaces.TileObject;
import hu.csaszi.twodee.runtime.MapState;
import hu.csaszi.twodee.util.Direction;
import hu.csaszi.twodee.util.PathfinderUtil;

import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;

public class NPC implements Character {

	protected java.awt.Image charSet, attackSet, deadSet;
	protected Animation charAnim, fightAnim, deadAnim, lastAnim, moveUp, 
	moveRightUp, moveRight, moveRightDown, moveDown, moveLeftDown, moveLeft, moveLeftUp,
	idleUp, idleRightUp, idleRight, idleRightDown, idleDown, idleLeftDown, idleLeft, idleLeftUp;
	protected String name;
	protected int hp;
	protected float speed;
	protected Shape charShape;
	protected float duration;
	protected MapState parentState;
	protected CharGraphics charGraphics;
	protected CharacterSet characterSet;
	protected int id;
	protected Command command;
	protected long lastAttackTime;

	protected boolean alive;
	protected boolean attack;

	protected float charOrtoPosX, charOrtoPosY;
	protected float tempPositionX, tempPositionY;

	public NPC(int id, MapState parent, CharGraphics charGraphics, String name, int hp, float speed, float ortoX, float ortoY) {
		this.id = id;
		this.parentState = parent;
		this.charGraphics = charGraphics;
		this.charSet = charGraphics.getCharSet();
		this.attackSet = charGraphics.getAttackSet();
		this.deadSet = charGraphics.getDeadSet();
		this.charShape = charGraphics.getCharShape();
		this.duration = charGraphics.getDuration();
		this.hp = hp;
		this.speed = speed;
		this.charOrtoPosX = ortoX;
		this.charOrtoPosY = ortoY;
		this.name = name;
		this.alive = true;

		Point playerPos = TileUtil.convertCoordsToIsometric(charOrtoPosX, charOrtoPosY);
		charShape.setLocation(playerPos.getX() / 100, playerPos.getY() / 100);

		Image[] playerImg = null;
		Image[] swingImg = null;
		Image[] deadImg = null;
		try {
			int  width = charSet.getWidth(null);
			int  height = charSet.getHeight(null);
			playerImg = ImageUtil.spliceImage(name, charSet, width/8, height/8);
			swingImg = ImageUtil.spliceImage(name + " - Attack", attackSet, 96, 96);
			deadImg = ImageUtil.spliceImage(name, deadSet, 96, 96);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Walking animations
		int mul = 0;
		int pos = 8;
		Image[] dead = { deadImg[0] };
		Image[] fight = { swingImg[0], swingImg[1], swingImg[2], swingImg[3], swingImg[4], swingImg[5], swingImg[6], swingImg[7] };
		Image[] walkRight = { playerImg[6 + mul * pos], playerImg[1 + mul * pos], playerImg[2 + mul * pos], playerImg[3 + mul * pos], playerImg[4 + mul * pos], playerImg[5 + mul * pos],
				playerImg[0 + mul * pos], playerImg[7 + mul * pos] };
		mul = 1;
		Image[] walkUp = { playerImg[6 + mul * pos], playerImg[1 + mul * pos], playerImg[2 + mul * pos], playerImg[3 + mul * pos], playerImg[4 + mul * pos], playerImg[5 + mul * pos],
				playerImg[0 + mul * pos], playerImg[7 + mul * pos] };
		mul = 2;
		Image[] walkRightUp = { playerImg[6 + mul * pos], playerImg[1 + mul * pos], playerImg[2 + mul * pos], playerImg[3 + mul * pos], playerImg[4 + mul * pos], playerImg[5 + mul * pos],
				playerImg[0 + mul * pos], playerImg[7 + mul * pos] };
		mul = 3;
		Image[] walkLeftUp = { playerImg[6 + mul * pos], playerImg[1 + mul * pos], playerImg[2 + mul * pos], playerImg[3 + mul * pos], playerImg[4 + mul * pos], playerImg[5 + mul * pos],
				playerImg[0 + mul * pos], playerImg[7 + mul * pos] };
		mul = 4;
		Image[] walkDown = { playerImg[6 + mul * pos], playerImg[1 + mul * pos], playerImg[2 + mul * pos], playerImg[3 + mul * pos], playerImg[4 + mul * pos], playerImg[5 + mul * pos],
				playerImg[0 + mul * pos], playerImg[7 + mul * pos] };
		mul = 5;
		Image[] walkRightDown = { playerImg[6 + mul * pos], playerImg[1 + mul * pos], playerImg[2 + mul * pos], playerImg[3 + mul * pos], playerImg[4 + mul * pos], playerImg[5 + mul * pos],
				playerImg[0 + mul * pos], playerImg[7 + mul * pos] };
		mul = 6;
		Image[] walkLeftDown = { playerImg[6 + mul * pos], playerImg[1 + mul * pos], playerImg[2 + mul * pos], playerImg[3 + mul * pos], playerImg[4 + mul * pos], playerImg[5 + mul * pos],
				playerImg[0 + mul * pos], playerImg[7 + mul * pos] };
		mul = 7;
		Image[] walkLeft = { playerImg[6 + mul * pos], playerImg[1 + mul * pos], playerImg[2 + mul * pos], playerImg[3 + mul * pos], playerImg[4 + mul * pos], playerImg[5 + mul * pos],
				playerImg[0 + mul * pos], playerImg[7 + mul * pos] };

		fightAnim = new Animation(fight, 30, true);
		deadAnim = new Animation(dead, 100);
		moveUp = new Animation(walkUp, (int) (duration / speed), false);
		moveDown = new Animation(walkDown, (int) (duration / speed), false);
		moveLeft = new Animation(walkLeft, (int) (duration / speed), false);
		moveRight = new Animation(walkRight, (int) (duration / speed), false);
		moveRightUp = new Animation(walkRightUp, (int) (duration / speed), false);
		moveRightDown = new Animation(walkRightDown, (int) (duration / speed), false);
		moveLeftUp = new Animation(walkLeftUp, (int) (duration / speed), false);
		moveLeftDown = new Animation(walkLeftDown, (int) (duration / speed), false);

		charAnim = moveDown;
	}

	public NPC(int id, MapState parent, CharacterSet characterSet, int duration, Shape charShape, String name, int hp, float speed, float ortoX, float ortoY) {
		this.id = id;
		this.parentState = parent;
		this.characterSet = characterSet;
		
		this.charShape = charShape;
		this.duration = duration;
		this.hp = hp;
		this.speed = speed;
		this.charOrtoPosX = ortoX;
		this.charOrtoPosY = ortoY;
		this.name = name;
		this.alive = true;

		Point playerPos = TileUtil.convertCoordsToIsometric(charOrtoPosX, charOrtoPosY);
		charShape.setLocation(playerPos.getX() / 100, playerPos.getY() / 100);

		// Walking animations
		Image[] dead = characterSet.getAnimation("dead", Direction.NORTH);
		Image[] fight = characterSet.getAnimation("fight", Direction.NORTH);
		
		Image[] walkRight = characterSet.getAnimation("walk", Direction.EAST);
		Image[] walkUp = characterSet.getAnimation("walk", Direction.NORTH);
		Image[] walkRightUp = characterSet.getAnimation("walk", Direction.NORTH_EAST);
		Image[] walkLeftUp = characterSet.getAnimation("walk", Direction.NORTH_WEST);
		Image[] walkDown = characterSet.getAnimation("walk", Direction.SOUTH);
		Image[] walkRightDown = characterSet.getAnimation("walk", Direction.SOUTH_EAST);
		Image[] walkLeftDown = characterSet.getAnimation("walk", Direction.SOUTH_WEST);
		Image[] walkLeft = characterSet.getAnimation("walk", Direction.WEST);

		Image[] idleRight = characterSet.getAnimation("idle", Direction.EAST);
		Image[] idleUp = characterSet.getAnimation("idle", Direction.NORTH);
		Image[] idleRightUp = characterSet.getAnimation("idle", Direction.NORTH_EAST);
		Image[] idleLeftUp = characterSet.getAnimation("idle", Direction.NORTH_WEST);
		Image[] idleDown = characterSet.getAnimation("idle", Direction.SOUTH);
		Image[] idleRightDown = characterSet.getAnimation("idle", Direction.SOUTH_EAST);
		Image[] idleLeftDown = characterSet.getAnimation("idle", Direction.SOUTH_WEST);
		Image[] idleLeft = characterSet.getAnimation("idle", Direction.WEST);

		this.fightAnim = new Animation(fight, 100, true);
		this.deadAnim = new Animation(dead, 100, true);
		this.moveUp = new Animation(walkUp, (int) (duration / speed), false);
		this.moveDown = new Animation(walkDown, (int) (duration / speed), false);
		this.moveLeft = new Animation(walkLeft, (int) (duration / speed), false);
		this.moveRight = new Animation(walkRight, (int) (duration / speed), false);
		this.moveRightUp = new Animation(walkRightUp, (int) (duration / speed), false);
		this.moveRightDown = new Animation(walkRightDown, (int) (duration / speed), false);
		this.moveLeftUp = new Animation(walkLeftUp, (int) (duration / speed), false);
		this.moveLeftDown = new Animation(walkLeftDown, (int) (duration / speed), false);

		this.idleUp = new Animation(idleUp, (int) (duration / speed), true);
		this.idleDown = new Animation(idleDown, (int) (duration / speed), true);
		this.idleLeft = new Animation(idleLeft, (int) (duration / speed), true);
		this.idleRight = new Animation(idleRight, (int) (duration / speed), true);
		this.idleRightUp = new Animation(idleRightUp, (int) (duration / speed), true);
		this.idleRightDown = new Animation(idleRightDown, (int) (duration / speed), true);
		this.idleLeftUp = new Animation(idleLeftUp, (int) (duration / speed), true);
		this.idleLeftDown = new Animation(idleLeftDown, (int) (duration / speed), true);

		charAnim = moveDown;
	}
	
	public int getId() {
		return id;
	}

	public NPC(int id, MapState parent, java.awt.Image charSet, java.awt.Image attackSet, java.awt.Image deadSet, String name, int hp, float speed, Shape shape, float ortoX, float ortoY) {
		this.id = id;
		this.parentState = parent;
		this.charSet = charSet;
		this.attackSet = attackSet;
		this.deadSet = deadSet;
		this.name = name;
		this.hp = hp;
		this.speed = speed;
		this.charShape = shape;
		this.duration = 100;
		this.charOrtoPosX = ortoX;
		this.charOrtoPosY = ortoY;
		this.alive = true;

		Point playerPos = TileUtil.convertCoordsToIsometric(charOrtoPosX, charOrtoPosY);
		charShape.setLocation(playerPos.getX() / 100, playerPos.getY() / 100);

		Image[] playerImg = null;
		Image[] swingImg = null;
		Image[] deadImg = null;
		try {
			playerImg = ImageUtil.spliceImage(name, charSet, 96, 96);
			swingImg = ImageUtil.spliceImage(name + " - Attack", attackSet, 96, 96);
			deadImg = ImageUtil.spliceImage(name, deadSet, 96, 96);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Walking animations
		int mul = 0;
		int pos = 8;
		Image[] dead = { deadImg[0] };
		Image[] fight = { swingImg[0], swingImg[1], swingImg[2], swingImg[3], swingImg[4], swingImg[5], swingImg[6], swingImg[7] };
		Image[] walkRight = { playerImg[6 + mul * pos], playerImg[1 + mul * pos], playerImg[2 + mul * pos], playerImg[3 + mul * pos], playerImg[4 + mul * pos], playerImg[5 + mul * pos],
				playerImg[0 + mul * pos], playerImg[7 + mul * pos] };
		mul = 1;
		Image[] walkUp = { playerImg[6 + mul * pos], playerImg[1 + mul * pos], playerImg[2 + mul * pos], playerImg[3 + mul * pos], playerImg[4 + mul * pos], playerImg[5 + mul * pos],
				playerImg[0 + mul * pos], playerImg[7 + mul * pos] };
		mul = 2;
		Image[] walkRightUp = { playerImg[6 + mul * pos], playerImg[1 + mul * pos], playerImg[2 + mul * pos], playerImg[3 + mul * pos], playerImg[4 + mul * pos], playerImg[5 + mul * pos],
				playerImg[0 + mul * pos], playerImg[7 + mul * pos] };
		mul = 3;
		Image[] walkLeftUp = { playerImg[6 + mul * pos], playerImg[1 + mul * pos], playerImg[2 + mul * pos], playerImg[3 + mul * pos], playerImg[4 + mul * pos], playerImg[5 + mul * pos],
				playerImg[0 + mul * pos], playerImg[7 + mul * pos] };
		mul = 4;
		Image[] walkDown = { playerImg[6 + mul * pos], playerImg[1 + mul * pos], playerImg[2 + mul * pos], playerImg[3 + mul * pos], playerImg[4 + mul * pos], playerImg[5 + mul * pos],
				playerImg[0 + mul * pos], playerImg[7 + mul * pos] };
		mul = 5;
		Image[] walkRightDown = { playerImg[6 + mul * pos], playerImg[1 + mul * pos], playerImg[2 + mul * pos], playerImg[3 + mul * pos], playerImg[4 + mul * pos], playerImg[5 + mul * pos],
				playerImg[0 + mul * pos], playerImg[7 + mul * pos] };
		mul = 6;
		Image[] walkLeftDown = { playerImg[6 + mul * pos], playerImg[1 + mul * pos], playerImg[2 + mul * pos], playerImg[3 + mul * pos], playerImg[4 + mul * pos], playerImg[5 + mul * pos],
				playerImg[0 + mul * pos], playerImg[7 + mul * pos] };
		mul = 7;
		Image[] walkLeft = { playerImg[6 + mul * pos], playerImg[1 + mul * pos], playerImg[2 + mul * pos], playerImg[3 + mul * pos], playerImg[4 + mul * pos], playerImg[5 + mul * pos],
				playerImg[0 + mul * pos], playerImg[7 + mul * pos] };

		fightAnim = new Animation(fight, 30, true);
		deadAnim = new Animation(dead, 100);
		moveUp = new Animation(walkUp, (int) (duration / speed), false);
		moveDown = new Animation(walkDown, (int) (duration / speed), false);
		moveLeft = new Animation(walkLeft, (int) (duration / speed), false);
		moveRight = new Animation(walkRight, (int) (duration / speed), false);
		moveRightUp = new Animation(walkRightUp, (int) (duration / speed), false);
		moveRightDown = new Animation(walkRightDown, (int) (duration / speed), false);
		moveLeftUp = new Animation(walkLeftUp, (int) (duration / speed), false);
		moveLeftDown = new Animation(walkLeftDown, (int) (duration / speed), false);

		charAnim = moveDown;
	}

	public void draw() {
		draw(null);

	}

	public void draw(Color color) {
		if (color != null) {
			charAnim.draw(charShape.getCenterX() - charAnim.getWidth() / 2, charShape.getCenterY() - charAnim.getHeight() + 30, color);
		} else {
			charAnim.draw(charShape.getCenterX() - charAnim.getWidth() / 2, charShape.getCenterY() - charAnim.getHeight() + 30);
		}

	}

	public Direction moveTowardsDirection(Character character) {
		return PathfinderUtil.getDirection(this.getCenterX(), this.getCenterY(), character.getCenterX(), character.getCenterY());
	}

	public float distanceFromSq(Character character) {
		float disX = Math.abs(this.getCenterX() - character.getCenterX());
		float disY = Math.abs(this.getCenterY() - character.getCenterY());

		//Log.info("distanceFromSq: " + (Math.pow(disX, 2) + Math.pow(disY, 2)));
		return (float) (Math.pow(disX, 2) + Math.pow(disY, 2));

	}

	public float getCenterX() {
		return this.charShape.getCenterX();
	}

	public float getCenterY() {
		return this.charShape.getCenterY();
	}

	public float getX() {
		return charShape.getCenterX();
	}

	public float getY() {
		return charShape.getCenterY();
	}

	public float getCharOrtoPosX() {

		return charOrtoPosX;
	}

	public void setCharOrtoPosX(float charOrtoPosX) {

		this.charOrtoPosX = charOrtoPosX;
	}

	public float getCharOrtoPosY() {

		return charOrtoPosY;
	}

	public void setCharOrtoPosY(float charOrtoPosY) {

		this.charOrtoPosY = charOrtoPosY;
	}

	public void setLocation(float ortoX, float ortoY) {
		Point playerPos = TileUtil.convertCoordsToIsometric(ortoX, ortoY);
		this.charShape.setLocation(playerPos.getX() / 100, playerPos.getY() / 100);
	}

	public void moveCharacter(int delta, Direction direction) {
		tempPositionX = charOrtoPosX;
		tempPositionY = charOrtoPosY;

		if (!attack && alive) {
			switch (direction) {
			case NORTH:
				charAnim = delta > 0 ? moveUp : idleUp;
				// if (startY < tempPositionY && startX < tempPositionX) {
				tempPositionX = (float) ((float) charOrtoPosX - (delta * .05 * parentState.getGlobalSpeed() * speed));
				tempPositionY = (float) ((float) charOrtoPosY - (delta * .05 * parentState.getGlobalSpeed() * speed));
				// }
				break;
			case NORTH_EAST:
				charAnim = delta > 0 ? moveRightUp : idleRightUp;
				// if (startY < tempPositionY) {
				tempPositionY = (float) ((float) charOrtoPosY - (delta * .1 * parentState.getGlobalSpeed() * speed));
				// }
				break;
			case EAST:
				charAnim = delta > 0 ? moveRight : idleRight;
				// if (startY < tempPositionY && endX > tempPositionX) {
				tempPositionX = (float) ((float) charOrtoPosX + (delta * .05 * parentState.getGlobalSpeed() * speed));
				tempPositionY = (float) ((float) charOrtoPosY - (delta * .05 * parentState.getGlobalSpeed() * speed));
				// }
				break;
			case SOUTH_EAST:
				charAnim = delta > 0 ? moveRightDown : idleRightDown;
				// if (endX > tempPositionX) {
				tempPositionX = (float) ((float) charOrtoPosX + (delta * .1 * parentState.getGlobalSpeed() * speed));
				// }
				break;
			case SOUTH:
				charAnim = delta > 0 ? moveDown : idleDown;
				// if (endY > tempPositionY && endX > tempPositionX) {
				tempPositionX = (float) ((float) charOrtoPosX + (delta * .05 * parentState.getGlobalSpeed() * speed));
				tempPositionY = (float) ((float) charOrtoPosY + (delta * .05 * parentState.getGlobalSpeed() * speed));
				// }
				break;
			case SOUTH_WEST:
				charAnim = delta > 0 ? moveLeftDown : idleLeftDown;
				// if (endY > tempPositionY) {
				tempPositionY = (float) ((float) charOrtoPosY + (delta * .1 * parentState.getGlobalSpeed() * speed));
				// }
				break;
			case WEST:
				charAnim = delta > 0 ? moveLeft : idleLeft;
				// if (endY > tempPositionY && startX < tempPositionX) {
				tempPositionX = (float) ((float) charOrtoPosX - (delta * .05 * parentState.getGlobalSpeed() * speed));
				tempPositionY = (float) ((float) charOrtoPosY + (delta * .05 * parentState.getGlobalSpeed() * speed));
				// }
				break;
			case NORTH_WEST:
				charAnim = delta > 0 ? moveLeftUp  : idleLeftUp;
				// if (startX < tempPositionX) {
				tempPositionX = (float) ((float) charOrtoPosX - (delta * .1 * parentState.getGlobalSpeed() * speed));
				// }
				break;
			default:
				break;
			}

			boolean move = (direction != null && !Direction.STAND.equals(direction));
			charAnim.setLooping(move);
			charAnim.setAutoUpdate(move);
			if (move) {
				boolean canMove = true;
				charAnim.start();

				List<TileObject> tiles = parentState.getTiledMap().getTilesByOrtho(tempPositionX, tempPositionY);
				if (tiles.isEmpty()) {
					canMove = false;
				} else if (parentState instanceof DefaultPlay) {
					for (TileObject tile : tiles) {
						if (tile.getType().isCollide()) {
							canMove = false;
							break;
						}
					}
				}

				if (canMove) {
					charOrtoPosX = tempPositionX;
					charOrtoPosY = tempPositionY;
				}
			} else {
				charAnim.stop();
				charAnim.restart();
			}

			Point playerPos = TileUtil.convertCoordsToIsometric(charOrtoPosX, charOrtoPosY);
			charShape.setLocation(playerPos.getX() / 100, playerPos.getY() / 100);
		}
	}

	public void moveToTile(int x, int y, int delta) {
		TileObject destTile = parentState.getTiledMap().getFloorTile(x, y);
		// TileObject charTile =
		// Game.getPlay().getTiledMap().getTileByPos(this.getCenterX(),
		// this.getCenterY());
		TileObject charTile = parentState.getTiledMap().getTilesByOrtho(this.getCharOrtoPosX(), this.getCharOrtoPosY()).get(0);
		Direction dir = PathfinderUtil.getDirection(this.getCenterX(), this.getCenterY(), destTile.getCenterPosX(), destTile.getCenterPosY());
		if (destTile != charTile) {
			moveCharacter(delta, dir);
		}
	}

	public void attack() {
		lastAnim = charAnim;
		attack = true;
		fightAnim.setLooping(attack);
		fightAnim.setAutoUpdate(attack);
		fightAnim.start();
		charAnim = fightAnim;
		lastAttackTime = System.currentTimeMillis();
	}

	public void update(int delta) {
		if (attack && lastAttackTime > 0 && System.currentTimeMillis() - lastAttackTime > delta*2) {
			attack = false;
			lastAttackTime = 0;
		}

		if(charAnim == fightAnim){
			if (charAnim.getFrame() == fightAnim.getFrameCount() - 1) {
				charAnim.stop();
				charAnim.restart();
				charAnim = lastAnim;
			}
		}

		if (this.getHp() == 0) {
			attack = false;
			alive = false;
			charAnim = deadAnim;
		}
		
		if(command != null){
			//TODO execute command
			command.execute(delta);
			
			if(command.isExecuted()){
				command.setOwner(null);
				command = null;
			}
		}
	}

	public boolean isAlive() {
		return this.alive;
	}

	public boolean isAttack() {
		return this.attack;
	}

	public Animation getCharAnim() {

		return charAnim;
	}

	public void setCharAnim(Animation charAnim) {

		this.charAnim = charAnim;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public int getHp() {

		return hp;
	}

	public void setHp(int hp) {

		this.hp = hp;
	}

	public float getSpeed() {

		return speed;
	}

	public void setSpeed(float speed) {

		this.speed = speed;
	}

	public Shape getCharShape() {

		return charShape;
	}

	public void setCharShape(Shape charShape) {

		this.charShape = charShape;
	}

	@Override
	public int compareTo(Drawable o) {
		if (o == null) {
			return 0;
		} else {

			if (this.getCenterY() > o.getCenterY()) {
				return 1;
			} else if (this.getCenterY() == o.getCenterY()) {
				return 0;
			} else if (this.getCenterY() < o.getCenterY()) {
				return -1;
			}
		}

		return 0;
	}

	@Override
	public Character setCommand(Command command) {
		this.command = command;
		command.setOwner(this);
		return this;
	}

	@Override
	public void collide() {
		// TODO Auto-generated method stub
		
	}
}
