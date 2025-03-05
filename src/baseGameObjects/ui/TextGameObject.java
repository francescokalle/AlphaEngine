package baseGameObjects.ui;

import baseGameObjects.GameObject;
import basics.Vector2;
import graphics.GamePanel;

import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class TextGameObject extends GameObject {

    private String text;
    private Font font;
    private String alignment;
    private Color textColor;
    private int fontSize;
    private int scalingFactor = 1; // Valore di default per lo scaling

    public TextGameObject(GamePanel gamePanel, Vector2 position, String text, String alignment, File fontFile, Color color, int fontSize) throws IOException, FontFormatException {
        super(gamePanel, position, Vector2.ZERO()); // Passiamo width e height come 0, lo calcoleremo dopo
        this.text = Objects.requireNonNull(text, "Text cannot be null");
        this.font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(fontFile, "Font file cannot be null"));
        this.alignment = Objects.requireNonNull(alignment, "Alignment cannot be null");
        this.textColor = color != null ? color : Color.BLACK; // Usa il colore passato, se presente
        this.fontSize = fontSize;

        // Estrai lo scaling factor dal nome del file del font (se è pixelato)
        extractScalingFactorFromFont(fontFile.getName());

        // Calcola automaticamente le dimensioni del testo
        Dimension textDimensions = calculateTextDimensions(text);
        this.dimension.x = textDimensions.width;
        this.dimension.y = textDimensions.height;

        // Genera l'immagine del testo
        super.staticImage = generateTextImage(text, dimension);
    }

    // Estrai lo scaling factor dal nome del file del font
    private void extractScalingFactorFromFont(String fontName) {
        try {
            // Esempio di nome file: "fontname.pixelated.2.ttf"
            String[] parts = fontName.split("\\.");
            if (parts.length >= 3 && parts[1].equals("pixelated")) {
                // Ottieni il fattore di scaling (la parte dopo ".pixelated.")
                scalingFactor = Integer.parseInt(parts[2]);
            }
        } catch (Exception e) {
            System.err.println("Impossibile estrarre lo scaling factor dal nome del font. Verrà utilizzato il valore di default (1).");
            scalingFactor = 1; // Imposta a 1 se non è possibile analizzare il file
        }
    }

    private Dimension calculateTextDimensions(String text) {
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // Imposta il font con la dimensione corretta e lo scaling
        Font scaledFont = font.deriveFont(Font.PLAIN, fontSize * scalingFactor);
        g2d.setFont(scaledFont);

        // Dividi il testo in linee
        String[] lines = text.split("\n");

        // Calcola la larghezza e l'altezza totali del testo
        int maxWidth = 0;
        int totalHeight = 0;

        for (String line : lines) {
            TextLayout layout = new TextLayout(line, scaledFont, g2d.getFontRenderContext());
            int textWidth = (int) layout.getBounds().getWidth();
            int textHeight = (int) layout.getBounds().getHeight();

            if (textWidth > maxWidth) {
                maxWidth = textWidth;
            }
            totalHeight += textHeight;
        }

        g2d.dispose();
        return new Dimension(maxWidth, totalHeight);
    }

    private BufferedImage generateTextImage(String text, Vector2 dimension) {
        // Crea un'immagine vuota
        BufferedImage image = new BufferedImage(dimension.x.intValue(), dimension.y.intValue(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // Disabilita l'antialiasing per mantenere l'aspetto pixelato
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        // Imposta il font con la dimensione corretta e lo scaling
        Font scaledFont = font.deriveFont(Font.PLAIN, fontSize * scalingFactor);
        g2d.setFont(scaledFont);
        g2d.setColor(textColor);

        // Dividi il testo in linee
        String[] lines = text.split("\n");

        // Posizione iniziale per il disegno del testo
        int yPos = 0;

        // Disegna ogni linea
        for (String line : lines) {
            TextLayout layout = new TextLayout(line, scaledFont, g2d.getFontRenderContext());

            // Calcola la posizione e l'allineamento del testo
            int textWidth = (int) layout.getBounds().getWidth();
            int textHeight = (int) layout.getBounds().getHeight();

            int xPos = 0;
            switch (alignment) {
                case "center":
                    xPos = (dimension.x.intValue() - textWidth) / 2;
                    break;
                case "right":
                    xPos = dimension.x.intValue() - textWidth; // Allinea a destra
                    break;
                case "left":
                default:
                    xPos = 0; // Allinea a sinistra (default)
                    break;
            }

            // Disegna la linea
            layout.draw(g2d, xPos, yPos + (int) layout.getAscent());

            // Aggiorna la posizione verticale per la prossima linea
            yPos += textHeight;
        }

        g2d.dispose();
        return image;
    }

    @Override
    public void update() {
        // Aggiungi la logica di aggiornamento se necessario
    }

    @Override
    public void draw(Graphics g) {
        BufferedImage textImage = generateTextImage(text, dimension);
        g.drawImage(textImage, position.x.intValue(), position.y.intValue(), null);
    }
}