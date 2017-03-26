package hu.csaszi.twodee.util;

import java.util.HashMap;
import java.util.Map;

public enum Direction {
	
	STAND(-1),
	NORTH(0),
	NORTH_EAST(1),
	EAST(2),
	SOUTH_EAST(3),
	SOUTH(4),
	SOUTH_WEST(5),
	WEST(6),
	NORTH_WEST(7);
	
	private int id;

	private Direction(int id){
		this.id = id;
	}
	
	public int getId(){
		return this.id;
	}
	
	public static Map<Integer, Direction> mapById = new HashMap<>();
	
	static{
		for(Direction model : Direction.values()){
			
			mapById.put(model.id, model);
		}
	}
	
	public static Direction getById(int id){
		return mapById.get(id);
	}
	
	@Override
	public String toString() {
		
		return this.getClass().getSimpleName() + "." + this.name();
	}
}
