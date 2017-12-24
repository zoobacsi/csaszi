package hu.csaszi.gameengine.render.graphics.gui;

import hu.csaszi.gameengine.render.core.Window;
import hu.csaszi.gameengine.render.core.gl.GLFWWindow;
import hu.csaszi.gameengine.render.core.gl.TileSheet;
import hu.csaszi.gameengine.render.core.gl.renderer.Camera;
import hu.csaszi.gameengine.render.core.gl.shaders.Shader;
import hu.csaszi.gameengine.render.graphics.assets.Assets;
import org.joml.Matrix4f;


public class GUI {

    private Shader shader;
    private Camera camera;
    private TileSheet sheet;

    public GUI(GLFWWindow window) {
        shader = new Shader("gui");
        camera = new Camera(window);
        sheet = new TileSheet("Soldier3", 4);
    }

    public void resizeCamera(Window window) {
        camera.setProjection(window.getWidth(), window.getHeight());
    }

    public void render() {
        Matrix4f mat = new Matrix4f();
        camera.getUntransformedProjection().scale(16, mat);
        mat.translate(-2.6f, -2f, 0);
        shader.bind();
        shader.setUniform("projection", mat);
        sheet.bindTile(shader, 1);
        //shader.setUniform("color", new Vector4f(0,0,0,0.4f));


        Assets.getModel("box").render();
    }
}
