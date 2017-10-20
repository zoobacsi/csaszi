package hu.csaszi.gameengine.render.graphics;

import hu.csaszi.gameengine.util.ResourceLoader;

import java.awt.image.BufferedImage;

public class Image {

	private BufferedImage img;
	
	public Image(String path) {
		img = ResourceLoader.loadImage(path);
	}
	
	public Image(BufferedImage img){
		this.img = img;
	}

	public BufferedImage getRawImage() {
		return img;
	}
	
	public int getWidth() {
		return img.getWidth(null);
	}
	
	public int getHeight(){
		return img.getHeight(null);
	}
}
