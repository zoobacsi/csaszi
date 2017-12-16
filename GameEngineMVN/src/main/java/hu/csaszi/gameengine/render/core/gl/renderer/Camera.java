package hu.csaszi.gameengine.render.core.gl.renderer;

import hu.csaszi.gameengine.input.Input;
import hu.csaszi.gameengine.render.core.Window;
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
        return projection.translate(position, new Matrix4f());
    }

    public void update(){

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
