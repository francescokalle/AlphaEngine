import javax.swing.*;

public class GameWindow {
    private JFrame frame;
    private GamePanel gamePanel;

    public GameWindow() {
        frame = new JFrame("AlphaEngine Test1");
        gamePanel = new GamePanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setFocusable(true);
        frame.add(gamePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }
}
