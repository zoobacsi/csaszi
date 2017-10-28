package hu.csaszi.gameengine.render.graphics.gui;

import hu.csaszi.gameengine.render.core.Drawer;
import hu.csaszi.gameengine.render.core.Window;

import java.util.ArrayList;
import java.util.List;

public class GUIManager {

    private static List<GUI> objects = new ArrayList<>();

    private GUIManager(){

    }

    public static void add(GUI gui){
        objects.add(gui);
    }

    public static void remove(GUI gui){
        objects.remove(gui);
    }

    public static void flush(){
        objects.clear();
    }

    public static void update(Window window){

        List<GUI> guiObjects = new ArrayList<>();

        guiObjects.addAll(objects);

        for(GUI gui : guiObjects) {
            if(gui != null && gui.isVisible()){
                gui.update(window);
            }
        }
    }

    public static void render(Window window, Drawer drawer){

        List<GUI> guiObjects = new ArrayList<>();

        guiObjects.addAll(objects);

        for(GUI gui : objects){
            if(gui != null && gui.isVisible()){
                gui.render(window, drawer);
            }
        }
    }

    public static List<GUI> getObjects(){

        List<GUI> results = new ArrayList<>();

        results.addAll(objects);

        return results;
    }
}
