import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ResourceLoader {

    // Carica un'immagine dalla cartella delle risorse
    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(ResourceLoader.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Carica un font dalla cartella delle risorse
    public static Font loadFont(String path, float size) {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.class.getResourceAsStream(path));
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
}
