package checkers.menu;

import checkers.GUI;
import checkers.Util;
import checkers.game.Game;
import checkers.game.player.HumanPlayer;
import checkers.game.player.Player;
import checkers.game.player.QuinnStrategy;
import checkers.game.player.SimpleQuinnStrategy;
import checkers.resources.GameLoader;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.IOException;

public class MenuPanel extends JLayeredPane {
    private static final long serialVersionUID = 1L;
    private final PlayerButton P1;
    private final PlayerButton P2;
    private final PlayerButton P3;
    private final PlayerButton P4;
    private final PlayerButton P5;
    private final PlayerButton P6;
    private final JButton shuffler;
    private boolean shuffle = false;
    private JLabel scrollCloseImage, dragonImage;

    public MenuPanel(GUI gui) {
        setPreferredSize(new Dimension(1280, 720));
        this.setLayout(null);

        shuffler = new JButton("");
        shuffler.setBounds(335, 115, 20, 20);
        shuffler.addActionListener(actionEvent -> {
            shuffle = !shuffle;
            if (shuffle) {
                shuffler.setIcon(GUI.getImageLoader().getMenuPanelImages().getFilledButton());
            } else {
                shuffler.setIcon(GUI.getImageLoader().getMenuPanelImages().getEmptyButton());
            }
        });
        shuffler.setIcon(GUI.getImageLoader().getMenuPanelImages().getEmptyButton());
        shuffler.setContentAreaFilled(false);
        shuffler.setBorderPainted(false);
        this.add(shuffler);

        P1 = new PlayerButton(1);
        P1.setBounds(80, 125, P1.getIcon().getIconWidth(), P1.getIcon().getIconHeight());
        P1.setPlayerType(Util.PlayerType.HUMAN);
        this.add(P1);

        P2 = new PlayerButton(2);
        P2.setBounds(320, 180, P2.getIcon().getIconWidth(), P2.getIcon().getIconHeight());
        P2.setPlayerType(Util.PlayerType.COMPUTER_HARD);
        this.add(P2);

        P3 = new PlayerButton(3);
        P3.setBounds(140, 258, P3.getIcon().getIconWidth(), P3.getIcon().getIconHeight());
        this.add(P3);

        P4 = new PlayerButton(4);
        P4.setBounds(420, 312, P4.getIcon().getIconWidth(), P4.getIcon().getIconHeight());
        this.add(P4);

        P5 = new PlayerButton(5);
        P5.setBounds(220, 410, P5.getIcon().getIconWidth(), P5.getIcon().getIconHeight());
        this.add(P5);

        P6 = new PlayerButton(6);
        P6.setBounds(480, 450, P6.getIcon().getIconWidth(), P6.getIcon().getIconHeight());
        this.add(P6);

        JButton start = new JButton("");
        start.setBounds(540, 591, GUI.getImageLoader().getMenuPanelImages().getStart().getIconWidth(), GUI.getImageLoader().getMenuPanelImages().getStart().getIconHeight());
        start.setIcon(GUI.getImageLoader().getMenuPanelImages().getStart());
        start.addActionListener(actionEvent -> {
            Player[] players = {
                    createPlayer(P1),
                    createPlayer(P2),
                    createPlayer(P3),
                    createPlayer(P4),
                    createPlayer(P5),
                    createPlayer(P6),
            };
            int playerCount = 0;
            for (Player p : players) {
                if (p != null) {
                    playerCount++;
                }
            }
            if (playerCount == 0) {
                JOptionPane.showMessageDialog(MenuPanel.this, "Game can't have 0 players", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (playerCount == 1) {
                JOptionPane.showMessageDialog(MenuPanel.this, "Game can't have 1 player", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (playerCount == 5) {
                JOptionPane.showMessageDialog(MenuPanel.this, "Game can't have 5 players", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                this.remove(dragonImage);
                gui.switchToGamePanel(players, shuffle);
            }
        });
        start.setContentAreaFilled(false);
        start.setBorderPainted(false);
        this.add(start);

        JButton load = new JButton("");
        load.setBounds(195, 590, GUI.getImageLoader().getMenuPanelImages().getLoad().getIconWidth(), GUI.getImageLoader().getMenuPanelImages().getLoad().getIconHeight());
        load.setIcon(GUI.getImageLoader().getMenuPanelImages().getLoad());
        load.addActionListener(actionEvent -> {
            // loads save
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    ".chcr save file", "chcr");
            chooser.setFileFilter(filter);
            chooser.setAcceptAllFileFilterUsed(false);
            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    Game game = GameLoader.readGameFromFile(chooser.getSelectedFile().getPath());
                    this.remove(dragonImage);
                    gui.switchToGamePanel(game);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(MenuPanel.this, "Error loading save file. Please try a different file.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        load.setContentAreaFilled(false);
        load.setBorderPainted(false);
        this.add(load);

        JButton exit = new JButton("");
        exit.setBounds(872, 597, GUI.getImageLoader().getMenuPanelImages().getExit().getIconWidth(), GUI.getImageLoader().getMenuPanelImages().getExit().getIconHeight());
        exit.setIcon(GUI.getImageLoader().getMenuPanelImages().getExit());
        exit.addActionListener(actionEvent -> {
            int confirmed = JOptionPane.showConfirmDialog(MenuPanel.this,
                    "Are you sure you want to quit the game?",
                    "Quit", JOptionPane.YES_NO_OPTION);
            if (confirmed == JOptionPane.YES_OPTION) {
                gui.close();
            }
        });
        exit.setContentAreaFilled(false);
        exit.setBorderPainted(false);
        this.add(exit);

        JLabel shuffleLabel = new JLabel("Shuffle Colors");
        shuffleLabel.setBounds(380, 110, 200, 30);
        shuffleLabel.setFont(Util.getBigFont());
        this.add(shuffleLabel);
        shuffleLabel.setVisible(true);

        scrollCloseImage = new JLabel(GUI.getImageLoader().getMenuPanelImages().getLogoBig());
        scrollCloseImage.setBounds(0, 0, 1280, 720);
        this.add(scrollCloseImage);

        dragonImage = new JLabel(GUI.getImageLoader().getMenuPanelImages().getDragon());
        dragonImage.setBounds(0, 0, 1280, 720);
        this.add(dragonImage, -1, 0);

        repaint();
    }

    private void closeOptions() {
        P1.setEnabled(true);
        P2.setEnabled(true);
        P3.setEnabled(true);
        P4.setEnabled(true);
        P5.setEnabled(true);
        P6.setEnabled(true);
        scrollCloseImage = new JLabel(GUI.getImageLoader().getMenuPanelImages().getScrollCloseAnimated());
        scrollCloseImage.setBounds(0, 0, 1280, 720);
        this.add(scrollCloseImage);
    }

    private void openOptions(PlayerButton playerButton) {
        playerButton.open();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(GUI.getImageLoader().getCommonImages().getBackground(), 0, 0, this);
    }

    private Player createPlayer(PlayerButton playerButton) {
        if (playerButton.playerType.equals(Util.PlayerType.NONE)) {
            return null;
        } else if (playerButton.playerType.equals(Util.PlayerType.COMPUTER_EASY)) {
            return new SimpleQuinnStrategy(Color.RED, playerButton.getName());
        } else if (playerButton.playerType.equals(Util.PlayerType.COMPUTER_HARD)) {
            return new QuinnStrategy(Color.RED, playerButton.getName());
        } else if (playerButton.playerType.equals(Util.PlayerType.HUMAN)) {
            return new HumanPlayer(Color.RED, playerButton.getName());
        }
        throw new RuntimeException("Error creating player");
    }

    public class PlayerButton extends JButton {
        private final PlayerOptionsPanel playerOptionsPanel;
        private final int playerNumber;
        private Util.PlayerType playerType = Util.PlayerType.NONE;
        private JLabel displayName, scrollImage;

        public PlayerButton(int playerNumber) {
            this.playerNumber = playerNumber;
            playerOptionsPanel = new PlayerOptionsPanel(playerNumber, this);
            playerOptionsPanel.setLayout(new BorderLayout());
            playerOptionsPanel.setBounds(0, 0, 1280, 720);
            playerOptionsPanel.setVisible(false);
            MenuPanel.this.add(playerOptionsPanel);
            this.setFocusPainted(false);
            this.setBorderPainted(false);
            this.setContentAreaFilled(false);
            this.setIcon(GUI.getImageLoader().getMenuPanelImages().getPlayerCloudIcon(playerNumber, playerType));
            this.setDisabledIcon(GUI.getImageLoader().getMenuPanelImages().getPlayerCloudIcon(playerNumber, playerType));
            this.addActionListener(actionEvent -> openOptions(PlayerButton.this));
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            this.setIcon(GUI.getImageLoader().getMenuPanelImages().getPlayerCloudIcon(playerNumber, playerType));
            this.setDisabledIcon(GUI.getImageLoader().getMenuPanelImages().getPlayerCloudIcon(playerNumber, playerType));
        }

        public void open() {
            playerType = Util.PlayerType.DEFAULT;
            Timer timer = new Timer(1000, actionEvent -> playerOptionsPanel.open());
            timer.setRepeats(false);
            timer.start();
            P1.setEnabled(false);
            P2.setEnabled(false);
            P3.setEnabled(false);
            P4.setEnabled(false);
            P5.setEnabled(false);
            P6.setEnabled(false);

            if (displayName != null) {
                MenuPanel.this.remove(displayName);
            }
            if (scrollCloseImage != null) {
                MenuPanel.this.remove(scrollCloseImage);
            }
            scrollImage = new JLabel(GUI.getImageLoader().getMenuPanelImages().getScrollOpenAnimated());
            scrollImage.setBounds(0, 0, 1280, 720);
            MenuPanel.this.add(scrollImage);
        }

        private void addName() {
            if (displayName != null) {
                MenuPanel.this.remove(displayName);
            }
            displayName = new JLabel(getName());
            displayName.setFont(Util.getBigFont());
            if (playerType.equals(Util.PlayerType.COMPUTER_EASY) || playerType.equals(Util.PlayerType.COMPUTER_HARD)) {
                displayName.setForeground(Util.RED);
            } else if (playerType.equals(Util.PlayerType.HUMAN)) {
                displayName.setForeground(Util.GREEN);
            } else {
                displayName = new JLabel("");
            }
            int y = 100;
            if (playerNumber == 3) {
                y += 20;
            } else if (playerNumber == 5) {
                y += 30;
            } else if (playerNumber == 6) {
                y += 10;
            }
            displayName.setBounds(this.getX() + 20, this.getY() + y, 1000, 30);
            MenuPanel.this.add(displayName);
        }

        public void close(Util.PlayerType selected) {
            if (scrollImage != null) {
                MenuPanel.this.remove(scrollImage);
            }
            playerType = selected;
            playerOptionsPanel.setVisible(false);
            addName();
            repaint();
            closeOptions();
        }

        public String getName() {
            return playerOptionsPanel.getName();
        }

        public void setPlayerType(Util.PlayerType playerType) {
            this.playerType = playerType;
            playerOptionsPanel.setSelected(playerType);
            addName();
            this.repaint();
        }
    }
}

	
