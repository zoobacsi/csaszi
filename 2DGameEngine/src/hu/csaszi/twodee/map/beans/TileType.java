package hu.csaszi.twodee.map.beans;

import hu.csaszi.twodee.runtime.Application;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Image;

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
/*
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
*/
	private TileType(Builder b){
	
		this.images = b.images;
		this.imagesNum = b.imagesNum;
		this.autoTile = b.autotile;
		this.name = b.name;
		this.actualImgNum = b.actualImgNum;
		this.tileOffset = b.tileOffset;
		this.collide = b.collide;
		this.randomImg = b.randomImg;
		this.movementCost = b.movementCost;
		this.numOfLayers = b.numOfLayers;
		this.layerNum = b.layerNum;
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
	
	
	public static class Builder{
		
		private List<Image[]> images = new ArrayList<>();
		private boolean autotile = false;
		private String name;
		private int imagesNum = 0;
		private int actualImgNum = 0;
		private int tileOffset = 0;
		private boolean collide = false;
		private boolean randomImg = false;
		private float movementCost = 1.0f;
		private int numOfLayers = 0;
		private int layerNum = 0;
		
		public Builder(String name){
			this.name = name;
		}
		
		public Builder addImages(Image[] imageSet){
		
			this.images.add(imageSet);
			if(imagesNum == 0){
				imagesNum = imageSet.length;
			}
			return this;
		}
		
		public Builder setImagesNum(int imagesNum){
			this.imagesNum = imagesNum;
			return this;
		}
		
		public Builder setAutotile(boolean autotile){
			this.autotile = autotile;
			return this;
		}
		
		public Builder setActualImgNum(int actualImgNum){
			this.actualImgNum = actualImgNum;
			return this;
		}
		
		public Builder setTileOffset(int tileOffset){
			this.tileOffset = tileOffset;
			return this;
		}
		
		public Builder setCollide(boolean collide){
			this.collide = collide;
			return this;
		}
		
		public Builder setRandomImg(boolean randomImg){
			this.randomImg = randomImg;
			return this;
		}
		
		public Builder setMovementCost(float movementCost){
			this.movementCost = movementCost;
			return this;
		}
		
		public Builder setNumOfLayers(int numOfLayers){
			this.numOfLayers = numOfLayers;
			return this;
		}
		
		public Builder setLayerNum(int layerNum){
			this.layerNum = layerNum;
			return this;
		}
		
		
		public TileType build(){
						
			if(name == null || name.isEmpty()){
				throw new IllegalArgumentException("Name must be set.");
			}
			if(images.isEmpty()){
				throw new IllegalArgumentException("There are no images added.");
			}
			if(imagesNum == 0){
				throw new IllegalArgumentException("ImagesNum must be non-zero positive integer.");
			}
			return new TileType(this);
		}
		
	}
}
