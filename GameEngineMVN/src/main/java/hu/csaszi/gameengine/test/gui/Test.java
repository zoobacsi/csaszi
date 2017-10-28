package hu.csaszi.gameengine.test.gui;

import hu.csaszi.gameengine.render.core.Window;
import hu.csaszi.gameengine.render.graphics.gui.GUI;

import java.awt.*;

public class Test extends GUI{

    public Test(int x, int y){
        this.x = x;
        this.y = y;
        this.sx = 100;
        this.sy = 50;
        this.doDraw = true;
        this.backgroundColor = Color.BLUE;
    }

    @Override
    public void update(Window window) {

    }
}