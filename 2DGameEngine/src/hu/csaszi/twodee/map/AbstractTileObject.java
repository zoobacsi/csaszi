package hu.csaszi.twodee.map;

import org.newdawn.slick.Graphics;

import hu.csaszi.twodee.map.beans.TileType;
import hu.csaszi.twodee.map.interfaces.TileObject;

public abstract class AbstractTileObject implements TileObject {

	protected int xPos;
	protected int yPos;
	protected TileType type;
	protected int xIndex;
	protected int yIndex;
	protected int depth;
	protected int yOff;
	protected int tileNum = 0;

	public abstract int getTileWidth();

	public abstract int getTileHeight();

	public int getDepth() {
		return depth;
	}

	@Override
	public abstract void render(Graphics g, float posX, float posY);

	public boolean collideTile(float orthoX, float orthoY) {
		boolean result = false;
		if (!this.getType().isCollide()) {
			int colXs = this.getXIndex() * 100;
			int colYs = this.getYIndex() * 100;
			int colXe = this.getXIndex() * 100 + 100;
			int colYe = this.getYIndex() * 100 + 100;
			if (!(orthoX > colXs && orthoX < colXe && orthoY > colYs && orthoY < colYe)) {
				result = true;
			}
		}
		return result;
	}

	public int getxPos() {

		return xPos;
	}

	public int getyPos() {

		return yPos;
	}

	public int getCenterPosX() {
		return xPos + (int) Math.floor(getTileWidth() / 2);
	}

	public int getCenterPosY() {
		return yPos + (int) Math.floor(getTileHeight() / 2);
	}

	public int getXIndex() {
		return xIndex;
	}

	public int getYIndex() {
		return yIndex;
	}

	public TileType getType() {
		return type;
	}

	public int getTileNum() {
		return tileNum;
	}

	public void setTileNum(int tileNum) {
		this.tileNum = tileNum;
	}

	public void setTileType(TileType type) {
		this.type = type;
	}
}
