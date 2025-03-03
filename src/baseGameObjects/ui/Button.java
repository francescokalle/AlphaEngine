package baseGameObjects.ui;

import basics.ResourceLoader;
import basics.Vector2;
import graphics.GamePanel;

import java.awt.image.BufferedImage;

public class Button extends ClickableSprite {
    protected BufferedImage imageIdle;
    protected BufferedImage imageHover;
    protected BufferedImage imageClick;
    public Button(GamePanel gamePanel, Vector2 position, Vector2 dimension, String pathToButtonSprites) {
        super(gamePanel, position, dimension);
        ResourceLoader.loadImage(ResourceLoader.searchFiles(pathToButtonSprites, "idle"));

        this.imageIdle = ResourceLoader.loadImage(ResourceLoader.searchFiles(pathToButtonSprites, "idle"));
        this.imageHover = ResourceLoader.loadImage(ResourceLoader.searchFiles(pathToButtonSprites, "hover"));
        this.imageClick = ResourceLoader.loadImage(ResourceLoader.searchFiles(pathToButtonSprites, "click"));

        if (imageIdle == null){
            throw new RuntimeException("Il bottone deve avere almeno l immagine di idle");
        }

    }

    @Override
    public void onIdle() {
        staticImage = imageIdle;
    }

    @Override
    public void onHover() {
        if (imageHover != null){
            staticImage = imageHover;
        } else {
            staticImage = imageIdle;
        }
    }

    @Override
    public void onClick() {
        if (imageClick != null){
            staticImage = imageClick;
        }
    }

    @Override
    public void update() {
        if (isJustClicked()){
            System.out.println("premuto");
        }

        super.update();
    }
}
