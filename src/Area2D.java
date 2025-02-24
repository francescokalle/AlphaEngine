import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Area2D extends Sprite {
    protected boolean debugMode = false;
    private List<Area2D> detectedAreas = new ArrayList<>();

    public Area2D(GamePanel gamePanel, int x, int y, int width, int height, BufferedImage staticImage) {
        super(gamePanel, x, y, width, height, staticImage);
        //System.out.println(staticImage);
    }

    public Area2D(GamePanel gamePanel, int x, int y, int width, int height, Animation animation) {
        super(gamePanel, x, y, width, height, animation);
    }

    public Area2D(GamePanel gamePanel, int x, int y, int width, int height) {
        super(gamePanel, x, y, width, height);
    }

    public void enableDebug(boolean debug) {
        this.debugMode = debug;
    }

    public List<Area2D> getDetectedAreas(List<Area2D> allAreas) {
        detectedAreas.clear();
        for (Area2D other : allAreas) {
            if (other != this && isColliding(other)) {
                detectedAreas.add(other);
            }
        }
        return detectedAreas;
    }

    protected boolean isColliding(Area2D other) {
        return this.x < other.x + other.width && this.x + this.width > other.x &&
                this.y < other.y + other.height && this.y + this.height > other.y;
    }

    @Override
    public void draw(Graphics g) {
        // Disegna sempre lo sprite, indipendentemente dal debug mode
        super.draw(g);

        // Se il debug mode è attivo, puoi disegnare anche l'area in evidenza
        if (debugMode) {
            g.setColor(new Color(255, 0, 255, 100)); // Viola trasparente per debug
            g.fillRect(x, y, width, height);
        }
    }
}
