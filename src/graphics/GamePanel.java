package graphics;

import baseGameObjects.Area2D;
import baseGameObjects.Collision2D;
import baseGameObjects.GameObject;
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


    private List<GameObject> gameObjects = new CopyOnWriteArrayList<>();
    private List<Collision2D> collisions = new CopyOnWriteArrayList<>();
    private List<Area2D> areas = new CopyOnWriteArrayList<>();
    private Vector2 cameraPosition = Vector2.ZERO();

    public GamePanel(GameWindow gameWindow) {
        setPreferredSize(new Dimension(1600, 1200));
        setDoubleBuffered(false);
        setIgnoreRepaint(true);
        this.gameWindow = gameWindow;

    }

    public void addSprite(GameObject gameObject) {
        gameObjects.add(gameObject);
        if (gameObject instanceof Collision2D) {
            collisions.add((Collision2D) gameObject);
        }
        if (gameObject instanceof Area2D) {
            areas.add((Area2D) gameObject);
        }
    }

    public void removeSprite(GameObject gameObject) {
        gameObjects.remove(gameObject);
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
        for (GameObject gameObject : gameObjects) {
            gameObject.update();
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
        gameObjects.sort((sprite1, sprite2) -> Integer.compare(sprite1.getZIndex(), sprite2.getZIndex()));
        for (GameObject gameObject : gameObjects) {
            gameObject.draw(g2d);
        }

        g2d.dispose();
    }
}
