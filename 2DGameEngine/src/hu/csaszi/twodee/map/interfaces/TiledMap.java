package hu.csaszi.twodee.map.interfaces;

import java.util.List;

import hu.csaszi.twodee.map.beans.TileType;
import hu.csaszi.twodee.entity.Character;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

public interface TiledMap extends TileBasedMap {

	public int getMaxX();
	public int getMaxY();
	public int getDefaultTileId();
	public int getMapTypeId();
	public String getMapFilePath();
	public TileObject[][][] getTileObjectMap();
	
	public void init(GameContainer gc, StateBasedGame sbg);
	public void render(GameContainer gc, int xOffset, int yOffset, int tileX, int tileY, int centerX, int centerY, Character player);                          
	public void renderBeyondPos(GameContainer gc, int yPosition, Character player);
	public void renderAbovePos(GameContainer gc, int yPosition, Character player);
	public void renderBetweenPos(GameContainer gc, int yTop, int yBottom, Character player);
	public void renderDecorations(GameContainer gc);
	public void addDecorationTile(TileType type, int tileNum, int xIn, int yIn, int depth, int yOffset);
	public void deleteDecorationTile(int positionX, int positionY, int depth);
	public int getWidth();
	public int getHeight();
	public int getTileWidth();
	public int getTileHeight();
	public TileObject getFloorTile(int x, int y);
	public TileObject getTile(int x, int y, int depth);
	public TileObject getDecorationTile(int x, int y, int depth);
	public void pathFinderVisited(int x, int y);
	public boolean blocked(PathFindingContext context, int tx, int ty);
	public List<TileObject> getTilesByOrtho(float tempPositionX, float tempPositionY);
	public TileObject getTileByPos(float xPos, float yPos);
	
	
}
