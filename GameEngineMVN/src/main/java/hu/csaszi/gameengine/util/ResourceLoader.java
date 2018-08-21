package hu.csaszi.gameengine.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class ResourceLoader {

    private static List locations = new ArrayList();

    static {
        locations.add(new ClasspathLocation());
        locations.add(new FileSystemLocation(new File(".")));
    }

    /**
     * Add a location that will be searched for resources
     *
     * @param location The location that will be searched for resoruces
     */
    public static void addResourceLocation(ResourceLocation location) {
        locations.add(location);
    }

    /**
     * Remove a location that will be no longer be searched for resources
     *
     * @param location The location that will be removed from the search list
     */
    public static void removeResourceLocation(ResourceLocation location) {
        locations.remove(location);
    }

    /**
     * Remove all the locations, no resources will be found until
     * new locations have been added
     */
    public static void removeAllResourceLocations() {
        locations.clear();
    }

    public static BufferedImage loadImage(String path) {

        try {
            File imgFile = new File(path);
            //System.out.println("exists: " + imgFile.exists());
            //System.out.println("path: " + new File(".//").getAbsolutePath());
            BufferedImage img = ImageIO.read(imgFile);

            return img;

        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public static InputStream getResourceAsStream(String ref) {
        InputStream in = null;

        for (int i = 0; i < locations.size(); i++) {
            ResourceLocation location = (ResourceLocation) locations.get(i);
            in = location.getResourceAsStream(ref);
            if (in != null) {
                break;
            }
        }

        if (in == null) {
            throw new RuntimeException("Resource not found: " + ref);
        }

        return new BufferedInputStream(in);
    }
}
