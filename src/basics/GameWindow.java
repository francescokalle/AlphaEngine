package basics;

import graphics.GamePanel;

import javax.swing.*;

public class GameWindow {
    public JFrame frame;
    public GamePanel gamePanel;

    public GameWindow() {
        frame = new JFrame("AlphaEngine Test1");
        gamePanel = new GamePanel(this);

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
