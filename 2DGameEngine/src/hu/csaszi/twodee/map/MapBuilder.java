package hu.csaszi.twodee.map;

import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.newdawn.slick.util.Log;

import hu.csaszi.twodee.exceptions.UnfinishedParameterSetup;
import hu.csaszi.twodee.map.beans.MapTile;
import hu.csaszi.twodee.map.interfaces.TiledMap;
import hu.csaszi.twodee.map.isometric.IsometricTiledMap;
import hu.csaszi.twodee.map.orthogonal.OrthogonalTiledMap;

public class MapBuilder {

	private MapType mapType;
	private int maxX;
	private int maxY;
	private int defaultTileId;
	private List<MapTile> mapTiles;
	private String mapFilePath;
	
	private MapBuilder(MapType mapType){
		this.mapType = mapType;
	}
	
	public static TiledMap buildDefaultMap(MapType mapType){
		
		try {
			return create(mapType).setDimensions(11, 11).setDefaultTileId(1).setMapTiles(new ArrayList<MapTile>()).build();
		} catch (UnfinishedParameterSetup e) {
			Log.error(e.getMessage(), e);
			throw new RuntimeErrorException(new Error());
		}
	}
	
	public static MapBuilder create(MapType mapType){
		
		MapBuilder mapBuilder = null;
		
		if(mapType != null){
			mapBuilder = new MapBuilder(mapType);
		}
		
		return mapBuilder;
	}
	
	public MapBuilder setDimensions(int maxX, int maxY){
		
		this.maxX = maxX;
		this.maxY = maxY;
		
		return this;
	}
	
	public MapBuilder setDefaultTileId(int defaultTileId){
		this.defaultTileId = defaultTileId;
		
		return this;
	}
	
	public MapBuilder setMapTiles(List<MapTile> mapTiles){
		this.mapTiles = mapTiles;
		
		return this;
	}
	
	public MapBuilder setMapFilePath(String mapFilePath){
		this.mapFilePath = mapFilePath;
		
		return this;
	}
	
	public TiledMap build() throws UnfinishedParameterSetup{
		
		if(maxX <= 0 || maxY <= 0 || defaultTileId < 0 || mapType == null || mapTiles == null || mapFilePath == null || mapFilePath.isEmpty()){
			
			throw new UnfinishedParameterSetup();
		}

		TiledMap tiledMap = null;
		
		if (mapType == MapType.ISOMETRIC) {
			tiledMap = new IsometricTiledMap(maxX, maxY, defaultTileId, mapTiles, mapFilePath);
		} else if (mapType == MapType.ORTHOGONAL) {
			tiledMap = new OrthogonalTiledMap(maxX, maxY, defaultTileId, mapTiles, mapFilePath);
		}
		
		
		return tiledMap;
	}
}
