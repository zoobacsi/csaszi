package hu.csaszi.gameengine.render.core.gl.shaders;

import hu.csaszi.gameengine.util.IOUtil;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL14.GL_FUNC_ADD;
import static org.lwjgl.opengl.GL14.glBlendEquation;
import static org.lwjgl.opengl.GL20.*;

public class Shader {

    private static final Logger logger = LoggerFactory.getLogger(Shader.class);

    public int getProgram() {
        return program;
    }

    private int program;
    private int vertexShader;
    private int fragmentShader;

    public Shader(String vertFile, String fragFile) {

        program = glCreateProgram();

        vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, readFile(vertFile));
        glCompileShader(vertexShader);

        if (glGetShaderi(vertexShader, GL_COMPILE_STATUS) != 1) {
            logger.error(glGetShaderInfoLog(vertexShader));
            System.exit(1);
        }

        fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, readFile(fragFile));
        glCompileShader(fragmentShader);

        if (glGetShaderi(fragmentShader, GL_COMPILE_STATUS) != 1) {
            logger.error(glGetShaderInfoLog(fragmentShader));
            System.exit(1);
        }

        init();
    }

    public Shader(String fileName) {

        program = glCreateProgram();

        vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, readFile(fileName + ".vs"));
        glCompileShader(vertexShader);

        if (glGetShaderi(vertexShader, GL_COMPILE_STATUS) != 1) {
            logger.error(glGetShaderInfoLog(vertexShader));
            System.exit(1);
        }

        fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, readFile(fileName + ".fs"));
        glCompileShader(fragmentShader);

        if (glGetShaderi(fragmentShader, GL_COMPILE_STATUS) != 1) {
            logger.error(glGetShaderInfoLog(fragmentShader));
            System.exit(1);
        }

        init();
    }

    private void init() {
        glAttachShader(program, vertexShader);
        glAttachShader(program, fragmentShader);

        glBindAttribLocation(program, 0, "vertices");
        glBindAttribLocation(program, 1, "textures");

        glLinkProgram(program);
        if (glGetProgrami(program, GL_LINK_STATUS) != 1) {
            logger.error(glGetProgramInfoLog(program));
            System.exit(1);
        }
        glValidateProgram(program);

        if (glGetProgrami(program, GL_VALIDATE_STATUS) != 1) {
            logger.error(glGetProgramInfoLog(program));
            System.exit(1);
        }

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    public void setUniform(String name, int value) {

        int location = glGetUniformLocation(program, name);

        if (location != -1) {
            glUniform1i(location, value);
        }
    }

    public void setUniform(String uniform, Vector4f value) {

        int location = glGetUniformLocation(program, uniform);

        if (location != -1) {
            glUniform4f(location,value.x, value.y, value.z, value.w);
        }
    }

    protected void finalize() throws Throwable {
//        glDetachShader(program, vertexShader);
//        glDetachShader(program, fragmentShader);
//        glDeleteShader(vertexShader);
//        glDeleteShader(fragmentShader);
//        glDeleteProgram(program);
        super.finalize();
    }

    public void setUniform(String name, Matrix4f value) {

        int location = glGetUniformLocation(program, name);

        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        value.get(buffer);
        if (location != -1) {
            glUniformMatrix4fv(location, false, buffer);
        }
    }

    public void bind() {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glUseProgram(program);
    }

    private String readFile(String fileName) {

        StringBuilder string = new StringBuilder();

        File file = IOUtil.getFile(fileName, "shaders/");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = br.readLine()) != null) {
                string.append(line);
                string.append("\n");
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }

        return string.toString();
    }
}
