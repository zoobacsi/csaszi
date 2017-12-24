package hu.csaszi.gameengine.physics;

import org.joml.Vector2f;

import java.util.HashMap;
import java.util.Map;

public enum Direction {

    NORTH(0, 0f, 1f),
    NORTH_EAST(1, 1f, 1f),
    EAST(2, 1f, 0f),
    SOUTH_EAST(3, 1f, -1f),
    SOUTH(4, 0f, -1f),
    SOUTH_WEST(5, -1f, -1f),
    WEST(6, -1f, 0f),
    NORTH_WEST(7, -1f, 1f);

    private int id;
    private float x;
    private float y;

    private Direction(int id, float x, float y) {
        this.id = id;
        this.x = x;
        this.y = y;
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

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
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
