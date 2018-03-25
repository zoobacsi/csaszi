package hu.csaszi.gameengine.test;

import cuchaz.jfxgl.JFXGLLauncher;
import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.render.core.Window;
import hu.csaszi.gameengine.render.core.gl.GLFWWindow;
import hu.csaszi.gameengine.test.states.TestSimpleGamePlayState;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleGame extends GameManager {

    private static Window window;
    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;
    private static final String TITLE = "Simple Game Test";

    private static final int GAME_ID = 0;

    private static Logger logger = LoggerFactory.getLogger(SimpleGame.class);

    public SimpleGame() {
//        GLFW.glfwInit();
//        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
//        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
//        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);
//        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        window = createWindow(TITLE, WIDTH, HEIGHT);
//        GLFW.glfwMakeContextCurrent( ((GLFWWindow)window).getWindow());
    }

    public static void main(String[] args) {
        JFXGLLauncher.launchMain(SimpleGame.class, args);
    }

    public static void jfxglmain(String[] args) throws Exception {

        logger.info("test");

        final SimpleGame main = new SimpleGame();

        main.addState(new TestSimpleGamePlayState(GAME_ID));
        main.enterState(GAME_ID, false);

        ((GLFWWindow)window)
                .setSize(640, 480)
                .setFullscreen(false);
        ((GLFWWindow)window).setCallbacks();

        window.run();

    }

}
