package hu.csaszi.gameengine.render.core.gl.renderer;

import hu.csaszi.gameengine.input.Input;
import hu.csaszi.gameengine.physics.Gravity;
import hu.csaszi.gameengine.render.core.Window;
import hu.csaszi.gameengine.util.MathUtil;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

    private Vector3f position;
    private Matrix4f projection;

    public Window getWindow() {
        return window;
    }

    private Window window;
    private Input input;

    private float curRotate;
    private float targetRotate;

    public Camera(Window window){

        int width = window.getWidth();
        int height = window.getHeight();

        this.window = window;
        this.input = window.getInput();

        position = new Vector3f(0, 0, 0);
        setProjection(width, height);
    }

//    public Camera(int width, int height){
//
//        position = new Vector3f(0, 0, 0);
//        setProjection(width, height);
//    }

    public void setProjection(int width, int height) {
        projection = new Matrix4f().setOrtho2D(-width/2, width/2, -height/2, height/2);
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void addPosition(Vector3f position) {
        this.position.add(position);
    }

    public Vector3f getPosition() {
        return position;
    }

    public Matrix4f getUntransformedProjection() {
        return projection;
    }

    public Matrix4f getProjection(){
        return projection.translate(position, new Matrix4f()).rotateLocalZ(curRotate);
    }

    public float getTargetRotate() {
        return targetRotate;
    }

    public void rotateCamera(float targetRotate) {
        System.out.println(Math.round(Math.floor((this.targetRotate - curRotate)*1000f)));
        if(Math.floor(Math.round(Math.floor(this.targetRotate - curRotate)*1000f)) != 0){
            return;
        }
        System.out.println("benis");
        this.targetRotate += targetRotate;
//
//        if(this.targetRotate >= MathUtil.RAD_360){
//            this.targetRotate = 0;
//        }

        Gravity.setGravity(this.targetRotate);
    }

    public void update(float delta){

        if(targetRotate > curRotate){
            curRotate += delta;
        }
        else if (targetRotate < curRotate){
            curRotate = targetRotate;

        }

        if (targetRotate == curRotate){
            if(curRotate == MathUtil.RAD_360) {
                curRotate = 0;
                targetRotate = 0;
            }
        }
//        if(input != null){
//
//            if(input.isKeyDown(GLFW_KEY_A)) {
//                position.sub(new Vector3f(-5,0,0));
//            }
//            if(input.isKeyDown(GLFW_KEY_D)) {
//                position.sub(new Vector3f(5,0,0));
//            }
//            if(input.isKeyDown(GLFW_KEY_W)) {
//                position.sub(new Vector3f(0,5,0));
//            }
//            if(input.isKeyDown(GLFW_KEY_S)) {
//                position.sub(new Vector3f(0,-5,0));
//            }
//        }
    }
}
