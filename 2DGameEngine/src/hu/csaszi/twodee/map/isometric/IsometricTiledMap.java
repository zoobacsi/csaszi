package hu.csaszi.twodee.map.isometric;

import hu.csaszi.twodee.entity.Character;
import hu.csaszi.twodee.map.AbstractTiledMap;
import hu.csaszi.twodee.map.MapType;
import hu.csaszi.twodee.map.TileUtil;
import hu.csaszi.twodee.map.beans.MapTile;
import hu.csaszi.twodee.map.beans.TileType;
import hu.csaszi.twodee.map.interfaces.TileObject;
import hu.csaszi.twodee.runtime.Application;
import hu.csaszi.twodee.util.FileUtil;
import hu.csaszi.twodee.util.PropsValues;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class IsometricTiledMap extends AbstractTiledMap {

	private int xOff = maxX * (int) Math.floor(PropsValues.ISOMETRIC_TILE_WIDTH * PropsValues.GLOBAL_SCALE) / 2 - (int) Math.floor(PropsValues.ISOMETRIC_TILE_HEIGHT * PropsValues.GLOBAL_SCALE) / 2;

	private int yOff = maxY * (int) Math.floor(PropsValues.ISOMETRIC_TILE_HEIGHT * PropsValues.GLOBAL_SCALE) / 2;

	public IsometricTiledMap(int maxX, int maxY, int defaultTileId, List<MapTile> mapTiles, String mapFilePath) {
		this.maxX = maxX;
		this.maxY = maxY;
		this.defaultTileId = defaultTileId;
		this.mapTiles = mapTiles;
		this.mapFilePath = mapFilePath;
		init(Application.getGameContainer(), Application.getInstance());
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) {

		xOff = maxX * (int) Math.floor(PropsValues.ISOMETRIC_TILE_WIDTH * PropsValues.GLOBAL_SCALE) / 2 - (int) Math.floor(PropsValues.ISOMETRIC_TILE_HEIGHT * PropsValues.GLOBAL_SCALE) / 2;
		yOff = maxY * (int) Math.floor(PropsValues.ISOMETRIC_TILE_HEIGHT * PropsValues.GLOBAL_SCALE) / 2;

		map = new TileObject[maxX][maxY][4];

		for (int y = 0; y < maxY; y++) {
			for (int x = 0; x < maxX; x++) {

				Point coords = TileUtil.convertCoordsToIsometric(x, y);

				//System.out.println("x: " + x + " y: " + y + " xoff: " + xOff + " yoff: " + yOff + " coordsxy: " + coords.x + " - " + coords.y);

				map[x][y][0] = new IsoTileObject(coords.x + xOff, coords.y + yOff, TileUtil.getTile(defaultTileId, true), x, y, 0);
			}
		}

		for (MapTile mapTile : mapTiles) {
			TileObject tile = map[mapTile.getX()][mapTile.getY()][mapTile.getDepth()];
			if (tile != null) {
				tile.setTileType(TileUtil.getTile(mapTile.getTile(), true));
			} else {

				Point coords = TileUtil.convertCoordsToIsometric(mapTile.getX(), mapTile.getY());
				TileType type = TileUtil.getTile(mapTile.getTile(), true);
				int tileNumber = mapTile.getTileNum();

				TileObject newTile = new IsoTileObject(coords.x + xOff, coords.y + yOff, type, tileNumber, mapTile.getX(), mapTile.getY(), mapTile.getDepth(), type.getTileOffset());
				map[mapTile.getX()][mapTile.getY()][mapTile.getDepth()] = newTile;
			}
		}
	}

	/**
	 * Renders the ground
	 */
	public void render(GameContainer gc, int xOffset, int yOffset, int tileX, int tileY, int centerX, int centerY, Character player) {
		for (int y = 0; y < maxY; y++) {
			for (int x = 0; x < maxX; x++) {
				TileObject tile = map[x][y][0];
				if (tile.getType().isAutoTile()) {
					tile.setTileNum(getNeighbourHood(tile.getType(), tile.getXIndex(), tile.getYIndex(), tile.getDepth()));
				}
				if (Math.abs(tile.getCenterPosX() - player.getCenterX()) < Application.SCREEN_WIDTH / 2 + 64
						&& Math.abs(tile.getCenterPosY() - player.getCenterY()) < Application.SCREEN_HEIGHT / 2 + 32) {
					tile.render(gc.getGraphics(), 0, 0);
				}
			}
		}
	}

	public void renderBeyondPos(GameContainer gc, int yPosition, Character player) {
		for (TileObject tile : getDecorationsBeyondPos(yPosition)) {
			if (tile.getDepth() < 2) {
				if (tile.getType().isAutoTile()) {
					tile.setTileNum(getNeighbourHood(tile.getType(), tile.getXIndex(), tile.getYIndex(), tile.getDepth()));
				}

				if (Math.abs(tile.getCenterPosX() - player.getCenterX()) < Application.SCREEN_WIDTH / 2 + 64
						&& Math.abs(tile.getCenterPosY() - player.getCenterY()) < Application.SCREEN_HEIGHT / 2 + 32) {
					tile.render(gc.getGraphics(), 0, 0);
				}
			}
		}
	}

	public void renderAbovePos(GameContainer gc, int yPosition, Character player) {
		for (TileObject tile : getDecorationsAbovePos(yPosition)) {
			if (tile.getType().isAutoTile()) {
				tile.setTileNum(getNeighbourHood(tile.getType(), tile.getXIndex(), tile.getYIndex(), tile.getDepth()));
			}
			if (Math.abs(tile.getCenterPosX() - player.getCenterX()) < Application.SCREEN_WIDTH / 2 + 64 && Math.abs(tile.getCenterPosY() - player.getCenterY()) < Application.SCREEN_HEIGHT / 2 + 32) {
				tile.render(gc.getGraphics(), 0, 0);
			}
		}
	}

	public void renderBetweenPos(GameContainer gc, int yTop, int yBottom, Character player) {
		for (TileObject tile : getDecorationsBetweenPos(yTop, yBottom)) {
			if (tile.getType().isAutoTile()) {
				tile.setTileNum(getNeighbourHood(tile.getType(), tile.getXIndex(), tile.getYIndex(), tile.getDepth()));
			}
			if (Math.abs(tile.getCenterPosX() - player.getCenterX()) < Application.SCREEN_WIDTH / 2 + 64 && Math.abs(tile.getCenterPosY() - player.getCenterY()) < Application.SCREEN_HEIGHT / 2 + 32) {
				tile.render(gc.getGraphics(), 0, 0);
			}
		}
	}

	@Override
	public void renderDecorations(GameContainer gc) {
		for (int depth = 1; depth < 4; depth++) {
			for (int y = 0; y < maxY; y++) {
				for (int x = 0; x < maxX; x++) {
					if (map[x][y][depth] != null) {
						map[x][y][depth].render(gc.getGraphics(), 0, 0);
					}
				}
			}
		}
	}

	public void addDecorationTile(TileType type, int tileNum, int xIn, int yIn, int depth, int yOffset) {

		Point coords = TileUtil.convertCoordsToIsometric(xIn, yIn);
		int tileNumber = tileNum;
		if (type.isAutoTile()) {
			tileNumber = getNeighbourHood(type, xIn, yIn, depth);
		}
		TileObject tile = new IsoTileObject(coords.x + xOff, coords.y + yOff, type, tileNumber, xIn, yIn, depth, yOffset);
		if(xIn > -1 && yIn > -1 && depth > -1){
			map[xIn][yIn][depth] = tile;
			FileUtil.appendToMapFile(new File(mapFilePath), tile, true);
		}
	}

	@Override
	public int getTileWidth() {
		return PropsValues.ISOMETRIC_TILE_WIDTH * PropsValues.GLOBAL_SCALE;
	}

	@Override
	public int getTileHeight() {
		return PropsValues.ISOMETRIC_TILE_HEIGHT * PropsValues.GLOBAL_SCALE;
	}

	public int getMapTypeId() {

		return MapType.ISOMETRIC.getId();
	}

	@Override
	public void pathFinderVisited(int x, int y) {
		// TODO Auto-generated method stub

	}

	public List<TileObject> getTilesByOrtho(float orthoX, float orthoY) {
		List<TileObject> results = new ArrayList<TileObject>();

		int posX = (int) Math.round(Math.floor((orthoX - maxX * 100) / 100));
		int posY = (int) Math.round(Math.floor(orthoY / 100));
		// System.out.println(posX + " - " + posY);
		if (posX >= 0 && posX < maxX && posY >= 0 && posY < maxY) {
			for (int d = 0; d < 4; d++) {
				TileObject tile = map[posX][posY][d];
				if (tile != null) {
					results.add(tile);
				}
			}
		}
		return results;
	}
}
