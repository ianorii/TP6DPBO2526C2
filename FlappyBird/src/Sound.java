import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.ArrayList;

// Pemutar efek suara dengan voice pool
public class Sound {
    private final ArrayList<Clip> clips = new ArrayList<>();
    private int index = 0;


    public Sound(String path, int voices) {
        for (int i = 0; i < voices; i++) {
            try {

                AudioInputStream audio = AudioSystem.getAudioInputStream(
                        getClass().getResource(path));
                Clip clip = AudioSystem.getClip();
                clip.open(audio);
                audio.close();

                // Warm-up agar pemutaran pertama tidak delay
                clip.setFramePosition(clip.getFrameLength());
                clip.start();

                clips.add(clip);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }


    public void play() {
        if (clips.isEmpty()) return;

        Clip clip = clips.get(index);
        index = (index + 1) % clips.size();

        clip.stop();
        clip.flush();
        clip.setFramePosition(0);
        clip.start();
    }

    // Loop audio (untuk BGM)
    public void loop() {
        if (clips.isEmpty()) return;

        Clip clip = clips.get(0);
        clip.stop();
        clip.flush();
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }


    public void stop() {
        for (Clip clip : clips) {
            clip.stop();
        }
    }


    public void close() {
        for (Clip clip : clips) {
            clip.close();
        }
    }
}
