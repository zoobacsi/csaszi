package hu.csaszi.gameengine.render.core.gl;

import cuchaz.jfxgl.CalledByEventsThread;
import cuchaz.jfxgl.JFXGL;
import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.input.Input;
import hu.csaszi.gameengine.render.core.Drawer;
import hu.csaszi.gameengine.render.core.Window;
import hu.csaszi.gameengine.render.core.gl.renderer.Camera;
import hu.csaszi.gameengine.render.graphics.gui.GUI;
import hu.csaszi.gameengine.test.HelloWorld;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.*;
import org.lwjgl.system.Callback;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;

public class GLFWWindow implements Window {

    private long window;

    private String title = "Sample Title";
    private int width;
    private int height;

    private boolean fullscreen;
    private boolean hasResized;
    private GLFWWindowSizeCallback windowSizeCallback;
    private GameManager gameManager;
    private Drawer drawer;
    private Callback debugProc;

    public Input getInput() {
        return input;
    }

    private Input input;

    private int currentFPS;

    public GLFWWindow(String title, int width, int height, GameManager gameManager) {

        this.title = title;
        this.gameManager = gameManager;

        setSize(width, height);
        setFullscreen(false);

        this.hasResized = false;
//        this.drawer = new GLDrawer(this);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isHasResized(){
        return hasResized;
    }
    public boolean isFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    @Override
    public Drawer getDrawer() {
        return drawer;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public void createWindow(String title) {

        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);
        glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);

        window = glfwCreateWindow(
                width,
                height,
                title,
                fullscreen ? glfwGetPrimaryMonitor() : 0,
                0);

        if (window == 0) {
            throw new IllegalStateException("Failed to create Window!");
        }


        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                close();
        });

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            if (!fullscreen) {
                // Get the resolution of the primary monitor
                GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

                // Center the window
                glfwSetWindowPos(
                        window,
                        (vidmode.width() - pWidth.get(0)) / 2,
                        (vidmode.height() - pHeight.get(0)) / 2
                );
            }
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        setLocalCallbacks();
    }

    public void cleanUp(){
        glfwFreeCallbacks(window);
    }

    public static void setCallbacks() {
        glfwSetErrorCallback(new GLFWErrorCallback() {
            @Override
            public void invoke(int error, long description) {
                throw new IllegalStateException(GLFWErrorCallback.getDescription(description));
            }
        });
    }

    private void setLocalCallbacks(){
        windowSizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long argWindow, int argWidth, int argHeight) {

//                if(window == argWindow) {
                    width = argWidth;
                    height = argHeight;
                    hasResized = true;
//                }
            }
        };

        glfwSetWindowSizeCallback(window, windowSizeCallback);
    }

    public GLFWWindow setSize(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    @Override
    public void show() {

        glfwShowWindow(window);
    }

    @Override
    public synchronized void update(float delta) {

        hasResized = false;

        if (input != null) {
            input.update();
        }
        glfwPollEvents();
        gameManager.update(delta);
    }

    @Override
    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
    }

    @Override
    public void close() {
        JFXGL.terminate();
        glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }


    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    private void init() {

        // Create the window
        createWindow(title);

        show();
    }

    private void loop() {

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.

        GL.createCapabilities();


        glEnable(GL_TEXTURE_2D);
        debugProc = GLUtil.setupDebugMessageCallback();

        double frameCap = 1.0 / 60.0;

        double frameTime = 0;
        int frames = 0;

        double time = Timer.getTime();
        double unprocessed = 0.0;

        input = new GLInput(this);

        // Set the clear color
        glClearColor(0.7f, 0.7f, 0.7f, 1.0f);

        gameManager.getCurrentState().init(this, gameManager);

//        JFXGL.start(window, null, new HelloWorldApp());


        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!shouldClose()) {

            boolean canRender = false;

            double time2 = Timer.getTime();
            double passed = time2 - time;
            unprocessed += passed;

            frameTime += passed;
            time = time2;

            while (unprocessed >= frameCap) {

                if (hasResized) {
                    glViewport(0,0,width,height);
                    gameManager.getCurrentState().getGUI().resizeCamera(this);
                    gameManager.getCurrentState().getCamera().setProjection(width, height);
                    gameManager.getCurrentState().getWorld().calculateView(this);
                }
                unprocessed -= frameCap;

                canRender = true;

                update((float) frameCap);

                if (input.isKeyDown(GLFW_KEY_ESCAPE)) {
                    glfwSetWindowShouldClose(window, true);
                }

                if (frameTime >= 1.0) {
                    frameTime = 0.0;
                    currentFPS = frames;
                    System.out.println("FPS: " + currentFPS);
                    frames = 0;
                }
            }

            if (canRender) {

                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

                gameManager.render();

                JFXGL.render();

//                GUI gui = gameManager.getCurrentState().getGUI();
//                if(gui != null){
//                    gui.render();
//                }
                glfwSwapBuffers(window); // swap the color buffers
                frames++;
            }
        }
    }

    public int getCurrentFPS() {
        return currentFPS;
    }

    public long getWindow() {
        return window;
    }

    public static class HelloWorldApp extends Application {

        @Override
        @CalledByEventsThread
        public void start(Stage stage)
                throws IOException {

            // create the UI
            Label label = new Label("Hello World!");
            TextField textField = new TextField("valami");
            label.setFont(new Font("Helvetica", 70));
            label.setTooltip(new Tooltip("Geci"));
            label.setAlignment(Pos.CENTER);
            Pane pane = new Pane();
            pane.getChildren().add(label);
            pane.getChildren().add(textField);

            Scene scene = new Scene(pane);

            Paint paint = new Color(0,1,1,0.7);
            scene.setFill(paint);
            stage.setScene(scene);
        }
    }
}
