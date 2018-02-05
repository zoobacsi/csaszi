package hu.csaszi.gameengine.render.core.gl;

import hu.csaszi.gameengine.render.graphics.imaging.PNGDecoder;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBTTPackContext;
import org.lwjgl.stb.STBTTPackedchar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import static hu.csaszi.gameengine.test.IOUtil.ioResourceToByteBuffer;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.stb.STBTruetype.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Texture implements Sprite {

    private static final Logger logger = LoggerFactory.getLogger(Texture.class);

    private int id;
    private int width;
    private int height;
    private int xIndex = 1;
    private int yIndex = 1;

    private int font_tex;
    private static final int BITMAP_W = 512;
    private static final int BITMAP_H = 512;
    private STBTTPackedchar.Buffer chardata;
    private static final float[] scale = {
            24.0f,
            14.0f
    };

    public Texture(String fileName) {
        this(fileName, 1, 1);
    }

    public Texture(String fileName, int x, int y) {

        this.xIndex = x;
        this.yIndex = y;

        BufferedImage bufferedImage;

        try {

            PNGDecoder decoder = new PNGDecoder(new FileInputStream(new File("src/main/resources/textures/" + fileName + ".png")));
            width = decoder.getWidth();
            height = decoder.getHeight();

            ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);
            decoder.decode(pixels, width * 4, PNGDecoder.Format.RGBA);

            pixels.flip();

            id = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, id);

            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

//        id = glGenTextures();
//        chardata = STBTTPackedchar.malloc(6 * 128);
//
//        try (STBTTPackContext pc = STBTTPackContext.malloc()) {
//            ByteBuffer ttf = ioResourceToByteBuffer("src/main/resources/fonts/arial.ttf", 512 * 1024);
//
//            ByteBuffer bitmap = BufferUtils.createByteBuffer(BITMAP_W * BITMAP_H);
//
//            stbtt_PackBegin(pc, bitmap, BITMAP_W, BITMAP_H, 0, 1, NULL);
//            for (int i = 0; i < 2; i++) {
//                int p = (i * 3 + 0) * 128 + 32;
//                chardata.limit(p + 95);
//                chardata.position(p);
//                stbtt_PackSetOversampling(pc, 1, 1);
//                stbtt_PackFontRange(pc, ttf, 0, scale[i], 32, chardata);
//
//                p = (i * 3 + 1) * 128 + 32;
//                chardata.limit(p + 95);
//                chardata.position(p);
//                stbtt_PackSetOversampling(pc, 2, 2);
//                stbtt_PackFontRange(pc, ttf, 0, scale[i], 32, chardata);
//
//                p = (i * 3 + 2) * 128 + 32;
//                chardata.limit(p + 95);
//                chardata.position(p);
//                stbtt_PackSetOversampling(pc, 3, 1);
//                stbtt_PackFontRange(pc, ttf, 0, scale[i], 32, chardata);
//            }
//            chardata.clear();
//            stbtt_PackEnd(pc);
//
//
//            glBindTexture(GL_TEXTURE_2D, id);
//            glTexImage2D(GL_TEXTURE_2D, 0, GL_ALPHA, BITMAP_W, BITMAP_H, 0, GL_ALPHA, GL_UNSIGNED_BYTE, bitmap);
//            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
//            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
//            glActiveTexture(GL_TEXTURE0 + 0);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    public Texture(BufferedImage bufferedImage) {

        this.xIndex = 1;
        this.yIndex = 1;

        this.width = bufferedImage.getWidth();
        this.height = bufferedImage.getHeight();

        int[] pixels = bufferedImage.getRGB(0, 0, width, height, null, 0, width);

        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4); //4 for RGBA, 3 for RGB

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = pixels[y * width + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                buffer.put((byte) (pixel & 0xFF));               // Blue component
                buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
            }
        }

        buffer.flip(); //FOR THE LOVE OF GOD DO NOT FORGET THIS

        // You now have a ByteBuffer filled with the color data of each pixel.
        // Now just create a texture ID and bind it. Then you can load it using
        // whatever OpenGL method you want, for example:

        id = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, id);

        //setup wrap mode
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        //setup texture scaling filtering
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        //Send texel data to OpenGL
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer); //GL_RGBA8 was GL_RGB8A
    }

    protected void finalize() throws Throwable {
        //glDeleteTextures(id);
        super.finalize();
    }

    @Override
    public void bind(int directionId, int sampler) {

        if (sampler >= 0 && sampler <= 31) {
            glActiveTexture(GL_TEXTURE0 + sampler);
            glBindTexture(GL_TEXTURE_2D, id);
        }
    }

    @Override
    public float getRatio() {
        return (height * 1f) / (width * 1f);
    }
}
