package hu.csaszi.gameengine.render.graphics.gui;

import hu.csaszi.gameengine.render.core.Window;

import java.awt.*;

public class Label extends GUI{

    public Label(String text, String font, int size, int x, int y){
        this(text, x, y);
        this.labelFont = new Font(font, Font.BOLD, size);
    }

    public Label(String text, int x, int y){

        this.x = x;
        this.y = y;
        this.doDraw = true;
        this.hasLabel = true;
        this.labelText = text;
        this.hasBackground = false;
        this.sx = 100;
        this.sy = 50;
        this.backgroundColor = Color.RED;
        this.foregroundColor = Color.yellow;
    }

    public void setFont(String font, int size){
        this.labelFont = new Font(font, Font.BOLD, size);
    }

    @Override
    public void update(Window window) {

    }
}

