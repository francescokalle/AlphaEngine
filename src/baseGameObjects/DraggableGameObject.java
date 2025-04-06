package baseGameObjects;

import basics.Input;
import basics.Vector2;
import graphics.Animation;
import graphics.GamePanel;

import java.awt.image.BufferedImage;

public class DraggableGameObject extends GameObject {
    private boolean dragging = false;
    private int offsetX, offsetY;
    private final GamePanel gamePanel;

    public DraggableGameObject(GamePanel gamePanel, Vector2 position, Vector2 dimension, BufferedImage staticImage) {
        super(gamePanel, position, dimension, staticImage);
        this.gamePanel = gamePanel;
    }

    public DraggableGameObject(GamePanel gamePanel, Vector2 position, Vector2 dimension, Animation animation) {
        super(gamePanel, position, dimension, animation);
        this.gamePanel = gamePanel;
    }

    public void update() {
        int mouseX = Input.getMousePosition().x + gamePanel.getCameraPosition().x.intValue();
        int mouseY = Input.getMousePosition().y + gamePanel.getCameraPosition().y.intValue();

        if (!dragging && Input.isMousePressed() && isMouseOver(mouseX, mouseY)) {
            dragging = true;
            offsetX = mouseX - position.x.intValue();
            offsetY = mouseY - position.y.intValue();
        }

        if (dragging) {
            if (!Input.isMousePressed()) {
                dragging = false;
            } else {
                position.x = mouseX - offsetX;
                position.y = mouseY - offsetY;
            }
        }
    }

    private boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= position.x.intValue() &&
                mouseX <= position.x.intValue() + dimension.x.intValue() &&
                mouseY >= position.y.intValue() &&
                mouseY <= position.y.intValue() + dimension.y.intValue();
    }
}
