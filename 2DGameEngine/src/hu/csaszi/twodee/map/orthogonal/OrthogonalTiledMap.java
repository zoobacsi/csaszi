package hu.csaszi.twodee.map.orthogonal;

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

public class OrthogonalTiledMap extends AbstractTiledMap {

	private int xOff = 0;
	private int yOff = 0;
	
	public OrthogonalTiledMap(int maxX, int maxY, int defaultTileId, List<MapTile> mapTiles, String mapFilePath){
		this.maxX = maxX;
		this.maxY = maxY;
		this.defaultTileId = defaultTileId;
		this.mapTiles = mapTiles;
		this.mapFilePath = mapFilePath;
		init(Application.getGameContainer(), Application.getInstance());
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) {
		map = new TileObject[maxX][maxY][4];

		int xOff = 0;
		int yOff = 0;
		
		for (int y = 0; y < maxY; y++) {
			for (int x = 0; x < maxX; x++) {

				Point coords = new Point(x * PropsValues.ORTHOGONAL_TILE_WIDTH, y * PropsValues.ORTHOGONAL_TILE_HEIGHT);

				//System.out.println("x: " + x + " y: " + y + " xoff: " + xOff + " yoff: " + yOff + " coordsxy: " + coords.x + " - " + coords.y);

				map[x][y][0] = new OrthoTileObject(coords.x + xOff, coords.y + yOff, TileUtil.getTile(defaultTileId, false), x, y, 0);
			}
		}

		for (MapTile mapTile : mapTiles) {
			TileObject tile = map[mapTile.getX()][mapTile.getY()][mapTile.getDepth()];
			//System.out.println("tile1: " + tile);
			
			if (tile != null) {
				tile.setTileType(TileUtil.getTile(mapTile.getTile(), false));
			} else {
				Point coords = new Point(mapTile.getX() * PropsValues.ORTHOGONAL_TILE_WIDTH, mapTile.getY() * PropsValues.ORTHOGONAL_TILE_HEIGHT);

				System.out.println("maptile: " + mapTile.getTile());
				TileType type = TileUtil.getTile(mapTile.getTile(), false);
				int tileNumber = mapTile.getTileNum();

				System.out.println("type: " + type);
				if(type != null){
					TileObject newTile = new OrthoTileObject(coords.x + xOff, coords.y + yOff, type, tileNumber, mapTile.getX(), mapTile.getY(), mapTile.getDepth(), type.getTileOffset());
					map[mapTile.getX()][mapTile.getY()][mapTile.getDepth()] = newTile;
				}
			}
		}
	}
	@Override
	public void render(GameContainer gc, int xOffset, int yOffset, int tileX, int tileY, int centerX, int centerY, Character player) {
		for (int y = 0; y < maxY; y++) {
			for (int x = 0; x < maxX; x++) {
				TileObject tile = map[x][y][0];
				if (tile.getType().isAutoTile()) {
					tile.setTileNum(getNeighbourHood(tile.getType(), tile.getXIndex(), tile.getYIndex(), tile.getDepth()));
				}
				if (Math.abs(tile.getCenterPosX() - player.getCenterX()) < Application.SCREEN_WIDTH / 2 + 50
						&& Math.abs(tile.getCenterPosY() - player.getCenterY()) < Application.SCREEN_HEIGHT / 2 + 50) {
					tile.render(gc.getGraphics(), 0, 0);
				}
			}
		}
	}
	@Override
	public void renderBeyondPos(GameContainer gc, int yPosition, Character player) {
		for (TileObject tile : getDecorationsBeyondPos(yPosition)) {
			if (tile.getDepth() < 2) {
				if (tile.getType().isAutoTile()) {
					tile.setTileNum(getNeighbourHood(tile.getType(), tile.getXIndex(), tile.getYIndex(), tile.getDepth()));
				}

				if (Math.abs(tile.getCenterPosX() - player.getCenterX()) < Application.SCREEN_WIDTH / 2 + 50
						&& Math.abs(tile.getCenterPosY() - player.getCenterY()) < Application.SCREEN_HEIGHT / 2 + 50) {
					tile.render(gc.getGraphics(), 0, 0);
				}
			}
		}
	}
	@Override
	public void renderAbovePos(GameContainer gc, int yPosition, Character player) {
		for (TileObject tile : getDecorationsAbovePos(yPosition)) {
			if (tile.getType().isAutoTile()) {
				tile.setTileNum(getNeighbourHood(tile.getType(), tile.getXIndex(), tile.getYIndex(), tile.getDepth()));
			}
			if (Math.abs(tile.getCenterPosX() - player.getCenterX()) < Application.SCREEN_WIDTH / 2 + 50 && Math.abs(tile.getCenterPosY() - player.getCenterY()) < Application.SCREEN_HEIGHT / 2 + 50) {
				tile.render(gc.getGraphics(), 0, 0);
			}
		}
	}
	@Override
	public void renderBetweenPos(GameContainer gc, int yTop, int yBottom, Character player) {
		for (TileObject tile : getDecorationsBetweenPos(yTop, yBottom)) {
			if (tile.getType().isAutoTile()) {
				tile.setTileNum(getNeighbourHood(tile.getType(), tile.getXIndex(), tile.getYIndex(), tile.getDepth()));
			}
			if (Math.abs(tile.getCenterPosX() - player.getCenterX()) < Application.SCREEN_WIDTH / 2 + 50 && Math.abs(tile.getCenterPosY() - player.getCenterY()) < Application.SCREEN_HEIGHT / 2 + 50) {
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
	@Override
	public void addDecorationTile(TileType type, int tileNum, int xIn, int yIn, int depth, int yOffset) {
		
		Point coords = new Point(xIn * maxX, yIn * maxY);
		int tileNumber = tileNum;
		if (type.isAutoTile()) {
			tileNumber = getNeighbourHood(type, xIn, yIn, depth);
		}
		TileObject tile = new OrthoTileObject(coords.x + xOff, coords.y + yOff, type, tileNumber, xIn, yIn, depth, yOffset);
		map[xIn][yIn][depth] = tile;
		FileUtil.appendToMapFile(new File(mapFilePath), tile, false);
	}
	
	@Override
	public int getTileWidth() {
		return PropsValues.ORTHOGONAL_TILE_WIDTH * PropsValues.GLOBAL_SCALE;
	}
	@Override
	public int getTileHeight() {
		return PropsValues.ORTHOGONAL_TILE_HEIGHT * PropsValues.GLOBAL_SCALE;
	}
	@Override
	public int getMapTypeId() {
		return MapType.ORTHOGONAL.getId();
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
