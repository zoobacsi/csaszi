package hu.csaszi.twodee.map.beans;

public class MapTile {

	private int x;
	private int y;
	private int depth;
	private int tile;
	private int tileNum;

	public MapTile(int x, int y, int depth, int tile, int tileNum) {
		this.x = x;
		this.y = y;
		this.depth = depth;
		this.tile = tile;
		this.tileNum = tileNum;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getTile() {
		return tile;
	}

	public int getDepth() {
		return depth;
	}

	public int getTileNum() {
		return tileNum;
	}
}
