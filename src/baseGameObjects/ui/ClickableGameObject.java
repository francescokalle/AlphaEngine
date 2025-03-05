package baseGameObjects.ui;

import baseGameObjects.GameObject;
import basics.Vector2;
import graphics.GamePanel;
import basics.Input;

public abstract class ClickableGameObject extends GameObject {

    private boolean isHovered = false; // Indica se il mouse è sopra lo sprite
    private boolean isClicked = false; // Indica se lo sprite è stato cliccato
    private boolean wasClicked = false; // Indica se lo sprite era cliccato nel frame precedente

    // Costruttore per uno sprite cliccabile
    public ClickableGameObject(GamePanel gamePanel, Vector2 position, Vector2 dimension) {
        super(gamePanel, position, dimension);
    }

    // Metodo di aggiornamento
    @Override
    public void update() {
        // Aggiorna lo stato precedente del clic
        wasClicked = isClicked;
        // Ottieni la posizione della telecamera dal GamePanel
        Vector2 cameraPosition = gamePanel.getCameraPosition();

        // Verifica se il mouse è sopra lo sprite
        isHovered = Input.isMouseOver(this, cameraPosition);

        // Verifica se lo sprite è stato cliccato
        if (isHovered && Input.isMouseClickedOnSprite(this, cameraPosition)) {
            isClicked = true;
        } else {
            isClicked = false;
        }

        // Chiama i metodi appropriati in base allo stato
        if (isClicked) {
            onClick(); // Se lo sprite è stato cliccato, chiama onClick()
        } else if (isHovered) {
            onHover(); // Se il mouse è sopra lo sprite, chiama onHover()
        } else {
            onIdle(); // Se non c'è interazione, chiama onIdle()
        }

    }

    // Metodi astratti da implementare nelle classi figlie
    public abstract void onIdle(); // Chiamato quando non c'è interazione
    public abstract void onHover(); // Chiamato quando il mouse è sopra lo sprite
    public abstract void onClick(); // Chiamato quando lo sprite viene cliccato

    // Metodo per verificare se lo sprite è stato appena cliccato (solo al primo frame)
    public boolean isJustClicked() {
        return isClicked && !wasClicked;
    }

    // Metodo per rimuovere lo sprite quando viene distrutto
    @Override
    public void destroy(GamePanel gamePanel) {
        super.destroy(gamePanel);
    }
}