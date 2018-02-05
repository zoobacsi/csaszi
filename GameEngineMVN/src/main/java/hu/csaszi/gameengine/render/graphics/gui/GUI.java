package hu.csaszi.gameengine.render.graphics.gui;

import hu.csaszi.gameengine.render.core.Window;
import hu.csaszi.gameengine.render.core.gl.GLFWWindow;
import hu.csaszi.gameengine.render.core.gl.TileSheet;
import hu.csaszi.gameengine.render.core.gl.renderer.Camera;
import hu.csaszi.gameengine.render.core.gl.shaders.Shader;
import hu.csaszi.gameengine.render.graphics.assets.Assets;
import org.joml.Matrix4f;
import org.joml.Vector2i;
import org.liquidengine.legui.DefaultInitializer;
import org.liquidengine.legui.border.SimpleLineBorder;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.*;
import org.liquidengine.legui.event.CursorEnterEvent;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.listener.CursorEnterEventListener;
import org.liquidengine.legui.listener.MouseClickEventListener;
import org.liquidengine.legui.listener.processor.EventProcessor;
import org.liquidengine.legui.system.context.CallbackKeeper;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.context.DefaultCallbackKeeper;
import org.liquidengine.legui.system.handler.processor.SystemEventProcessor;
import org.liquidengine.legui.system.renderer.Renderer;
import org.liquidengine.legui.system.renderer.nvg.NvgRenderer;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;


public class GUI {

    private Shader shader;
    private Camera camera;
    private TileSheet sheet;
    private Context context;
    private Renderer renderer;
    private Frame frame;
    private static volatile boolean running = false;
    private SystemEventProcessor systemEventProcessor;

    public GUI(GLFWWindow window) {
        shader = new Shader("gui");
        camera = new Camera(window);
        sheet = new TileSheet("Soldier3", 4);

//        frame = new Frame(window.getWidth(), 200);
//        frame.getContainer().setBackgroundColor(0f, 0.7f, 0.1f, 0.4f);
//
//        Button button = new Button("valami", 20, 20, 160, 30);
//
//        SimpleLineBorder border = new SimpleLineBorder(ColorConstants.black(), 1);
//        button.setBorder(border);
//
//        boolean[] added = {false};
//        button.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
//            System.out.println("lófasz");
//            if (!added[0]) {
//                added[0] = true;
//                frame.getContainer().addAll(generateOnFly());
//            }
//        });
//
//        button.getListenerMap().addListener(CursorEnterEvent.class, (CursorEnterEventListener) System.out::println);
//
//        frame.getContainer().add(button);
//
//
//        DefaultInitializer initializer = new DefaultInitializer(window.getWindow(), frame);
//
//        CallbackKeeper keeper = new DefaultCallbackKeeper();
//
//        GLFWKeyCallbackI glfwKeyCallbackI = (w1, key, code, action, mods) -> running = !(key == GLFW_KEY_ESCAPE && action != GLFW_RELEASE);
//        GLFWWindowCloseCallbackI glfwWindowCloseCallbackI = w -> running = false;
//        initializer.getCallbackKeeper().getChainKeyCallback().add(glfwKeyCallbackI);
//        initializer.getCallbackKeeper().getChainWindowCloseCallback().add(glfwWindowCloseCallbackI);
//        // Event processor for system events. System events should be processed and translated to gui events.
//
//        keeper.getChainKeyCallback().add(glfwKeyCallbackI);
//        keeper.getChainWindowCloseCallback().add(glfwWindowCloseCallbackI);
//
//        systemEventProcessor = new SystemEventProcessor();
//        systemEventProcessor.addDefaultCallbacks(keeper);
//
//        context = initializer.getContext();
//        renderer = initializer.getRenderer();
//        renderer.initialize();
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
//
//        context.updateGlfwWindow();
//        Vector2i windowSize = context.getWindowSize();
//
//        renderer.render(frame, context);
//        // Now we need to process events. Firstly we need to process system events.
//        systemEventProcessor.processEvents(frame, context);
//
//        // When system events are translated to GUI events we need to process them.
//        // This event processor calls listeners added to ui components
//        EventProcessor.getInstance().processEvents();

    }

//    private static List<Component> generateOnFly() {
//        List<Component> list = new ArrayList<>();
//
//        Label label = new Label(20, 60, 200, 20);
//        label.getTextState().setText("Generated on fly label");
//        label.getTextState().setTextColor(ColorConstants.red());
//
//        RadioButtonGroup group = new RadioButtonGroup();
//        RadioButton radioButtonFirst = new RadioButton("First", 20, 90, 200, 20);
//        RadioButton radioButtonSecond = new RadioButton("Second", 20, 110, 200, 20);
//
//        radioButtonFirst.setRadioButtonGroup(group);
//        radioButtonSecond.setRadioButtonGroup(group);
//
//        list.add(label);
//        list.add(radioButtonFirst);
//        list.add(radioButtonSecond);
//
//        return list;
//    }
//
//    public void destroy(){
//        renderer.destroy();
//
//    }
}
