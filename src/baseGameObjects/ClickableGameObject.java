package baseGameObjects;

import basics.Vector2;
import graphics.GamePanel;
import basics.Input;

public abstract class ClickableGameObject extends GameObject {

    private boolean isHovered = false; // Indica se il mouse è sopra lo sprite
    private boolean isClicked = false; // Indica se lo sprite è stato cliccato
    private boolean wasPressed = false; // Indica se lo sprite era cliccato nel frame precedente
    private boolean isPressed = false;
    private boolean justReleased = false;

    private boolean mouseWasAlreadyPressedOutside = false;

    // Costruttore per uno sprite cliccabile
    public ClickableGameObject(GamePanel gamePanel, Vector2 position, Vector2 dimension) {
        super(gamePanel, position, dimension);
    }

    // Metodo di aggiornamento
    @Override
    public void update() {
        System.out.println(this.parent);
        // Ottieni la posizione della telecamera dal GamePanel
        Vector2 cameraPosition = gamePanel.getCameraPosition();

        // Verifica se il mouse è sopra lo sprite
        isHovered = Input.isMouseOver(this, cameraPosition);

        // Verifica se lo sprite è stato cliccato
        if (isHovered && Input.isMousePressedOnSprite(this, cameraPosition) && !mouseWasAlreadyPressedOutside) {
            isPressed = true;
        } else {
            isPressed = false;
        }

        // Calcola i valori degli stati secondari
        isClicked = !wasPressed && isPressed;
        justReleased = !isPressed && wasPressed;

        // Chiama i metodi appropriati in base allo stato
        if (isPressed) {

            if (isClicked){
                onClick();
                //System.out.println(wasPressed + " ;" + isPressed + " = " + isClicked);
            }
            onPressed(); // Se lo sprite è stato cliccato, chiama onClick()
        } else if (isHovered) {
            if (justReleased){
                justReleased();
            }
            onHover(); // Se il mouse è sopra lo sprite, chiama onHover()
        } else {
            if (justReleased){
                justReleased();
            }
            onIdle(); // Se non c'è interazione, chiama onIdle()
        }

        // Aggiorna lo stato precedente del clic e del pressed
        wasPressed = isPressed;
        mouseWasAlreadyPressedOutside = Input.isMousePressed() && !isPressed;

        //System.out.println(mouseWasAlreadyPressedOutside);
    }

    // Metodi astratti da implementare nelle classi figlie
    public abstract void onIdle(); // Chiamato quando non c'è interazione
    public abstract void onHover(); // Chiamato quando il mouse è sopra lo sprite
    public abstract void onClick(); // Chiamato quando lo sprite viene cliccato
    public abstract void onPressed(); // Chiamato quando lo sprite viene cliccato
    public abstract void justReleased();

    // Metodo per rimuovere lo sprite quando viene distrutto
    @Override
    public void destroy(GamePanel gamePanel) {
        super.destroy(gamePanel);
    }
}