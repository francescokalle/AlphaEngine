package basics;

import baseGameObjects.GameObject;
import graphics.GamePanel;

import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener, Runnable {
    public static Set<Integer> pressedKeys = new HashSet<>();
    public static Set<Character> pressedChars = new HashSet<>();
    private static Set<Integer> newlyPressedKeys = new HashSet<>();
    public static Set<Character> newlyPressedChars = new HashSet<>();

    public static boolean mousePressed = false;
    public static Point mousePosition = new Point(0, 0);
    private static int mouseWheelDelta = 0;

    private boolean running = true;
    private final int UPDATE_RATE = 16;
    private static GamePanel gamePanel;

    public Input(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        registerInputListeners(gamePanel);
    }

    private void registerInputListeners(GamePanel gamePanel) {
        gamePanel.addKeyListener(this);
        gamePanel.addMouseListener(this);
        gamePanel.addMouseMotionListener(this);
        gamePanel.addMouseWheelListener(this);
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();
    }

    public static boolean isKeyPressed(int keyCode) {
        return pressedKeys.contains(keyCode);
    }

    public static boolean isKeyPressed(char keyChar) {
        return pressedChars.contains(keyChar);
    }

    public static boolean isNewKeyPressed(int keyCode) {
        return newlyPressedKeys.contains(keyCode);
    }

    public static boolean isNewKeyPressed(char keyChar) {
        if (pressedChars.contains(keyChar) && !newlyPressedChars.contains(keyChar)) {
            newlyPressedChars.add(keyChar);
            return true;
        }
        return false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
        pressedChars.add(e.getKeyChar());
        if (pressedKeys.contains(e.getKeyCode()) && !newlyPressedKeys.contains(e.getKeyCode())) {
            newlyPressedKeys.add(e.getKeyCode());
        }
        if (pressedChars.contains(e.getKeyChar()) && !newlyPressedChars.contains(e.getKeyChar())) {
            newlyPressedChars.add(e.getKeyChar());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
        pressedChars.remove(e.getKeyChar());
        newlyPressedKeys.remove(e.getKeyCode());
        newlyPressedChars.remove(e.getKeyChar());
    }

    @Override public void keyTyped(KeyEvent e) {}

    public static boolean isMousePressed() {
        return mousePressed;
    }

    public static boolean isMouseOver(GameObject gameObject, Vector2 cameraOffset) {
        int x = gameObject.getPosition().x.intValue() - cameraOffset.x.intValue();
        int y = gameObject.getPosition().y.intValue() - cameraOffset.y.intValue();
        int width = gameObject.getDimension().x.intValue();
        int height = gameObject.getDimension().y.intValue();

        return mousePosition.x >= x && mousePosition.x <= x + width &&
                mousePosition.y >= y && mousePosition.y <= y + height;
    }

    public static boolean isMousePressedOnSprite(GameObject gameObject, Vector2 cameraOffset) {
        return mousePressed && isMouseOver(gameObject, cameraOffset);
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mousePressed(MouseEvent e) { mousePressed = true; }
    @Override public void mouseReleased(MouseEvent e) { mousePressed = false; }
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseMoved(MouseEvent e) { mousePosition = e.getPoint(); }
    @Override public void mouseDragged(MouseEvent e) { mousePosition = e.getPoint(); }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        mouseWheelDelta += e.getWheelRotation();
    }

    public static int getMouseWheelRotation() {
        int rotation = mouseWheelDelta;
        mouseWheelDelta = 0;
        return rotation;
    }

    public static Vector2 getMousePosition(Vector2 cameraOffset) {
        return new Vector2(mousePosition.x + cameraOffset.x.intValue(), mousePosition.y + cameraOffset.y.intValue());
    }

    private void update() {
        newlyPressedKeys.clear();
        newlyPressedChars.clear();
    }

    @Override
    public void run() {
        while (running) {
            update();
            try {
                Thread.sleep(UPDATE_RATE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        running = false;
    }
}