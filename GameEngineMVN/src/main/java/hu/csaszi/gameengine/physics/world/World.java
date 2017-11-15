package hu.csaszi.gameengine.physics.world;

import hu.csaszi.gameengine.render.core.Window;
import hu.csaszi.gameengine.render.core.gl.renderer.Camera;
import hu.csaszi.gameengine.render.core.gl.shaders.Shader;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class World {

    private final int view = 6;
    private byte[] tiles;
    private int width;
    private int height;
    private int scale;

    private Matrix4f world;

    public World(){
        width = 64;
        height = 64;
        scale = 32;
        tiles = new byte[width * height];

        world = new Matrix4f().translate(new Vector3f(0));
        world.scale(scale);

    }

    public  void render(TileRenderer render, Shader shader, Camera camera){
//        for (int i = 0; i < height; i++){
//            for(int j = 0; j < width; j++){
//                render.renderTile(tiles[j + i * width], j, -i, shader, world, camera);
//            }
//        }
       int posX = ((int)camera.getPosition().x + (camera.getWindow().getWidth()/2)) / (scale * 2);
       int posY = ((int)camera.getPosition().x + (camera.getWindow().getHeight()/2)) / (scale * 2);

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
    }

    public Tile getTile(int x, int y){

        try {
            return Tile.tiles[tiles[x + y * width]];
        } catch (ArrayIndexOutOfBoundsException e){
            return null;
        }
    }
}
