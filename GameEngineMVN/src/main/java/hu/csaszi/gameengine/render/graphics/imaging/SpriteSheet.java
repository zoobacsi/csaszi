package hu.csaszi.gameengine.render.graphics.imaging;

import java.awt.image.BufferedImage;

public class SpriteSheet {

	private BufferedImage img;
	private int sx;
	private int sy;
	private BufferedImage[][] spriteCache;

	public SpriteSheet(Image img, int sx, int sy) {

		this.img = img.loadedImage;
		this.sx = sx;
		this.sy = sy;
		
		spriteCache = new BufferedImage[img.getWidth() / sx][img.getHeight()/sy];
	}

	public BufferedImage getSpriteImage(int x, int y) {

		if(img == null){
			System.out.println("SpriteSheet >> No Image set");
			return null;
		}
		if(spriteCache[x][y] == null){
			spriteCache[x][y] = img.getSubimage(x * sx, y * sy, sx, sy);
		}
		return spriteCache[x][y];
	}
	
	public Image getSprite(int arrayX, int arrayY) {
		return new Image(getSpriteImage(arrayX, arrayY));
	}
}
