public class Renderer implements Runnable {
    private GamePanel gamePanel;
    private boolean running = true;

    public Renderer(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {
        while (running) {
            gamePanel.repaint();
            try {
                Thread.sleep(16); // ~60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
