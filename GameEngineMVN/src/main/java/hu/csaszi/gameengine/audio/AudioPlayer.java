package hu.csaszi.gameengine.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class AudioPlayer {

    public static synchronized void playSound(AudioClip sfx, double vol){

        Thread thread = new Thread(){
            public void run(){

                try{

                    AudioInputStream stream = sfx.getAudioStream();

                    Clip clip = AudioSystem.getClip();

                    clip.open(stream);
                    setVol(vol, clip);
                    clip.start();


                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }; thread.start();
    }

    private static void setVol(double vol, Clip clip){
        FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    }
}
