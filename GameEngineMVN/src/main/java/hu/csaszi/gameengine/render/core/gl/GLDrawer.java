package hu.csaszi.gameengine.render.core.gl;

import hu.csaszi.gameengine.render.core.Drawer;
import hu.csaszi.gameengine.render.core.Window;
import hu.csaszi.gameengine.render.graphics.imaging.Image;

import java.awt.*;

public class GLDrawer implements Drawer {

    private Window window;

    public GLDrawer(Window window) {

        this.window = window;
    }

    @Override
    public void drawImage(Image img, int x, int y) {

    }

    @Override
    public void drawString(String string, int x, int y) {

    }

    @Override
    public void drawString(String string, int x, int y, Color color) {

    }

    @Override
    public void drawString(String string, Font font, int x, int y, Color color) {

    }

    @Override
    public void fillRect(int convert, int convert1, int convert2, int convert3, Color backgroundColor) {

    }

    @Override
    public void drawRect(int convert, int convert1, int convert2, int convert3, Color frameColor) {

    }

    @Override
    public void setCameraPos(int cx, int cy) {

    }

}
