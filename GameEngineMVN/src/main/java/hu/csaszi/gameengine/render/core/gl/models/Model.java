package hu.csaszi.gameengine.render.core.gl.models;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Model {

    private int drawCount;
    private int vId;
    private int tId;
    private int iId;
    private int vao;

    public float getRatio() {
        return ratio;
    }

    private float ratio;

    public float getScale() {
        return scale;
    }

    private float scale;

    public Model(float[] vertices, float[] texCoords, int[] indices, float ratio) {
        this(vertices, texCoords, indices, ratio, 1f);
    }
    public Model(float[] vertices, float[] texCoords, int[] indices, float ratio, float scale) {

        this.ratio = ratio;
        drawCount = indices.length;

        vId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vId);
        glBufferData(GL_ARRAY_BUFFER, createBuffer(vertices), GL_STATIC_DRAW);

        tId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, tId);
        glBufferData(GL_ARRAY_BUFFER, createBuffer(texCoords), GL_STATIC_DRAW);

        iId = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, createBuffer(indices), GL_STATIC_DRAW);


        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        vao = glGenVertexArrays();
    }

    protected void finalize() throws Throwable{
//        glDeleteBuffers(vId);
//        glDeleteBuffers(tId);
//        glDeleteBuffers(iId);
        super.finalize();
    }
    public void render(){

        glBindVertexArray(vao);

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glBindBuffer(GL_ARRAY_BUFFER, vId);
        glVertexAttribPointer(0,3, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ARRAY_BUFFER, tId);
        glVertexAttribPointer(1,2, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iId);
        glDrawElements(GL_TRIANGLES, drawCount, GL_UNSIGNED_INT, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
    }

    private IntBuffer createBuffer(int[] data) {

        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();

        return buffer;
    }

    private FloatBuffer createBuffer(float[] data) {

        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();

        return buffer;
    }
}
