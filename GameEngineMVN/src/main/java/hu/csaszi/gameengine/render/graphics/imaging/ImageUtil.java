package hu.csaszi.gameengine.render.graphics.imaging;

import java.awt.image.BufferedImage;

public class ImageUtil {

//    public static BufferedImage

//    public static BufferedImage convertToBufferedImage(java.awt.Image img, int imgType) {
//
//        BufferedImage buffImg = new BufferedImage(img.getWidth(null), img.getHeight(null), imgType);
//        Graphics g = buffImg.getGraphics();
//        g.drawImage(img, 0, 0, null);
//        return buffImg;
//    }
//
//    public static Image convertAwtToSlickImage(String name, java.awt.Image img) throws IOException, SlickException {
//
//        BufferedImage buffImg = convertToBufferedImage(img, BufferedImage.TYPE_INT_ARGB);
//        Texture texture = BufferedImageUtil.getTexture(name, buffImg);
//
//    Image slickImage = new Image(texture.getImageWidth(), texture.getImageHeight());
//		slickImage.setTexture(texture);
//		slickImage.setName(name);
//
//		return slickImage;
//}
//
//    public static Image[][] spliceSpriteRows(String spriteName, java.awt.Image srcImage, int spriteWidth, int spriteHeight) throws IOException, SlickException{
//
//        Image[][] result = null;
//
//        int xTiles = srcImage.getWidth(null) / spriteWidth;
//        int yTiles = srcImage.getHeight(null) / spriteHeight;
//
//        String name = null;
//        if(xTiles > 0 || yTiles > 0){
//
//            result = new Image[yTiles][xTiles];
//
//            for(int y = 0; y < yTiles; y++){
//                for(int x = 0; x < xTiles; x++){
//
//                    name = spriteName + "-" + y + "-" + x;
//
//                    BufferedImage buffImg = new BufferedImage(spriteWidth, spriteHeight, BufferedImage.TYPE_INT_ARGB);
//
//                    // draws the image chunk
//                    Graphics2D gr = buffImg.createGraphics();
//                    gr.drawImage(srcImage, 0, 0, spriteWidth, spriteHeight, spriteWidth * x, spriteHeight * y, spriteWidth * x + spriteWidth, spriteHeight * y + spriteHeight, null);
//                    gr.dispose();
//                    Texture texture = BufferedImageUtil.getTexture(name, buffImg);
//
//                    Image slickImage = new Image(texture.getImageWidth(), texture.getImageHeight());
//                    slickImage.setTexture(texture);
//                    slickImage.setName(name);
//                    result[y][x] = slickImage;
//                }
//            }
//        }
//
//        return result;
//    }
//
//
//    public static Image[] spliceImage(String tileName, java.awt.Image srcImage, int tileWidth, int tileHeight) throws IOException, SlickException{
//        Image[] result = null;
//
//        int xTiles = srcImage.getWidth(null) / tileWidth;
//        int yTiles = srcImage.getHeight(null) / tileHeight;
//        if(xTiles > 0 || yTiles > 0){
//            int size = xTiles * yTiles;
//            int count = 0;
//            result = new Image[size];
//
//            String name = null;
//            for (int y = 0; y < yTiles; y++) {
//                for (int x = 0; x < xTiles; x++) {
//                    //name = x + "-" + y;
//                    name = tileName;
//                    BufferedImage buffImg = new BufferedImage(tileWidth, tileHeight,BufferedImage.TYPE_INT_ARGB);
//
//                    // draws the image chunk
//                    Graphics2D gr = buffImg.createGraphics();
//                    gr.drawImage(srcImage, 0, 0, tileWidth, tileHeight, tileWidth * x, tileHeight * y, tileWidth * x + tileWidth, tileHeight * y + tileHeight, null);
//                    gr.dispose();
//                    Texture texture = BufferedImageUtil.getTexture(name, buffImg);
//
//                    Image slickImage = new Image(texture.getImageWidth(), texture.getImageHeight());
//                    slickImage.setTexture(texture);
//                    slickImage.setName(name);
//                    result[count++] = slickImage;
//                }
//            }
//
//        }
//
//        return result;
//    }
}
