package hu.csaszi.gameengine.render.core.gl;

import hu.csaszi.gameengine.render.core.gl.shaders.Shader;
import org.joml.Matrix4f;

public class TileSheet {
    private Texture texture;
    private Matrix4f scaleX;
    private Matrix4f translation;
    private int amountOfTilesX;

    public TileSheet(String texture, int amountOfTilesX){
        this.texture = new Texture("sheets/" + texture);
        scaleX = new Matrix4f().scale(1.0f / (float)amountOfTilesX);
        translation = new Matrix4f();
        this.amountOfTilesX = amountOfTilesX;
    }

    public void bindTile(Shader shader, int x, int y){
        scaleX.translate(x, y,0,translation);

        shader.setUniform("sampler", 0);
        shader.setUniform("texModifier", translation);

        texture.bind(0, 0);
    }

    public void bindTile(Shader shader, int tile){
        int x = tile % amountOfTilesX;
        int y = tile / amountOfTilesX;

        bindTile(shader, x, y);
    }
}
