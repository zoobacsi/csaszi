package hu.csaszi.twodee.graphics;

import hu.csaszi.twodee.entity.interfaces.Drawable;

import java.util.Comparator;

public class DrawableComparator implements Comparator<Drawable> {

    @Override
    public int compare(Drawable o1, Drawable o2) {
	if(o1 == null || o2 == null){
	    return 0;
	} else{
	    
	    if (o1.getCenterY() > o2.getCenterY()) {
		return 1;
	    } else if (o1.getCenterY() == o2.getCenterY()) {
		return 0;
	    } else if (o1.getCenterY() < o2.getCenterY()) {
		return -1;
	    }
	}
	
	return 0;
    }

}
