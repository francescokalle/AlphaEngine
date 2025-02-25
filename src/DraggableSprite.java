import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class DraggableSprite extends Sprite {
    private boolean dragging = false;
    private int offsetX, offsetY; // Offset per mantenere la posizione relativa al cursore

    public DraggableSprite(GamePanel gamePanel, Vector2 position, int width, int height, BufferedImage staticImage) {
        super(gamePanel, position, width, height, staticImage);
        addMouseListeners(gamePanel);
    }

    public DraggableSprite(GamePanel gamePanel, Vector2 position, int width, int height, Animation animation) {
        super(gamePanel, position, width, height, animation);
        addMouseListeners(gamePanel);
    }

    private void addMouseListeners(GamePanel gamePanel) {
        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isMouseOver(e.getX(), e.getY())) {
                    dragging = true;
                    offsetX = (int) (e.getX() - position.x);
                    offsetY = (int) (e.getY() - position.y);
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
        return mouseX >= position.x && mouseX <= position.x + width && mouseY >= position.y && mouseY <= position.y + height;
    }
}
