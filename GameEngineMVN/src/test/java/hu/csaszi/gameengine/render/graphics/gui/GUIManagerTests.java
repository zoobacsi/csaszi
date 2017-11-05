package hu.csaszi.gameengine.render.graphics.gui;

import hu.csaszi.gameengine.render.core.software.SoftwareDrawer;
import hu.csaszi.gameengine.render.core.Window;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;

public class GUIManagerTests {

    @Test
    public void testGetObjectNotNull(){

        assertNotNull(GUIManager.getObjects());
    }

    @Test
    public void testGetObjectEmpty(){

        assertEquals(0, GUIManager.getObjects().size());
    }

    @Test
    public void testAdd(){

        GUI gui = mock(GUI.class);
        GUIManager.add(gui);

        assertEquals(1, GUIManager.getObjects().size());
    }

    @Test
    public void testRemove(){

        GUI gui = mock(GUI.class);
        GUIManager.remove(gui);

        assertEquals(0, GUIManager.getObjects().size());
    }

    @Test
    public void testFlush(){

        GUI gui = mock(GUI.class);
        GUI gui2 = mock(GUI.class);

        GUIManager.add(gui);
        GUIManager.add(gui2);

        GUIManager.flush();

        assertEquals(0, GUIManager.getObjects().size());
    }

    @Test
    public void testUpdate(){

        GUIManager.flush();

        Window window = mock(Window.class);

        GUI gui = mock(GUI.class);

        when(gui.isVisible()).thenReturn(true);

        GUIManager.add(gui);
        GUIManager.update(window);

        verify(gui, atLeastOnce()).isVisible();
        verify(gui, atLeastOnce()).update(window);
        verifyNoMoreInteractions(gui);
    }
//
//    @Test
//    public void testRender(){
//
//        GUIManager.flush();
//
//        Window window = mock(Window.class);
//        SoftwareDrawer drawer = mock(SoftwareDrawer.class);
//
//        GUI gui = mock(GUI.class);
//
//        when(gui.isVisible()).thenReturn(true);
//        when(window.getDrawer()).thenReturn(drawer);
//
//        GUIManager.add(gui);
//        GUIManager.render(window, window.getDrawer());
//
//        verify(gui, atLeastOnce()).isVisible();
//        verify(gui, atLeastOnce()).render(window, drawer);
//        verifyNoMoreInteractions(gui);
//    }

}
