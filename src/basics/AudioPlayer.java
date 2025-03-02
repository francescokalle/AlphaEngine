package basics;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public class AudioPlayer {

    private static Clip clip;

    public static void play(String path) {
        stop(); // Ferma l'audio precedente se sta suonando

        try {
            InputStream audioStream = ResourceLoader.class.getResourceAsStream(path);
            if (audioStream == null) {
                throw new IllegalArgumentException("File audio non trovato: " + path);
            }

            // Converti InputStream in AudioInputStream
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(audioStream);

            // Ottieni il formato audio e apri un Clip
            AudioFormat format = audioInput.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(audioInput);

            // Riproduci il suono
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
