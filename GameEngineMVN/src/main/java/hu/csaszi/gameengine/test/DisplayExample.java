package hu.csaszi.gameengine.test;

import hu.csaszi.gameengine.render.core.gl.Texture;
import hu.csaszi.gameengine.render.core.gl.Timer;
import hu.csaszi.gameengine.render.core.gl.models.Model;
import hu.csaszi.gameengine.render.core.gl.renderer.Camera;
import hu.csaszi.gameengine.render.core.gl.shaders.Shader;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class DisplayExample {

//    // The window handle
//    private long window;
//
//    public void run() {
//        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
//
//        init();
//        loop();
//
//        // Free the window callbacks and destroy the window
//        glfwFreeCallbacks(window);
//        glfwDestroyWindow(window);
//
//        // Terminate GLFW and free the error callback
//        glfwTerminate();
//        glfwSetErrorCallback(null).free();
//    }
//
//    private void init() {
//        // Setup an error callback. The default implementation
//        // will print the error message in System.err.
//        GLFWErrorCallback.createPrint(System.err).set();
//
//        // Initialize GLFW. Most GLFW functions will not work before doing this.
//        if (!glfwInit())
//            throw new IllegalStateException("Unable to initialize GLFW");
//
//        // Configure GLFW
//        glfwDefaultWindowHints(); // optional, the current window hints are already the default
//        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
//        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
//
//        // Create the window
//        window = glfwCreateWindow(640, 480, "Hello World!", NULL, NULL);
//        if (window == NULL)
//            throw new RuntimeException("Failed to create the GLFW window");
//
//        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
//        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
//            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
//                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
//        });
//
//        // Get the thread stack and push a new frame
//        try (MemoryStack stack = stackPush()) {
//            IntBuffer pWidth = stack.mallocInt(1); // int*
//            IntBuffer pHeight = stack.mallocInt(1); // int*
//
//            // Get the window size passed to glfwCreateWindow
//            glfwGetWindowSize(window, pWidth, pHeight);
//
//            // Get the resolution of the primary monitor
//            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
//
//            // Center the window
//            glfwSetWindowPos(
//                    window,
//                    (vidmode.width() - pWidth.get(0)) / 2,
//                    (vidmode.height() - pHeight.get(0)) / 2
//            );
//        } // the stack frame is popped automatically
//
//        // Make the OpenGL context current
//        glfwMakeContextCurrent(window);
//        // Enable v-sync
//        glfwSwapInterval(1);
//
//        // Make the window visible
//        glfwShowWindow(window);
//    }
//
//    private void loop() {
//        // This line is critical for LWJGL's interoperation with GLFW's
//        // OpenGL context, or any context that is managed externally.
//        // LWJGL detects the context that is current in the current thread,
//        // creates the GLCapabilities instance and makes the OpenGL
//        // bindings available for use.
//        GL.createCapabilities();
//
//        Camera camera = new Camera(640, 480);
//
//        glEnable(GL_TEXTURE_2D);
//
//        float[] vertices = new float[]{
//               -0.5f, 0.5f, 0, // TOP LEFT      0
//                0.5f, 0.5f, 0, // TOP RIGHT     1
//                0.5f,-0.5f, 0, // BOTTOM RIGHT  2
//                -0.5f, -0.5f, 0 // BOTTOM LEFT 3
//
//        };
//
//        float[] texCoords = new float[] {
//                0,0,
//                1,0,
//                1,1,
//                0,1
//        };
//
//        int[] indices = new int[] {
//            0,1,2,
//            2,3,0
//        };
//
//        Model model = new Model(vertices, texCoords, indices);
//
//        Shader shader = new Shader("shader");
//
//        Texture texture = new Texture("src/main/resources/grass.png");
//
//        Matrix4f projection = new Matrix4f()
//                .ortho2D(-640/2, 640/2, -480/2, 480/2);
//
//        Matrix4f scale = new Matrix4f()
//                .translate(new Vector3f(100, 0, 0))
//                .scale(64);
//
//        Matrix4f target = new Matrix4f();
//
//        camera.setPosition(new Vector3f(-100, 0, 0));
//
//        double frameCap = 1.0/60.0;
//
//        double frameTime = 0;
//        int frames = 0;
//
//        double time = Timer.getTime();
//        double unprocessed = 0.0;
//        // Set the clear color
//        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
//
//        // Run the rendering loop until the user has attempted to close
//        // the window or has pressed the ESCAPE key.
//        while (!glfwWindowShouldClose(window)) {
//
//            boolean canRender = false;
//
//            double time2 = Timer.getTime();
//            double passed = time2 - time;
//            unprocessed += passed;
//
//            frameTime += passed;
//            time = time2;
//
//            while(unprocessed >= frameCap){
//
//                unprocessed -= frameCap;
//
//                canRender = true;
//
//                target = scale;
//                if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GL_TRUE) {
//                    glfwSetWindowShouldClose(window, true);
//                }
//
//                // Poll for window events. The key callback above will only be
//                // invoked during this call.
//                glfwPollEvents();
//                if(frameTime >= 1.0){
//                    frameTime = 0.0;
//                    System.out.println("FPS: " + frames);
//                    frames = 0;
//                }
//            }
//
//            if(canRender){
//
//                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
//
//                shader.bind();
//                shader.setUniform("sampler", 0);
//                shader.setUniform("projection", camera.getProjection().mul(target));
//                texture.bind(0);
//
//                model.render();
//
//                glfwSwapBuffers(window); // swap the color buffers
//                frames++;
//            }
//
//
//        }
//    }
//
//    public static void main(String[] args) {
//        new DisplayExample().run();
//    }

}