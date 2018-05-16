package hu.csaszi.gameengine.render.core.gl;

import hu.csaszi.gameengine.render.core.gl.renderer.Camera;
import hu.csaszi.gameengine.render.core.gl.shaders.Shader;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.stb.STBTTPackContext;
import org.lwjgl.stb.STBTTPackedchar;
import org.lwjgl.system.Callback;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static hu.csaszi.gameengine.util.IOUtil.ioResourceToByteBuffer;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.stb.STBTruetype.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memAllocFloat;
import static org.lwjgl.system.MemoryUtil.memFree;

public class GLDrawer {

    private Camera camera;
    private GLFWWindow window;
    private Shader shader;
    private int ww;
    private int wh;
    private static final int BITMAP_W = 512;
    private static final int BITMAP_H = 512;

    private static final float[] scale = {
            24.0f,
            14.0f
    };

    private static final int[] sf = {
            0, 1, 2,
            0, 1, 2
    };

    // ----

    private final STBTTAlignedQuad q  = STBTTAlignedQuad.malloc();
    private final FloatBuffer xb = memAllocFloat(1);
    private final FloatBuffer      yb = memAllocFloat(1);
    private Callback debugProc;

    // ----

    private int fbw = ww;
    private int fbh = wh;

    private int font_tex;

    private STBTTPackedchar.Buffer chardata;

    private int font = 3;

    private boolean integer_align;
    private boolean translating;
    private boolean rotating;

    private boolean supportsSRGB;
    private boolean srgb;

    private float rotate_t, translate_t;

    private boolean show_tex;

    public GLDrawer(GLFWWindow window, Camera camera) {
        this.camera = camera;
        this.window = window;
        this.ww = window.getWidth();
        this.wh = window.getHeight();
        this.shader = new Shader("shader");
    }

    public void init(){
        load_fonts();
    }
    private void load_fonts() {
        font_tex = glGenTextures();
        chardata = STBTTPackedchar.malloc(6 * 128);

        shader = new Shader("fontshader");

        try (STBTTPackContext pc = STBTTPackContext.malloc()) {
            ByteBuffer ttf = ioResourceToByteBuffer("src/main/resources/fonts/arial.ttf", 512 * 1024);

            ByteBuffer bitmap = BufferUtils.createByteBuffer(BITMAP_W * BITMAP_H);

            stbtt_PackBegin(pc, bitmap, BITMAP_W, BITMAP_H, 0, 1, NULL);
            for (int i = 0; i < 2; i++) {
                int p = (i * 3 + 0) * 128 + 32;
                chardata.limit(p + 95);
                chardata.position(p);
                stbtt_PackSetOversampling(pc, 1, 1);
                stbtt_PackFontRange(pc, ttf, 0, scale[i], 32, chardata);

                p = (i * 3 + 1) * 128 + 32;
                chardata.limit(p + 95);
                chardata.position(p);
                stbtt_PackSetOversampling(pc, 2, 2);
                stbtt_PackFontRange(pc, ttf, 0, scale[i], 32, chardata);

                p = (i * 3 + 2) * 128 + 32;
                chardata.limit(p + 95);
                chardata.position(p);
                stbtt_PackSetOversampling(pc, 3, 1);
                stbtt_PackFontRange(pc, ttf, 0, scale[i], 32, chardata);
            }
            chardata.clear();
            stbtt_PackEnd(pc);

            glBindTexture(GL_TEXTURE_2D, font_tex);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_ALPHA, BITMAP_W, BITMAP_H, 0, GL_ALPHA, GL_UNSIGNED_BYTE, bitmap);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glActiveTexture(GL_TEXTURE0 + 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void draw_init() {
        glDisable(GL_CULL_FACE);
        glDisable(GL_TEXTURE_2D);
//        glDisable(GL_LIGHTING);
        glDisable(GL_DEPTH_TEST);

//        glViewport(0, 0, fbw, fbh);

//        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
//        glClear(GL_COLOR_BUFFER_BIT);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, ww, wh, 0.0, -100.0, 100.0);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
    }

    private void draw_world() {
        int sfont = sf[font];

//        glEnable(GL_BLEND);
//        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glColor3f(1.0f, 1.0f, 1.0f);

        print(80, 01, sfont, "Controls:");
        print(100, 60, sfont, "S: toggle font size");
        print(100, 85, sfont, "O: toggle oversampling");
        print(100, 110, sfont, "T: toggle translation");
        print(100, 135, sfont, "R: toggle rotation");
        print(100, 160, sfont, "P: toggle pixel-snap (only non-oversampled)");
        if (supportsSRGB) {
            print(100, 185, sfont, "G: toggle srgb gamma-correction");
        }
        print(100, 210, sfont, "B: toggle to black-on-white");
        print(100, 235, sfont, "V: view font texture");
        print(80, 300, sfont, "Current font:");
    }

    public void draw() {
        draw_init();
        draw_world();
    }

    private void drawBoxTC(float x0, float y0, float x1, float y1, float s0, float t0, float s1, float t1) {

        glTexCoord2f(s0, t0);
        glVertex3f(x0, y0, 0);
        glTexCoord2f(s1, t0);
        glVertex3f(x1, y0, 0);
        glTexCoord2f(s1, t1);
        glVertex3f(x1, y1, 0);
        glTexCoord2f(s0, t1);
        glVertex3f(x0, y1, 0);
    }

    private void print(float x, float y, int font, String text) {
        xb.put(0, x);
        yb.put(0, y);
        shader.bind();

        chardata.position(font * 128);
        Matrix4f mat = new Matrix4f();
//        camera.getUntransformedProjection().scale(16, mat);
//        mat.translate(10, 0, 0);
//        shader.bind();
//        shader.setUniform("projection", mat);
//        shader.setUniform("sampler", 0);

        glEnable(GL_TEXTURE_2D);
        glActiveTexture(GL_TEXTURE0 + 0);
        glBindTexture(GL_TEXTURE_2D, font_tex);
        Matrix4f target = new Matrix4f();
        target.scale(320);

        shader.setUniform("sampler", 0);
        shader.setUniform("projection", target);

        glBegin(GL_QUADS);

        for (int i = 0; i < text.length(); i++) {
            stbtt_GetPackedQuad(chardata, BITMAP_W, BITMAP_H, text.charAt(i), xb, yb, q, font == 0 && integer_align);

            drawBoxTC(
                    q.x0(), q.y0(), q.x1(), q.y1(),
                    q.s0(), q.t0(), q.s1(), q.t1()
            );
        }
        glEnd();


    }

    public void destroy() {
        chardata.free();

        if (debugProc != null) {
            debugProc.free();
        }

//        glfwFreeCallbacks(window);
//        glfwTerminate();
        glfwSetErrorCallback(null).free();

        memFree(yb);
        memFree(xb);

        q.free();
    }
}
