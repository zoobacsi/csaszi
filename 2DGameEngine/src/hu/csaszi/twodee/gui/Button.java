package hu.csaszi.twodee.gui;

import hu.csaszi.twodee.runtime.Application;

import org.newdawn.slick.Image;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;

public class Button {

	private MouseOverArea mouseOverArea;
	
	public Button(Image image, int x, int y, ComponentListener componentListener){
		
		mouseOverArea = new MouseOverArea(Application.getGameContainer(), image, x, y, componentListener);
	}
	
	public void render(){
		mouseOverArea.render(Application.getGameContainer(), Application.getGameContainer().getGraphics());
	}
}
