package checkers;

import checkers.game.Game;
import checkers.game.GamePanel;
import checkers.game.player.Player;
import checkers.menu.MenuPanel;
import checkers.resources.ImageLoader;

import javax.swing.*;
import java.awt.*;

public class GUI {
    private static final ImageLoader imageLoader = new ImageLoader();
    private Game game;
    private final JFrame frame;
    private JPanel gamePanel;
    private JLayeredPane menuPanel;
    private final JLayeredPane containerPane = new JLayeredPane();

    public GUI() {
        frame = new JFrame("Chinese Checkers");
        containerPane.setPreferredSize(new Dimension(1280, 720));
        menuPanel = new MenuPanel(this);
        menuPanel.setBounds(0, 0, 1280, 720);
        containerPane.add(menuPanel, 0, 0);
        frame.add(containerPane);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static ImageLoader getImageLoader() {
        return imageLoader;
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
            new GUI();
        });
    }

    public void switchToGamePanel(Player[] players, boolean shuffle) {
        if (game == null) {
            game = new Game(players, shuffle);
        }
        JLabel dragon = new JLabel(GUI.getImageLoader().getMenuPanelImages().getDragonAnimated());
        dragon.setBounds(0, 0, 1280, 720);
        menuPanel.add(dragon);
        JPanel transitionPane = new JPanel();
        transitionPane.setBounds(0, 0, 1280, 720);
        JLabel transition = new JLabel(GUI.getImageLoader().getCommonImages().getCloudTransition());
        transition.setBounds(0, 0, 1280, 720);
        transitionPane.add(transition);
        transitionPane.setOpaque(false);
        Timer addCloudTimer = new Timer(500, actionEvent -> {
            containerPane.add(transitionPane, 1, 0);
            Timer timer = new Timer(550, actionEvent1 -> {
                containerPane.remove(menuPanel);
                gamePanel = new GamePanel(this, game, new Dimension(1280, 720));
                gamePanel.setBounds(0, 0, 1280, 720);
                containerPane.add(gamePanel, 0, 0);
            });
            Timer t = new Timer(1080, actionEvent1 -> {
                containerPane.remove(transitionPane);
                containerPane.repaint();
            });
            t.setRepeats(false);
            t.start();
            timer.setRepeats(false);
            timer.start();
        });
        addCloudTimer.setRepeats(false);
        addCloudTimer.start();
        frame.pack();
    }

    public void switchToGamePanel(Game game) {
        this.game = game;
        gamePanel = new GamePanel(this, game, new Dimension(1280, 720));
        frame.setContentPane(gamePanel);
        frame.pack();
    }

    public void switchToMenuPanel() {
        game = null;

        JPanel transitionPane = new JPanel();
        transitionPane.setBounds(0, 0, 1280, 720);
        JLabel transition = new JLabel(GUI.getImageLoader().getCommonImages().getCloudTransition());
        transition.setBounds(0, 0, 1280, 720);
        transitionPane.add(transition);
        transitionPane.setOpaque(false);
        Timer addCloudTimer = new Timer(300, actionEvent -> {
            containerPane.add(transitionPane, 1, 0);
            Timer timer = new Timer(550, actionEvent1 -> {
                containerPane.remove(gamePanel);
                menuPanel = new MenuPanel(this);
                menuPanel.setBounds(0, 0, 1280, 720);
                containerPane.add(menuPanel, 0, 0);
            });
            Timer t = new Timer(1080, actionEvent1 -> {
                containerPane.remove(transitionPane);
                containerPane.repaint();
            });
            t.setRepeats(false);
            t.start();
            timer.setRepeats(false);
            timer.start();
        });
        addCloudTimer.setRepeats(false);
        addCloudTimer.start();
        frame.pack();
    }

    public void close() {
        frame.dispose();
    }
}
