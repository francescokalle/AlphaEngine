package gameObjects;

import basics.Vector2;
import graphics.Animation;
import graphics.GamePanel;

import java.awt.image.BufferedImage;

public class Background extends Sprite{
    int width;
    int height;
    int default_zIndex = -100;
    // Costruttore per sprite con animazione
    public Background(GamePanel gamePanel, Animation animation) {
        super(gamePanel);
        //graphics.GamePanel gamePanel, int x, int y, int width, int height, graphics.Animation animation
        super.position = Vector2.ZERO();
        super.dimension = new Vector2(gamePanel.getWidth(), gamePanel.getHeight());
        super.animation = animation;
        super.zIndex = default_zIndex;
    }

    // Costruttore per sprite statico
    public Background(GamePanel gamePanel, BufferedImage staticImage) {
        super(gamePanel);
        super.position = Vector2.ZERO();
        super.dimension = new Vector2(gamePanel.getWidth(), gamePanel.getHeight());
        super.staticImage = staticImage;
        super.zIndex = default_zIndex;
    }

    // Costruttore per sprite senza grafica
    public Background(GamePanel gamePanel) {
        super(gamePanel);
        super.position = Vector2.ZERO();
        super.dimension = new Vector2(gamePanel.getWidth(), gamePanel.getHeight());
        super.zIndex = default_zIndex;
    }
}
