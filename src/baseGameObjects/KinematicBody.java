package baseGameObjects;

import basics.Vector2;
import graphics.Animation;
import graphics.GamePanel;

import java.awt.image.BufferedImage;

public class KinematicBody extends Collision2D{
    protected int speed = 5;
    protected Vector2 direction = Vector2.ZERO();
    protected GamePanel gamePanel; // Riferimento a graphics.GamePanel

    // Costruttore per animazione
    public KinematicBody(GamePanel gamePanel, Vector2 position, Vector2 dimension, Animation animation) {
        super(gamePanel, position, dimension, animation);
        this.gamePanel = gamePanel; // Salvo il riferimento
    }

    // Costruttore per immagine statica
    public KinematicBody(GamePanel gamePanel, Vector2 position, Vector2 dimension, BufferedImage staticImage) {
        super(gamePanel, position, dimension, staticImage);
        this.gamePanel = gamePanel; // Salvo il riferimento
    }

    @Override
    public void update() {

        super.update();
    }

    // Metodo per verificare se il Player sta collidendo con un oggetto
    protected boolean isColliding(int nextX, int nextY, Collision2D obj) {
        return nextX < obj.getPosition().x.intValue() + obj.getDimension().x.intValue() &&
                nextX + dimension.x.intValue() > obj.getPosition().x.intValue() &&
                nextY < obj.getPosition().y.intValue() + obj.getDimension().y.intValue() &&
                nextY + dimension.y.intValue() > obj.getPosition().y.intValue();
    }

    protected void moveAndCollide() {
        Vector2 nextPositionX = new Vector2(position.x.intValue() + direction.x.intValue() * speed, position.y);
        Vector2 nextPositionY = new Vector2(position.x, position.y.intValue() + direction.y.intValue() * speed);

        // Controlla separatamente collisioni sugli assi X e Y
        if (!isBlocked(nextPositionX)) {
            position.x = position.x.intValue() + direction.x.intValue() * speed;
        }
        if (!isBlocked(nextPositionY)) {
            position.y = position.y.intValue() + direction.y.intValue() * speed;
        }
    }

    // Controlla se il movimento è bloccato
    protected boolean isBlocked(int nextX, int nextY) {
        for (Collision2D obj : gamePanel.getAllCollisions()) {
            if (obj != this && isColliding(nextX, nextY, obj)) {
                return true;
            }
        }
        return false;
    }
    protected boolean isBlocked(Vector2 nextPosition) {
        for (Collision2D obj : gamePanel.getAllCollisions()) {
            if (obj != this && isColliding((int) nextPosition.x, (int) nextPosition.y, obj)) {
                return true;
            }
        }
        return false;
    }
}
