package hu.csaszi.twodee.graphics;

import java.util.HashMap;
import java.util.Map;

import hu.csaszi.twodee.util.Direction;

import org.newdawn.slick.Image;

public class CharacterSet {

	protected Image charSet;
	@Deprecated
	protected Image[][] deadAnim;
	@Deprecated
	protected Image[][] walkAnim;
	@Deprecated
	protected Image[][] fightAnim;
	@Deprecated
	protected Image[][] idleAnim;
	
	protected Map<String, Image[][]> animationMap = new HashMap<>();
	
	CharacterSet(Image charSet){
		this.charSet = charSet;
		
		//Build animationMap from charset
		
	}
	
	public CharacterSet putAnimationToMap(String key, Image[][] animation){
		
		animationMap.put(key, animation);
		
		return this;
	}
	
	@Deprecated
	public Image[] getDeadAnim(Direction direction){
		
		return getAnim(deadAnim, direction);
	}
	
	@Deprecated
	public Image[] getIdleAnim(Direction direction){
		
		return getAnim(idleAnim, direction);
	}
	
	@Deprecated
	public Image[] getWalkAnim(Direction direction){
		
		return getAnim(walkAnim, direction);
	}
	
	@Deprecated
	public Image[] getFightAnim(Direction direction){
		
		return getAnim(fightAnim, direction);
	}

	public Image[] getAnimation(String key, Direction direction){
		
		Image[][] animation = animationMap.get(key);
		
		return getAnim(animation, direction);
	}
	
	private Image[] getAnim(Image[][] animation, Direction direction){
		
		if(animation == null || direction == null){
			return null;
		}
		
		if(Direction.STAND.equals(direction)){
			direction = Direction.SOUTH_EAST;
		}
		
		if(animation.length > 1){	
			return animation[direction.getId()];
		} else if (animation.length == 1) {
			return animation[0];
		} else {
			return null;
		}
	}
}
