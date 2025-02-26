import java.awt.image.BufferedImage;

public class KinematicBody extends Collision2D{
    private int speed = 5;
    private Vector2 direction = new Vector2().ZERO();
    private GamePanel gamePanel; // Riferimento a GamePanel

    // Costruttore per animazione
    public KinematicBody(GamePanel gamePanel, Vector2 position, int width, int height, Animation animation) {
        super(gamePanel, position, width, height, animation);
        this.gamePanel = gamePanel; // Salvo il riferimento
    }

    // Costruttore per immagine statica
    public KinematicBody(GamePanel gamePanel, Vector2 position, int width, int height, BufferedImage staticImage) {
        super(gamePanel, position, width, height, staticImage);
        this.gamePanel = gamePanel; // Salvo il riferimento
    }

    @Override
    public void update() {
        direction = new Vector2(0, 0);

        if(Input.isKeyPressed('w')){
            direction.y = -1;
        }
        if(Input.isKeyPressed('s')){
            direction.y = 1;
        }
        if(Input.isKeyPressed('a')){
            direction.x = -1;
        }
        if(Input.isKeyPressed('d')){
            direction.x = 1;
        }

        if(Input.isKeyPressed('u')){
            System.out.println(unstick());
        }

        moveAndCollide();
        super.update();
    }

    // Metodo per verificare se il Player sta collidendo con un oggetto
    private boolean isColliding(int nextX, int nextY, Collision2D obj) {
        return nextX < obj.getPosition().x + obj.getWidth() &&
                nextX + width > obj.getPosition().x &&
                nextY < obj.getPosition().y + obj.getHeight() &&
                nextY + height > obj.getPosition().y;
    }

    private void moveAndCollide() {
        Vector2 nextPositionX = new Vector2(position.x + direction.x * speed, position.y);
        Vector2 nextPositionY = new Vector2(position.x, position.y + direction.y * speed);

        // Controlla separatamente collisioni sugli assi X e Y
        if (!isBlocked(nextPositionX)) {
            position.x += direction.x * speed;
        }
        if (!isBlocked(nextPositionY)) {
            position.y += direction.y * speed;
        }
    }

    // Controlla se il movimento è bloccato
    private boolean isBlocked(int nextX, int nextY) {
        for (Collision2D obj : gamePanel.getAllCollisions()) {
            if (obj != this && isColliding(nextX, nextY, obj)) {
                return true;
            }
        }
        return false;
    }
    private boolean isBlocked(Vector2 nextPosition) {
        for (Collision2D obj : gamePanel.getAllCollisions()) {
            if (obj != this && isColliding((int) nextPosition.x, (int) nextPosition.y, obj)) {
                return true;
            }
        }
        return false;
    }
}
