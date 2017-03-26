package hu.csaszi.twodee.graphics;

import java.awt.Image;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

public class CharGraphics {

    private Image charSet, attackSet, deadSet;
    private Shape charShape;
    private float duration;
    private String name;
    
    public CharGraphics(String name, Image charSet, Image attackSet, Image deadSet, Shape charShape, float duration){
	this.name = name;
	this.charSet = charSet;
	this.attackSet = attackSet;
	this.deadSet = deadSet;
	this.charShape = charShape;
	this.duration = duration;
    }

    public String getName(){
	return name;
    }
    
    public void setName(String name){
	this.name = name;
    }
    
    public Image getCharSet() {
        return charSet;
    }

    public void setCharSet(Image charSet) {
        this.charSet = charSet;
    }

    public Image getAttackSet() {
        return attackSet;
    }

    public void setAttackSet(Image attackSet) {
        this.attackSet = attackSet;
    }

    public Image getDeadSet() {
        return deadSet;
    }

    public void setDeadSet(Image deadSet) {
        this.deadSet = deadSet;
    }

    public Shape getCharShape() {
        return new Circle(charShape.getCenterX(), charShape.getCenterY(), 1);
    }

    public void setCharShape(Shape charShape) {
        this.charShape = charShape;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }
}
