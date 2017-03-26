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

	//private BoxLayout boxLayout;
	private de.matthiasmann.twl.Label label;
	private de.matthiasmann.twl.BorderLayout borderLayout;
	private de.matthiasmann.twl.ResizableFrame frame;
	private de.matthiasmann.twl.ColumnLayout columnLayout;
	private Group buttonGroupH, buttonGroupV;
	private Label fpsLabel;
	private de.matthiasmann.twl.Label normalX;
	private de.matthiasmann.twl.Label normalY;
	private de.matthiasmann.twl.Label playerX;
	private de.matthiasmann.twl.Label playerY;
	private de.matthiasmann.twl.Label tileX;
	private de.matthiasmann.twl.Label tileY;
	private de.matthiasmann.twl.Label selectedTile;
	private BoxLayout btnBox;
	private BoxLayout valueBox;
	private BoxLayout horizontal;
	
	@Override
	protected RootPane createRootPane() {
		
		RootPane rp = super.createRootPane();
		rp.setTheme("playState");
	
//		columnLayout = new ColumnLayout();
		
		valueBox = new BoxLayout(BoxLayout.Direction.VERTICAL);
		btnBox = new BoxLayout(BoxLayout.Direction.VERTICAL);
		horizontal = new BoxLayout(BoxLayout.Direction.HORIZONTAL);
//		
//		Columns columns = columnLayout.getColumns("label", "value");
//		buttonGroupH = columnLayout.createSequentialGroup();
//		buttonGroupV = columnLayout.createParallelGroup();
//		columnLayout.setVerticalGroup(buttonGroupV);
//		columnLayout.setHorizontalGroup(buttonGroupH);
//		
//		columnLayout.addRow(columns);
//		columnLayout.addDefaultGaps();
//		
//		borderLayout = new BorderLayout();
//		columnLayout.setTheme("boxLayout");
		horizontal.setTheme("boxLayout");
//		boxLayout = new de.matthiasmann.twl.BoxLayout();
//		boxLayout.setTheme("boxLayout");
		
		label = new Label();
		label.setMinSize(100, 100);
		label.setBorderSize(new Border(6));
		

		fpsLabel = new Label();
		fpsLabel.setMinSize(100, 100);
		fpsLabel.setBorderSize(new Border(6));
		fpsLabel.setText("FPS: ");
		fpsLabel.setAutoSize(true);

		normalX = new Label();
		normalX.setMinSize(100, 100);
		normalX.setBorderSize(new Border(6));
		normalX.setText("NormalX: ");
		normalX.setAutoSize(true);
		
		normalY = new Label();
		normalY.setMinSize(100, 100);
		normalY.setBorderSize(new Border(6));
		normalY.setText("NormalY: ");
		normalY.setAutoSize(true);
		
		playerX = new Label();
		playerX.setMinSize(100, 100);
		playerX.setBorderSize(new Border(6));
		playerX.setText("PlayerX: ");
		playerX.setAutoSize(true);
		
		playerY = new Label();
		playerY.setMinSize(100, 100);
		playerY.setBorderSize(new Border(6));
		playerY.setText("PlayerY: ");
		playerY.setAutoSize(true);
		
		tileX = new Label();
		tileX.setMinSize(100, 100);
		tileX.setBorderSize(new Border(6));
		tileX.setText("TileX: ");
		tileX.setAutoSize(true);
		
		tileY = new Label();
		tileY.setMinSize(100, 100);
		tileY.setBorderSize(new Border(6));
		tileY.setText("TileY: ");
		tileY.setAutoSize(true);

		selectedTile = new Label();
		selectedTile.setMinSize(100, 100);
		selectedTile.setBorderSize(new Border(6));
		selectedTile.setText("SelectedTile: ");
		selectedTile.setAutoSize(true);
		
//		buttonGroupV.addWidget(fpsLabel, Alignment.TOPLEFT); 
//		buttonGroupH.addWidget(fpsLabel, Alignment.LEFT);
//		
//		buttonGroupV.addWidget(label, Alignment.TOPRIGHT);
//		buttonGroupH.addWidget(label, Alignment.RIGHT);
//		columnLayout.addRow(columns);
//		buttonGroupV.addWidget(normalX, Alignment.CENTER); 
//		buttonGroupH.addWidget(normalX, Alignment.LEFT);
//		columnLayout.addRow(columns);
//		buttonGroupV.addWidget(normalY, Alignment.CENTER); 
//		buttonGroupH.addWidget(normalY, Alignment.LEFT);
//		columnLayout.addRow(columns);
//		buttonGroupV.addWidget(playerX, Alignment.TOPLEFT); 
//		buttonGroupH.addWidget(playerX, Alignment.LEFT);
//		columnLayout.addRow(columns);
//		buttonGroupV.addWidget(playerY, Alignment.TOPLEFT); 
//		buttonGroupH.addWidget(playerY, Alignment.LEFT);
//		columnLayout.addRow(columns);
//		buttonGroupV.addWidget(tileX, Alignment.TOPLEFT); 
//		buttonGroupH.addWidget(tileX, Alignment.LEFT);
//		columnLayout.addRow(columns);
//		buttonGroupV.addWidget(tileY, Alignment.TOPLEFT); 
//		buttonGroupH.addWidget(tileY, Alignment.LEFT);
//		
//		buttonGroupV.addWidget(selectedTile, Alignment.TOPLEFT); 
//		buttonGroupH.addWidget(selectedTile, Alignment.LEFT);
		
		btnBox.add(fpsLabel);
		btnBox.add(normalX);
		btnBox.add(normalY);
		btnBox.add(playerX);
		btnBox.add(playerY);
		btnBox.add(tileX);
		btnBox.add(tileY);
		btnBox.add(selectedTile);
		
		valueBox.add(label);
		
		
		horizontal.add(btnBox);
		horizontal.add(valueBox);
		
		rp.add(horizontal);
		
//		rp.add(columnLayout);
			
		return rp;
	}

	@Override
	protected void layoutRootPane() {
		//columnLayout.adjustSize();
		horizontal.setSize(256, 512);
		horizontal.setPosition(Application.SCREEN_WIDTH * 3/4 , 0);
		
		fpsLabel.adjustSize();
		fpsLabel.setBorderSize(40, 6, 6, 6);
		normalX.setBorderSize(6);
		normalY.setBorderSize(6);
		playerX.setBorderSize(6);
		playerY.setBorderSize(6);
		tileX.setBorderSize(6);
		tileY.setBorderSize(6);
		selectedTile.setBorderSize(6);
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

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
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
		pathFinder = new AStarPathFinder(tiledMap, maxX * maxY, true);
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

		g.drawString("Normal X: " + (player.getCharOrtoPosX() - (maxX * 100)) + "\nNormal Y: " + player.getCharOrtoPosY(), Application.SCREEN_WIDTH * 3 / 4, 20);
		g.drawString("Player X: " + player.getCenterX() + "\nPlayer Y: " + player.getCenterY(), Application.SCREEN_WIDTH * 3 / 4, 108);
		g.drawString("Tile X: " + positionX + "\nTile Y: " + positionY, Application.SCREEN_WIDTH * 3 / 4, 64);

		if (selectedType != null) {
			g.drawString("SelectedTile: " + selectedType.getImage(selectedType.getActualImgNum()).getName() + " [" + selectedType.getActualImgNum() + "]", Application.SCREEN_WIDTH * 3 / 4, 172);
			g.setColor(Color.darkGray);
			g.fillRoundRect(Application.SCREEN_WIDTH * 3 / 4, 200, selectedType.getImage(selectedType.getActualImgNum()).getWidth(), selectedType.getImage(selectedType.getActualImgNum()).getHeight(),
					3);
			g.setColor(Color.white);
			selectedType.getImage(selectedType.getActualImgNum()).draw(Application.SCREEN_WIDTH * 3 / 4, 200);
			g.drawRoundRect(Application.SCREEN_WIDTH * 3 / 4, 200, selectedType.getImage(selectedType.getActualImgNum()).getWidth(), selectedType.getImage(selectedType.getActualImgNum()).getHeight(),
					3);
		} else {
			g.drawString("SelectedTile: none", Application.SCREEN_WIDTH * 3 / 4, 172);
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

//				TileObject enemyTile = tiledMap.getTilesByOrtho(enemy.getCharOrtoPosX(), enemy.getCharOrtoPosY()).get(0);
//				TileObject playerTile = tiledMap.getTilesByOrtho(player.getCharOrtoPosX(), player.getCharOrtoPosY()).get(0);
//
//				if (enemy.distanceFromSq(player) < 200) {
//					if (enemyTile != null && playerTile != null) {
//						drawPath = pathFinder.findPath(enemy, enemyTile.getXIndex(), enemyTile.getYIndex(), playerTile.getXIndex(), playerTile.getYIndex());
//					}
//					Step step1 = null;
//					Step step2 = null;
//					TileObject nextTile = null;
//
//					if (drawPath != null) {
//
//						if (drawPath.getLength() > 0) {
//							step1 = drawPath.getStep(0);
//							step2 = drawPath.getStep(1);
//						}
//						if (step1 != null && step2 != null) {
//
//							if (enemy.distanceFromSq(player) > 50f) {
//								enemy.moveToTile(step2.getX(), step2.getY(), delta);
//							} else {
//								enemy.moveCharacter(0, Direction.STAND);
//							}
//						} else {
//							enemy.moveCharacter(0, Direction.STAND);
//						}
//					}
//				} else {
//					enemy.moveCharacter(1, player.moveTowardsDirection(enemy));
//				}

				enemy.update(delta);
				if (player.isAttack()) {
					if (enemy.distanceFromSq(player) < 2500) {
						enemy.setHp(enemy.getHp() - 1);
						enemy.moveCharacter(delta * 10, player.moveTowardsDirection(enemy));
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
	}

	public Character getPlayer() {
		return player;
	}
}
