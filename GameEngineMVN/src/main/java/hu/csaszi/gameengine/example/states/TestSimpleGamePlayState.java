package hu.csaszi.gameengine.example.states;

import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.game.GameState;
import hu.csaszi.gameengine.gui.Calculator;
import hu.csaszi.gameengine.gui.Demo;
import hu.csaszi.gameengine.gui.GuiManager;
import hu.csaszi.gameengine.physics.objects.Entity;
import hu.csaszi.gameengine.physics.objects.EntityManager;
import hu.csaszi.gameengine.physics.objects.Player;
import hu.csaszi.gameengine.physics.objects.Transform;
import hu.csaszi.gameengine.physics.world.Tile;
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
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
	private Player player;
	private EntityManager entityManager;

	public World getWorld() {
		return world;
	}
	public Camera getCamera() {
		return camera;
	}

	@Override
	public void init(GLFWWindow window, GameManager gameManager) {


		entityManager = EntityManager.createEntityManager(this);
//
//		AudioPlayer.addClip("playerHit", new AudioClip("src/main/resources/phit.wav"));
//
//		for(int i = 0; i < 6; i++){
//			ObjectManager.addObject(new BlockSpawner(window, i*64+64));
//		}
//
//		ObjectManager.addObject(new Player(window, "playerHit"));

		if(GL.getCapabilities() != null) {
			camera = new Camera(window);

			world = new World("test");
			tileRenderer = new TileRenderer(true);

			shader = new Shader("shader");

			world.setTile(Tile.getTile("desert").setSolid(), 1, 0);

			world.setTile(Tile.getTile("water0").setSolid(), 4, 4);

			camera.setPosition(new Vector3f(0, 0, 0));

			Transform transform = new Transform();
			transform.scale.x = 1;
			transform.scale.y = 1;
			transform.pos.x = 2;
			transform.pos.y = -6;
			player = new Player(transform);
			entityManager.addObject(player);

			Transform transformEnemy = new Transform();
			transformEnemy.pos.x = 0;
			transformEnemy.pos.y = -4;

			entityManager.addObject(new Entity(new Animation(1, 1, "soldier"), transformEnemy){
				@Override
				public void update(float delta, GLFWWindow window, Camera camera, World world) {
					move(new Vector2f(5*delta, 0));
					super.update(delta, window, camera, world);
				}
			});
			try {
				readEntitesMap(entityManager);
			} catch (IOException e) {
				e.printStackTrace();
			}

//            gui = new GUI(window);

			Calculator calc = new Calculator(300, 50);
			Demo demo = new Demo(50, 50);

			GuiManager.addGuiNode(calc);
			GuiManager.addGuiNode(demo);
        }

	}

	private void readEntitesMap(EntityManager entityManager)throws IOException{
		BufferedImage entitySheet = ImageIO.read(new File("src/main/resources/levels/" + world.getTag() + "_entities.png"));

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
							Entity entity = new Entity(2, transform, "tall"){
								@Override
								public void update(float delta, GLFWWindow window, Camera camera, World world) {
									move(new Vector2f(dir * 5*delta, 0));

									if( collideWithTile(world)){
										useAnimation(ANIM_IDLE);
									} else {
										useAnimation(ANIM_WALK);
									}
								}
							};
							TextureSheet sheet = new TextureSheet("sheets/Soldier3", 32, 48);
							entity.setSprite(Entity.ANIM_IDLE, new Animation(5, sheet, AnimationKeys.DEFAULT_CHARSET_IDLE_FRAMES));
							entity.setSprite(Entity.ANIM_WALK, new Animation(5, sheet, AnimationKeys.DEFAULT_CHARSET_WALKING_FRAMES));
							entityManager.addObject(entity);
							break;
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

		camera.update();
		entityManager.update(delta, gameManager);
		world.correctCamera(camera);
	}
}
