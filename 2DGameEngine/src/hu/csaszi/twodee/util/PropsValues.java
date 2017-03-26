package hu.csaszi.twodee.util;

import java.util.Properties;

public class PropsValues {

	private Properties properties; 
	
	public static PropsValues instance;
	
	private PropsValues(){

	}
	
	public static PropsValues getInstance(){
		
		if(instance == null){
			instance = new PropsValues();
		}
		
		return instance;
	}
	
	public static final String TITLE = PropsUtil.getString(PropsKeys.TITLE, "Sample Game");
	
	public static final int GLOBAL_SCALE = PropsUtil.getInteger(PropsKeys.GLOBAL_SCALE, 1);
	public static final float GLOBAL_SPEED = PropsUtil.getFloat(PropsKeys.GLOBAL_SPEED, 2f);
	
	public static final int ISOMETRIC_TILE_WIDTH = PropsUtil.getInteger(PropsKeys.ISOMETRIC_TILE_WIDTH, 128);
	public static final int ISOMETRIC_TILE_HEIGHT = PropsUtil.getInteger(PropsKeys.ISOMETRIC_TILE_HEIGHT, 54);
	public static final int ORTHOGONAL_TILE_WIDTH = PropsUtil.getInteger(PropsKeys.ORTHOGONAL_TILE_WIDTH, 100);
	public static final int ORTHOGONAL_TILE_HEIGHT = PropsUtil.getInteger(PropsKeys.ORTHOGONAL_TILE_HEIGHT, 100);
}
