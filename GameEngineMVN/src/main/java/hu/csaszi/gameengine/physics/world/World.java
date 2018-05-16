package hu.csaszi.gameengine.physics.world;

import hu.csaszi.gameengine.physics.Point;
import hu.csaszi.gameengine.physics.collission.AABB;
import hu.csaszi.gameengine.render.core.Window;
import hu.csaszi.gameengine.render.core.gl.GLFWWindow;
import hu.csaszi.gameengine.render.core.gl.models.Model;
import hu.csaszi.gameengine.render.core.gl.renderer.Camera;
import hu.csaszi.gameengine.render.core.gl.shaders.Shader;
import hu.csaszi.gameengine.render.graphics.assets.Assets;
import hu.csaszi.gameengine.util.MathUtil;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class World {

    private int viewX = 24;
    private int viewY = 24;

    private byte[] tiles;
    private AABB[] boundingBoxes;
    private int width;
    private int height;
    private int scale;
    private String tag;

    private Matrix4f world;
    private float xOff;
    private float yOff;

    public World(String world) {
        this.tag = world;
        try {

            File file = new File("src/main/resources/levels/" + world + "_tiles.png");

            BufferedImage tileSheet = ImageIO.read(file);
            width = tileSheet.getWidth();
            height = tileSheet.getHeight();

            scale = 16;

            this.world = new Matrix4f().setTranslation(new Vector3f());
            this.world.scale(scale);

            int[] colorTileSheet = tileSheet.getRGB(0, 0, width, height, null, 0, width);

            tiles = new byte[width * height];
            boundingBoxes = new AABB[width * height];

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int red = (colorTileSheet[x + y * width] >> 16) & 0xFF;

                    Tile tile;
                    try {
                        tile = Tile.tiles[red];
                    } catch (ArrayIndexOutOfBoundsException e) {
                        tile = null;
                    }
                    if (tile != null) {
                        setTile(tile, x, y);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public World() {
        width = 64;
        height = 64;
        scale = 32;
        tiles = new byte[width * height];
        boundingBoxes = new AABB[width * height];
        world = new Matrix4f().translate(new Vector3f(0));
        world.scale(scale);

    }

    public void calculateView(GLFWWindow window) {
        viewX = (window.getWidth() / (scale * 2)) + 4;
        viewY = (window.getHeight() / (scale * 2)) + 4;
    }

    public Matrix4f getWorldMatrix() {
        return world;
    }



    public void render(TileRenderer render, Shader shader, Camera camera) {

        Model tileModel = Assets.getModel("iso");
        xOff = (width * 1f / 2 - tileModel.getRatio() / 2)/ 2;
        yOff = (height *  tileModel.getRatio() / 2)/ 2;

//       int posX = ((int)camera.getPosition().x + (camera.getWindow().getWidth()/2)) / (scale * 2);
//       int posY = ((int)camera.getPosition().y - (camera.getWindow().getHeight()/2)) / (scale * 2);
        int posX = (int) camera.getPosition().x / (scale * 2) + scale/2;
        int posY = (int) camera.getPosition().y / (scale * 2) - scale/2;

//        for (int i = 0; i < viewX; i++) {
//            for (int j = 0; j < viewY; j++) {
//                Tile tile = getTile(i - posX - (viewX / 2) + 1, j + posY - (viewY / 2));
//                if (tile != null) {
//                    render.renderTile(tile.getId(), i - posX - (viewX / 2) + 1, -j - posY + (viewY / 2), shader, world, camera);
//                }
//            }
//        }
        for (int y = 0; y < viewY; y++) {
            for (int x = 0; x < viewX; x++) {
                Point coords = MathUtil.convertCoordsToIsometric(x, y, tileModel.getRatio());
//                int tileX = x - posX - (viewX / 2) + 1;
//                int tileY = y + posY - (viewY / 2);
//                Tile tile = getTile(tileX, tileY);
                Tile tile = getTile(x, y);
                if (tile != null) {
                    //System.out.println("coords.x: " + coords.getX() + " coords.y: " + coords.getY());
                    float xCoord = coords.getX() + xOff /*- posX*/ - (viewX / 2) + 1 + 24;
                    float yCoord = -coords.getY() - yOff /*- posY*/ + (viewY /2) - 8;
                    if(x < 6 && y < 2) {

                        System.out.print(x + "-" + y + ":  xCoord: " + xCoord + " yCoord: " + yCoord + " xOff: " + xOff + " yOff: " + yOff + " posX: " + posX + " posY: " + posY + " ");
                        System.out.println("coords.x: " + coords.getX() + " coords.y: " + coords.getY());

                    }
                    render.renderTile(tile.getId(), xCoord, yCoord, shader, world, camera);
                }
            }
        }
        System.out.println("\n");
    }

    public void correctCamera(Camera camera) {
        Vector3f pos = camera.getPosition();
        Window window = camera.getWindow();

        int w = -width * scale * 2;
        int h = height * scale * 2;

        if (pos.x > -(window.getWidth() / 2) + scale) {
            pos.x = -(window.getWidth() / 2) + scale;
        }
        if (pos.x < w + (window.getWidth() / 2) + scale) {
            pos.x = w + (window.getWidth() / 2) + scale;
        }

        if (pos.y < (window.getHeight() / 2) - scale) {
            pos.y = (window.getHeight() / 2) - scale;
        }
        if (pos.y > h - (window.getHeight() / 2) - scale) {
            pos.y = h - (window.getHeight() / 2) - scale;
        }
    }

    public void setTile(Tile tile, int x, int y) {
        tiles[x + y * width] = tile.getId();
        if (tile.isSolid()) {
            boundingBoxes[x + y * width] = new AABB(new Vector2f(x * 2, -y * 2), new Vector2f(1, 1));
        } else {
            boundingBoxes[x + y * width] = null;
        }
    }

    public Tile getTile(int x, int y) {

        try {
            return Tile.tiles[tiles[x + y * width]];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public AABB getTileBoundingBox(int x, int y) {

        try {
            return boundingBoxes[x + y * width];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public String getTag() {
        return tag;
    }

    public int getScale() {
        return scale;
    }
}
