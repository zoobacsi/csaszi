package hu.csaszi.twodee.map.interfaces;

import hu.csaszi.twodee.map.beans.TileType;

import org.newdawn.slick.Graphics;

public interface TileObject {

	public int getTileWidth();
	public int getTileHeight();
	public int getDepth();
	public void render(Graphics g, float posX, float posY);
	public boolean collideTile(float orthoX, float orthoY);
	public int getxPos();
	public int getyPos();
	public int getCenterPosX();
	public int getCenterPosY();
	public int getXIndex();
	public int getYIndex();
	public TileType getType();
	public int getTileNum();
	public void setTileNum(int tileNum);
	public void setTileType(TileType type);
	
}
