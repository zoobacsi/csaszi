package hu.csaszi.gameengine.render.core.gl;

import hu.csaszi.gameengine.render.graphics.imaging.SpriteSheet;
import org.lwjgl.BufferUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

public class Texture implements Sprite{

    private static final Logger logger = LoggerFactory.getLogger(Texture.class);

    private int id;
    private int width;
    private int height;
    private int xIndex = 1;
    private int yIndex = 1;

    public Texture(String fileName){
        this(fileName, 1, 1);
    }

    public Texture(String fileName, int x, int y) {

        this.xIndex = x;
        this.yIndex = y;

        BufferedImage bufferedImage;

        try{

            bufferedImage = ImageIO.read(new File("src/main/resources/textures/" + fileName + ".png"));
            width = bufferedImage.getWidth();
            height = bufferedImage.getHeight();
System.out.println(width + " " + height);
//            int tileWidth = width;
//            int tileHeight;

            int[] pixels_raw = new int[width * height * 4];
            pixels_raw = bufferedImage.getRGB(0, 0, width, height,null, 0 , width);

            ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);

            for(int i = 0; i < width; i++){
                for(int j = 0; j < height; j++){
                    int pixel = pixels_raw[i*width + j];
                    pixels.put((byte)((pixel >> 16) & 0xFF)); // RED
                    pixels.put((byte)((pixel >> 8) & 0xFF)); // GREEN
                    pixels.put((byte)(pixel & 0xFF)); // BLUE
                    pixels.put((byte)((pixel >> 24) & 0xFF)); // ALPHA
                }
            }

            pixels.flip();

            id = glGenTextures();

            glBindTexture(GL_TEXTURE_2D, id);

            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA,GL_UNSIGNED_BYTE, pixels);

        } catch (IOException e){
            logger.error(e.getMessage(), e);
        }
    }

    protected void finalize() throws Throwable{
        glDeleteTextures(id);
        super.finalize();
    }

    @Override
    public void bind(int sampler) {

        if(sampler >= 0 && sampler <=31) {
            glActiveTexture(GL_TEXTURE0 + sampler);
            glBindTexture(GL_TEXTURE_2D, id);
        }
    }

    @Override
    public float getRatio() {
        return (height * 1f) / (width * 1f);
    }
}
