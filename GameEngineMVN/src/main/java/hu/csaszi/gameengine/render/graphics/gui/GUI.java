package hu.csaszi.gameengine.render.graphics.gui;

import hu.csaszi.gameengine.render.core.Window;
import hu.csaszi.gameengine.render.core.gl.GLFWWindow;
import hu.csaszi.gameengine.render.core.gl.TileSheet;
import hu.csaszi.gameengine.render.core.gl.renderer.Camera;
import hu.csaszi.gameengine.render.core.gl.shaders.Shader;


public class GUI {

    private Shader shader;
    private Camera camera;
    private TileSheet sheet;


    private static volatile boolean running = false;

    public GUI(GLFWWindow window) {
        shader = new Shader("legacy.vert", "legacy.frag");
        camera = new Camera(window);
        sheet = new TileSheet("Soldier3", 4);
}

    public void resizeCamera(Window window) {
        camera.setProjection(window.getWidth(), window.getHeight());
    }


    public void render() {
//        Matrix4f mat = new Matrix4f();
//        camera.getUntransformedProjection().scale(16, mat);
//        mat.translate(-2.6f, -2f, 0);
        shader.bind();
//        shader.setUniform("projection", mat);
//        sheet.bindTile(shader, 1);
//        Assets.getModel("box").render();

//
//        String scoreText = "Score sf sdfg \n fgsdf  dsgsdf \n fasdgsadf a sgfsdfgadwf  \n sdgserg fwedfwergfsdfawdfasd\n fgsdf  dsgsdf \n fasdgsadf a sgfsdfgadwf  \n sdgserg fwedfwergfsdfawdfasd\n fgsdf  dsgsdf \n fasdgsadf a sgfsdfgadwf  \n sdgserg fwedfwergfsdfawdfasd\n fgsdf  dsgsdf \n fasdgsadf a sgfsdfgadwf  \n sdgserg fwedfwergfsdfawdfasd\n fgsdf  dsgsdf \n fasdgsadf a sgfsdfgadwf  \n sdgserg fwedfwergfsdfawdfasd\n fgsdf  dsgsdf \n fasdgsadf a sgfsdfgadwf  \n sdgserg fwedfwergfsdfawdfasd";
//        int scoreTextWidth = renderer.getTextWidth(scoreText);
//        int scoreTextHeight = renderer.getTextHeight(scoreText);
//        float scoreTextX = (camera.getWindow().getWidth() - scoreTextWidth) / 2f;
//        float scoreTextY = camera.getWindow().getHeight() - scoreTextHeight - 5;
//        renderer.drawText(scoreText, scoreTextX, scoreTextY, Color.WHITE);
    }
}
