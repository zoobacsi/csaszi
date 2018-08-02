package hu.csaszi.gameengine.physics.world;

import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.physics.Direction;
import hu.csaszi.gameengine.physics.collission.AABB;
import hu.csaszi.gameengine.physics.objects.*;
import hu.csaszi.gameengine.render.core.Window;
import hu.csaszi.gameengine.render.core.gl.Animation;
import hu.csaszi.gameengine.render.core.gl.GLFWWindow;
import hu.csaszi.gameengine.render.core.gl.renderer.Camera;
import hu.csaszi.gameengine.render.core.gl.shaders.Shader;
import hu.csaszi.gameengine.util.IOUtil;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.Buffer;

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

    public World(String world) {
        this.tag = world;
        try {
            File file = IOUtil.getFile(world + "_tiles.png", "levels/");
            BufferedImage tileSheet = ImageIO.read(file);

            width = tileSheet.getWidth();
            height = tileSheet.getHeight();
            scale = 16;

            this.world = new Matrix4f().setTranslation(new Vector3f());

//            float matIso[] = {
//                    0.7071f,    0.4082f,    0.0f,   0.0f,
//                    0.0f,       0.8166f,    0.0f,   0.0f,
//                    0.7071f,    -0.4082f,   0.0f,   0.0f,
//                    0.0f,       0.0f,       0.0f,   1.0f
//            };
//
//            this.world.set(matIso);
//
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

//       int posX = ((int)camera.getPosition().x + (camera.getWindow().getWidth()/2)) / (scale * 2);
//       int posY = ((int)camera.getPosition().y - (camera.getWindow().getHeight()/2)) / (scale * 2);
        int posX = (int) camera.getPosition().x / (scale * 2);
        int posY = (int) camera.getPosition().y / (scale * 2);

        for (int i = 0; i < viewX; i++) {
            for (int j = 0; j < viewY; j++) {
                Tile tile = getTile(i - posX - (viewX / 2) + 1, j + posY - (viewY / 2));
                if (tile != null) {
                    int direction = getNeighbourHood(tile, i, j, 0);
                    render.renderTile(tile.getId(), i - posX - (viewX / 2) + 1, -j - posY + (viewY / 2), shader, world, camera);
                }
            }
        }

    }

    protected int getNeighbourHood(Tile tile, int x, int y, int depth) {
        int result = 0;

        TileObject tile = getTile(x, y - 1, depth);
        if ((tile != null && tile.getType().equals(type)) || y == 0) {
            result += 1;
        }

        tile = getTile(x + 1, y, depth);
        if ((tile != null && tile.getType().equals(type)) || x == maxX - 1) {
            result += 2;
        }

        tile = getTile(x, y + 1, depth);
        if ((tile != null && tile.getType().equals(type)) || y == maxY - 1) {
            result += 4;
        }

        tile = getTile(x - 1, y, depth);
        if ((tile != null && tile.getType().equals(type)) || x == 0) {
            result += 8;
        }

        return result;
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
