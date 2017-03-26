package hu.csaszi.twodee.map.interfaces;

import java.util.Map;

import org.newdawn.slick.util.pathfinding.PathFindingContext;

public interface PathfindableMap {

	public int getWidthInTiles();
	public int getHeightInTiles();
	public void pathFinderVisited(int x, int y);
	public boolean blocked(PathFindingContext context, int tx, int ty);
	public float getCost(PathFindingContext context, int tx, int ty);
	//public Map<String, TileObject> getVisitedMap();

}
