package gameObjects;

import basics.Vector2;
import graphics.Animation;
import graphics.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Area2D extends Sprite {
    protected boolean debugMode = false;
    private List<Area2D> detectedAreas = new ArrayList<>();

    public Area2D(GamePanel gamePanel, Vector2 position, Vector2 dimension, BufferedImage staticImage) {
        super(gamePanel, position, dimension, staticImage);
        //System.out.println(staticImage);
    }

    public Area2D(GamePanel gamePanel, Vector2 position, Vector2 dimension, Animation animation) {
        super(gamePanel, position, dimension, animation);
    }

    public Area2D(GamePanel gamePanel, Vector2 position, Vector2 dimension) {
        super(gamePanel, position, dimension);
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
        return this.position.x.intValue() < other.position.x.intValue() + other.dimension.x.intValue() && this.position.x.intValue() + this.dimension.x.intValue() > other.position.x.intValue() &&
                this.position.y.intValue() < other.position.y.intValue() + other.dimension.y.intValue() && this.position.y.intValue() + this.dimension.y.intValue() > other.position.y.intValue();
    }

    @Override
    public void draw(Graphics g) {
        // Disegna sempre lo sprite, indipendentemente dal debug mode
        super.draw(g);

        // Se il debug mode è attivo, puoi disegnare anche l'area in evidenza
        if (debugMode) {
            g.setColor(new Color(255, 0, 255, 100)); // Viola trasparente per debug
            g.fillRect(((int) position.x.intValue()), ((int) position.y.intValue()), dimension.x.intValue(), dimension.y.intValue());
        }
    }
}
