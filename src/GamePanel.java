import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GamePanel extends JPanel {
    private List<Sprite> sprites = new CopyOnWriteArrayList<>();
    private List<Collision2D> collisions = new CopyOnWriteArrayList<>();
    private List<Area2D> areas = new CopyOnWriteArrayList<>();

    public GamePanel() {
        setPreferredSize(new Dimension(1600, 1200));
    }

    public void addSprite(Sprite sprite) {
        sprites.add(sprite);
        if (sprite instanceof Collision2D) {
            collisions.add((Collision2D) sprite);
        }
        if (sprite instanceof Area2D) {
            areas.add((Area2D) sprite);
        }
    }

    public List<Collision2D> getAllCollisions() {
        return collisions;
    }

    public List<Area2D> getAllAreas() {
        return areas;
    }

    public void update() {
        for (Sprite sprite : sprites) {
            sprite.update();
        }
        //System.out.println(sprites);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Disegna gli sprite
        for (Sprite sprite : sprites) {
            sprite.draw(g);
        }

        // Disegna le aree di debug
        for (Area2D area : areas) {
            area.draw(g);
        }

        // Disegna le collisioni di debug
        for (Collision2D collision : collisions) {
            collision.draw(g);
        }
    }
}
