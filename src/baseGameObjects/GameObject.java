package baseGameObjects;

import basics.Vector2;
import graphics.Animation;
import graphics.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.gc;

public class GameObject {
    protected Vector2 dimension = Vector2.ZERO();
    protected Vector2 position = Vector2.ZERO();
    protected Vector2 relativePosition = Vector2.ZERO(); // Posizione relativa al genitore

    protected Animation animation;
    protected BufferedImage staticImage;
    protected int zIndex = 0;
    protected double rotationAngle = 0;
    protected GamePanel gamePanel;

    protected GameObject stickedTo = null;
    protected List<GameObject> sons = new ArrayList<>();
    protected GameObject parent;

    // COSTRUTTORI
    protected GameObject(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        gamePanel.addSprite(this);
    }

    protected GameObject(GamePanel gamePanel, Vector2 position, Vector2 dimension, Animation animation) {
        this(gamePanel);
        this.position = position;
        this.dimension = dimension;
        this.animation = animation;
    }

    protected GameObject(GamePanel gamePanel, Vector2 position, Vector2 dimension, BufferedImage staticImage) {
        this(gamePanel);
        this.position = position;
        this.dimension = dimension;
        this.staticImage = staticImage;
    }

    protected GameObject(GamePanel gamePanel, Vector2 position, Vector2 dimension) {
        this(gamePanel);
        this.position = position;
        this.dimension = dimension;
    }

    // METODO UPDATE RIVISTO
    public void update() {
        if (animation != null) animation.update();

        // Aggiorna posizione in base al genitore
        if (parent != null) {
            this.position = parent.getPosition().add(relativePosition);
        }

        // Aggiorna eventuali oggetti attaccati
        if (stickedTo != null) {
            this.position = stickedTo.getPosition();
        }
    }

    // GESTIONE GERARCHIA
    public void setParent(GameObject parent) {
        this.parent = parent;
        if (parent != null) {
            this.relativePosition = this.position.subtract(parent.getPosition());
        }
    }

    public void addSon(GameObject son) {
        sons.add(son);
        son.setParent(this);
    }

    public void removeSon(GameObject son) {
        sons.remove(son);
        son.removeParent();
    }

    public void removeParent() {
        if (parent != null) {
            this.position = parent.getPosition().add(relativePosition); // Mantiene posizione assoluta
            parent = null;
            relativePosition = Vector2.ZERO();
        }
    }

    // METODI PER POSIZIONE
    public Vector2 getAbsolutePosition() {
        return parent != null ? parent.getPosition().add(relativePosition) : position;
    }

    public void setRelativePosition(Vector2 relativePosition) {
        this.relativePosition = relativePosition;
    }

    public void setPosition(Vector2 position) {
        if (parent != null) {
            this.relativePosition = position.subtract(parent.getPosition());
        } else {
            this.position = position;
        }
    }

    // ALTRI METODI (DRAW, STICK/UNSTICK, DESTROY)
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(
                position.x.intValue() + dimension.x.intValue() / 2,
                position.y.intValue() + dimension.y.intValue() / 2
        );
        g2d.rotate(Math.toRadians(rotationAngle));

        if (animation != null) {
            g2d.drawImage(animation.getCurrentFrame(),
                    -dimension.x.intValue() / 2,
                    -dimension.y.intValue() / 2,
                    dimension.x.intValue(),
                    dimension.y.intValue(),
                    null
            );
        } else if (staticImage != null) {
            g2d.drawImage(staticImage,
                    -dimension.x.intValue() / 2,
                    -dimension.y.intValue() / 2,
                    dimension.x.intValue(),
                    dimension.y.intValue(),
                    null
            );
        }
        g2d.dispose();
    }

    public boolean stickTo(GameObject other) {
        if (other.stickedTo == null) {
            this.stickedTo = other;
            other.stickedTo = this;
            return true;
        }
        return false;
    }

    public boolean unstick() {
        if (stickedTo != null) {
            stickedTo.stickedTo = null;
            stickedTo = null;
            return true;
        }
        return false;
    }

    public void destroy(GamePanel gamePanel) {
        gamePanel.removeSprite(this);
        sons.forEach(son -> son.destroy(gamePanel));
        staticImage = null;
        animation = null;
        gc();
    }

    // GETTERS/SETTERS
    public Vector2 getPosition() { return position; }
    public Vector2 getDimension() { return dimension; }
    public int getZIndex() { return zIndex; }
    public void setZIndex(int zIndex) { this.zIndex = zIndex; }
    public void setRotation(double degrees) { rotationAngle = degrees; }
}