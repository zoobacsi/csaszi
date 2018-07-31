package hu.csaszi.twodee.map;

import hu.csaszi.twodee.graphics.ImageUtil;
import hu.csaszi.twodee.map.beans.TileType;
import hu.csaszi.twodee.map.interfaces.TileObject;
import hu.csaszi.twodee.util.PropsValues;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.xml.bind.JAXBContext;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class TileUtil {

	private static Map<Integer, TileType> isometricTileMap;
	private static Map<Integer, TileType> orthogonalTileMap;

	static {
		try {
			System.out.println("init tileutil");
			initIsometricTiles();
			initOrthogonalTiles();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	private static void loadTilesetXml(File file){
//		 try {
//
//
//				JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
//
//				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//				Customer customer = (Customer) jaxbUnmarshaller.unmarshal(file);
//				System.out.println(customer);
//
//			  } catch (JAXBException e) {
//				e.printStackTrace();
//			  }
	}
	
	private static void initIsometricTiles() throws SlickException, IOException {
		
		isometricTileMap = new HashMap<Integer, TileType>();
		
		Image grassTile = new Image("res/grassdirt.png");
		grassTile.setName("Grass");
		
		isometricTileMap.put(1, new TileType.Builder("Grass").addImages(new Image[] { grassTile }).setMovementCost(2).build());

		Image[] waterTiles1 = ImageUtil.spliceImage("Water1", new ImageIcon("res/autowater2.png").getImage(), 128, 54);
		Image[] waterTiles2 = ImageUtil.spliceImage("Water2", new ImageIcon("res/autowater3.png").getImage(), 128, 54);
		List<Image[]> imagesList = new ArrayList<>();
		imagesList.add(waterTiles1);
		imagesList.add(waterTiles2);

		isometricTileMap.put(2, new TileType.Builder("Water").addImages(waterTiles1).addImages(waterTiles2).setImagesNum(16).setNumOfLayers(2).setCollide(true).setAutotile(true).setMovementCost(5).build());

		Image wallsouth = new Image("res/wallsouth1.png");
		wallsouth.setName("Wall - South");
		Image wallnorth = new Image("res/wallnorth.png");
		wallnorth.setName("Wall - North");
		Image walleast = new Image("res/walleast.png");
		walleast.setName("Wall - East");
		Image wallwest = new Image("res/wallwest.png");
		wallwest.setName("Wall - West");
		Image[] walls = new Image[] { wallsouth, walleast, wallnorth, wallwest };
		
		isometricTileMap.put(3, new TileType.Builder("Wall").addImages(walls).setCollide(true).setTileOffset((int) Math.floor(PropsValues.ISOMETRIC_TILE_HEIGHT * PropsValues.GLOBAL_SCALE))
				.setMovementCost(100).build());

		Image[] roadTiles = ImageUtil.spliceImage("Road", new ImageIcon("res/autoroad.png").getImage(), 128, 54);
		isometricTileMap.put(4, new TileType.Builder("Road").addImages(roadTiles).setAutotile(true).setMovementCost(1).build());

		Image treeTile1 = new Image("res/tree1.png");
		treeTile1.setName("Tree1");
		Image treeTile2 = new Image("res/tree2.png");
		treeTile2.setName("Tree2");
		Image treeTile3 = new Image("res/tree3.png");
		treeTile3.setName("Tree3");
		Image treeTile4 = new Image("res/tree4.png");
		treeTile4.setName("Tree4");
		Image[] trees = new Image[] { treeTile1, treeTile2, treeTile3, treeTile4 };
		
		isometricTileMap.put(5, new TileType.Builder("Tree").addImages(trees).setCollide(true).setTileOffset((int) Math.floor(PropsValues.ISOMETRIC_TILE_HEIGHT * PropsValues.GLOBAL_SCALE * 2))
				.setRandomImg(true).setMovementCost(100).build());

		Image building1 = new Image("res/build3.png");
		building1.setName("Building1");
		Image building2 = new Image("res/build4.png");
		building2.setName("Building2");
		Image building3 = new Image("res/building.png");
		building3.setName("Building3");
		Image[] buildings = new Image[] { building1, building2, building3 };
		
		isometricTileMap.put(6, new TileType.Builder("Building").addImages(buildings).setCollide(true).setTileOffset((int) Math.floor(PropsValues.ISOMETRIC_TILE_HEIGHT * PropsValues.GLOBAL_SCALE))
				.setRandomImg(true).setMovementCost(100).build());

		Image field1 = new Image("res/dinnye.png");
		field1.setName("MelonField-1");
		Image field2 = new Image("res/dinnye2.png");
		field2.setName("MelonField-2");
		Image field3 = new Image("res/dinnye3.png");
		field3.setName("MelonField-3");
		Image[] fields = new Image[] { field1, field2, field3 };
		
		isometricTileMap.put(7, new TileType.Builder("Melon Field").addImages(fields).setCollide(true).setRandomImg(true).setMovementCost(100).build());

		Image[] dirtTiles = ImageUtil.spliceImage("Dirt", new ImageIcon("res/autodirt.png").getImage(), 128, 54);
		
		isometricTileMap.put(8, new TileType.Builder("Dirt").addImages(dirtTiles).setAutotile(true).setMovementCost(1).build());

	}

	private static void initOrthogonalTiles() throws SlickException, IOException {
		orthogonalTileMap = new HashMap<Integer, TileType>();
		
		Image grassTile = new Image("res/orthograss.png");
		grassTile.setName("Grass");
		
		orthogonalTileMap.put(1, new TileType.Builder("Grass").addImages(new Image[] { grassTile }).setMovementCost(2).build());

		Image[] waterTiles1 = ImageUtil.spliceImage("Water1", new ImageIcon("res/autowater2.png").getImage(), 128, 54);
		Image[] waterTiles2 = ImageUtil.spliceImage("Water2", new ImageIcon("res/autowater3.png").getImage(), 128, 54);
		List<Image[]> imagesList = new ArrayList<>();
		imagesList.add(waterTiles1);
		imagesList.add(waterTiles2);

		orthogonalTileMap.put(2, new TileType.Builder("Water").addImages(waterTiles1).addImages(waterTiles2).setCollide(true).setAutotile(true).setMovementCost(5).build());

		Image wallsouth = new Image("res/wallsouth1.png");
		wallsouth.setName("Wall - South");
		Image wallnorth = new Image("res/wallnorth.png");
		wallnorth.setName("Wall - North");
		Image walleast = new Image("res/walleast.png");
		walleast.setName("Wall - East");
		Image wallwest = new Image("res/wallwest.png");
		wallwest.setName("Wall - West");
		Image[] walls = new Image[] { wallsouth, walleast, wallnorth, wallwest };
		
		orthogonalTileMap.put(3, new TileType.Builder("Wall").addImages(walls).setCollide(true).setTileOffset((int) Math.floor(PropsValues.ISOMETRIC_TILE_HEIGHT * PropsValues.GLOBAL_SCALE))
				.setMovementCost(100).build());

		Image[] roadTiles = ImageUtil.spliceImage("Road", new ImageIcon("res/autoroad.png").getImage(), 128, 54);
		orthogonalTileMap.put(4, new TileType.Builder("Road").addImages(roadTiles).setAutotile(true).setMovementCost(1).build());

		Image treeTile1 = new Image("res/tree1.png");
		treeTile1.setName("Tree1");
		Image treeTile2 = new Image("res/tree2.png");
		treeTile2.setName("Tree2");
		Image treeTile3 = new Image("res/tree3.png");
		treeTile3.setName("Tree3");
		Image treeTile4 = new Image("res/tree4.png");
		treeTile4.setName("Tree4");
		Image[] trees = new Image[] { treeTile1, treeTile2, treeTile3, treeTile4 };
		
		orthogonalTileMap.put(5, new TileType.Builder("Tree").addImages(trees).setCollide(true).setTileOffset((int) Math.floor(PropsValues.ISOMETRIC_TILE_HEIGHT * PropsValues.GLOBAL_SCALE * 2))
				.setRandomImg(true).setMovementCost(100).build());

		Image building1 = new Image("res/build3.png");
		building1.setName("Building1");
		Image building2 = new Image("res/build4.png");
		building2.setName("Building2");
		Image building3 = new Image("res/building.png");
		building3.setName("Building3");
		Image[] buildings = new Image[] { building1, building2, building3 };
		
		orthogonalTileMap.put(6, new TileType.Builder("Building").addImages(buildings).setCollide(true).setTileOffset((int) Math.floor(PropsValues.ISOMETRIC_TILE_HEIGHT * PropsValues.GLOBAL_SCALE))
				.setRandomImg(true).setMovementCost(100).build());

		Image field1 = new Image("res/dinnye.png");
		field1.setName("MelonField-1");
		Image field2 = new Image("res/dinnye2.png");
		field2.setName("MelonField-2");
		Image field3 = new Image("res/dinnye3.png");
		field3.setName("MelonField-3");
		Image[] fields = new Image[] { field1, field2, field3 };
		
		orthogonalTileMap.put(7, new TileType.Builder("Melon Field").addImages(fields).setCollide(true).setRandomImg(true).setMovementCost(100).build());

		Image[] dirtTiles = ImageUtil.spliceImage("Dirt", new ImageIcon("res/autodirt.png").getImage(), 128, 54);
		
		orthogonalTileMap.put(8, new TileType.Builder("Dirt").addImages(dirtTiles).setAutotile(true).setMovementCost(1).build());

	}

	public static TileType getTile(int tileId, boolean isometric) {

		if (isometric) {
			return getIsometricTile(tileId);
		} else {
			return getOrthogonalTile(tileId);
		}
	}

	public static TileType getIsometricTile(int tileId) {
		TileType result = null;
		if (isometricTileMap.keySet().contains(tileId)) {
			result = isometricTileMap.get(tileId);
		}

		return result;
	}

	public static TileType getOrthogonalTile(int tileId) {
		TileType result = null;
		if (orthogonalTileMap.keySet().contains(tileId)) {
			result = orthogonalTileMap.get(tileId);
		}

		return result;
	}

	public static int getTileId(TileType type, boolean isometric) {
		if (isometric) {
			return getIsometricTileId(type);
		} else {
			return getOrthogonalTileId(type);
		}
	}

	public static int getIsometricTileId(TileType type) {
		int result = -1;
		if (isometricTileMap.containsValue(type)) {
			for (Integer key : isometricTileMap.keySet()) {
				if (type == isometricTileMap.get(key)) {
					result = key;
				}
			}
		}
		return result;
	}

	public static int getOrthogonalTileId(TileType type) {
		int result = -1;
		if (orthogonalTileMap.containsValue(type)) {
			for (Integer key : orthogonalTileMap.keySet()) {
				if (type == orthogonalTileMap.get(key)) {
					result = key;
				}
			}
		}
		return result;
	}

	public static double getTileDistance(TileObject tile1, TileObject tile2) {

		int x1 = tile1.getXIndex();
		int y1 = tile1.getYIndex();
		int x2 = tile2.getXIndex();
		int y2 = tile2.getYIndex();

		return Point.distanceSq(x1, y1, x2, y2);
	}

	public static Point convertCoordsToIsometric(int x, int y) {
		int xPoint = (x - y) * (int) Math.floor(PropsValues.ISOMETRIC_TILE_WIDTH * PropsValues.GLOBAL_SCALE) / 2;
		int yPoint = (x + y) * (int) Math.floor(PropsValues.ISOMETRIC_TILE_HEIGHT * PropsValues.GLOBAL_SCALE) / 2;
		return new Point(xPoint, yPoint);
	}

	public static org.newdawn.slick.geom.Point convertCoordsToIsometric(float x, float y) {
		float xPoint = ((x - y) * PropsValues.ISOMETRIC_TILE_WIDTH * PropsValues.GLOBAL_SCALE) / 2;
		float yPoint = ((x + y) * PropsValues.ISOMETRIC_TILE_HEIGHT * PropsValues.GLOBAL_SCALE) / 2;
		return new org.newdawn.slick.geom.Point(xPoint, yPoint);
	}

	public static TileType selectTile(Input input, TileType type, boolean isometric) {
		TileType selectedType = type;

		if (input.isKeyPressed(Input.KEY_0)) {
			selectedType = null;
		} else if (input.isKeyPressed(Input.KEY_1)) {
			selectedType = getTile(3, isometric);
		} else if (input.isKeyPressed(Input.KEY_2)) {
			selectedType = getTile(2, isometric);
		} else if (input.isKeyPressed(Input.KEY_3)) {
			selectedType = getTile(4, isometric);
		} else if (input.isKeyPressed(Input.KEY_4)) {
			selectedType = getTile(5, isometric);
		} else if (input.isKeyPressed(Input.KEY_5)) {
			selectedType = getTile(6, isometric);
		} else if (input.isKeyPressed(Input.KEY_6)) {
			selectedType = getTile(7, isometric);
		} else if (input.isKeyPressed(Input.KEY_7)) {
			selectedType = getTile(8, isometric);
		}

		return selectedType;
	}

	public static int getOrtoPosByXIndex(int maxX, int x) {
		return maxX * 100 + x * 100 + 50;
	}

	public static int getOrtoPosByYIndex(int y) {
		return y * 100 + 50;
	}

}
