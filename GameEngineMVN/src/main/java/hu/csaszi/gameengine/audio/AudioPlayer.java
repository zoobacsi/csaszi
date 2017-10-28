package hu.csaszi.gameengine.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.util.HashMap;
import java.util.Map;

public class AudioPlayer {

    private static volatile Map<String,AudioClip> clipMap = new HashMap<>();

    private static volatile double vol = 1.0;

    public static synchronized void playSound(String clipName){

        playSound(clipMap.get(clipName));
    }

    public static synchronized void playSound(AudioClip sfx){

        if(sfx == null){
            return;
        }

        Thread thread = new Thread(){
            public void run(){

                try{

                    AudioInputStream stream = sfx.getAudioStream();

                    Clip clip = AudioSystem.getClip();

                    clip.open(stream);
                    setVol(clip);
                    clip.start();


                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }; thread.start();
    }


    private static synchronized void setVol(Clip clip){
        FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float dB = (float)(Math.log(vol) / Math.log(10) * 20);
        gain.setValue(dB);
    }

    public static synchronized void increaseVolume(){
        if(vol < 1.0){
            vol += 0.05;
        }
        if(vol > 0.95){
            vol = 1.0;
        }
    }

    public static synchronized void decreaseVolume(){
        if(vol > 0.0){
            vol -= 0.05;
        }
        if(vol < 0.05){
            vol = 0.0;
        }
    }

    public static synchronized void addClip(String key, AudioClip clip){
        clipMap.put(key, clip);
    }

    public static synchronized void clearClips(){
        clipMap.clear();
    }

    public static synchronized double getVolume(){
        return vol;
    }
}
