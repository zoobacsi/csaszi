package hu.csaszi.gameengine.render.core.gl;

import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.input.Input;
import hu.csaszi.gameengine.render.core.Drawer;
import hu.csaszi.gameengine.render.core.Window;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL15;
import org.lwjgl.system.MemoryStack;

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
    private GameManager gameManager;
    private Drawer drawer;

    public Input getInput() {
        return input;
    }

    private Input input;

    private int currentFPS;

    public GLFWWindow(String title, int width, int height, GameManager gameManager) {

        setSize(width, height);
        this.title = title;
        this.gameManager = gameManager;
        setFullscreen(false);

        this.drawer = new GLDrawer(this);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
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

            if(!fullscreen){
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
    }

    public static void setCallbacks(){
        glfwSetErrorCallback(new GLFWErrorCallback() {
            @Override
            public void invoke(int error, long description) {
                throw new IllegalStateException(GLFWErrorCallback.getDescription(description));
            }
        });
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

        glfwPollEvents();

        if(input != null){
            input.update();
        }

        gameManager.update(delta);
    }

    @Override
    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
    }

    @Override
    public void close() {

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

        double frameCap = 1.0 / 60.0;

        double frameTime = 0;
        int frames = 0;

        double time = Timer.getTime();
        double unprocessed = 0.0;

        input = new GLInput(this);

        // Set the clear color
        glClearColor(0.7f, 0.7f, 0.7f, 1.0f);

        gameManager.getCurrentState().init(this, gameManager);

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

                unprocessed -= frameCap;

                canRender = true;

                update((float)frameCap);

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

                glfwSwapBuffers(window); // swap the color buffers
                frames++;
            }
        }
    }

    public int getCurrentFPS() {
        return currentFPS;
    }

    public long getWindow(){
        return window;
    }
}
