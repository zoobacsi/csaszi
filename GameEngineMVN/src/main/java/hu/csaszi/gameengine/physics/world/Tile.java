package hu.csaszi.gameengine.physics.world;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Tile {

    public static Tile[] tiles = new Tile[16];

    private static final Map<String, Tile> tileMap = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(Tile.class);
    private static byte numberOfTiles = 0;

    private byte id;
    private String texture;

    static {
        addTile("grass");
        addTile("desert");
        for(int i = 0; i < 16; i++){
            addTile("water" + i);
        }
    }

    public Tile(String texture) {
        this.id = numberOfTiles++;
        this.texture = texture;
        if(tiles[id] != null){
            throw new IllegalStateException("Tiles at "+ id+ "is already being used!");
        }

        if(numberOfTiles == tiles.length){
            tiles = Arrays.copyOf(tiles, tiles.length * 2);
            logger.info("New tiles length: " + tiles.length + " actual ID: " + id);
        }

        tiles[id] = this;
    }

    public String getTexture() {
        return texture;
    }

    public byte getId() {
        return id;
    }

    public static void addTile(String texture){
        tileMap.put(texture, new Tile(texture));
    }

//    public void clearTileMap(){
//        tileMap.clear();
//    }

    public static Tile getTile(String texture) {
        return tileMap.get(texture);
    }
}
