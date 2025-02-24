import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainGame {
    public static void main(String[] args) throws IOException, FontFormatException {
        GameWindow gameWindow = new GameWindow();
        GamePanel gamePanel = gameWindow.getGamePanel();
        File defaultFontFile = new File("resources/fonts/defaultfont.pixelated.20.otf");

        // Carica la sprite sheet per il player
        BufferedImage playerImage = ResourceLoader.loadImage("/player.png"); // Assicurati di avere questa immagine nella cartella resources
        //Animation playerAnimation = new Animation(spriteSheet, 4, 64, 64, 10); // 4 frame, ogni frame 64x64

        //Crea Sfondo
        Background background = new Background(gamePanel, ResourceLoader.loadImage("/background.jpeg"));
        // Crea il giocatore e aggiungilo al game panel
        Player player = new Player(gamePanel, 300, 300, 200, 200, playerImage);

        // Istanza di TextSprite con il messaggio, font, larghezza e allineamento specificati
        TextSprite textSprite = new TextSprite(gamePanel,gamePanel.getWidth()/2, gamePanel.getHeight()/2, "aiutooooo\nsono down", "center", defaultFontFile, new Color(0, 255, 0), 5);

        // Avvia i thread
        GameLoop gameLoop = new GameLoop(gamePanel);
        Renderer renderer = new Renderer(gamePanel);

        Thread gameThread = new Thread(gameLoop);
        Thread renderThread = new Thread(renderer);

        gameThread.start();
        renderThread.start();
    }
}
