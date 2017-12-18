package hu.csaszi.gameengine.game;

import hu.csaszi.gameengine.physics.world.World;
import hu.csaszi.gameengine.render.core.Drawer;
import hu.csaszi.gameengine.render.core.Window;
import hu.csaszi.gameengine.render.core.gl.GLFWWindow;
import hu.csaszi.gameengine.render.core.gl.renderer.Camera;
import hu.csaszi.gameengine.render.graphics.gui.GUI;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class GameManagerTests {

    private GameManager gameManager;

    @Before
    public void setUp(){
        gameManager = new GameManager();
    }

    @Test
    public void testCreateWindow(){

        gameManager.createWindow("test", 700,500);

        assertNotNull(gameManager.getWindow());
        assertEquals(682, gameManager.getWindow().getWidth());
        assertEquals(453, gameManager.getWindow().getHeight());
        assertEquals("test", gameManager.getWindow().getTitle());
    }

    @Test
    public void testAddAndEnterState(){

        GameManager gameManager = new GameManager();//mock(GameManager.class);
        gameManager.createWindow("test", 0,0);

        GameStateImpl state = spy(new GameStateImpl());
        when(state.getStateId()).thenReturn(1);

        gameManager.addState(state);
        gameManager.enterState(1, true);

        verify(state, atLeastOnce()).getStateId();
        verify(state, atLeastOnce()).init(gameManager.getWindow(), gameManager);
        verifyNoMoreInteractions(state);
    }

    class GameStateImpl extends GameState{

        @Override
        public void init(GLFWWindow window, GameManager gameManager) {

        }

        @Override
        public GUI getGUI() {
            return null;
        }

        @Override
        public void render(GLFWWindow window, Drawer drawer, GameManager gameManager) {

        }

        @Override
        public World getWorld() {
            return null;
        }

        @Override
        public Camera getCamera() {
            return null;
        }

        @Override
        public void update(float delta, GameManager gameManager) {

        }
    }
}
