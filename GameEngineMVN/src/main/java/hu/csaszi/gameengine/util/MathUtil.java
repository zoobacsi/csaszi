package hu.csaszi.gameengine.util;

public class MathUtil {

    public static final float RAD_45 = (float)Math.PI/4;
    public static final float RAD_90 = (float)Math.PI/2;
    public static final float RAD_180 = (float)Math.PI;
    public static final float RAD_270 = RAD_90 + RAD_180;
    public static final float RAD_360 = (float)Math.PI*2;

    public static int convert(float number){
        return (int)Math.round(Math.floor(number));
    }
}
