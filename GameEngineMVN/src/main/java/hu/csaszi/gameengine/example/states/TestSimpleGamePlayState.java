package hu.csaszi.gameengine.example.states;

import hu.csaszi.gameengine.audio.OggPlayer;
import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.game.GameState;
import hu.csaszi.gameengine.gui.DebugPanel;
import hu.csaszi.gameengine.gui.GuiManager;
import hu.csaszi.gameengine.physics.objects.Entity;
import hu.csaszi.gameengine.physics.objects.EntityManager;
import hu.csaszi.gameengine.physics.objects.Player;
import hu.csaszi.gameengine.physics.objects.Transform;
import hu.csaszi.gameengine.physics.objects.ai.PatrolCommand;
import hu.csaszi.gameengine.physics.world.TileRenderer;
import hu.csaszi.gameengine.physics.world.World;
import hu.csaszi.gameengine.render.core.Drawer;
import hu.csaszi.gameengine.render.core.gl.Animation;
import hu.csaszi.gameengine.render.core.gl.GLFWWindow;
import hu.csaszi.gameengine.render.core.gl.TextureSheet;
import hu.csaszi.gameengine.render.core.gl.renderer.Camera;
import hu.csaszi.gameengine.render.core.gl.shaders.Shader;
import hu.csaszi.gameengine.render.graphics.AnimationKeys;
import hu.csaszi.gameengine.render.graphics.gui.GUI;
import hu.csaszi.gameengine.util.IOUtil;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class TestSimpleGamePlayState extends GameState {

	public TestSimpleGamePlayState(int stateId) {
		this.stateId = stateId;
	}

	private boolean inited;

	private World world;
	private TileRenderer tileRenderer;
	private Shader shader;
	private GUI gui;
	private Camera camera;
	private Map<String, String> debugMap = new HashMap<>();

	public Player getPlayer() {
		return player;
	}

	private Player player;
	private EntityManager entityManager;

	public World getWorld() {
		return world;
	}
	public Camera getCamera() {
		return camera;
	}

	private DebugPanel debugPanel;

	@Override
	public void init(GLFWWindow window, GameManager gameManager) {

		entityManager = EntityManager.createEntityManager(this);

		File file = IOUtil.getFile("03_Gridscape_-_Cybergrid_Rocker___ALBUM_MASTER.ogg");
		OggPlayer.runOgg(file);

		if(GL.getCapabilities() != null) {
			camera = new Camera(window);

			world = new World("test");
			tileRenderer = new TileRenderer(true);

			shader = new Shader("shader");

			camera.setPosition(new Vector3f(0, 0, 0));


			Transform transformEnemy = new Transform();
			transformEnemy.pos.x = 0;
			transformEnemy.pos.y = -4;

			try {
				readEntitesMap(entityManager);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}

//			Calculator calc = new Calculator(300, 50);
			debugPanel = new DebugPanel(600, 0);
			debugPanel.setDebugMap(debugMap);
//
			GuiManager.addGameManager(gameManager);
//			GuiManager.addGuiNode(calc);
			GuiManager.addGuiNode(debugPanel);

			Transform transform = new Transform();
			transform.scale.x = 1;
			transform.scale.y = 1;
			transform.pos.x = 4;
			transform.pos.y = -2;

			player = new Player(transform);
			entityManager.addObject(player);

		}
	}

	public void putDebugInfo(String key, String value) {
		debugMap.put(key, value);
	}

	private void readEntitesMap(EntityManager entityManager) throws IOException, URISyntaxException {
		File file = IOUtil.getFile(world.getTag() + "_entities.png", "levels/");
		BufferedImage entitySheet = ImageIO.read(file);

		int width = entitySheet.getWidth();
		int height = entitySheet.getHeight();

		Transform.setWorldBoundaries(width, height);

		int[] colorEntitySheet = entitySheet.getRGB(0, 0, width, height, null, 0,width);
		Transform transform;

		for(int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int entityIndex = (colorEntitySheet[x + y * width] >> 16) & 0xFF;
				int entityAlpha = (colorEntitySheet[x + y * width] >> 24) & 0xFF;
				int dir = x - y > 0 ? 1 : -1;
				if(entityAlpha > 0){

					transform = new Transform();
					transform.pos.x = x * 2;
					transform.pos.y = -y * 2;
					switch (entityIndex){
						case 1:
							Entity entity = new Entity(2, transform, "tall")/*{
								@Override
								public void update(float delta, GLFWWindow window, Camera camera, World world) {
									move(new Vector2f(dir * 5*delta, 0));

									if( collideWithTile(world)){
										useAnimation(ANIM_IDLE);
									} else {
										useAnimation(ANIM_WALK);
									}
								}
							}*/;
							TextureSheet sheet = new TextureSheet("sheets/green", 135, 147);
							entity.setSprite(Entity.ANIM_IDLE, new Animation(5, sheet, AnimationKeys.PLATFORMER_CHARSET_IDLE_FRAMES));
							entity.setSprite(Entity.ANIM_WALK, new Animation(5, sheet, AnimationKeys.PLATFORMER_CHARSET_WALKING_FRAMES));
							entity.setCommand(new PatrolCommand(entity, player));
							entityManager.addObject(entity);
							//break;
							return;
						default:
							break;
					}
				}
			}
		}
	}

	@Override
	public GUI getGUI(){
		return gui;
	}

	@Override
	public void render(GLFWWindow window, Drawer drawer, GameManager gameManager) {

		world.render(tileRenderer, shader, camera);
		entityManager.render(shader, camera, world);
//        gui.render();
	}

	@Override
	public void update(float delta, GameManager gameManager) {

		camera.update(delta);
		entityManager.update(delta, gameManager);
		world.correctCamera(camera);
		debugPanel.setPosX(player.getPositionX());
		debugPanel.setPosY(player.getPositionY());

//		while (GuiManager.getGuiNodeSet().iterator().hasNext()) {
//			GuiNode guiNode = GuiManager.getGuiNodeSet().iterator().next();
//			if (guiNode instanceof DebugPanel) {
//				((DebugPanel)guiNode).setFps(gameManager.getWindow().getCurrentFPS());
//				((DebugPanel)guiNode).setPosX(player.getPositionX());
//				((DebugPanel)guiNode).setPosY(player.getPositionY());
//			}
//		}
	}
}
