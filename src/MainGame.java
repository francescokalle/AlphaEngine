import baseGameObjects.ui.Button;
import baseGameObjects.ui.TextSprite;
import basics.*;
import baseGameObjects.*;
import customGameObjects.Player;
import graphics.GamePanel;
import graphics.Renderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainGame {
    public static void main(String[] args) throws IOException, FontFormatException {
        GameWindow gameWindow = new GameWindow();
        GamePanel gamePanel = gameWindow.getGamePanel();
        Input input = new Input(gamePanel);
        Camera2D camera = new Camera2D(gamePanel, new Vector2(400, 300)); // Posizione iniziale della camera
        camera.setActive(true);

        File defaultFontFile = new File("resources/fonts/defaultfont.pixelated.20.otf");

        // Carica la sprite sheet per il player
        BufferedImage playerImage = ResourceLoader.loadImage("/player.png"); // Assicurati di avere questa immagine nella cartella resources
        //graphics.Animation playerAnimation = new graphics.Animation(spriteSheet, 4, 64, 64, 10); // 4 frame, ogni frame 64x64

        //Crea Sfondo
        Background background = new Background(gamePanel, ResourceLoader.loadImage("/background.jpeg"));
        // Crea il giocatore e aggiungilo al game panel
        Player player = new Player(gamePanel, new Vector2(600, 600), new Vector2(200, 200), playerImage);
        Collision2D muretto = new Collision2D(gamePanel, new Vector2(100, 100), new Vector2(30, 600));
        muretto.enableDebug(true);
        DraggableSprite draggableSprite = new DraggableSprite(gamePanel, new Vector2(300, 600), new Vector2(100, 100), playerImage);
        //player.stickTo(draggableSprite);
        TextSprite textSprite = new TextSprite(gamePanel,new Vector2((float) gamePanel.getWidth() /2, (float) gamePanel.getHeight() /2), "aiutooooo\nsono down", "right", defaultFontFile, new Color(0, 255, 0), 5);
        textSprite.setZIndex(-9);

        Button button = new Button(gamePanel, new Vector2(400, 300),  new Vector2(400, 200));

        // Avvia i thread
        GameLoop gameLoop = new GameLoop(gamePanel);
        Renderer renderer = new Renderer(gamePanel);

        Thread gameThread = new Thread(gameLoop);
        Thread renderThread = new Thread(renderer);

        gameThread.start();
        renderThread.start();
    }
}
