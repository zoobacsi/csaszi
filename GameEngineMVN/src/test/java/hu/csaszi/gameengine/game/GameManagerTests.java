package hu.csaszi.gameengine.game;

import hu.csaszi.gameengine.render.core.Drawer;
import hu.csaszi.gameengine.render.core.Window;
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

        gameManager.createWindow("test", 700,500,true);

        assertNotNull(gameManager.getWindow());
        assertEquals(682, gameManager.getWindow().getWidth());
        assertEquals(453, gameManager.getWindow().getHeight());
        assertEquals("test", gameManager.getWindow().getTitle());
    }

    @Test
    public void testAddAndEnterState(){

        GameManager gameManager = new GameManager();//mock(GameManager.class);
        gameManager.createWindow("test", 0,0,true);

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
        public void init(Window window, GameManager gameManager) {

        }

        @Override
        public void render(Window window, Drawer drawer, GameManager gameManager) {

        }

        @Override
        public void update(Window window, GameManager gameManager) {

        }
    }
}
