package hu.csaszi.gameengine.render.graphics;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ImageTests {

    private Image image;

    @Before
    public void setup(){
        image = new Image("src/test/resources/urgay.png");
    }
    @Test
    public void testImageNotNull() {
        assertNotNull(image);
    }

    @Test
    public void testImageSize() {
        System.out.println(image.getHeight() + " " + image.getWidth());
        assertEquals(image.getHeight(), 352);
        assertEquals(image.getWidth(), 512);
    }
}
