package hu.csaszi.gameengine.test;
import java.io.IOException;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import cuchaz.jfxgl.CalledByEventsThread;
import cuchaz.jfxgl.JFXGL;
import cuchaz.jfxgl.JFXGLLauncher;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HelloWorld {

    public static void main(String[] args) {
        JFXGLLauncher.launchMain(HelloWorld.class, args);
    }

    public static void jfxglmain(String[] args)
            throws Exception {

        // create a window using GLFW (with a core OpenGL context)
        GLFW.glfwInit();
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        long hwnd = GLFW.glfwCreateWindow(800, 600, "JFXGL", MemoryUtil.NULL, MemoryUtil.NULL);

        // init OpenGL
        GLFW.glfwMakeContextCurrent(hwnd);
        GL.createCapabilities();

        try {

            // start the JavaFX app
            JFXGL.start(hwnd, args, new HelloWorldApp());

            // render loop
            while (!GLFW.glfwWindowShouldClose(hwnd)) {

                // render the JavaFX UI
                JFXGL.render();

                GLFW.glfwSwapBuffers(hwnd);
                GLFW.glfwPollEvents();
            }

        } finally {

            // cleanup
            JFXGL.terminate();
            Callbacks.glfwFreeCallbacks(hwnd);
            GLFW.glfwDestroyWindow(hwnd);
            GLFW.glfwTerminate();
        }
    }

    public static class HelloWorldApp extends Application {

        @Override
        @CalledByEventsThread
        public void start(Stage stage)
                throws IOException {

//            // create the UI
//            Label label = new Label("Hello World!");
//            TextField textField = new TextField("valami");
//            label.setFont(new Font("Helvetica", 70));
//            label.setTooltip(new Tooltip("Geci"));
//            label.setAlignment(Pos.CENTER);
//            Pane pane = new Pane();
//            pane.getChildren().add(label);
//            pane.getChildren().add(textField);
//
//            Scene scene = new Scene(pane);
//
//            Paint paint = new Color(0,1,1,0.7);
//            scene.setFill(paint);
//            stage.setScene(scene);
//            stage.setOpacity(0.5);
        }
    }
}