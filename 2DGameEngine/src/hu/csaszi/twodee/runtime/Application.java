package hu.csaszi.twodee.runtime;

import hu.csaszi.twodee.gamestates.DefaultMenu;
import hu.csaszi.twodee.gamestates.DefaultPlay;
import hu.csaszi.twodee.gui.twl.TWLStateBasedGame;
import hu.csaszi.twodee.util.FileUtil;
import hu.csaszi.twodee.util.PropsValues;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.Random;
import java.util.prefs.Preferences;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

public class Application extends TWLStateBasedGame{


	public static final int menu = 0;
	public static final int play = 1;
	public static final int gameOver = 2;
	
	public static final int SCREEN_WIDTH = 1024;
	public static final int SCREEN_HEIGHT = 768;
	public static Random random;
	public static Application instance;

	private static AppGameContainer appgc;
//	private static Preferences userPref = Preferences.userRoot();
	private static Preferences userPref = Preferences.systemRoot();
	
	public static void putPref(String key, String value){
	    userPref.put(key, value);
	}
	
	public static String getPref(String key, String defValue){
	    return userPref.get(key, defValue);
	}

	private Application(String name) {
		super(name);
		
		random = new Random();
		System.out.println("lol");
		this.addState(new DefaultMenu(menu));
		this.addState(new DefaultPlay(play));
		System.out.println("wut");
		
	}

	public static Application getInstance(){
		return instance;
	}
	
	
	public static Random getRandom(){
		return random;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

//        System.setProperty("user.name","EnglishWords");
		
		try {
			instance = new Application(PropsValues.TITLE);
			appgc = new AppGameContainer(instance);
			appgc.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
              
			appgc.start();
			appgc.setTargetFrameRate(60);
			appgc.setVSync(true);
			
		}
		catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {

		this.getState(menu).init(gc, this);
		System.out.println("1 " + this.getState(menu));
		this.enterState(menu);
		System.out.println("2");
		
	}
	
	public static GameContainer getGameContainer(){
		return getInstance().getGameContainerImpl();
	}

	private GameContainer getGameContainerImpl(){
		return appgc;
	}

	@Override
	protected URL getThemeURL() {
		URL url = null;
		
		try{
		//RadicalFish
			url = new File("src/hu/csaszi/twodee/runtime/ui/RadicalFish.xml").toURI().toURL();
		} catch(Exception e){
			Log.error("faszom");
			url = Application.class.getResource("ui/RadicalFish.xml");
		}
		
		return url;
		//Application.class.getResource("ui/RadicalFish.xml");
	}

}
