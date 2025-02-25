import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite {
    protected int width, height;
    protected Vector2 position = new Vector2(0, 0);
    protected Animation animation;
    protected BufferedImage staticImage; // Per immagini statiche
    protected int zIndex = 0; // Valore di default zIndex è 0
    protected double rotationAngle = 0; // Angolo di rotazione (in gradi)

    // Costruttore default !!!PERICOLO!!!
    public Sprite(GamePanel gamePanel) {
        gamePanel.addSprite(this);
    }

    // Costruttore per sprite con animazione
    public Sprite(GamePanel gamePanel, Vector2 position, int width, int height, Animation animation) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.animation = animation;
        this.staticImage = null; // Non ha immagine statica
        gamePanel.addSprite(this);
    }

    // Costruttore per sprite statico
    public Sprite(GamePanel gamePanel, Vector2 position, int width, int height, BufferedImage staticImage) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.staticImage = staticImage;
        this.animation = null; // Non ha animazione
        gamePanel.addSprite(this);
    }

    // Costruttore per sprite senza grafica
    public Sprite(GamePanel gamePanel, Vector2 position, int width, int height) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.staticImage = null;
        this.animation = null; // Non ha animazione
        gamePanel.addSprite(this);
    }

    // Metodo di aggiornamento
    public void update() {
        if (animation != null) {
            animation.update();
        }
    }

    // Metodo di disegno che tiene conto dello zIndex e della rotazione
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();  // Crea una copia del contesto grafico

        // Traslazione: porta l'origine al centro dell'oggetto (pivot)
        g2d.translate(position.x + (float) width / 2, position.y + (float)height / 2);

        // Ruota l'oggetto (l'angolo di rotazione è in gradi, quindi lo convertiamo in radianti)
        g2d.rotate(Math.toRadians(rotationAngle));

        // Disegna l'immagine centrata (con la rotazione applicata)
        if (animation != null) {
            g2d.drawImage(animation.getCurrentFrame(), -width / 2, -height / 2, width, height, null);
        } else if (staticImage != null) {
            g2d.drawImage(staticImage, -width / 2, -height / 2, width, height, null);
        }

        // Non è necessario chiamare restore(), perché quando il metodo esce dallo scope, lo stato grafico verrà ripristinato automaticamente.
        g2d.dispose();  // Chiude la copia del contesto grafico
    }


    public Vector2 getPosition() {
        return  position;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}