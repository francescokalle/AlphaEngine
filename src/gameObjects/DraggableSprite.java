package gameObjects;

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
                if (isMouseOver(e.getX(), e.getY())) {
                    dragging = true;
                    offsetX = (int) (e.getX() - position.x.intValue());
                    offsetY = (int) (e.getY() - position.y.intValue());
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
                    position.x = e.getX() - offsetX;
                    position.y = e.getY() - offsetY;
                }
            }
        });
    }

    private boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= position.x.intValue() && mouseX <= position.x.intValue() + dimension.x.intValue() && mouseY >= position.y.intValue() && mouseY <= position.y.intValue() + dimension.y.intValue();
    }
}