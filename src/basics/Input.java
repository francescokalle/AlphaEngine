package basics;

import gameObjects.Sprite;
import graphics.GamePanel;

import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
    // Set per tenere traccia dei tasti e dei caratteri premuti
    public static Set<Integer> pressedKeys = new HashSet<>();
    public static Set<Character> pressedChars = new HashSet<>();

    // Variabili per la gestione del mouse
    public static boolean mousePressed = false;  // Se il mouse è premuto
    public static Point mousePosition = new Point(0, 0);  // Posizione del mouse
    public static int mouseWheelRotation = 0;  // Per il movimento della rotellina
    public GamePanel gamePanel;

    // Costruttore che riceve il graphics.GamePanel per registrare il KeyListener e il MouseListener
    public Input(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        registerInputListeners(gamePanel);
    }

    // Registra i listener per tastiera e mouse
    private void registerInputListeners(GamePanel gamePanel) {
        gamePanel.addKeyListener(this);
        gamePanel.addMouseListener(this);
        gamePanel.addMouseMotionListener(this);
        gamePanel.addMouseWheelListener(this);
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();
    }

    // --- Gestione Tastiera ---

    // Verifica se una chiave con KeyCode specifico è premuta
    public static boolean isKeyPressed(int keyCode) {
        return pressedKeys.contains(keyCode);
    }

    // Verifica se una chiave con carattere specifico è premuta
    public static boolean isKeyPressed(char keyChar) {
        return pressedChars.contains(keyChar);
    }

    // Metodo chiamato quando una chiave viene premuta
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        char keyChar = e.getKeyChar();
        pressedKeys.add(keyCode);  // Aggiungi al set di tasti premuti tramite keyCode
        pressedChars.add(keyChar); // Aggiungi al set di tasti premuti tramite carattere
    }

    // Metodo chiamato quando una chiave viene rilasciata
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        char keyChar = e.getKeyChar();
        pressedKeys.remove(keyCode);  // Rimuovi dal set quando la chiave viene rilasciata
        pressedChars.remove(keyChar); // Rimuovi dal set quando la chiave viene rilasciata
    }

    // Metodo chiamato quando una chiave viene digitata
    @Override
    public void keyTyped(KeyEvent e) {
        // Questo metodo non è necessario per il nostro scopo, quindi non facciamo nulla
    }

    // --- Gestione Mouse ---

    // Verifica se il mouse è premuto
    public static boolean isMousePressed() {
        return mousePressed;
    }

    // Verifica se il mouse è sopra uno sprite
    public static boolean isMouseOver(Sprite sprite) {
        int x = (int) sprite.getPosition().x;
        int y = (int) sprite.getPosition().y;
        int width = sprite.getDimension().x.intValue();
        int height = sprite.getDimension().y.intValue();

        // Verifica se la posizione del mouse è all'interno delle dimensioni dello sprite
        return mousePosition.x >= x && mousePosition.x <= x + width &&
                mousePosition.y >= y && mousePosition.y <= y + height;
    }

    // Verifica se il mouse è stato cliccato su uno sprite
    public static boolean isMouseClickedOnSprite(Sprite sprite) {
        return isMousePressed() && isMouseOver(sprite);
    }

    // Gestisce l'evento di clic del mouse
    @Override
    public void mouseClicked(MouseEvent e) {
        // Non è necessario fare nulla qui
    }

    // Gestisce l'evento di premere un tasto del mouse
    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = true;  // Imposta lo stato del mouse come premuto
    }

    // Gestisce l'evento di rilascio di un tasto del mouse
    @Override
    public void mouseReleased(MouseEvent e) {
        mousePressed = false;  // Imposta lo stato del mouse come non premuto
    }

    // Gestisce l'evento di quando il mouse entra in una zona
    @Override
    public void mouseEntered(MouseEvent e) {
        // Qui non facciamo nulla, ma possiamo aggiungere altre logiche in futuro
    }

    // Gestisce l'evento di quando il mouse esce da una zona
    @Override
    public void mouseExited(MouseEvent e) {
        // Qui non facciamo nulla, ma possiamo aggiungere altre logiche in futuro
    }

    // Gestisce l'evento del movimento del mouse
    @Override
    public void mouseMoved(MouseEvent e) {
        mousePosition = e.getPoint();
    }

    // Gestisce l'evento di entrata in una zona di mouse (mouseDragged)
    @Override
    public void mouseDragged(MouseEvent e) {
        mousePosition = e.getPoint();
    }

    // Gestisce l'evento di scroll della rotellina del mouse
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        mouseWheelRotation = e.getWheelRotation();
    }

    // Restituisce la posizione del mouse
    public static Point getMousePosition() {
        return mousePosition;
    }

    // Restituisce la direzione dello scroll della rotellina
    public static int getMouseWheelRotation() {
        return mouseWheelRotation;
    }
}
