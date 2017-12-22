package hu.csaszi.gameengine.render.graphics.gui;

import hu.csaszi.gameengine.render.core.Drawer;
import hu.csaszi.gameengine.render.core.Window;
import hu.csaszi.gameengine.render.core.gl.GLFWWindow;
import hu.csaszi.gameengine.render.core.gl.TileSheet;
import hu.csaszi.gameengine.render.core.gl.renderer.Camera;
import hu.csaszi.gameengine.render.core.gl.shaders.Shader;
import hu.csaszi.gameengine.render.graphics.assets.Assets;
import hu.csaszi.gameengine.render.graphics.imaging.Image;
import hu.csaszi.gameengine.util.MathUtil;
import org.joml.Matrix4f;
import org.joml.Vector4f;

import java.awt.*;


public class GUI {

    private Shader shader;
    private Camera camera;
    private TileSheet sheet;

    public GUI(GLFWWindow window){
        shader = new Shader("gui");
        camera = new Camera(window);
        sheet = new TileSheet("Soldier3", 4);
    }

    public void resizeCamera(Window window){
        camera.setProjection(window.getWidth(), window.getHeight());
    }

    public void render(){
        Matrix4f mat = new Matrix4f();
        camera.getUntransformedProjection().scale(16, mat);
        mat.translate(-2.6f, -2f, 0);
        shader.bind();
        shader.setUniform("projection", mat);
        sheet.bindTile(shader, 1);
        //shader.setUniform("color", new Vector4f(0,0,0,0.4f));


        Assets.getModel("box").render();
    }
//    protected float x;
//    protected float y;
//    protected float sx;
//    protected float sy;
//
//    protected Image image;
//
//    protected boolean hasImage;
//    protected boolean doDraw;
//    protected boolean showWireFrame;
//    protected boolean hasBackground;
//    protected boolean hasLabel;
//
//    protected String labelText = "";
//
//    protected Color backgroundColor;
//    protected Color frameColor;
//    protected Color foregroundColor;
//
//    protected Font labelFont;
//
//    public abstract void update(Window window);
//
//    public void render(Window window, Drawer drawer){
//
//        if(doDraw){
//
//            if(!hasImage || image == null){
//                if(hasBackground){
//                    drawer.fillRect(MathUtil.convert(x), MathUtil.convert(getY()), MathUtil.convert(getSx()), MathUtil.convert(getSy()), backgroundColor);
//                }
//            } else {
//                drawer.drawImage(image.getLoadedImage(), MathUtil.convert(x), MathUtil.convert(getY()));
//            }
//
//            if(showWireFrame){
//                drawer.drawRect(MathUtil.convert(x), MathUtil.convert(getY()), MathUtil.convert(getSx()), MathUtil.convert(getSy()), frameColor);
//            }
//            if(hasLabel && labelText != null && !labelText.isEmpty()){
//                drawer.drawString(labelText, labelFont, MathUtil.convert(x), MathUtil.convert(y), foregroundColor);
//            }
//        }
//    }
//
//    public void setLabelText(String text){
//        this.labelText = text;
//    }
//
//    public void setPos(int x, int y){
//        this.x = x;
//        this.y = y;
//    }
//
//    public void setVisible(boolean state){
//        doDraw = state;
//    }
//
//    public boolean isVisible(){
//        return doDraw;
//    }
//
//    public float getX(){
//        return x;
//    }
//
//    public float getY() {
//        return y;
//    }
//
//    public float getSx() {
//        return sx;
//    }
//
//    public float getSy() {
//        return sy;
//    }
}
