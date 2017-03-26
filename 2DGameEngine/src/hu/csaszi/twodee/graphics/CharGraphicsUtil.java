package hu.csaszi.twodee.graphics;

import hu.csaszi.twodee.util.Direction;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.util.Log;

public class CharGraphicsUtil {

	private static CharGraphicsUtil instance;
	private Map<String, CharGraphics> graphicsMap;

	public Map<String, CharGraphics> getGraphicsMap() {
		return graphicsMap;
	}

	public static CharGraphicsUtil getInstance() {
		if (instance == null) {
			instance = new CharGraphicsUtil();
		}
		return instance;
	}

	private CharGraphicsUtil() {
		graphicsMap = new HashMap<String, CharGraphics>();

		graphicsMap.put("SwordsMan", new CharGraphics("SwordsMan",
				new ImageIcon("res/player4.png").getImage(), new ImageIcon(
						"res/swing.png").getImage(), new ImageIcon(
						"res/dead.png").getImage(), new Circle(0, 0, 1), 100));
		
		graphicsMap.put("Walker", new CharGraphics("Walker",
				new ImageIcon("res/walk.png").getImage(), new ImageIcon(
						"res/swing.png").getImage(), new ImageIcon(
						"res/dead.png").getImage(), new Circle(0, 0, 1), 100));
	}
	
	public static CharacterSet getDefaultCharSet(String name, String picturePath){
		
		Map<Direction, Integer> walkMap = new HashMap<>();
		walkMap.put(Direction.EAST, 0);
		walkMap.put(Direction.NORTH, 1);
		walkMap.put(Direction.NORTH_EAST, 2);
		walkMap.put(Direction.NORTH_WEST, 3);
		walkMap.put(Direction.SOUTH, 4);
		walkMap.put(Direction.SOUTH_EAST, 5);
		walkMap.put(Direction.SOUTH_WEST, 6);
		walkMap.put(Direction.WEST, 7);
		
		Map<Direction, Integer> fightMap = new HashMap<>();
		fightMap.put(Direction.NORTH, 8);
		Map<Direction, Integer> deadMap = new HashMap<>();
		deadMap.put(Direction.NORTH, 9);
		
		Map<Direction, Integer> idleMap = new HashMap<>();
		idleMap.put(Direction.EAST, 10);
		idleMap.put(Direction.NORTH, 11);
		idleMap.put(Direction.NORTH_EAST, 12);
		idleMap.put(Direction.NORTH_WEST, 13);
		idleMap.put(Direction.SOUTH, 14);
		idleMap.put(Direction.SOUTH_EAST, 15);
		idleMap.put(Direction.SOUTH_WEST, 16);
		idleMap.put(Direction.WEST, 17);
		
		CharacterSet characterSet = null;
		try{
		
			characterSet = CharSetBuilder.create("SwordMan", new ImageIcon(picturePath)
				.getImage()).setTileDimension(96, 96).setWalkSpriteRows(walkMap)
				.setFightSpriteRows(fightMap).setDeadSpriteRows(deadMap)
				.setIdleSpriteRows(idleMap).build();
		
		} catch (Exception e){
			Log.error(e.getMessage(), e);
		}
		
		return characterSet;
	}

	public static CharGraphics getGraphics(String key) throws Exception {

		CharGraphics graph = getInstance().getGraphicsMap().get(key);

		if (graph != null) {
			return graph;
		} else {
			throw new Exception("No such graphics! " + key);
		}
	}

	public static void putGraphics(String key, CharGraphics graph) {

		getInstance().getGraphicsMap().put(key, graph);
	}
}
