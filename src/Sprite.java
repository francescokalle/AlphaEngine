import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.System.gc;

public class Sprite {
    protected int width, height;
    protected Vector2 position = new Vector2(0, 0);
    protected Animation animation;
    protected BufferedImage staticImage; // Per immagini statiche
    protected int zIndex = 0; // Valore di default zIndex è 0
    protected double rotationAngle = 0; // Angolo di rotazione (in gradi)
    protected Sprite stickedTo = null;

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

    public boolean stickTo(Sprite other){
        if(other.stickedTo == null){
            this.stickedTo = other;
            other.stickedTo = this;

            this.position = stickedTo.position;
            stickedTo.position = this.position;
            return true;
        } else {
            return false;
        }
    }

    public boolean unstick(){
        if (this.stickedTo != null){
            this.position = this.position.value();
            stickedTo.position = stickedTo.position.value();

            stickedTo.stickedTo = null;
            this.stickedTo = null;

            return true;
        } else {
            return false;
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

    public void destroy(GamePanel gamePanel) {
        // Rimuovi lo sprite dal GamePanel, se il GamePanel ha una lista di sprite
        if (gamePanel != null) {
            gamePanel.removeSprite(this); // Supponiamo che GamePanel abbia un metodo removeSprite
        }

        // Impostiamo gli altri riferimenti relativi allo sprite a null
        this.stickedTo = null;

        // Se il sprite è un'immagine, possiamo anche impostarla su null per risparmiare memoria (se appropriato)
        this.staticImage = null;
        this.animation = null;

        // Impostiamo la posizione e altre variabili a valori neutri, se necessario
        this.position = null;  // Set null, or reset to default Vector2(0,0)

        // Eventualmente possiamo anche rimuovere altri riferimenti che potrebbero esserci per lo sprite (ad esempio, se lo sprite è legato a altri oggetti o eventi)

        gc();
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

    public int getZIndex() {
        return zIndex;
    }

    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
    }
}