package hu.csaszi.twodee.map.isometric;

import hu.csaszi.twodee.map.AbstractTileObject;
import hu.csaszi.twodee.map.beans.TileType;
import hu.csaszi.twodee.map.interfaces.TileObject;
import hu.csaszi.twodee.util.PropsValues;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class IsoTileObject extends AbstractTileObject{

	public IsoTileObject(int x, int y, TileType type, int xIn, int yIn, int depth) {
		this.xPos = x;
		this.yPos = y;
		this.type = type;
		this.xIndex = xIn;
		this.yIndex = yIn;
		this.depth = depth;
		// this.yOff = gfx.getHeight() - 54;
		this.yOff = 0;
		this.tileNum = 0;
	}

	public int getTileWidth() {
		return (int) Math.floor(PropsValues.ISOMETRIC_TILE_WIDTH * PropsValues.GLOBAL_SCALE);
	}

	public int getTileHeight() {
		return (int) Math.floor(PropsValues.ISOMETRIC_TILE_HEIGHT * PropsValues.GLOBAL_SCALE);
	}

	public IsoTileObject(int x, int y, TileType type, int tileNum, int xIn, int yIn, int depth, int yOff) {
		this(x, y, type, xIn, yIn, depth);
		this.yOff = -yOff;
		this.tileNum = tileNum;
	}

	public void render(Graphics g, float posX, float posY) {
		Image img = null;
		
		if(type.getNumOfLayers() > 1){
			if (tileNum > 0) {
				img = type.getImage(tileNum, type.getLayerNum());
				if (img == null) {
					img = type.getImage();
				}
			} else {
				img = type.getImage();
			}
		} else{
			if (tileNum > 0) {
				img = type.getImage(tileNum);
				if (img == null) {
					img = type.getImage();
				}
			} else {
				img = type.getImage();
			}
		}
		//System.out.println("xpos: " + xPos + " ypos: " + yPos);
		img.draw((float) (xPos + Math.floor(posX)), (float) (yPos + Math.floor(posY) + yOff), PropsValues.GLOBAL_SCALE, Color.white);
		// g.drawImage(img, xPos + Math.round(posX), yPos + Math.round(posY) +
		// yOff, Color.white);
	}
}
