package hu.csaszi.gameengine.physics.world;

import hu.csaszi.gameengine.render.core.gl.Texture;
import hu.csaszi.gameengine.render.core.gl.models.Model;
import hu.csaszi.gameengine.render.core.gl.renderer.Camera;
import hu.csaszi.gameengine.render.core.gl.shaders.Shader;
import hu.csaszi.gameengine.render.graphics.assets.Assets;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;

public class TileRenderer {

    private Map<String, Texture> tileTextures;
    private Model model;
    private boolean isometric;

    public TileRenderer(boolean isometric){

        this.isometric = isometric;

        tileTextures = new HashMap<String, Texture>();

        for(int i = 0; i < Tile.tiles.length; i++){
            if(Tile.tiles[i] != null){
                String textureKey = Tile.tiles[i].getTexture();
                if(Tile.tiles[i].isAutoTile()){
                    for (int num = 0; num < Tile.tiles[i].getTexturesNumber(); num++) {
                        textureKey = Tile.tiles[i].getTexture() + "-" + num;
                        putTexture(textureKey, Tile.tiles[i].getTexture(num));
                    }
                } else {
                    putTexture(textureKey, new Texture(textureKey));
                }

            }
        }

        model = Assets.getModel("box");
    }

    private void putTexture(String textureKey, Texture texture) {
        if (!tileTextures.containsKey(textureKey)) {
            tileTextures.put(textureKey, texture);
        }
    }

    public void renderTile(byte id, float x, float y, Shader shader, Matrix4f world, Camera camera, int direction){
        shader.bind();
        String textureKey = Tile.tiles[id].getTexture();

        if(Tile.tiles[id].isAutoTile()){
            textureKey = textureKey + "-" + direction;
        }

        if (tileTextures.containsKey(textureKey)) {
            tileTextures.get(textureKey).bind(0, 0);
        }

        Matrix4f tilePos = new Matrix4f().translate(new Vector3f(x*2, y*2, 0));
        Matrix4f target = new Matrix4f();

        camera.getProjection().mul(world, target);
        target.mul(tilePos);

        shader.setUniform("sampler", 0);
        shader.setUniform("projection", target);
        model.render();
    }

    public void renderAutoTile(byte id, float x, float y, Shader shader, Matrix4f world, Camera camera, int tileNum){
        shader.bind();
        String textureKey = Tile.tiles[id].getTexture() + "-" + tileNum;

        if(tileTextures.containsKey(textureKey)){
            tileTextures.get(textureKey).bind(0, 0);
        }

        Matrix4f tilePos = new Matrix4f().translate(new Vector3f(x*2, y*2, 0));
        Matrix4f target = new Matrix4f();

        camera.getProjection().mul(world, target);
        target.mul(tilePos);

        shader.setUniform("sampler", 0);
        shader.setUniform("projection", target);
        model.render();
    }

}
