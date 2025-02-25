import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.List;

public class Player extends Collision2D implements KeyListener {
    private int speed = 2;
    private Vector2 movment = new Vector2().ZERO();
    private GamePanel gamePanel; // Riferimento a GamePanel

    // Costruttore per animazione
    public Player(GamePanel gamePanel, Vector2 position, int width, int height, Animation animation) {
        super(gamePanel, position, width, height, animation);
        this.gamePanel = gamePanel; // Salvo il riferimento
        registerKeyListener(gamePanel);
    }

    // Costruttore per immagine statica
    public Player(GamePanel gamePanel, Vector2 position, int width, int height, BufferedImage staticImage) {
        super(gamePanel, position, width, height, staticImage);
        this.gamePanel = gamePanel; // Salvo il riferimento
        registerKeyListener(gamePanel);
    }

    // Registrazione automatica del KeyListener
    public void registerKeyListener(GamePanel gamePanel) {
        gamePanel.addKeyListener(this);
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();
        System.out.println("diocan");
    }

    @Override
    public void update() {
        if(Input.isKeyPressed('w')){
            position.y += 1;
        }
        position.subtract(movment.multiply(new Vector2(speed, speed)));

        super.update();
    }

    // Metodo per verificare se il Player sta collidendo con un oggetto
    private boolean isColliding(int nextX, int nextY, Collision2D obj) {
        return nextX < obj.getPosition().x + obj.getWidth() &&
                nextX + width > obj.getPosition().x &&
                nextY < obj.getPosition().y + obj.getHeight() &&
                nextY + height > obj.getPosition().y;
    }

    // Controlla se il movimento è bloccato
    private boolean isBlocked(int nextX, int nextY) {
        for (Collision2D obj : gamePanel.getAllCollisions()) {
            if (obj != this && isColliding(nextX, nextY, obj)) {
                return true;
            }
        }
        return false;
    }
}
