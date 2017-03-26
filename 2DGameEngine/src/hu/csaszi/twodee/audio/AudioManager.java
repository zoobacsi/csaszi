package hu.csaszi.twodee.audio;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

public class AudioManager {

	public static void playDefaultMusic() throws SlickException{
		
		Music music = new Music("res/01 - Hoardly.MOD");
		music.setVolume(0.2f);
		music.play();
		music.setVolume(0.2f);
	}
}
