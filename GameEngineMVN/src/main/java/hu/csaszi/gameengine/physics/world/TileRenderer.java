package hu.csaszi.gameengine.physics.world;

import hu.csaszi.gameengine.render.core.gl.Texture;
import hu.csaszi.gameengine.render.core.gl.models.Model;
import hu.csaszi.gameengine.render.core.gl.renderer.Camera;
import hu.csaszi.gameengine.render.core.gl.shaders.Shader;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;

public class TileRenderer {

    private Map<String, Texture> tileTextures;
    private Model model;

    public TileRenderer(){

        tileTextures = new HashMap<String, Texture>();

        float[] vertices = new float[]{
                -0.5f, 0.5f, 0, // TOP LEFT      0
                0.5f, 0.5f, 0, // TOP RIGHT     1
                0.5f, -0.5f, 0, // BOTTOM RIGHT  2
                -0.5f, -0.5f, 0 // BOTTOM LEFT 3
        };

        float[] texCoords = new float[]{
                0, 0,
                1, 0,
                1, 1,
                0, 1
        };

        int[] indices = new int[]{
                0, 1, 2,
                2, 3, 0
        };

        model = new Model(vertices, texCoords, indices);

        for(int i = 0; i < Tile.tiles.length; i++){
            if(Tile.tiles[i] != null){
                String textureKey = Tile.tiles[i].getTexture();
                if(!tileTextures.containsKey(textureKey)){
                    tileTextures.put(textureKey, new Texture(textureKey));
                }
            }
        }
    }

    public void renderTile(byte id, int x, int y, Shader shader, Matrix4f world, Camera camera){

        shader.bind();
        String textureKey = Tile.tiles[id].getTexture();

        if(tileTextures.containsKey(textureKey)){
            tileTextures.get(textureKey).bind(0);
        }

        Matrix4f tilePos = new Matrix4f().translate(new Vector3f(x*1, y*1, 0));
        Matrix4f target = new Matrix4f();

        camera.getProjection().mul(world, target);
        target.mul(tilePos);

        shader.setUniform("sampler", 0);
        shader.setUniform("projection", target);
        model.render();
    }
}
