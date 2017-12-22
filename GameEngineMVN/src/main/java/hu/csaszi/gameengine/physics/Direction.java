package hu.csaszi.gameengine.physics;

import org.joml.Vector2f;

import java.util.HashMap;
import java.util.Map;

public enum Direction {

    NORTH(0),
    NORTH_EAST(1),
    EAST(2),
    SOUTH_EAST(3),
    SOUTH(4),
    SOUTH_WEST(5),
    WEST(6),
    NORTH_WEST(7);

    private int id;

    private Direction(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static Map<Integer, Direction> mapById = new HashMap<>();

    static {
        for (Direction model : Direction.values()) {

            mapById.put(model.id, model);
        }
    }

    public static Direction getById(int id) {
        return mapById.get(id);
    }

    public static Direction getByVector(Vector2f direction){
        if (direction.x == 0 && direction.y > 0){
            return NORTH;
        }
        if (direction.x > 0 && direction.y == 0){
            return EAST;
        }
        if (direction.x == 0 && direction.y < 0){
            return SOUTH;
        }
        if (direction.x < 0 && direction.y == 0){
            return WEST;
        }
        if (direction.x > 0 && direction.y > 0){
            return NORTH_EAST;
        }
        if (direction.x < 0 && direction.y > 0){
            return NORTH_WEST;
        }
        if (direction.x > 0 && direction.y < 0){
            return SOUTH_EAST;
        }
        if (direction.x < 0 && direction.y < 0){
            return SOUTH_WEST;
        }
        return null;
    }

    @Override
    public String toString() {

        return this.getClass().getSimpleName() + "." + this.name();
    }
}
