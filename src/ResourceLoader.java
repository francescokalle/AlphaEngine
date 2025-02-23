import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ResourceLoader {
    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(ResourceLoader.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Font loadFont(String path, float size) {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.class.getResourceAsStream(path));
            return font.deriveFont(size);
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("Arial", Font.PLAIN, (int) size);
        }
    }
}
