package hu.csaszi.twodee.graphics;

import hu.csaszi.twodee.util.Direction;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class CharSetBuilder {

	private String name;
	private int width;
	private int height;
	private Image charSet;
	private java.awt.Image rawImage;
	private int[] idleSpriteRows;
	private int[] walkSpriteRows;
	private int[] fightSpriteRows;
	private int[] deadSpriteRows;

	
	private CharSetBuilder(){
		
	}
	
	
	
	public static CharSetBuilder create(String name, java.awt.Image charSet) throws IOException, SlickException {
		
		CharSetBuilder charSetBuilder = new CharSetBuilder().setCharSet(charSet, ImageUtil.convertAwtToSlickImage(name, charSet));
		
		return charSetBuilder;
	}

	public CharSetBuilder setCharSet(java.awt.Image raw, Image charSet) {

		this.charSet = charSet;
		this.rawImage = raw;
		
		return this;
	}
	
	public CharSetBuilder setTileDimension(int width, int height) {
		this.width = width;
		this.height = height;
		
		return this;
	}
	
	public CharSetBuilder setIdleSpriteRows(Map<Direction, Integer> idleSpriteRowMap){
		
		idleSpriteRows = new int[idleSpriteRowMap.size()];
		
		for(Entry<Direction, Integer> entry : idleSpriteRowMap.entrySet()){
			idleSpriteRows[entry.getKey().getId()] = entry.getValue();
		}
		
		return this;
	}
	
	public CharSetBuilder setWalkSpriteRows(Map<Direction, Integer> walkSpriteRowMap){
		
		walkSpriteRows = new int[walkSpriteRowMap.size()];
		
		for(Entry<Direction, Integer> entry : walkSpriteRowMap.entrySet()){
			walkSpriteRows[entry.getKey().getId()] = entry.getValue();
		}
		
		return this;
	}
	
	public CharSetBuilder setFightSpriteRows(Map<Direction, Integer> fightSpriteRowMap){
		
		fightSpriteRows = new int[fightSpriteRowMap.size()];
		
		for(Entry<Direction, Integer> entry : fightSpriteRowMap.entrySet()){
			fightSpriteRows[entry.getKey().getId()] = entry.getValue();
		}
		
		return this;
	}

	public CharSetBuilder setDeadSpriteRows(Map<Direction, Integer> deadSpriteRowMap){
	
		deadSpriteRows = new int[deadSpriteRowMap.size()];
		
		for(Entry<Direction, Integer> entry : deadSpriteRowMap.entrySet()){
			deadSpriteRows[entry.getKey().getId()] = entry.getValue();
		}
		
		return this;
	}
	
	public CharacterSet build() throws IOException, SlickException{
		
		CharacterSet characterSet = new CharacterSet(charSet);
	
		Image[][] allSprites = ImageUtil.spliceSpriteRows(name, rawImage, width, height);
		Image[][] idleAnimation = createAnimation(allSprites, idleSpriteRows);
		Image[][] walkAnimation = createAnimation(allSprites, walkSpriteRows);
		Image[][] fightAnimation = createAnimation(allSprites, fightSpriteRows);
		Image[][] deadAnimation = createAnimation(allSprites, deadSpriteRows);
		
		characterSet.putAnimationToMap("idle", idleAnimation);
		characterSet.putAnimationToMap("walk", walkAnimation);
		characterSet.putAnimationToMap("fight", fightAnimation);
		characterSet.putAnimationToMap("dead", deadAnimation);
		
		return characterSet;
	}
	
	private Image[][] createAnimation(Image[][] allSprites, int[] directionRows){
		
		Image[][] result = new Image[directionRows.length][allSprites[0].length];
		
		int index = 0;
		for(int directionRow : directionRows){
			
			result[index++] = allSprites[directionRow];
		}
		
		return result;
	}
	
}
