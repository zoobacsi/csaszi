package hu.csaszi.gameengine.render.core.gl;

import hu.csaszi.gameengine.util.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

public class TextureSheet {

    private static Logger logger = LoggerFactory.getLogger(TextureSheet.class);

    private int tileNumX;
    private int tileNumY;
    private Texture[][] textures;
    private BufferedImage bufferedImage;

    public TextureSheet(String fileName, int tileWidth, int tileHeight){

        try{
            File file = IOUtil.getFile(fileName + ".png", "textures/");
            bufferedImage = ImageIO.read(file);

            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();

            tileNumX = width / tileWidth;
            tileNumY = height / tileHeight;
            textures = new Texture[tileNumX][tileNumY];

            for(int y = 0; y < tileNumY; y++){
                for(int x = 0; x < tileNumX; x++){
                    textures[x][y] = new Texture(bufferedImage.getSubimage(
                            x * tileWidth,
                            y * tileHeight,
                            tileWidth,
                            tileHeight));
                }
            }

        } catch (IOException e){
            logger.error(e.getMessage(), e);
        }

    }

    public Texture getTexture(int x, int y){
        return textures[x][y];
    }

    public Texture getTexture(int tile){
        int x = tile % tileNumX;
        int y = tile / tileNumY;
        return textures[x][y];
    }
}
