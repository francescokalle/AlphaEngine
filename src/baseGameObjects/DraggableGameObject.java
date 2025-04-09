package baseGameObjects;

import basics.Input;
import basics.Vector2;
import graphics.Animation;
import graphics.GamePanel;

import java.awt.image.BufferedImage;

public class DraggableGameObject extends ClickableGameObject {
    private boolean isDragging = false;
    private Vector2 dragOffset;
    GameObject oldParent;

    public DraggableGameObject(GamePanel gamePanel, Vector2 position, Vector2 dimension, Animation animation) {
        super(gamePanel, position, dimension);
        this.animation = animation;
    }

    public DraggableGameObject(GamePanel gamePanel, Vector2 position, Vector2 dimension, BufferedImage staticImage) {
        super(gamePanel, position, dimension);
        this.staticImage = staticImage;
    }

    @Override
    public void update() {
        //System.out.println(Input.getMousePosition(gamePanel.getCameraPosition()));

        super.update(); // Chiama SEMPRE il parent

    }

    @Override
    public void onIdle() {

    }

    @Override
    public void onHover() {

    }

    @Override
    public void onClick() {
        Vector2 mousePos = Input.getMousePosition(gamePanel.getCameraPosition());
        oldParent = parent;
        this.parent = null;
        dragOffset = mousePos.subtract(this.getPosition());
    }

    @Override
    public void onPressed() {

        Vector2 mousePos = Input.getMousePosition(gamePanel.getCameraPosition());

        this.setPosition(mousePos.subtract(dragOffset));

    }

    @Override
    public void justReleased() {
        this.setParent(oldParent);
        //System.out.println(parent);
    }
}