package hu.csaszi.gameengine.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;

public class AudioClip {

    private File file;

    public AudioClip(String path){
        file = new File(path);
        if(!file.exists()){
            System.out.println("Error >> AudioClip not found.");
        }
    }

    public AudioClip(File file){
        this.file = file;
        if(!file.exists()){
            System.out.println("Error >> AudioClip not found.");
        }
    }

    public AudioInputStream getAudioStream(){

        try{

            return AudioSystem.getAudioInputStream(file);
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
