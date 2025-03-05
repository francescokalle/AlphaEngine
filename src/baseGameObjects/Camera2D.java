package baseGameObjects;

import basics.Vector2;
import graphics.GamePanel;

public class Camera2D extends GameObject {
    private GamePanel gamePanel;
    private boolean isActive = true; // Se true, la camera controlla la visuale

    public Camera2D(GamePanel gamePanel, Vector2 position) {
        super(gamePanel, position, new Vector2(0, 0)); // La camera non ha dimensione fisica
        this.gamePanel = gamePanel;
    }

    @Override
    public void update() {
        if (isActive) {
            gamePanel.setCameraPosition(position);
        }
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public boolean isActive() {
        return isActive;
    }
}
