package hu.csaszi.gameengine.physics.world;

import hu.csaszi.gameengine.physics.collission.AABB;
import hu.csaszi.gameengine.render.core.Window;
import hu.csaszi.gameengine.render.core.gl.renderer.Camera;
import hu.csaszi.gameengine.render.core.gl.shaders.Shader;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

public class World {

    private final int view = 24;
    private byte[] tiles;
    private AABB[] boundingBoxes;
    private int width;
    private int height;
    private int scale;

    private Matrix4f world;

    public World(String world){
        try {
            BufferedImage tileSheet = ImageIO.read(new File("src/main/resources/levels/" + world + "_tiles.png"));
            //BufferedImage entitySheet = ImageIO.read(new File("./levels/" + world + "_entities.png"));

            width = tileSheet.getWidth();
            height = tileSheet.getHeight();
            scale = 32;

            this.world = new Matrix4f().setTranslation(new Vector3f());
            this.world.scale(scale);

            int[] colorTileSheet = tileSheet.getRGB(0, 0, width, height, null, 0,width);

            tiles = new byte[width * height];
            boundingBoxes = new AABB[width * height];

            for(int y = 0; y < height; y++){
                for(int x = 0; x < width; x++){
                    int red = (colorTileSheet[x + y * width] >> 16) & 0xFF;

                    Tile tile;
                    try{
                        tile = Tile.tiles[red];
                    } catch (ArrayIndexOutOfBoundsException e){
                        tile = null;
                    }
                    if(tile != null){
                        setTile(tile, x, y);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public World(){
        width = 64;
        height = 64;
        scale = 32;
        tiles = new byte[width * height];
        boundingBoxes = new AABB[width * height];
        world = new Matrix4f().translate(new Vector3f(0));
        world.scale(scale);

    }

    public Matrix4f getWorldMatrix() {
        return world;
    }

    public  void render(TileRenderer render, Shader shader, Camera camera){

       int posX = ((int)camera.getPosition().x + (camera.getWindow().getWidth()/2)) / (scale * 2);
       int posY = ((int)camera.getPosition().y - (camera.getWindow().getHeight()/2)) / (scale * 2);

       for(int i = 0; i < view; i++){
           for(int j = 0; j < view; j++){
               Tile tile = getTile(i - posX, j + posY);
               if(tile != null){
                   render.renderTile(tile.getId(),i-posX, -j-posY, shader, world, camera);
               }
           }
       }

    }

    public void correctCamera(Camera camera){
        Vector3f pos = camera.getPosition();
        Window window = camera.getWindow();

        int w = -width * scale * 2;
        int h = height * scale * 2;

        if(pos.x > -(window.getWidth() / 2) + scale) {
            pos.x = -(window.getWidth() / 2) + scale;
        }
        if(pos.x < w + (window.getWidth() / 2) + scale) {
            pos.x = w + (window.getWidth() / 2) + scale;
        }

        if(pos.y < (window.getHeight() / 2) - scale) {
            pos.y = (window.getHeight() / 2) - scale;
        }
        if(pos.y > h - (window.getHeight() / 2) - scale) {
            pos.y = h - (window.getHeight() / 2) - scale;
        }
    }

    public void setTile(Tile tile, int x, int y) {
        tiles[x + y * width] = tile.getId();
        if(tile.isSolid()){
            System.out.println(x + " " + y + " lófasz");
            boundingBoxes[x + y * width] = new AABB(new Vector2f(x*2, -y * 2), new Vector2f(1, 1));
        } else {
            boundingBoxes[x + y * width] = null;
        }
    }

    public Tile getTile(int x, int y){

        try {
            return Tile.tiles[tiles[x + y * width]];
        } catch (ArrayIndexOutOfBoundsException e){
            return null;
        }
    }
    public AABB getTileBoundingBox(int x, int y){

        try {
            return boundingBoxes[x + y * width];
        } catch (ArrayIndexOutOfBoundsException e){
            return null;
        }
    }

    public int getScale() {
        return scale;
    }
}
