import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GamePanel extends JPanel {
    private List<Sprite> sprites = new CopyOnWriteArrayList<>();

    public GamePanel() {
        setPreferredSize(new Dimension(1600, 1200));
    }

    public void addSprite(Sprite sprite) {
        sprites.add(sprite);
    }

    public void update() {
        for (Sprite sprite : sprites) {
            sprite.update();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Sprite sprite : sprites) {
            sprite.draw(g);
        }
    }
}
