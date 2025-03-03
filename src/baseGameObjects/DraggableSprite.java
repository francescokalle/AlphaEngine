package baseGameObjects;

import basics.Vector2;
import graphics.Animation;
import graphics.GamePanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class DraggableSprite extends Sprite {
    private boolean dragging = false;
    private int offsetX, offsetY; // Offset per mantenere la posizione relativa al cursore

    public DraggableSprite(GamePanel gamePanel, Vector2 position, Vector2 dimension, BufferedImage staticImage) {
        super(gamePanel, position, dimension, staticImage);
        addMouseListeners(gamePanel);
    }

    public DraggableSprite(GamePanel gamePanel, Vector2 position, Vector2 dimension, Animation animation) {
        super(gamePanel, position, dimension, animation);
        addMouseListeners(gamePanel);
    }

    private void addMouseListeners(GamePanel gamePanel) {
        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Correggi le coordinate del mouse rispetto alla telecamera
                int mouseX = e.getX() + gamePanel.getCameraPosition().x.intValue();
                int mouseY = e.getY() + gamePanel.getCameraPosition().y.intValue();

                if (isMouseOver(mouseX, mouseY)) {
                    dragging = true;
                    offsetX = (int) (mouseX - position.x.intValue());
                    offsetY = (int) (mouseY - position.y.intValue());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                dragging = false;
            }
        });

        gamePanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragging) {
                    // Correggi le coordinate del mouse rispetto alla telecamera
                    int mouseX = e.getX() + gamePanel.getCameraPosition().x.intValue();
                    int mouseY = e.getY() + gamePanel.getCameraPosition().y.intValue();

                    // Aggiorna la posizione dello sprite
                    position.x = mouseX - offsetX;
                    position.y = mouseY - offsetY;
                }
            }
        });
    }

    private boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= position.x.intValue() &&
                mouseX <= position.x.intValue() + dimension.x.intValue() &&
                mouseY >= position.y.intValue() &&
                mouseY <= position.y.intValue() + dimension.y.intValue();
    }
}