package hu.csaszi.twodee.map;

import java.util.HashMap;
import java.util.Map;

public enum MapType {

	ORTHOGONAL(1),
	ISOMETRIC(2);
	
	private static final Map<Integer, MapType> mapById;
    
    private static final Map<String, MapType> mapByName;
    
    private int id;
    
    static {
        
        mapById = new HashMap<Integer, MapType>();
        
        mapByName = new HashMap<String, MapType>();
        
        for (MapType value : values()) {
            
            mapById.put(value.getId(), value);
            
            mapByName.put(value.name(), value);
            
        }
        
    }
    
    private MapType(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public static MapType getById(int id) {
        return mapById.get(id);
    }
    
    public static MapType getByName(String name) {
        return mapByName.get(name);
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + "." + name();
    }
}
