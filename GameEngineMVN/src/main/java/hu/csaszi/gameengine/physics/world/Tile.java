package hu.csaszi.gameengine.physics.world;

public class Tile {

    public static Tile[] tiles = new Tile[16];
    public static  final Tile testTile = new Tile((byte)0, "grass");
    public static final Tile test2 = new Tile((byte)1, "desert");

    private byte id;
    private String texture;

    public Tile(byte id, String texture) {
        this.id = id;
        this.texture = texture;
        if(tiles[id] != null){
            throw new IllegalStateException("Tiles at "+ id+ "is already being used!");
        }

        tiles[id] = this;
    }

    public String getTexture() {
        return texture;
    }

    public byte getId() {
        return id;
    }
}
