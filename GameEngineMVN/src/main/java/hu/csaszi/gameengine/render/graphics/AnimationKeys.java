package hu.csaszi.gameengine.render.graphics;

public class AnimationKeys {

    public static final int[][] DEFAULT_CHARSET_WALKING_FRAMES = new int[][] {
            {8, 9, 10, 11},
            {8, 9, 10, 11},
            {8, 9, 10, 11},
            {8, 9, 10, 11},
            {4, 5, 6, 7},
            {4, 5, 6, 7},
            {4, 5, 6, 7},
            {4, 5, 6, 7}
    };
//    {12, 13, 14, 15},
//    {0, 1, 2, 3},
    public static final int[][] DEFAULT_CHARSET_IDLE_FRAMES = new int[][] {
            {8},
            {8},
            {8},
            {8},
            {4},
            {4},
            {4},
            {4}
    };

    public static final int[][] PLATFORMER_CHARSET_WALKING_FRAMES = new int[][] {
            {0, 1, 2, 3},
            {0, 1, 2, 3},
            {0, 1, 2, 3},
            {0, 1, 2, 3},
            {4, 5, 6, 7},
            {4, 5, 6, 7},
            {4, 5, 6, 7},
            {4, 5, 6, 7}
    };

    public static final int[][] PLATFORMER_CHARSET_IDLE_FRAMES = new int[][] {
            {0},
            {0},
            {0},
            {0},
            {4},
            {4},
            {4},
            {4}
    };
}
