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

public class GamePanel extends JPanel {
    private GameWindow gameWindow;
    private boolean isFullscreen = false;
    private List<GameObject> gameObjects = new CopyOnWriteArrayList<>();
    private List<Collision2D> collisions = new CopyOnWriteArrayList<>();
    private List<Area2D> areas = new CopyOnWriteArrayList<>();
    private Vector2 cameraPosition = Vector2.ZERO();
    private float cameraZoom = 1.0f;

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
        isFullscreen = !isFullscreen;
        gameWindow.frame.dispose();
        gameWindow.frame.setUndecorated(isFullscreen);
        gameWindow.frame.setExtendedState(isFullscreen ? JFrame.MAXIMIZED_BOTH : JFrame.NORMAL);

        if (!isFullscreen) {
            gameWindow.frame.setSize(800, 600);
        }

        gameWindow.frame.setVisible(true);
        requestFocusInWindow();
    }

    public void update() {
        for (GameObject gameObject : gameObjects) {
            gameObject.update();
        }

        if (Input.isNewKeyPressed(122)) { // Tasto F11
            toggleFullscreen();
        }
    }

    public void setCameraPosition(Vector2 position) {
        this.cameraPosition = position;
    }

    public Vector2 getCameraPosition() {
        return cameraPosition;
    }

    public void setCameraZoom(float zoom) {
        this.cameraZoom = zoom;
    }

    public float getCameraZoom() {
        return cameraZoom;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Calcola il centro dello schermo per il pivot
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // Trasformazioni in ordine corretto:
        // 1. Trasla al centro
        // 2. Applica lo zoom
        // 3. Trasla indietro
        // 4. Applica offset camera
        g2d.translate(centerX, centerY);
        g2d.scale(cameraZoom, cameraZoom);
        g2d.translate(-centerX, -centerY);
        g2d.translate(-cameraPosition.x.intValue(), -cameraPosition.y.intValue());

        // Ordina e disegna gli sprite
        gameObjects.sort((sprite1, sprite2) -> Integer.compare(sprite1.getZIndex(), sprite2.getZIndex()));
        for (GameObject gameObject : gameObjects) {
            gameObject.draw(g2d);
        }

        g2d.dispose();
    }
    public int getWidth() {
        return super.getWidth();
    }

    public int getHeight() {
        return super.getHeight();
    }
}