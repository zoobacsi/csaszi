package hu.csaszi.twodee.map.beans;

import hu.csaszi.twodee.runtime.Application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class TileType {
	
	private List<Image[]> images;
	private int imagesNum;
	private boolean autoTile;
	private String name;
	private int actualImgNum = 0;
	private int tileOffset = 0;
	private boolean collide;
	private boolean randomImg = false;
	private float movementCost;
	private int numOfLayers = 1;
	private int layerNum = 0;

	public int getLayerNum() {
		if(numOfLayers > 1){
			layerNum = (int)((long)(System.currentTimeMillis() / 1000)) % numOfLayers ;
			
		}
		
		
		return layerNum;
	}

	public TileType(String name, List<Image[]> images, boolean collide, int imagesNum, boolean autoTile, int offset, boolean randomImg, float moveCost) throws IOException, SlickException{
		this.name = name;
		this.autoTile = autoTile;
		this.images = new ArrayList<>();
		this.imagesNum = imagesNum;
		this.collide = collide;
		this.actualImgNum = 0;
		this.tileOffset = offset;
		this.randomImg = randomImg;
		this.movementCost = moveCost;
		
		this.numOfLayers = images.size();
		this.images.addAll(images);
	}
	
	public TileType(String name, Image[] images, boolean collide, int imagesNum, boolean autoTile, int offset, boolean randomImg, float moveCost) throws IOException, SlickException{
		this.name = name;
		this.autoTile = autoTile;
		this.images = new ArrayList<>();
		this.imagesNum = imagesNum;
		this.collide = collide;
		this.actualImgNum = 0;
		this.tileOffset = offset;
		this.randomImg = randomImg;
		this.movementCost = moveCost;
		
		this.images.add(images);
	}
	
	public TileType(String name, Image[] images, boolean collide, int imagesNum, boolean autoTile, int offset, float moveCost) throws IOException, SlickException{
		this.name = name;
		this.autoTile = autoTile;
		this.images = new ArrayList<>();
		this.imagesNum = imagesNum;
		this.collide = collide;
		this.actualImgNum = 0;
		this.tileOffset = offset;
		this.randomImg = false;
		this.movementCost = moveCost;
		
		this.images.add(images);
	}
	
	public TileType(String name, Image image, boolean collide, boolean autoTile, int offset, float moveCost) throws IOException, SlickException{
		this.name = name;
		this.autoTile = autoTile;
		this.imagesNum = 1;
		this.images = new ArrayList<>();
		this.collide = collide;
		this.actualImgNum = 0;
		this.tileOffset = offset;
		this.randomImg = false;
		this.movementCost = moveCost;
		
		this.images.add(new Image[]{image});
	}

	public float getMovementCost(){
		return movementCost;
	}
	
	
	public Image getImage(){
		Image defaultImg = images.get(0)[0];
	
		return defaultImg;
	}
	
	public int getRandomImageNum(){
		int random = 0;
		if(randomImg){
			random = Application.random.nextInt(this.imagesNum);
		}
		return random;
	}
	
	public Image getImage(int num){
		if(num < imagesNum){
			return images.get(0)[num];
		} else{
			return null;
		}
	}
	
	public Image getImage(int num, int layer){
		if(num < imagesNum && layer < images.size()){
			return images.get(layer)[num];
		} else{
			return null;
		}
	}
	
	public int getNumOfLayers() {
		return numOfLayers;
	}

	public int getImagesNum(){
		return imagesNum;
	}
	
	public boolean isAutoTile(){
		return autoTile;
	}
	
	public void increaseActualImgNum(){
		this.actualImgNum++;
		if(imagesNum <= actualImgNum){
			this.actualImgNum = 0;
		} 
	}
	
	public String getName(){
		return name;
	}
	
	public int getActualImgNum(){
		return actualImgNum;
	}
	
	public int getTileOffset(){
		return tileOffset;
	}

	public boolean isCollide(){
		return collide;
	}
	
	public boolean isRandom(){
		return this.randomImg;
	}
}
