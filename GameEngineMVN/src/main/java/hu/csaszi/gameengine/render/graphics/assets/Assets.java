package hu.csaszi.gameengine.render.graphics.assets;

import hu.csaszi.gameengine.render.core.gl.models.Model;

import java.util.HashMap;

public class Assets {

    protected static HashMap<String, Model> models;

    static {
        models = new HashMap<>();
        models.put("tall", createRectangleModel(2f));
        models.put("box", createRectangleModel(1f));

    }

    public static Model getModel(String model){
        return models.get(model);
    }

    private static Model createRectangleModel(float ratio){

        float[] vertices = new float[]{
                -1f, ratio, 0, // TOP LEFT      0
                1f, ratio, 0, // TOP RIGHT     1
                1f, -1f, 0, // BOTTOM RIGHT  2
                -1f, -1f, 0 // BOTTOM LEFT 3
        };

        float[] texCoords = new float[]{
                0, 0,
                1, 0,
                1, 1,
                0, 1
        };

        int[] indices = new int[]{
                0, 1, 2,
                2, 3, 0
        };

        return new Model(vertices, texCoords, indices);
    }
}
