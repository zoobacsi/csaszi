package hu.csaszi.twodee.gamestates;

import hu.csaszi.twodee.camera.Camera;
import hu.csaszi.twodee.commands.ScoutCommand;
import hu.csaszi.twodee.entity.Character;
import hu.csaszi.twodee.entity.NPC;
import hu.csaszi.twodee.entity.Player;
import hu.csaszi.twodee.entity.interfaces.Drawable;
import hu.csaszi.twodee.graphics.CharGraphicsUtil;
import hu.csaszi.twodee.graphics.CharacterSet;
import hu.csaszi.twodee.gui.twl.BasicTWLGameState;
import hu.csaszi.twodee.gui.twl.RootPane;
import hu.csaszi.twodee.input.InputUtil;
import hu.csaszi.twodee.map.MapType;
import hu.csaszi.twodee.map.TileUtil;
import hu.csaszi.twodee.map.beans.TileType;
import hu.csaszi.twodee.map.interfaces.TileObject;
import hu.csaszi.twodee.map.interfaces.TiledMap;
import hu.csaszi.twodee.runtime.Application;
import hu.csaszi.twodee.runtime.MapState;
import hu.csaszi.twodee.util.Direction;
import hu.csaszi.twodee.util.FileUtil;
import hu.csaszi.twodee.util.PropsValues;

import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.Path.Step;

import de.matthiasmann.twl.Alignment;
import de.matthiasmann.twl.Border;
import de.matthiasmann.twl.BoxLayout;
import de.matthiasmann.twl.ColumnLayout;
import de.matthiasmann.twl.ColumnLayout.Columns;
import de.matthiasmann.twl.DialogLayout.Group;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.ProgressBar;
import de.matthiasmann.twl.renderer.DynamicImage.Format;
import de.matthiasmann.twl.theme.AnimatedImage;

public class DefaultPlay extends BasicTWLGameState implements MapState {

	protected boolean debug = true;
	protected Character player;
	// XXX
//	protected TileType wall, water, road, tree, grass, building, field, dirt;

	protected static float globalSpeed = PropsValues.GLOBAL_SPEED;
	protected Image selectBox;
	protected int globalDepth = 0;
	protected TileType selectedType;
	protected boolean quit = false;
	protected int[] duration = { 100, 100, 100, 100, 100, 100, 100, 100 };
	protected static int maxX = 100;
	protected static int maxY = 100;

	protected int positionX, positionY;

	protected TileObject curTile;

	protected Camera camera;
	protected TiledMap tiledMap;

	protected float mouseX, mouseY;
	protected int state;
	protected HashMap<Integer, TileType> tileMap = new HashMap<Integer, TileType>();
	protected int tileBarXOff = (Application.SCREEN_WIDTH - 640) / 2;
	protected boolean tileBarMouse;
	protected AStarPathFinder pathFinder;
	public AStarPathFinder getPathFinder() {
		return pathFinder;
	}

	protected boolean paintPath;
	protected ScheduledThreadPoolExecutor threadPool;
	protected List<Character> characterList = new ArrayList<Character>();
	protected int mapIndex;
	protected Path drawPath;
	protected boolean isometric = true;
	protected boolean initialized = false;
	
	private de.matthiasmann.twl.renderer.DynamicImage image;
	
	private de.matthiasmann.twl.Label fpsLabel, normalX, normalY, playerX, playerY, tileX, tileY, selectedTile;
	private de.matthiasmann.twl.Label label, normalXLabel, normalYLabel, playerXLabel, playerYLabel, tileXLabel, tileYLabel, selectedTileLabel, tile;
	private BoxLayout btnBox;
	private BoxLayout valueBox;
	private BoxLayout horizontal;
	private ProgressBar hpBar;
	
	public List<Character> getCharactersByTile(int x, int y){
		List<Character> list = new ArrayList<>();
		for(Character character : characterList){
			if(!(character instanceof Player)){
				TileObject tileObject = tiledMap.getTilesByOrtho(character.getCharOrtoPosX(), character.getCharOrtoPosY()).get(0);
				//Log.info("x: " + tileObject.getXIndex() + " y: " + tileObject.getYIndex() + " tx: " + x + " ty: " + y);
				if(tileObject.getXIndex() == x && tileObject.getYIndex() == y){
					list.add(character);
				}
			}
		}
		return list;
	}
	
	@Override
	protected RootPane createRootPane() {
		
		RootPane rp = super.createRootPane();
		rp.setTheme("playState");
	
		hpBar = new ProgressBar();
		hpBar.setTheme("progressbar");
		
		valueBox = new BoxLayout(BoxLayout.Direction.VERTICAL);
		btnBox = new BoxLayout(BoxLayout.Direction.VERTICAL);
		horizontal = new BoxLayout(BoxLayout.Direction.HORIZONTAL);

		horizontal.setTheme("boxLayout");

		label = new Label();

		fpsLabel = new Label();
		fpsLabel.setText("FPS: ");

		normalX = new Label();
		normalX.setText("NormalX: ");
		
		normalY = new Label();
		normalY.setText("NormalY: ");
		
		playerX = new Label();
		playerX.setText("PlayerX: ");
		
		playerY = new Label();
		playerY.setText("PlayerY: ");
		
		tileX = new Label();
		tileX.setText("TileX: ");
		
		tileY = new Label();
		tileY.setText("TileY: ");

		selectedTile = new Label();
		selectedTile.setText("SelectedTile: ");
		
		btnBox.add(fpsLabel);
		btnBox.add(normalX);
		btnBox.add(normalY);
		btnBox.add(playerX);
		btnBox.add(playerY);
		btnBox.add(tileX);
		btnBox.add(tileY);
		btnBox.add(selectedTile);

		normalXLabel = new Label();
		normalYLabel = new Label();
		playerXLabel = new Label();
		playerYLabel = new Label();
		tileXLabel = new Label();
		tileYLabel = new Label();
		selectedTileLabel = new Label();
		tile = new Label();
		
		valueBox.add(label);
		valueBox.add(normalXLabel);
		valueBox.add(normalYLabel);
		valueBox.add(playerXLabel);
		valueBox.add(playerYLabel);
		valueBox.add(tileXLabel);
		valueBox.add(tileYLabel);
		valueBox.add(selectedTileLabel);
		valueBox.add(tile);
		
		
		valueBox.add(hpBar);

		
		horizontal.add(btnBox);
		horizontal.add(valueBox);
		
		rp.add(horizontal);
		
		return rp;
	}

	@Override
	protected void layoutRootPane() {
		//columnLayout.adjustSize();
		
		btnBox.setBorderSize(0, 30, 0, 0);
		
		horizontal.setSize(256, 512);
		horizontal.setPosition(Application.SCREEN_WIDTH * 3/4 , 0);
		
		fpsLabel.setBorderSize(40, 6, 6, 6);
		normalX.setBorderSize(6);
		normalY.setBorderSize(6);
		playerX.setBorderSize(6);
		playerY.setBorderSize(6);
		tileX.setBorderSize(6);
		tileY.setBorderSize(6);
		selectedTile.setBorderSize(6);
		
		label.setBorderSize(40, 6 , 6, 6);
		normalXLabel.setBorderSize(6);
		normalYLabel.setBorderSize(6);
		normalYLabel.setBorderSize(6);
		playerXLabel.setBorderSize(6);
		playerYLabel.setBorderSize(6);
		tileXLabel.setBorderSize(6);
		tileYLabel.setBorderSize(6);
		selectedTileLabel.setBorderSize(6);
		tile.setBorderSize(6);
		
		hpBar.setBorderSize(6);
	}
	
	public DefaultPlay(int state) {

		System.out.println("default play");
		this.mapIndex = 0;
	}

	public void mouseButton1() {

		int mousx = Mouse.getX();
		if (tileBarMouse) {
			if (mousx > tileBarXOff && mousx < tileBarXOff + 64) {
				selectedType = tileMap.get(Integer.valueOf(1));
			} else if (mousx > tileBarXOff + 64 && mousx < tileBarXOff + 128) {
				selectedType = tileMap.get(Integer.valueOf(2));
			} else if (mousx > tileBarXOff + 128 && mousx < tileBarXOff + 196) {
				selectedType = tileMap.get(Integer.valueOf(3));
			} else if (mousx > tileBarXOff + 196 && mousx < tileBarXOff + 256) {
				selectedType = tileMap.get(Integer.valueOf(4));
			} else if (mousx > tileBarXOff + 256 && mousx < tileBarXOff + 320) {
				selectedType = tileMap.get(Integer.valueOf(5));
			} else if (mousx > tileBarXOff + 320 && mousx < tileBarXOff + 386) {
				selectedType = tileMap.get(Integer.valueOf(6));
			} else if (mousx > tileBarXOff + 386 && mousx < tileBarXOff + 450) {
				selectedType = tileMap.get(Integer.valueOf(7));
			} else if (mousx > tileBarXOff + 450 && mousx < tileBarXOff + 504) {
				selectedType = tileMap.get(Integer.valueOf(8));
			} else if (mousx > tileBarXOff + 576 && mousx < tileBarXOff + 640 && globalDepth == 0) {
				selectedType = tileMap.get(Integer.valueOf(0));
			} else {
				selectedType = null;
			}

			//setLabelImage();
			
		} else if (curTile != null) {
			if (selectedType != null) {
				int imageNum = selectedType.getActualImgNum();
				if (selectedType.isRandom()) {
					imageNum = selectedType.getRandomImageNum();
				}
				tiledMap.addDecorationTile(selectedType, imageNum, positionX, positionY, globalDepth, selectedType.getTileOffset());
			} else {
				tiledMap.deleteDecorationTile(positionX, positionY, globalDepth);
			}
		}
	}

	public void mouseButton2() {

		if (selectedType != null) {
			selectedType.increaseActualImgNum();
		}
	}

	private void increaseDepthLevel(int i) {

		globalDepth += i;
		if (globalDepth > 3) {
			globalDepth = 3;
		} else if (globalDepth < 0) {
			globalDepth = 0;
		}
		if (globalDepth != 0 && selectedType == tileMap.get(0)) {
			selectedType = null;
		}

	}

	@Override
	public float getGlobalSpeed() {
		return globalSpeed;
	}

	@Override
	public TiledMap getTiledMap() {
		return tiledMap;
	}

	@Override
	public int getID() {
		return 1;
	}

	public void paintPath(Graphics g, int sx, int sy, int tx, int ty) {

		Step step = null;
		if (drawPath != null) {
			for (int index = 0; index < drawPath.getLength(); index++) {
				step = drawPath.getStep(index);
				TileObject tile = tiledMap.getFloorTile(step.getX(), step.getY());
				g.drawString(String.valueOf(index), tile.getCenterPosX(), tile.getCenterPosY());
			}
		}
	}
	
	public void reset() throws SlickException{
		initialized = false;
		init(Application.getGameContainer(), Application.getInstance());
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		
		if(!initialized){
			
			// FIXME Audio --- should come from map file
			// AudioManager.playDefaultMusic();
	
			try {
				tiledMap = FileUtil.parseFile(new File("res/maps/isomap.txt"));
				// tiledMap = FileUtil.parseFile(new
				// File("res/maps/orthomap.txt"));
			} catch (FileNotFoundException e) {
				throw new SlickException(e.getMessage(), e);
			}
	
			isometric = tiledMap.getMapTypeId() == MapType.ISOMETRIC.getId();
	
			maxX = tiledMap.getMaxX();
			maxY = tiledMap.getMaxY();
			pathFinder = new AStarPathFinder(tiledMap, maxX * maxY, false);
			camera = new Camera(container, tiledMap);
	
			// Starting position
			TileObject startingTile = tiledMap.getFloorTile(2, 3);
	
	//		CharGraphics swordMan = null;
	//		try {
	//			swordMan = CharGraphicsUtil.getGraphics("SwordsMan");
	//		} catch (Exception e) {
	//			new SlickException(e.getMessage());
	//			e.printStackTrace();
	//		}
	//		
	//		CharGraphics walker = null;
	//		try {
	//			walker = CharGraphicsUtil.getGraphics("Walker");
	//		} catch (Exception e) {
	//			new SlickException(e.getMessage());
	//			e.printStackTrace();
	//		}
			
			CharacterSet characterSet = CharGraphicsUtil.getDefaultCharSet("SwordMan", "res/swordman.png");
			characterList.clear();
			
			NPC enemy = new NPC(1, this, characterSet, 100, new Circle(0, 0, 1), "Enemy1", 3, 1f, maxX * 100 + (startingTile.getXIndex() + 3) * 100 + 50, (startingTile.getYIndex() + 5) * 100 + 50);
			characterList.add(enemy.setCommand(new ScoutCommand(enemy)));
			
			enemy = new NPC(2, this, characterSet, 100, new Circle(0, 0, 1), "Enemy2", 3, 1f, maxX * 100 + (startingTile.getXIndex() + 2) * 100 + 50, (startingTile.getYIndex() + 1) * 100 + 50);
			characterList.add(enemy.setCommand(new ScoutCommand(enemy)));
			
			enemy = new NPC(3, this, characterSet, 100, new Circle(0, 0, 1), "Enemy3", 3, 1f, maxX * 100 + (startingTile.getXIndex() + 1) * 100 + 50, (startingTile.getYIndex() + 3) * 100 + 50);
			characterList.add(enemy.setCommand(new ScoutCommand(enemy)));
			
			player = new Player(0, this, characterSet, 100, new Circle(0, 0, 1), "player", 5, 1f, (float) (maxX * 100 + startingTile.getXIndex() * 100 + 50), (float) (startingTile.getYIndex() * 100 + 50));
	
			characterList.add(player);
	
			selectBox = new Image("res/selectbox.png");
	
			tileMap.put(Integer.valueOf(0), TileUtil.getTile(1, isometric));
			tileMap.put(Integer.valueOf(1), TileUtil.getTile(3, isometric));
			tileMap.put(Integer.valueOf(2), TileUtil.getTile(2, isometric));
			tileMap.put(Integer.valueOf(3), TileUtil.getTile(4, isometric));
			tileMap.put(Integer.valueOf(4), TileUtil.getTile(5, isometric));
			tileMap.put(Integer.valueOf(5), TileUtil.getTile(6, isometric));
			tileMap.put(Integer.valueOf(6), TileUtil.getTile(7, isometric));
			tileMap.put(Integer.valueOf(6), TileUtil.getTile(8, isometric));
	
			camera.centerOn(player.getX(), player.getY());
	
			Font awtFont = new Font("Courier New", java.awt.Font.PLAIN, 26);
			container.getGraphics().setFont(new TrueTypeFont(awtFont, true));
			container.getGraphics().setAntiAlias(true);
			container.getGraphics().setLineWidth(0.5f);
			
			initialized = true;
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		camera.translateGraphics();
		camera.centerOn(player.getX(), player.getY());
		camera.drawMap(player);

		tiledMap.renderBeyondPos(container, (int) Math.round(Math.floor(characterList.get(0).getCenterY())), player);

		int topY = 0;

		for (Drawable drawable : characterList) {

			tiledMap.renderBetweenPos(container, topY, Math.round(drawable.getCenterY()), player);
			if (drawable instanceof Character) {
				if (((Character) drawable).getName().equalsIgnoreCase("player")) {
					drawable.draw();
				} else {
					drawable.draw(Color.red);
				}
			}
			topY = Math.round(drawable.getCenterY());
		}

		tiledMap.renderAbovePos(container, (int) topY, player);

		if (debug) {
			g.drawRect(player.getCenterX(), player.getCenterY(), 2, 2);
		}

		if (curTile != null && !tileBarMouse) {
			selectBox.setAlpha(0.5f);
			selectBox.draw(curTile.getxPos(), curTile.getyPos() - tiledMap.getTileHeight(), PropsValues.GLOBAL_SCALE);
		}
		if (selectedType != null && curTile != null && !tileBarMouse) {
			Image cursorTile = selectedType.getImage(selectedType.getActualImgNum());
			cursorTile.setAlpha(0.5f);
			cursorTile.draw(curTile.getxPos(), curTile.getyPos() - selectedType.getTileOffset(), PropsValues.GLOBAL_SCALE);
			cursorTile.setAlpha(1.0f);
		}

		if (paintPath) {
			paintPath(g, 0, 0, 20, 20);

		}

		camera.untranslateGraphics();

//		g.drawString("Normal X: " + (player.getCharOrtoPosX() - (maxX * 100)) + "\nNormal Y: " + player.getCharOrtoPosY(), Application.SCREEN_WIDTH * 3 / 4, 20);
//		g.drawString("Player X: " + player.getCenterX() + "\nPlayer Y: " + player.getCenterY(), Application.SCREEN_WIDTH * 3 / 4, 108);
//		g.drawString("Tile X: " + positionX + "\nTile Y: " + positionY, Application.SCREEN_WIDTH * 3 / 4, 64);

		if (selectedType != null) {
//			g.drawString("SelectedTile: " + selectedType.getImage(selectedType.getActualImgNum()).getName() + " [" + selectedType.getActualImgNum() + "]", Application.SCREEN_WIDTH * 3 / 4, 172);
			g.setColor(Color.darkGray);
			g.fillRoundRect(Application.SCREEN_WIDTH * 3 / 4, 200, selectedType.getImage(selectedType.getActualImgNum()).getWidth(), selectedType.getImage(selectedType.getActualImgNum()).getHeight(),
					3);
			g.setColor(Color.white);
			selectedType.getImage(selectedType.getActualImgNum()).draw(Application.SCREEN_WIDTH * 3 / 4, 200);
			g.drawRoundRect(Application.SCREEN_WIDTH * 3 / 4, 200, selectedType.getImage(selectedType.getActualImgNum()).getWidth(), selectedType.getImage(selectedType.getActualImgNum()).getHeight(),
					3);
		} else {
//			g.drawString("SelectedTile: none", Application.SCREEN_WIDTH * 3 / 4, 172);
		}

		g.drawRect(tileBarXOff - 21, Application.SCREEN_HEIGHT - 60, 20, 53);
		g.drawString(String.valueOf(globalDepth), tileBarXOff - 19, Application.SCREEN_HEIGHT - 50);
		TileType type;
		for (int i = 0; i < 9; i++) {
			g.drawString(String.valueOf(i + 1), tileBarXOff + i * 64, Application.SCREEN_HEIGHT - 80);
			g.setColor(Color.darkGray);
			g.fillRoundRect(tileBarXOff + i * 64, Application.SCREEN_HEIGHT - 60, 64, 54, 2);
			g.setColor(Color.white);
			g.drawRoundRect(tileBarXOff + i * 64, Application.SCREEN_HEIGHT - 60, 64, 54, 2);
			type = tileMap.get(Integer.valueOf(i + 1));
			if (type != null) {
				type.getImage().draw(tileBarXOff + i * 64, Application.SCREEN_HEIGHT - 60, 0.5f);
			}

		}

		if (globalDepth == 0) {
			g.drawString(String.valueOf(0), tileBarXOff + 9 * 64, Application.SCREEN_HEIGHT - 80);
			g.setColor(Color.darkGray);
			g.fillRoundRect(tileBarXOff + 9 * 64, Application.SCREEN_HEIGHT - 60, 64, 54, 2);
			g.setColor(Color.white);
			g.drawRoundRect(tileBarXOff + 9 * 64, Application.SCREEN_HEIGHT - 60, 64, 54, 2);
			type = tileMap.get(Integer.valueOf(0));
			if (type != null) {
				type.getImage().draw(tileBarXOff + 9 * 64, Application.SCREEN_HEIGHT - 60, 0.5f);
			}
		}

		if (quit == true) {
			g.drawString("Resume (R)", 250, 100);
			g.drawString("Main Menu (M)", 250, 150);
			g.drawString("Quit Game (Q)", 250, 200);
			if (quit == false) {
				g.clear();
			}
		}

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		
		label.setText(Application.getInstance().getGameContainer().getFPS() + "FPS");
		normalXLabel.setText(String.valueOf(player.getCharOrtoPosX() - (maxX * 100)));
		normalYLabel.setText(String.valueOf(player.getCharOrtoPosY()));
		playerXLabel.setText(String.valueOf(player.getCenterX()));
		playerYLabel.setText(String.valueOf(player.getCenterY()));
		tileXLabel.setText(String.valueOf(positionX));
		tileYLabel.setText(String.valueOf(positionY));
		if(selectedType != null){
			selectedTileLabel.setText(selectedType.getImage(selectedType.getActualImgNum()).getName() + " [" + selectedType.getActualImgNum() + "]");
			
		}
		
		tileBarMouse = Mouse.getY() > 6 && Mouse.getY() < 60 && Mouse.getX() > tileBarXOff && Mouse.getX() < Application.SCREEN_WIDTH - tileBarXOff;

		mouseX = camera.getCameraX() + Mouse.getX();
		mouseY = camera.getCameraY() + (Application.SCREEN_HEIGHT - Mouse.getY());

		Input input = container.getInput();

		selectedType = TileUtil.selectTile(input, selectedType, isometric);

		Direction direction = InputUtil.getDirection(input);

		if (player != null) {
			if (input.isKeyPressed(Input.KEY_SPACE)) {
				player.attack();
			} else {
				player.moveCharacter(delta, direction);
			}
			player.update(delta);
		}

		for (Drawable drawable : characterList) {

			Character character = (Character) drawable;
			Character enemy = null;

			if (!character.getName().equalsIgnoreCase("player")) {
				enemy = character;
			}

			if (enemy != null && enemy.isAlive()) {

				enemy.update(delta);
				if (player.isAttack()) {
					if (enemy.distanceFromSq(player) < 2500) {
						enemy.setHp(enemy.getHp() - 1);
						enemy.moveCharacter(delta * 100, player.moveTowardsDirection(enemy));
					}
				}
				if(enemy.isAttack()){
					if (player.distanceFromSq(enemy) < 2500) {
						player.setHp(player.getHp() - 1);
						player.moveCharacter(delta * 100, enemy.moveTowardsDirection(player));
					}
				}
			}
		}

		TileObject tile = tiledMap.getTileByPos(mouseX, mouseY);
		if (tile != null) {
			positionX = tile.getXIndex();
			positionY = tile.getYIndex();
			curTile = tile;

		}
		if (tileBarMouse) {
			int wheel = Mouse.getDWheel();
			if (wheel > 0) {
				increaseDepthLevel(1);
			}
			if (wheel < 0) {
				increaseDepthLevel(-1);
			}
		} else {
			// int wheel = Mouse.getDWheel();
			// if(wheel > 0){
			// increaseGlobalScale();
			// }
			// if(wheel < 0){
			// decreaseGlobalScale();
			// }
		}

		if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			mouseButton1();
		} else if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
			mouseButton2();
		} else {
			positionX = -1;
			positionY = -1;
		}

		if (input.isKeyDown(Input.KEY_ESCAPE) || input.isKeyDown(Input.KEY_Q)) {
			FileUtil.saveMap(new File(tiledMap.getMapFilePath()), tiledMap);
			quit = true;
		}
		// if (input.isKeyPressed(Input.KEY_E)) {
		// villageCenter.expand();
		// villageCenter.render();
		// }

		if (quit == true) {
			if (input.isKeyDown(Input.KEY_R)) {
				quit = false;
			}
			if (input.isKeyDown(Input.KEY_M)) {
				game.enterState(0);
			}
			if (input.isKeyDown(Input.KEY_Q)) {

				System.exit(0);
			}
		}
		if (input.isKeyPressed(Input.KEY_P)) {
			paintPath = !paintPath;
		}

		Collections.sort(characterList);
		
		hpBar.setValue(player.getHp() / 5.0f);
	}

	public Character getPlayer() {
		return player;
	}
	
	public void setLabelImage(){
		image = getRootPane().getGUI().getRenderer().createDynamicImage(200, 200);
		Image actualImage = selectedType.getImage(selectedType.getActualImgNum());
		if(actualImage != null){
			
			byte[] bytes = selectedType.getImage(selectedType.getActualImgNum()).getTexture().getTextureData();
			
			if(bytes.length > 0){
				if(image != null){
					image.update(ByteBuffer.wrap(bytes), Format.RGBA);
					tile.setBackground(image);
				}
			}
		}
	}
}
