package baseGameObjects;

import basics.Vector2;
import graphics.GamePanel;

public class Camera2D extends GameObject {
    private boolean isActive = true;
    private Vector2 offset = Vector2.ZERO(); // Offset aggiuntivo per regolazione fine

    public Camera2D(GamePanel gamePanel, Vector2 position) {
        super(gamePanel, position, new Vector2(0, 0));
    }

    @Override
    public void update() {
        super.update();

        if (isActive) {
            // Calcola la posizione centrata considerando dimensioni del player
            Vector2 centeredPosition = new Vector2(
                    getAbsolutePosition().x.doubleValue() - (gamePanel.getWidth()/2),
                    getAbsolutePosition().y.doubleValue() - (gamePanel.getHeight()/2)
            ).add(offset); // Aggiungi eventuale offset

            gamePanel.setCameraPosition(centeredPosition);
        }
    }

    // Metodo per regolare l'offset (utile per effetti cinematici)
    public void setOffset(Vector2 offset) {
        this.offset = offset;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public boolean isActive() {
        return isActive;
    }
}