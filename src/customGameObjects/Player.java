package customGameObjects;

import baseGameObjects.KinematicBody;
import baseGameObjects.Camera2D;
import basics.Input;
import basics.Vector2;
import graphics.Animation;
import graphics.GamePanel;

import java.awt.image.BufferedImage;

public class Player extends KinematicBody {
    private Camera2D playerCamera;

    public Player(GamePanel gamePanel, Vector2 position, Vector2 dimension, BufferedImage staticImage) {
        super(gamePanel, position, dimension, staticImage);
        initializeCamera(gamePanel);
    }

    public Player(GamePanel gamePanel, Vector2 position, Vector2 dimension, Animation animation) {
        super(gamePanel, position, dimension, animation);
        initializeCamera(gamePanel);
    }

    private void initializeCamera(GamePanel gamePanel) {
        // Posiziona la camera al centro del player
        Vector2 cameraOffset = new Vector2(
                gamePanel.getWidth()/2,
                gamePanel.getHeight()/2
        );

        playerCamera = new Camera2D(gamePanel, position);
        this.addSon(playerCamera);

        // Imposta un offset per regolazione fine (se necessario)
        playerCamera.setOffset(new Vector2(dimension.x.intValue() / 2, dimension.y.intValue() / 2)); // Puoi regolare questi valori
        //playerCamera.setOffset(new Vector2(0, 0));
    }

    @Override
    public void update() {
        direction = Vector2.ZERO();

        if(Input.isKeyPressed('w')) direction.y = -1;
        if(Input.isKeyPressed('s')) direction.y = 1;
        if(Input.isKeyPressed('a')) direction.x = -1;
        if(Input.isKeyPressed('d')) direction.x = 1;

        moveAndCollide();
        super.update();
    }

    public Camera2D getCamera() {
        return playerCamera;
    }
}