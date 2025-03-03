package baseGameObjects.ui;

import basics.ResourceLoader;
import basics.Vector2;
import graphics.GamePanel;

public class Button extends ClickableSprite {

    public Button(GamePanel gamePanel, Vector2 position, Vector2 dimension) {
        super(gamePanel, position, dimension);
    }

    @Override
    public void onIdle() {
        staticImage = ResourceLoader.loadImage("/button.idle.jpg");
    }

    @Override
    public void onHover() {
        staticImage = ResourceLoader.loadImage("/button.idle.jpg");
    }

    @Override
    public void onClick() {
        staticImage = ResourceLoader.loadImage("/button.pressed.jpg");
    }

    @Override
    public void update() {
        if (isJustClicked()){
            System.out.println("premuto");
        }

        super.update();
    }
}
