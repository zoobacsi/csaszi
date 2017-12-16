package hu.csaszi.gameengine.render.graphics.imaging;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Image {

	private static final Logger logger = LoggerFactory.getLogger(Image.class);

	protected BufferedImage loadedImage;
	private boolean flipped;
	private float alpha=1;
	private int rotation;

	public Image(String path) {
		try {
			 loadedImage = ImageIO.read(new File(path));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	public Image(BufferedImage img) {
		this.loadedImage = img;
	}

	public BufferedImage getGraphics() {
		return loadedImage;
	}

	public void draw(int x, int y) {
		//GameManager.getWindowFromPool().getDrawer().drawImage(loadedImage, x, y);
	}

	public void save(String path) {
		try {
			ImageIO.write(loadedImage, "PNG", new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void scale(int sx, int sy) {
		loadedImage = (BufferedImage) loadedImage.getScaledInstance(sx, sy, BufferedImage.SCALE_DEFAULT);
	}

	public Image getScaledCopy(int sx, int sy) {
		return new Image((BufferedImage) loadedImage.getScaledInstance(sx, sy, BufferedImage.SCALE_DEFAULT));
	}

	public void flip() {
		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		tx.translate(-loadedImage.getWidth(null), 0);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		flipped = !flipped;
		loadedImage = op.filter(loadedImage, null);
	}

	public void setRotation(int rot) {
		this.rotation = rot;
	}

	public Image getFlippedCopy() {
		Image img = new Image(loadedImage);
		img.flip();
		flipped = !flipped;
		return img;
	}

	public int getWidth() {
		return loadedImage.getWidth();
	}

	public int getHeight() {
		return loadedImage.getHeight();
	}

	public boolean isFlipped() {
		return flipped;
	}
	public void setOpacity(float opacity) {
		this.alpha = opacity;
	}
	public float getAlpha() {
		return alpha;
	}

	public BufferedImage getLoadedImage() {
		return loadedImage;
	}
}
