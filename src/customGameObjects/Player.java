package customGameObjects;

import baseGameObjects.KinematicBody;
import basics.Input;
import basics.Vector2;
import graphics.Animation;
import graphics.GamePanel;

import java.awt.image.BufferedImage;

public class Player extends KinematicBody {

    public Player(GamePanel gamePanel, Vector2 position, Vector2 dimension, BufferedImage staticImage) {
        super(gamePanel, position, dimension, staticImage);
    }

    public Player(GamePanel gamePanel, Vector2 position, Vector2 dimension, Animation animation) {
        super(gamePanel, position, dimension, animation);
    }

    @Override
    public void update() {
        direction = Vector2.ZERO();

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
        //System.out.println(Input.pressedChars);

        gamePanel.setCameraPosition(new Vector2(position.x.intValue() - gamePanel.getWidth()/2 + dimension.x.intValue()/2, position.y.intValue() - gamePanel.getHeight()/2 + dimension.y.intValue()/2));

        moveAndCollide();

        super.update();
    }
}
