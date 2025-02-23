import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Background extends Sprite{
    int width;
    int height;
    int default_zIndex = -100;
    // Costruttore per sprite con animazione
    public Background(GamePanel gamePanel, Animation animation) {
        super(gamePanel);
        //GamePanel gamePanel, int x, int y, int width, int height, Animation animation
        super.x = 0;
        super.y = 0;
        super.width = gamePanel.getWidth();
        super.height = gamePanel.getHeight();
        super.animation = animation;
        super.zIndex = default_zIndex;
    }

    // Costruttore per sprite statico
    public Background(GamePanel gamePanel, BufferedImage staticImage) {
        super(gamePanel);
        super.x = 0;
        super.y = 0;
        super.width = gamePanel.getWidth();
        super.height = gamePanel.getHeight();
        super.staticImage = staticImage;
        super.zIndex = default_zIndex;
    }

    // Costruttore per sprite senza grafica
    public Background(GamePanel gamePanel) {
        super(gamePanel);
        super.x = 0;
        super.y = 0;
        super.width = gamePanel.getWidth();
        super.height = gamePanel.getHeight();
        super.zIndex = default_zIndex;
    }
}
