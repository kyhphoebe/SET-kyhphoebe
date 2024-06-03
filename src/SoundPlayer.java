import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * class to play sound
 */
public class SoundPlayer {

    /**
     * play the sound once
     * @param soundName
     */
    public static void playSound(String soundName) {
        try {
            File soundFile = new File(soundName);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Clip loopMusic(String soundName) {
        try{
            File soundFile = new File(soundName);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
            return clip;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}