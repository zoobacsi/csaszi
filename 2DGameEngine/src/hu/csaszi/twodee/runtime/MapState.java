package hu.csaszi.twodee.runtime;

import hu.csaszi.twodee.map.interfaces.TiledMap;


public interface MapState {

	public float getGlobalSpeed();
	public TiledMap getTiledMap();
}
