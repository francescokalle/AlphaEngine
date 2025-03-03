package basics;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.util.Objects;

public class ResourceLoader {

    // Carica un'immagine dalla cartella delle risorse
    public static BufferedImage loadImage(String path) {
        if (path == null){
            return null;
        }
        try {
            return ImageIO.read(Objects.requireNonNull(ResourceLoader.class.getResource(path)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Carica un font dalla cartella delle risorse
    public static Font loadFont(String path, float size) {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(ResourceLoader.class.getResourceAsStream(path)));
            return font.deriveFont(size);
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("Arial", Font.PLAIN, (int) size);
        }
    }

    // Carica un file audio dalla cartella delle risorse
    public static AudioInputStream loadAudio(String path) {
        try {
            InputStream audioStream = ResourceLoader.class.getResourceAsStream(path);
            return AudioSystem.getAudioInputStream(audioStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Cerca il primo file in una cartella che contiene una stringa nel nome
    public static String searchFiles(String folderPath, String searchString) {
        try {
            // Accede alla cartella delle risorse
            File folder = new File(ResourceLoader.class.getResource(folderPath).toURI());

            // Se la cartella esiste e contiene file
            if (folder.exists() && folder.isDirectory()) {
                for (File file : Objects.requireNonNull(folder.listFiles())) {
                    // Se il nome del file contiene la stringa, ritorna il path relativo
                    if (file.getName().contains(searchString)) {
                        return folderPath + "/" + file.getName();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
