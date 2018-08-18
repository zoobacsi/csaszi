package hu.csaszi.gameengine.physics.world;

import hu.csaszi.gameengine.render.core.gl.Texture;
import hu.csaszi.gameengine.render.core.gl.TextureSheet;
import hu.csaszi.gameengine.render.core.gl.TileSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Tile {

    public static Tile[] tiles = new Tile[255];

    private static final Map<String, Tile> tileMap = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(Tile.class);
    private static byte numberOfTiles = 0;

    private byte id;
    private boolean solid;
    private String texture;
    private TextureSheet textureSheet;

    public Texture getTexture(int direction) {
        return textureSheet.getTexture(direction);
    }

    public int getTexturesNumber() {
        return textureSheet.getTexturesCount();
    }

    public boolean isAutoTile() {
        if(textureSheet == null) {
            return false;
        }

        return textureSheet.getTexturesCount() > 0;
    }

    static {

        addTile("bg");
        addTileSet("lofasz", 4, 4).setSolid();
    }

    public Tile(String texture) {
        this.id = numberOfTiles++;
        this.texture = texture;
        this.solid = false;
        if(tiles[id] != null){
            throw new IllegalStateException("Tiles at "+ id+ "is already being used!");
        }

        if(numberOfTiles == tiles.length){
            tiles = Arrays.copyOf(tiles, tiles.length * 2);
            logger.info("New tiles length: " + tiles.length + " actual ID: " + id);
        }

        tiles[id] = this;
    }

    public Tile(String texture, TextureSheet sheet) {

        this.id = numberOfTiles++;
        this.texture = texture;
        this.textureSheet = sheet;
        this.solid = false;
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

    public static Tile addTile(String texture){

        Tile tile = new Tile(texture);
        tileMap.put(texture, tile);

        return tile;
    }

    public static Tile addTileSet(String texture, int xNum, int yNum){

        TextureSheet sheet = new TextureSheet("lofasz", 8, 8);

        Tile tile = new Tile(texture, sheet);
        tileMap.put(texture, tile);

        return tile;
    }

//    public void clearTileMap(){
//        tileMap.clear();
//    }

    public static Tile getTile(String texture) {
        return tileMap.get(texture);
    }

    public Tile setSolid(){
        this.solid = true;
        return this;
    }

    public boolean isSolid(){
        return solid;
    }
}
