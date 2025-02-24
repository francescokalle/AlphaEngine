import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Area2D extends Sprite {
    protected int x, y, width, height;
    protected boolean debugMode = false; // Abilitazione del debug
    protected Sprite attachedSprite = null;  // Aggiunta del riferimento allo Sprite attaccato (opzionale)

    public Area2D(GamePanel gamePanel, int x, int y, int width, int height, Sprite attechedSprite, Color color){
        super(gamePanel, x, y, width, height);

        this.width = width;
        this.height = height;

        attachedSprite = attechedSprite;

        BufferedImage box = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = box.getGraphics();
        g.setColor(Color.RED);
        g.fillRect(0, 0, width, height);
        g.dispose();

        super.staticImage = box;
    }

    public Area2D(GamePanel gamePanel, int x, int y, int width, int height, Color color){
        super(gamePanel, x, y, width, height);

        BufferedImage box = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = box.getGraphics();
        g.setColor(Color.RED);
        g.fillRect(0, 0, width, height);
        g.dispose();

        super.staticImage = box;
    }

    @Override
    public void update() {
        updatePosition();
        super.update();
    }

    // Funzione che "attacca" l'Area2D ad uno Sprite, sincronizzando il centro
    public void attachToSprite(Sprite sprite) {
        this.attachedSprite = sprite;
        updatePosition();  // Sincronizza la posizione dell'Area2D con il centro dello Sprite
    }

    // Sincronizza la posizione dell'Area2D con il centro dello Sprite
    public void updatePosition() {
        if (attachedSprite != null) {
            // Calcola la posizione in modo che il centro di Area2D sia allineato con il centro dello Sprite
            super.x = attachedSprite.getX() + attachedSprite.getWidth()/2 - this.width/2;
            super.y = attachedSprite.getY() + attachedSprite.getHeight()/2 - this.height/2;
        }
    }

    // Rileva se un altro Area2D entra nell'area
    public List<Area2D> checkCollision(List<Area2D> allAreas) {
        List<Area2D> collidedAreas = new ArrayList<>();
        for (Area2D area : allAreas) {
            if (area != this && isOverlapping(area)) {
                collidedAreas.add(area); // Se c'è sovrapposizione, aggiungi l'area
            }
        }
        return collidedAreas;
    }

    // Verifica se due aree si sovrappongono
    protected boolean isOverlapping(Area2D other) {
        return !(other.x > x + width || other.x + other.width < x || other.y > y + height || other.y + other.height < y);
    }

    // Funzione di debug per disegnare il rettangolo dell'area
    public void debugDraw(Graphics g) {
        if (debugMode) {
            g.setColor(new Color(128, 0, 128, 128)); // Colore viola semitrasparente
            g.fillRect(x, y, width, height);
            g.setColor(Color.MAGENTA); // Bordo viola
            g.drawRect(x, y, width, height);
        }
    }

    // Getter e Setter per abilitare/disabilitare il debug
    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public boolean isDebugMode() {
        return debugMode;
    }
}
