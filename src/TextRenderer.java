import java.awt.Graphics2D;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;

public interface TextRenderer {
    // Metodo per generare l'immagine del testo con font personalizzato
    BufferedImage generateTextImage(String text, int width, int height);

    // Metodo per impostare il font personalizzato
    void setCustomFont(String fontName, int fontSize);
}
