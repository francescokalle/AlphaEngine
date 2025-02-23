import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class Player extends Sprite implements KeyListener {
    private int speed = 5;
    private boolean movingLeft, movingRight, movingUp, movingDown;

    // Costruttore per animazione
    public Player(GamePanel gamePanel, int x, int y, int width, int height, Animation animation) {
        super(gamePanel, x, y, width, height, animation);
        registerKeyListener(gamePanel);
    }

    // Costruttore per immagine statica
    public Player(GamePanel gamePanel, int x, int y, int width, int height, BufferedImage staticImage) {
        super(gamePanel, x, y, width, height, staticImage);
        registerKeyListener(gamePanel);
    }

    // Registrazione automatica del KeyListener
    public void registerKeyListener(GamePanel gamePanel) {
        gamePanel.addKeyListener(this);  // Aggiunge il Player come KeyListener
        gamePanel.setFocusable(true);  // Assicura che GamePanel riceva eventi da tastiera
        gamePanel.requestFocusInWindow();  // Garantisce che il GamePanel abbia il focus
    }


    @Override
    public void update() {
        if (movingLeft) {
            x -= speed;
        }
        if (movingRight) {
            x += speed;
        }
        if (movingUp) {
            y -= speed;
        }
        if (movingDown) {
            y += speed;
        }
        //System.out.println(x+"; "+y);
        super.update(); // Se c'è un'animazione, la aggiorna
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_A:
                movingLeft = true;
                break;
            case KeyEvent.VK_D:
                movingRight = true;
                break;
            case KeyEvent.VK_W:
                movingUp = true;
                break;
            case KeyEvent.VK_S:
                movingDown = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_A:
                movingLeft = false;
                break;
            case KeyEvent.VK_D:
                movingRight = false;
                break;
            case KeyEvent.VK_W:
                movingUp = false;
                break;
            case KeyEvent.VK_S:
                movingDown = false;
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Non serve gestire questo evento
    }
}
