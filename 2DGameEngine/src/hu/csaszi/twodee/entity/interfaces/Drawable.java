package hu.csaszi.twodee.entity.interfaces;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public interface Drawable {
    
    public void draw();
    
    public void draw(Color color);
    
    public float getCenterY();
    
    public float getCenterX();

}
