import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

public class Input implements KeyListener {
    // Set per tenere traccia dei tasti premuti
    private static Set<Integer> pressedKeys = new HashSet<>();
    private static Set<Character> pressedChars = new HashSet<>();
    private GamePanel gamePanel;

    // Costruttore che riceve il GamePanel per registrare il KeyListener
    public Input(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        registerKeyListener(gamePanel);
    }

    // Registra il KeyListener
    private void registerKeyListener(GamePanel gamePanel) {
        gamePanel.addKeyListener(this);
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();
    }

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

        pressedKeys.add(keyCode); // Aggiungi al set di tasti premuti tramite keyCode
        pressedChars.add(keyChar); // Aggiungi al set di tasti premuti tramite carattere
    }

    // Metodo chiamato quando una chiave viene rilasciata
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        char keyChar = e.getKeyChar();

        pressedKeys.remove(keyCode); // Rimuovi dal set quando la chiave viene rilasciata
        pressedChars.remove(keyChar); // Rimuovi dal set quando la chiave viene rilasciata
    }

    // Metodo chiamato quando una chiave viene digitata
    @Override
    public void keyTyped(KeyEvent e) {
        // Questo metodo non è necessario per il nostro scopo, quindi non facciamo nulla
    }
}
