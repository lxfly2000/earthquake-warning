package cn.dxr.quake.Utils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundUtil {
    public void playSound(String filename) {
        try{
            Clip clip= AudioSystem.getClip();
            AudioInputStream s=AudioSystem.getAudioInputStream(new File(filename));
            clip.open(s);
            clip.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}