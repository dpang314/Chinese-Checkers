package checkers;

import checkers.game.Game;
import checkers.game.GamePanel;
import checkers.game.player.Player;
import checkers.menu.MenuPanel;
import checkers.resources.ImageLoader;

import javax.swing.*;

public class GUI {
    private static final ImageLoader imageLoader = new ImageLoader();
    private Game game;
    private final JFrame frame;

    public GUI() {
        frame = new JFrame();
        switchToMenuPanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static ImageLoader getImageLoader() {
        return imageLoader;
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                new GUI();
            }
        });
    }

    public void switchToGamePanel(Player[] players, boolean shuffle) {
        if (game == null) {
            game = new Game(players, shuffle);
        }
        GamePanel gamePanel = new GamePanel(this, game, 1280, 720);
        frame.setContentPane(gamePanel);
        frame.pack();
    }

    public void switchToGamePanel(Game game) {
        this.game = game;
        GamePanel gamePanel = new GamePanel(this, game, 1280, 720);
        frame.setContentPane(gamePanel);
        frame.pack();
    }

    public void switchToMenuPanel() {
        game = null;
        MenuPanel menuPanel = new MenuPanel(this);
        frame.setContentPane(menuPanel);
        frame.pack();
    }

    public void close() {
        frame.dispose();
    }
}
