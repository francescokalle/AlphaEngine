import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TextSprite extends Sprite implements TextRenderer {
    private String text;
    private File fontFile;
    private Font font;
    private String alignment;
    private Color textColor;
    private int scalingFactor = 1;  // Valore di default per il scaling
    private int fontSize;
    private int originalX;

    // Costruttore che inizializza il testo, il font e il colore
    public TextSprite(GamePanel gamePanel, int x, int y, String text, String alignment, File fontFile, Color color, int fontSize) throws IOException, FontFormatException {

        super(gamePanel, x, y, 0, 0);  // Passiamo width e height come 0, lo calcoleremo dopo
        this.fontFile = fontFile;
        this.font = Font.createFont(Font.TRUETYPE_FONT , fontFile);
        this.text = text;
        this.textColor = color != null ? color : Color.BLACK;  // Usa il colore passato, se presente
        this.alignment = alignment;  // Default alignment
        this.fontSize = fontSize;
        this.originalX = x;

        System.out.println(text);

        // Gestisci il font e il possibile scaling
        if (fontFile.getName().contains(".pixelated.")) {
            extractScalingFactorFromFont(fontFile.getName());
            System.out.printf("Font pixelato trovato con scaling factor: "+scalingFactor);
        }

        // Calcola automaticamente le dimensioni
        Dimension textDimensions = calculateTextDimensions(text);
        this.width = textDimensions.width;
        this.height = textDimensions.height;

        // Usa il contesto grafico per generare l'immagine del testo
        super.staticImage = generateTextImage(text, width, height);
    }

    // Estrai il scaling factor dal nome del file del font
    private void extractScalingFactorFromFont(String fontName) {
        try {
            // Estrai la parte che contiene il scaling factor dal nome del file (esempio: "fontname.pixelated.2.ttf")
            String[] parts = fontName.split("\\.");
            if (parts.length >= 3 && parts[1].equals("pixelated")) {
                // Ottieni il fattore di scaling (la parte dopo ".pixelated.")
                scalingFactor = Integer.parseInt(parts[2]) * fontSize;
            }
        } catch (Exception e) {
            e.printStackTrace();
            scalingFactor = fontSize;  // Imposta a 1 se non è possibile analizzare il file
            System.out.println("Impossibile estrarre il scaling factor, valore di default impostato a 1.");
        }
    }

    // Calcola dinamicamente la larghezza e l'altezza del testo
    private Dimension calculateTextDimensions(String text) {
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // Imposta il font con il fattore di scaling
        Font scaledFont = font.deriveFont((float) font.getSize() * scalingFactor);
        g2d.setFont(scaledFont);

        // Usa il contesto di rendering per creare TextLayout
        TextLayout layout = new TextLayout(text, scaledFont, g2d.getFontRenderContext());

        // Calcola e restituisce la larghezza e l'altezza del testo
        int textWidth = (int) layout.getBounds().getWidth();
        int textHeight = (int) layout.getBounds().getHeight();
        g2d.dispose();

        return new Dimension(textWidth, textHeight);
    }

    // Metodo per generare l'immagine del testo
    @Override
    public BufferedImage generateTextImage(String text, int width, int height) {
        // Crea un'immagine vuota
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // Disabilita l'antialiasing per mantenere l'aspetto pixelato
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        // Imposta il font con il fattore di scaling
        Font scaledFont = font.deriveFont((float) font.getSize() * scalingFactor);
        g2d.setFont(scaledFont);
        g2d.setColor(textColor);

        // Usa il contesto di rendering per creare TextLayout
        TextLayout layout = new TextLayout(text, scaledFont, g2d.getFontRenderContext());

        // Calcola la posizione e l'allineamento del testo
        int textWidth = (int) layout.getBounds().getWidth();
        int textHeight = (int) layout.getBounds().getHeight();

        int xPos = 0;
        int yPos = (height - textHeight) / 2 + (int) layout.getAscent();  // Centra verticalmente il testo

        // Allineamento orizzontale
        switch (alignment) {
            case "center":
                x = this.originalX - textWidth / 2;
                break;
            case "left":
            default:
                xPos = 0;
                break;
        }

        // Disegna il testo
        layout.draw(g2d, xPos, yPos);
        g2d.dispose();

        return image;
    }

    // Imposta il font personalizzato
    @Override
    public void setCustomFont(String fontName, int fontSize) {
        this.font = new Font(fontName, Font.PLAIN, fontSize);
    }

    // Imposta l'allineamento del testo
    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    // Imposta il colore del testo
    public void setTextColor(Color color) {
        this.textColor = color;
    }

    @Override
    public void update() {
        // Aggiungi la logica di aggiornamento se necessario
    }

    @Override
    public void draw(Graphics g) {
        BufferedImage textImage = generateTextImage(text, width, height);
        g.drawImage(textImage, x, y, null);
    }
}
