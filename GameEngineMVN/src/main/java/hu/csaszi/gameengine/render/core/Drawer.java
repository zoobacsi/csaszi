package hu.csaszi.gameengine.render.core;

import java.awt.*;

public interface Drawer {

    public void drawImage(Image img, int x, int y);
    public void drawString(String string, int x, int y);
    public void drawString(String string, int x, int y, Color color);
    public void drawString(String string, Font font, int x, int y, Color color);

    void fillRect(int convert, int convert1, int convert2, int convert3, Color backgroundColor);

    void drawRect(int convert, int convert1, int convert2, int convert3, Color frameColor);

    void setCameraPos(int cx, int cy);
}
