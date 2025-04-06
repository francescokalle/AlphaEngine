import baseGameObjects.ui.Button;
import baseGameObjects.ui.TextGameObject;
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

        Camera2D camera = new Camera2D(gamePanel, new Vector2(400, 300));
        camera.setActive(true);

        File defaultFontFile = new File("resources/fonts/defaultfont.pixelated.20.otf");

        // Carica la sprite sheet per il player
        BufferedImage playerImage = ResourceLoader.loadImage("/player.png");

        // Crea Sfondo
        Background background = new Background(gamePanel, ResourceLoader.loadImage("/background.jpeg"));

        // Crea il giocatore e altri oggetti
        Player player = new Player(gamePanel, new Vector2(600, 600), new Vector2(200, 200), playerImage);
        Collision2D muretto = new Collision2D(gamePanel, new Vector2(100, 100), new Vector2(30, 600));
        muretto.enableDebug(true);
        DraggableGameObject draggableSprite = new DraggableGameObject(gamePanel, new Vector2(300, 600), new Vector2(100, 100), playerImage);
        draggableSprite.addSon(draggableSprite);

        TextGameObject textSprite = new TextGameObject(gamePanel, new Vector2((float) gamePanel.getWidth() / 2, (float) gamePanel.getHeight() / 2),
                "Alpha engine 0.0.0 #16 internal", "left", defaultFontFile, new Color(0, 255, 0), 5, new Vector2(600, 600));
        textSprite.setZIndex(-9);

        Button button = new Button(gamePanel, new Vector2(400, 300), new Vector2(400, 200), "/button");

        // Avvia i thread del gioco, del rendering e degli input
        GameLoop gameLoop = new GameLoop(gamePanel);
        Renderer renderer = new Renderer(gamePanel);
        Input input = new Input(gamePanel);


        Thread gameThread = new Thread(gameLoop);
        Thread renderThread = new Thread(renderer);
        Thread inputThread = new Thread(input);

        gameThread.start();
        renderThread.start();
        inputThread.start();
    }
}
