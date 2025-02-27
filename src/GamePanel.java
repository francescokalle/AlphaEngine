import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class  GamePanel extends JPanel {
    private List<Sprite> sprites = new CopyOnWriteArrayList<>();
    private List<Collision2D> collisions = new CopyOnWriteArrayList<>();
    private List<Area2D> areas = new CopyOnWriteArrayList<>();

    public GamePanel() {
        setPreferredSize(new Dimension(1600, 1200));
        setDoubleBuffered(false);
        setIgnoreRepaint(true);

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

    public void removeSprite(Sprite sprite) {
        sprites.remove(sprite);
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

        // Ordina gli sprite in base allo zIndex (dal più piccolo al più grande)
        sprites.sort((sprite1, sprite2) -> Integer.compare(sprite1.getZIndex(), sprite2.getZIndex()));
        //System.out.println(sprites.toString());

        // Disegna gli sprite ordinati
        for (Sprite sprite : sprites) {
            //System.out.println(sprite.toString());
            sprite.draw(g);
        }
    }
}
