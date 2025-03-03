package graphics;

import baseGameObjects.Area2D;
import baseGameObjects.Collision2D;
import baseGameObjects.Sprite;
import basics.GameWindow;
import basics.Input;
import basics.Vector2;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class  GamePanel extends JPanel {
    private GameWindow gameWindow;
    private boolean isFullscreen = false; // Stato attuale della finestra


    private List<Sprite> sprites = new CopyOnWriteArrayList<>();
    private List<Collision2D> collisions = new CopyOnWriteArrayList<>();
    private List<Area2D> areas = new CopyOnWriteArrayList<>();
    private Vector2 cameraPosition = Vector2.ZERO();

    public GamePanel(GameWindow gameWindow) {
        setPreferredSize(new Dimension(1600, 1200));
        setDoubleBuffered(false);
        setIgnoreRepaint(true);
        this.gameWindow = gameWindow;

    }

    public void addSprite(Sprite sprite) {
        sprites.add(sprite);
        if (sprite instanceof Collision2D) {
            collisions.add((Collision2D) sprite);
        }
        if (sprite instanceof Area2D) {
            areas.add((Area2D) sprite);
        }
    }

    public void removeSprite(Sprite sprite) {
        sprites.remove(sprite);
    }

    public List<Collision2D> getAllCollisions() {
        return collisions;
    }

    public List<Area2D> getAllAreas() {
        return areas;
    }

    private void toggleFullscreen() {
        isFullscreen = !isFullscreen; // Cambia stato
        System.out.println(isFullscreen);

        gameWindow.frame.dispose(); // Chiude temporaneamente la finestra
        gameWindow.frame.setUndecorated(isFullscreen); // Attiva/disattiva la modalità fullscreen
        gameWindow.frame.setExtendedState(isFullscreen ? JFrame.MAXIMIZED_BOTH : JFrame.NORMAL);

        if (!isFullscreen) {
            gameWindow.frame.setSize(800, 600); // Imposta una dimensione standard
        }

        gameWindow.frame.setVisible(true); // Rende la finestra visibile di nuovo
        requestFocusInWindow(); // Riporta il focus sugli input
    }

    public void update() {
        for (Sprite sprite : sprites) {
            sprite.update();
        }

        if (Input.isNewKeyPressed(122)){
            toggleFullscreen();
        }
        //System.out.println(sprites);
    }

    public void setCameraPosition(Vector2 position) {
        this.cameraPosition = position;
    }

    public Vector2 getCameraPosition() {
        return cameraPosition;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Trasla la visuale in base alla posizione della camera
        g2d.translate(-cameraPosition.x.intValue(), -cameraPosition.y.intValue());

        // Ordina e disegna gli sprite
        sprites.sort((sprite1, sprite2) -> Integer.compare(sprite1.getZIndex(), sprite2.getZIndex()));
        for (Sprite sprite : sprites) {
            sprite.draw(g2d);
        }

        g2d.dispose();
    }
}
