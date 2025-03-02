package gameObjects;

import basics.Vector2;
import graphics.Animation;
import graphics.GamePanel;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Collision2D extends Area2D {
    private List<Collision2D> detectedCollisions = new ArrayList<>();

    public Collision2D(GamePanel gamePanel, Vector2 position, Vector2 dimension, BufferedImage staticImage) {
        super(gamePanel, position, dimension, staticImage);
    }
    public Collision2D(GamePanel gamePanel, Vector2 position, Vector2 dimension, Animation animation) {
        super(gamePanel, position, dimension, animation);
    }

    public Collision2D(GamePanel gamePanel, Vector2 position, Vector2 dimension) {
        super(gamePanel, position, dimension);
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
