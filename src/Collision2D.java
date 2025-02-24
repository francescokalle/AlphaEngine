import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Collision2D extends Area2D {
    private List<Collision2D> detectedCollisions = new ArrayList<>();

    public Collision2D(GamePanel gamePanel, int x, int y, int width, int height, BufferedImage staticImage) {
        super(gamePanel, x, y, width, height, staticImage);
    }
    public Collision2D(GamePanel gamePanel, int x, int y, int width, int height, Animation animation) {
        super(gamePanel, x, y, width, height, animation);
    }

    public Collision2D(GamePanel gamePanel, int x, int y, int width, int height) {
        super(gamePanel, x, y, width, height);
    }

    public void enableDebug(boolean debug) {
        super.debugMode = debug;
    }

    public List<Collision2D> getCollisions(List<Collision2D> allCollisions) {
        detectedCollisions.clear();
        for (Collision2D other : allCollisions) {
            if (other != this && isColliding(other)) {
                detectedCollisions.add(other);
            }
        }
        return detectedCollisions;
    }
}
