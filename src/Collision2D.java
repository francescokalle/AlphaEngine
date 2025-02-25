import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Collision2D extends Area2D {
    private List<Collision2D> detectedCollisions = new ArrayList<>();

    public Collision2D(GamePanel gamePanel, Vector2 position, int width, int height, BufferedImage staticImage) {
        super(gamePanel, position, width, height, staticImage);
    }
    public Collision2D(GamePanel gamePanel, Vector2 position, int width, int height, Animation animation) {
        super(gamePanel, position, width, height, animation);
    }

    public Collision2D(GamePanel gamePanel, Vector2 position, int width, int height) {
        super(gamePanel, position, width, height);
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
