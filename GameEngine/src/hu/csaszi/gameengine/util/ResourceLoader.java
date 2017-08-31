package hu.csaszi.gameengine.util;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ResourceLoader {

	public static Image loadImage(String path) {

		try {
			File imgFile = new File(path);
			System.out.println("exists: " + imgFile.exists());
			Image img = ImageIO.read(imgFile);
			
			return img;
			
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}

}
