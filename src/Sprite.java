import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

public class Sprite {
    protected int x, y, width, height;
    protected Animation animation;
    protected BufferedImage staticImage; // Per immagini statiche
    protected int zIndex = 0; // Valore di default zIndex è 0

    //Costruttore default !!!PERICOLO!!!
    public  Sprite(GamePanel gamePanel){
        gamePanel.addSprite(this);
    }

    // Costruttore per sprite con animazione
    public Sprite(GamePanel gamePanel, int x, int y, int width, int height, Animation animation) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.animation = animation;
        this.staticImage = null; // Non ha immagine statica
        gamePanel.addSprite(this);
    }

    // Costruttore per sprite statico
    public Sprite(GamePanel gamePanel, int x, int y, int width, int height, BufferedImage staticImage) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.staticImage = staticImage;
        this.animation = null; // Non ha animazione
        gamePanel.addSprite(this);
    }

    // Costruttore per sprite senza grafica
    public Sprite(GamePanel gamePanel, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
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

    // Metodo di disegno che tiene conto dello zIndex
    public void draw(Graphics g) {
        if (animation != null) {
            g.drawImage(animation.getCurrentFrame(), x, y, width, height, null);
        } else if (staticImage != null) {
            g.drawImage(staticImage, x, y, width, height, null);
        }
    }

    // Getter per il zIndex
    public int getZIndex() {
        return zIndex;
    }

    // Setter per il zIndex
    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
    }
}
