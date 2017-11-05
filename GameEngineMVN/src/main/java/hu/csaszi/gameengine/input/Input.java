package hu.csaszi.gameengine.input;

public interface Input {

    public boolean isKeyPressed(int key);
    public boolean isKeyDown(int key);
    public boolean isKeyReleased(int key);

    public boolean isMouseButtonPressed(int button);
    public boolean isMouseButtonDown(int button);
    public boolean isMouseButtonReleased(int button);

    public int getMouseX();
    public int getMouseY();
}
