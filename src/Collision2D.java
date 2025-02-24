import java.awt.Graphics;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

public class Collision2D extends Area2D {

    public Collision2D(GamePanel gamePanel, int x, int y, int width, int height, Sprite attachedSprite, Color color) {
        super(gamePanel, x, y, width, height, attachedSprite, color);
    }

    public Collision2D(GamePanel gamePanel, int x, int y, int width, int height, Color color) {
        super(gamePanel, x, y, width, height, color);
    }

    // Funzione che ritorna le collisioni con altre Collision2D
    public List<Collision2D> checkCollisions(List<Collision2D> allCollisions) {
        List<Collision2D> touchedCollisions = new ArrayList<>();
        for (Collision2D collision : allCollisions) {
            if (collision != this && isOverlapping(collision)) {
                touchedCollisions.add(collision); // Aggiungi la collisione alla lista
                handleCollision(collision); // Gestisce la separazione e la reazione alla collisione
            }
        }
        return touchedCollisions;
    }

    // Funzione che gestisce la separazione delle collisioni
    private void handleCollision(Collision2D other) {
        if (isOverlapping(other)) {
            // Calcola l'area di sovrapposizione
            int overlapX = Math.min(this.x + this.width, other.x + other.width) - Math.max(this.x, other.x);
            int overlapY = Math.min(this.y + this.height, other.y + other.height) - Math.max(this.y, other.y);

            // Calcola la direzione in cui separare
            int deltaX = 0, deltaY = 0;

            if (overlapX < overlapY) {
                // Separazione orizzontale
                if (this.x < other.x) {
                    deltaX = overlapX;  // Spostiamo questa Collision2D verso destra
                } else {
                    deltaX = -overlapX; // Spostiamo questa Collision2D verso sinistra
                }
            } else {
                // Separazione verticale
                if (this.y < other.y) {
                    deltaY = overlapY;  // Spostiamo questa Collision2D verso il basso
                } else {
                    deltaY = -overlapY; // Spostiamo questa Collision2D verso l'alto
                }
            }

            // Muovi questa collisione per separarla dalla collisione
            this.x += deltaX;
            this.y += deltaY;

            // Se l'area è attaccata a uno sprite, anche lo sprite si muove
            if (this.attachedSprite != null) {
                this.attachedSprite.setX(this.x + (this.attachedSprite.getWidth() - this.width) / 2);
                this.attachedSprite.setY(this.y + (this.attachedSprite.getHeight() - this.height) / 2);
            }
        }
    }

    // Funzione di debug per disegnare il rettangolo della collisione
    @Override
    public void debugDraw(Graphics g) {
        if (isDebugMode()) { // Usa il getter per il debug
            g.setColor(new Color(255, 255, 0, 128)); // Colore giallo semitrasparente per collisione
            g.fillRect(x, y, width, height);
            g.setColor(Color.YELLOW); // Bordo giallo
            g.drawRect(x, y, width, height);
        }
    }
}
