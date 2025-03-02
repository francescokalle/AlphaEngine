package basics;

import graphics.GamePanel;

public class GameLoop implements Runnable {
    private GamePanel gamePanel;
    private boolean running = true;

    public GameLoop(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {
        while (running) {
            gamePanel.update();
            try {
                Thread.sleep(16); // ~60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
