import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Area2D extends Sprite {
    protected boolean debugMode = false;
    private List<Area2D> detectedAreas = new ArrayList<>();

    public Area2D(GamePanel gamePanel, Vector2 position, int width, int height, BufferedImage staticImage) {
        super(gamePanel, position, width, height, staticImage);
        //System.out.println(staticImage);
    }

    public Area2D(GamePanel gamePanel, Vector2 position, int width, int height, Animation animation) {
        super(gamePanel, position, width, height, animation);
    }

    public Area2D(GamePanel gamePanel, Vector2 position, int width, int height) {
        super(gamePanel, position, width, height);
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
        return this.position.x < other.position.x + other.width && this.position.x + this.width > other.position.x &&
                this.position.y < other.position.y + other.height && this.position.y + this.height > other.position.y;
    }

    @Override
    public void draw(Graphics g) {
        // Disegna sempre lo sprite, indipendentemente dal debug mode
        super.draw(g);

        // Se il debug mode è attivo, puoi disegnare anche l'area in evidenza
        if (debugMode) {
            g.setColor(new Color(255, 0, 255, 100)); // Viola trasparente per debug
            g.fillRect(((int) position.x), ((int) position.y), width, height);
        }
    }
}
