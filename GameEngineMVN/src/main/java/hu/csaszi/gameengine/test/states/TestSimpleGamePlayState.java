package hu.csaszi.gameengine.test.states;

import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.game.GameState;
import hu.csaszi.gameengine.physics.objects.EntityManager;
import hu.csaszi.gameengine.physics.world.Tile;
import hu.csaszi.gameengine.physics.world.TileRenderer;
import hu.csaszi.gameengine.physics.world.World;
import hu.csaszi.gameengine.render.core.Drawer;
import hu.csaszi.gameengine.render.core.Window;
import hu.csaszi.gameengine.render.core.gl.renderer.Camera;
import hu.csaszi.gameengine.render.core.gl.shaders.Shader;
import hu.csaszi.gameengine.test.objects.Player;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;

public class TestSimpleGamePlayState extends GameState {

	public TestSimpleGamePlayState(int stateId) {
		this.stateId = stateId;
	}

	private boolean inited;

	private World world;
	private TileRenderer tileRenderer;
	private Shader shader;

	public World getWorld() {
		return world;
	}

	public Camera getCamera() {
		return camera;
	}

	private Camera camera;

	private Player player;

	private EntityManager entityManager;

	@Override
	public void init(Window window, GameManager gameManager) {

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

			world = new World();
			tileRenderer = new TileRenderer();
			shader = new Shader("shader");

			world.setTile(Tile.getTile("desert"), 0, 0);

			world.setTile(Tile.getTile("water0"), 31, 31);

			camera.setPosition(new Vector3f(0, 0, 0));

			player = new Player(window, "hit");
			entityManager.addObject(player);
		}

	}

	@Override
	public void render(Window window, Drawer drawer, GameManager gameManager) {
//		drawer.drawString("Score: " + Player.getScore(), window.getWidth()/2 - 34, 10);
//
//		if(!Player.isPlayerAlive()){
//			drawer.drawString("Press Space To Restart, Score: " + Player.getScore(), window.getWidth()/2-34, window.getHeight()/2 - 30);
//		}
		entityManager.render(shader, camera);

		world.render(tileRenderer, shader, camera);

	}

	@Override
	public void update(float delta, GameManager gameManager) {

		camera.update();

		entityManager.update(delta, gameManager);

		world.correctCamera(camera);
//		AWTInput AWTInput = window.getInput();
//
//		if(AWTInput.isKeyDown(KeyEvent.VK_Y)){
//			AudioPlayer.decreaseVolume();
//			GUIManager.getObjects().get(0).setLabelText("Volume: " + Math.round(AudioPlayer.getVolume() * 100f));
//		}
//		if(AWTInput.isKeyDown(KeyEvent.VK_X)){
//			AudioPlayer.increaseVolume();
//			GUIManager.getObjects().get(0).setLabelText("Volume: " + Math.round(AudioPlayer.getVolume() * 100f));
//		}
//
//		if(AWTInput.isKeyDown(KeyEvent.VK_ESCAPE)){
//			window.close();
//		}
	}
}
