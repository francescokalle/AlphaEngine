import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.List;

public class Player extends Collision2D implements KeyListener {
    private int speed = 5;
    private boolean movingLeft, movingRight, movingUp, movingDown;
    private GamePanel gamePanel; // Riferimento a GamePanel

    // Costruttore per animazione
    public Player(GamePanel gamePanel, int x, int y, int width, int height, Animation animation) {
        super(gamePanel, x, y, width, height, animation);
        this.gamePanel = gamePanel; // Salvo il riferimento
        registerKeyListener(gamePanel);
    }

    // Costruttore per immagine statica
    public Player(GamePanel gamePanel, int x, int y, int width, int height, BufferedImage staticImage) {
        super(gamePanel, x, y, width, height, staticImage);
        this.gamePanel = gamePanel; // Salvo il riferimento
        registerKeyListener(gamePanel);
    }

    // Registrazione automatica del KeyListener
    public void registerKeyListener(GamePanel gamePanel) {
        gamePanel.addKeyListener(this);
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();
    }

    @Override
    public void update() {
        int nextX = x;
        int nextY = y;

        if (movingLeft) nextX -= speed;
        if (movingRight) nextX += speed;
        if (movingUp) nextY -= speed;
        if (movingDown) nextY += speed;

        // Ottieni tutte le collisioni nel gioco
        List<Collision2D> collisions = gamePanel.getAllCollisions();

        boolean blockedLeft = false;
        boolean blockedRight = false;
        boolean blockedUp = false;
        boolean blockedDown = false;

        // Controllo collisioni per ogni oggetto nella lista
        for (Collision2D obj : collisions) {
            if (obj == this) continue; // Ignora se stesso

            if (isColliding(nextX, y, obj)) {
                if (nextX < x) {  // Collisione a SINISTRA
                    blockedLeft = true;
                } else if (nextX > x) {  // Collisione a DESTRA
                    blockedRight = true;
                }
            }

            if (isColliding(x, nextY, obj)) {
                if (nextY < y) {  // Collisione in ALTO
                    blockedUp = true;
                } else if (nextY > y) {  // Collisione in BASSO
                    blockedDown = true;
                }
            }
        }

        // Blocca il movimento solo se c'è ancora un ostacolo
        if (blockedLeft) movingLeft = false;
        if (blockedRight) movingRight = false;
        if (blockedUp) movingUp = false;
        if (blockedDown) movingDown = false;

        // Se il tasto è premuto e non c'è più un blocco, permetti di muoversi
        if (!blockedLeft && movingLeft) x -= speed;
        if (!blockedRight && movingRight) x += speed;
        if (!blockedUp && movingUp) y -= speed;
        if (!blockedDown && movingDown) y += speed;

        super.update();
    }

    // Metodo per verificare se il Player sta collidendo con un oggetto
    private boolean isColliding(int nextX, int nextY, Collision2D obj) {
        return nextX < obj.getX() + obj.getWidth() &&
                nextX + width > obj.getX() &&
                nextY < obj.getY() + obj.getHeight() &&
                nextY + height > obj.getY();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_A:
                if (!isBlocked(x - speed, y)) movingLeft = true;
                break;
            case KeyEvent.VK_D:
                if (!isBlocked(x + speed, y)) movingRight = true;
                break;
            case KeyEvent.VK_W:
                if (!isBlocked(x, y - speed)) movingUp = true;
                break;
            case KeyEvent.VK_S:
                if (!isBlocked(x, y + speed)) movingDown = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_A: movingLeft = false; break;
            case KeyEvent.VK_D: movingRight = false; break;
            case KeyEvent.VK_W: movingUp = false; break;
            case KeyEvent.VK_S: movingDown = false; break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

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
