package basics;

import java.awt.image.BufferedImage;

public interface TextRenderer {
    // Metodo per generare l'immagine del testo con font personalizzato
    BufferedImage generateTextImage(String text, Vector2 dimension);

    // Metodo per impostare il font personalizzato
    void setCustomFont(String fontName, int fontSize);
}
