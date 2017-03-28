package hu.csaszi.twodee.map;

import hu.csaszi.twodee.entity.Character;
import hu.csaszi.twodee.gamestates.DefaultPlay;
import hu.csaszi.twodee.map.beans.MapTile;
import hu.csaszi.twodee.map.beans.TileType;
import hu.csaszi.twodee.map.interfaces.PathfindableMap;
import hu.csaszi.twodee.map.interfaces.TileObject;
import hu.csaszi.twodee.map.interfaces.TiledMap;
import hu.csaszi.twodee.runtime.Application;
import hu.csaszi.twodee.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.pathfinding.PathFindingContext;

public abstract class AbstractTiledMap implements TiledMap, PathfindableMap {

	protected int maxX;
	protected int maxY;
	protected int defaultTileId;
	protected TileObject[][][] map;
	protected List<MapTile> mapTiles;
	protected String mapFilePath;

	public String getMapFilePath() {
		return mapFilePath;
	}

	protected int getNeighbourHood(TileType type, int x, int y, int depth) {
		int result = 0;

		TileObject tile = getTile(x, y - 1, depth);
		if ((tile != null && tile.getType().equals(type)) || y == 0) {
			result += 1;
		}

		tile = getTile(x + 1, y, depth);
		if ((tile != null && tile.getType().equals(type)) || x == maxX - 1) {
			result += 2;
		}

		tile = getTile(x, y + 1, depth);
		if ((tile != null && tile.getType().equals(type)) || y == maxY - 1) {
			result += 4;
		}

		tile = getTile(x - 1, y, depth);
		if ((tile != null && tile.getType().equals(type)) || x == 0) {
			result += 8;
		}

		return result;
	}

	public List<TileObject> getDecorations() {
		List<TileObject> result = new ArrayList<TileObject>();
		for (int depth = 1; depth < 3; depth++) {
			for (int y = 0; y < maxY; y++) {
				for (int x = 0; x < maxX; x++) {
					if (map[x][y][depth] != null) {
						result.add(map[x][y][depth]);
					}
				}
			}
		}
		return result;
	}

	public List<TileObject> getDecorationsBeyondPos(int yPosition) {
		TileObject tile = null;
		List<TileObject> result = new ArrayList<TileObject>();

		for (int y = 0; y < maxY; y++) {
			for (int x = 0; x < maxX; x++) {
				tile = map[x][y][1];
				if (tile != null) {
					result.add(tile);
				}
			}
		}

		for (int depth = 2; depth < 4; depth++) {
			for (int y = 0; y < maxY; y++) {
				for (int x = 0; x < maxX; x++) {
					tile = map[x][y][depth];
					if (tile != null && tile.getCenterPosY() <= yPosition) {
						result.add(tile);
					}
				}
			}
		}
		return result;
	}

	public List<TileObject> getDecorationsBetweenPos(int topY, int bottomY) {
		TileObject tile = null;
		List<TileObject> result = new ArrayList<TileObject>();

		for (int depth = 2; depth < 4; depth++) {
			for (int y = 0; y < maxY; y++) {
				for (int x = 0; x < maxX; x++) {
					tile = map[x][y][depth];
					if (tile != null && tile.getCenterPosY() > topY && tile.getCenterPosY() <= bottomY) {
						result.add(tile);
					}
				}
			}
		}
		return result;
	}

	public List<TileObject> getDecorationsAbovePos(int yPosition) {
		TileObject tile = null;
		List<TileObject> result = new ArrayList<TileObject>();
		for (int depth = 2; depth < 4; depth++) {
			for (int y = 0; y < maxY; y++) {
				for (int x = 0; x < maxX; x++) {
					tile = map[x][y][depth];
					if (tile != null && tile.getCenterPosY() > yPosition) {
						result.add(tile);
					}
				}
			}
		}
		return result;
	}

	@Override
	public int getWidthInTiles() {
		return maxX;
	}

	@Override
	public int getHeightInTiles() {
		return maxY;
	}

	@Override
	public float getCost(PathFindingContext context, int tx, int ty) {
		float cost = 100;
		TileObject tile = null;
		for (int depth = 0; depth < 4; depth++) {
			tile = map[tx][ty][depth];
			if (tile != null) {
				if (cost > tile.getType().getMovementCost()) {
					cost = tile.getType().getMovementCost();
				}
			}
		}
		return cost;
	}

	@Override
	public int getMaxX() {
		return maxX;
	}

	@Override
	public int getMaxY() {
		return maxY;
	}

	@Override
	public int getDefaultTileId() {
		return defaultTileId;
	}

	@Override
	public int getWidth() {
		return maxY * getTileWidth();
	}

	@Override
	public int getHeight() {
		return maxY * getTileHeight();
	}

	@Override
	public TileObject[][][] getTileObjectMap() {
		return map;
	}

	@Override
	public TileObject getFloorTile(int x, int y) {
		TileObject tile = null;
		try {
			tile = map[x][y][0];
		} catch (Throwable e) {
			tile = null;
		}
		return tile;
	}

	@Override
	public TileObject getTile(int x, int y, int depth) {
		TileObject tile = null;
		try {
			tile = map[x][y][depth];

		} catch (Throwable e) {
			tile = null;
		}
		return tile;
	}

	@Override
	public TileObject getDecorationTile(int x, int y, int depth) {
		TileObject tile = null;
		try {
			if (depth > 0) {
				tile = map[x][y][depth];
			}

		} catch (Throwable e) {
			tile = null;
		}
		return tile;
	}

	@Override
	public boolean blocked(PathFindingContext context, int tx, int ty) {
		boolean blocked = false;
		TileObject tile = null;
		for (int depth = 0; depth < 4; depth++) {
			tile = map[tx][ty][depth];
			if (tile != null) {
				if (tile.getType().isCollide()) {
					blocked = true;
					break;
				}
			}
		}
		int size = ((DefaultPlay)Application.getInstance().getCurrentState()).getCharactersByTile(tx, ty).size();
		//Log.info("size: " + size);
		if(size > 0){
			blocked = true;
		}
		return blocked;
	}

	public void deleteDecorationTile(int positionX, int positionY, int depth) {
		if(positionX > -1 && positionY > -1){
			if (depth > 0) {
				map[positionX][positionY][depth] = null;
			} else if (depth == 0) {
				map[positionX][positionY][depth].setTileType(TileUtil.getTile(defaultTileId, getMapTypeId() == MapType.ISOMETRIC.getId()));
			}
			FileUtil.saveMap(new File(mapFilePath), this);
		}
	}

	public TileObject getTileByPos(float xPos, float yPos) {

		int startX = 0;
		int endX = 0;
		int startY = 0;
		int endY = 0;
		TileObject result = null;
		List<TileObject> results = new ArrayList<TileObject>();

		for (int y = 0; y < maxY; y++) {
			for (int x = 0; x < maxX; x++) {
				startX = map[x][y][0].getxPos();
				startY = map[x][y][0].getyPos();
				endX = map[x][y][0].getxPos() + getTileWidth();
				endY = map[x][y][0].getyPos() + getTileHeight();

				if (xPos > startX && xPos < endX && yPos > startY && yPos < endY) {
					results.add(map[x][y][0]);
				}
			}
		}
		double distance = getTileWidth() * 2;

		for (TileObject tile : results) {
			double tempDistance = Math.sqrt(Math.pow(Math.abs(xPos - tile.getCenterPosX()), 2) + Math.pow(Math.abs(yPos - tile.getCenterPosY()), 2));

			if (tempDistance < distance) {
				distance = tempDistance;
				result = tile;
			}
		}

		return result;
	}

	public abstract void init(GameContainer gc, StateBasedGame sbg);

	public abstract void render(GameContainer gc, int xOffset, int yOffset, int tileX, int tileY, int centerX, int centerY, Character player);

	public abstract void renderBeyondPos(GameContainer gc, int yPosition, Character player);

	public abstract void renderAbovePos(GameContainer gc, int yPosition, Character player);

	public abstract void renderBetweenPos(GameContainer gc, int yTop, int yBottom, Character player);

	public abstract void renderDecorations(GameContainer gc);

	public abstract void addDecorationTile(TileType type, int tileNum, int xIn, int yIn, int depth, int yOffset);

	public abstract int getTileWidth();

	public abstract int getTileHeight();

	public abstract int getMapTypeId();
}
