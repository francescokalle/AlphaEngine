package baseGameObjects.ui;

import baseGameObjects.GameObject;
import basics.Vector2;
import graphics.GamePanel;

import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TextGameObject extends GameObject {

    private String text;
    private Font font;
    private String alignment;
    private Color textColor;
    private int fontSize;
    private int scalingFactor = 1;
    private Vector2 maxSize;

    public TextGameObject(GamePanel gamePanel, Vector2 position, String text, String alignment, File fontFile, Color color, int fontSize, Vector2 maxSize) throws IOException, FontFormatException {
        super(gamePanel, position, Vector2.ZERO());
        this.text = Objects.requireNonNull(text, "Text cannot be null");
        this.font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(fontFile, "Font file cannot be null"));
        this.alignment = Objects.requireNonNull(alignment, "Alignment cannot be null");
        this.textColor = color != null ? color : Color.BLACK;
        this.fontSize = fontSize;
        this.maxSize = maxSize;

        extractScalingFactorFromFont(fontFile.getName());

        adjustTextToFit();

        super.staticImage = generateTextImage();
    }

    private void extractScalingFactorFromFont(String fontName) {
        try {
            String[] parts = fontName.split("\\.");
            if (parts.length >= 3 && parts[1].equals("pixelated")) {
                scalingFactor = Integer.parseInt(parts[2]);
            }
        } catch (Exception e) {
            System.err.println("Impossibile estrarre lo scaling factor dal nome del font. Verrà utilizzato il valore di default (1).");
            scalingFactor = 1;
        }
    }

    private void adjustTextToFit() {
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        Font scaledFont = font.deriveFont(Font.PLAIN, fontSize * scalingFactor);
        g2d.setFont(scaledFont);

        List<String> lines = wrapText(g2d, text, maxSize.x.intValue());

        int totalHeight = calculateTotalHeight(g2d, scaledFont, lines);

        while (totalHeight > maxSize.y.intValue() && fontSize > 5) {
            fontSize--;
            scaledFont = font.deriveFont(Font.PLAIN, fontSize * scalingFactor);
            g2d.setFont(scaledFont);
            lines = wrapText(g2d, text, maxSize.x.intValue());
            totalHeight = calculateTotalHeight(g2d, scaledFont, lines);
        }

        int maxWidthUsed = 0;
        for (String line : lines) {
            int width = g2d.getFontMetrics().stringWidth(line);
            if (width > maxWidthUsed) {
                maxWidthUsed = width;
            }
        }

        this.dimension.x = Math.min(maxSize.x.intValue(), maxWidthUsed);
        this.dimension.y = totalHeight;

        g2d.dispose();
    }

    private List<String> wrapText(Graphics2D g2d, String text, int maxWidth) {
        List<String> lines = new ArrayList<>();
        FontMetrics metrics = g2d.getFontMetrics();
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            String testLine = currentLine.length() == 0 ? word : currentLine + " " + word;
            int textWidth = metrics.stringWidth(testLine);

            if (textWidth > maxWidth && currentLine.length() > 0) {
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(word);
            } else if (textWidth > maxWidth) {
                // Spezza la parola troppo lunga
                StringBuilder brokenWord = new StringBuilder();
                for (char c : word.toCharArray()) {
                    if (metrics.stringWidth(brokenWord.toString() + c) > maxWidth) {
                        lines.add(brokenWord.toString());
                        brokenWord = new StringBuilder();
                    }
                    brokenWord.append(c);
                }
                if (brokenWord.length() > 0) {
                    lines.add(brokenWord.toString());
                }
                currentLine = new StringBuilder();
            } else {
                currentLine = new StringBuilder(testLine);
            }
        }

        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }

        return lines;
    }

    private int calculateTotalHeight(Graphics2D g2d, Font font, List<String> lines) {
        FontMetrics metrics = g2d.getFontMetrics(font);
        return lines.size() * metrics.getHeight();
    }

    private BufferedImage generateTextImage() {
        BufferedImage image = new BufferedImage(dimension.x.intValue(), dimension.y.intValue(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        Font scaledFont = font.deriveFont(Font.PLAIN, fontSize * scalingFactor);
        g2d.setFont(scaledFont);
        g2d.setColor(textColor);

        List<String> lines = wrapText(g2d, text, maxSize.x.intValue());

        int yPos = 0;
        for (String line : lines) {
            TextLayout layout = new TextLayout(line, scaledFont, g2d.getFontRenderContext());

            int textWidth = (int) layout.getBounds().getWidth();
            int xPos = 0;
            switch (alignment) {
                case "center":
                    xPos = (dimension.x.intValue() - textWidth) / 2;
                    break;
                case "right":
                    xPos = dimension.x.intValue() - textWidth;
                    break;
                case "left":
                default:
                    xPos = 0;
                    break;
            }

            layout.draw(g2d, xPos, yPos + (int) layout.getAscent());
            yPos += layout.getBounds().getHeight();
        }

        g2d.dispose();
        return image;
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void draw(Graphics g) {
        BufferedImage textImage = generateTextImage();
        g.drawImage(textImage, position.x.intValue(), position.y.intValue(), null);
    }
}