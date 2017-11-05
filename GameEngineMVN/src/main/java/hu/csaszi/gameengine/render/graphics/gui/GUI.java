package hu.csaszi.gameengine.render.graphics.gui;

import hu.csaszi.gameengine.render.core.Drawer;
import hu.csaszi.gameengine.render.core.Window;
import hu.csaszi.gameengine.render.graphics.imaging.Image;
import hu.csaszi.gameengine.util.MathUtil;

import java.awt.*;


public abstract class GUI {


    protected float x;
    protected float y;
    protected float sx;
    protected float sy;

    protected Image image;

    protected boolean hasImage;
    protected boolean doDraw;
    protected boolean showWireFrame;
    protected boolean hasBackground;
    protected boolean hasLabel;

    protected String labelText = "";

    protected Color backgroundColor;
    protected Color frameColor;
    protected Color foregroundColor;

    protected Font labelFont;

    public abstract void update(Window window);

    public void render(Window window, Drawer drawer){

        if(doDraw){

            if(!hasImage || image == null){
                if(hasBackground){
                    drawer.fillRect(MathUtil.convert(x), MathUtil.convert(getY()), MathUtil.convert(getSx()), MathUtil.convert(getSy()), backgroundColor);
                }
            } else {
                drawer.drawImage(image.getLoadedImage(), MathUtil.convert(x), MathUtil.convert(getY()));
            }

            if(showWireFrame){
                drawer.drawRect(MathUtil.convert(x), MathUtil.convert(getY()), MathUtil.convert(getSx()), MathUtil.convert(getSy()), frameColor);
            }
            if(hasLabel && labelText != null && !labelText.isEmpty()){
                drawer.drawString(labelText, labelFont, MathUtil.convert(x), MathUtil.convert(y), foregroundColor);
            }
        }
    }

    public void setLabelText(String text){
        this.labelText = text;
    }

    public void setPos(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setVisible(boolean state){
        doDraw = state;
    }

    public boolean isVisible(){
        return doDraw;
    }

    public float getX(){
        return x;
    }

    public float getY() {
        return y;
    }

    public float getSx() {
        return sx;
    }

    public float getSy() {
        return sy;
    }
}
