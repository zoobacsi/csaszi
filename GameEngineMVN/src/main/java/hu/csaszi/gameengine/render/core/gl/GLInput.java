package hu.csaszi.gameengine.render.core.gl;

import hu.csaszi.gameengine.input.Input;
import org.lwjgl.BufferUtils;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class GLInput implements Input {

    private long window;

    private boolean keys[];
    private boolean mouse[];

    private int mouseX;
    private int mouseY;
    private DoubleBuffer xBuffer;
    private DoubleBuffer yBuffer;

    public GLInput(GLFWWindow window) {
        this(window.getWindow());
    }

    public GLInput(long window) {

        this.window = window;

        this.keys = new boolean[GLFW_KEY_LAST];
        for(int i = 0; i < GLFW_KEY_LAST; i++){
            keys[i] = false;
        }

        this.mouse = new boolean[GLFW_MOUSE_BUTTON_LAST];
        for(int i = 0; i < GLFW_MOUSE_BUTTON_LAST; i++){
            mouse[i] = false;
        }

        xBuffer = BufferUtils.createDoubleBuffer(1);
        yBuffer = BufferUtils.createDoubleBuffer(1);
    }

    @Override
    public boolean isKeyPressed(int key) {
        return (isKeyDown(key) && !keys[key]);
    }

    public boolean isKeyDown(int key){
        return glfwGetKey(window, key) == GLFW_TRUE;
    }

    @Override
    public boolean isKeyReleased(int key) {
        return (!isKeyDown(key) && keys[key]);
    }

    @Override
    public boolean isMouseButtonPressed(int button) {
        return (isMouseButtonDown(button) && !mouse[button]);
    }

    public boolean isMouseButtonDown(int button) {
        return glfwGetMouseButton(window, button) == GLFW_TRUE;
    }

    @Override
    public boolean isMouseButtonReleased(int button) {
        return (!isMouseButtonDown(button) && mouse[button]);
    }

    @Override
    public int getMouseX() {
        return mouseX;
    }

    @Override
    public int getMouseY() {
        return mouseY;
    }

    public void update(){

        for(int i = 32; i < GLFW_KEY_LAST; i++) {
            keys[i] = isKeyDown(i);
        }

        for(int i = 0; i < GLFW_MOUSE_BUTTON_LAST; i++) {
            mouse[i] = isMouseButtonDown(i);
        }


        glfwGetCursorPos(window, xBuffer, yBuffer);
        mouseX = (int)xBuffer.get(0);
        mouseY = (int)yBuffer.get(0);
    }

}
