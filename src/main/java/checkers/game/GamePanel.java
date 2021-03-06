package checkers.game;

import checkers.GUI;
import checkers.Util;
import checkers.game.board.Position;
import checkers.game.player.ComputerStrategy;
import checkers.game.player.Player;
import checkers.resources.GameLoader;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    private static final int BUTTON_LEFT = 920;
    private static final int BUTTON_BOTTOM = 570;
    private static final int SPACING = 80;
    private static final int BUTTON_HEIGHT = 60;
    private final Game game;
    private final ArrayList<JLabel> highlightedButtons = new ArrayList<>();
    private final ActionListener saveAction = e -> save();
    private final GUI gui;
    private final ActionListener quitAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int confirmed = JOptionPane.showConfirmDialog(GamePanel.this,
                    "Are you sure you want to quit the game?",
                    "Quit", JOptionPane.YES_NO_OPTION);
            if (confirmed == JOptionPane.YES_OPTION) {
                if (!game.gameOver()) {
                    if (save()) {
                        gui.switchToMenuPanel();
                    }
                } else {
                    gui.switchToMenuPanel();
                }
            }
        }
    };
    private final ActionListener exitAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int confirmed = JOptionPane.showConfirmDialog(GamePanel.this,
                    "Are you sure you want to exit the program?",
                    "Exit", JOptionPane.YES_NO_OPTION);
            if (confirmed == JOptionPane.YES_OPTION) {
                if (!game.gameOver()) {
                    if (save()) {
                        gui.close();
                    }
                } else {
                    gui.close();
                }
            }
        }
    };
    boolean repaintButtons;
    private final ActionListener endAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            game.endTurn();
            repaintButtons = true;
            repaint();
            renderComputerMoves();
        }
    };
    private final ActionListener undoAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            game.undo();
            repaintButtons = true;
            repaint();
        }
    };

    public GamePanel(GUI gui, Game game, Dimension screenSize) {
        this.gui = gui;
        this.game = game;
        Player[] players = game.getPlayers();
        for (Player p : players) {
            if (p != null) {
                JLabel name = new JLabel(p.getName());
                Canvas c = new Canvas();
                FontMetrics metrics = c.getFontMetrics(Util.getBigFont());
                int width = metrics.stringWidth(p.getName()) + 54;
                int height = metrics.getHeight() + 20;
                name.setVerticalTextPosition(JLabel.CENTER);
                name.setHorizontalTextPosition(JLabel.CENTER);

                if (p.getColor().equals(Color.RED)) {
                    name.setForeground(Util.RED);
                    name.setIcon(GUI.getImageLoader().getCommonImages().getRedNameScroll(width, height));
                    name.setBounds(462 - width / 2, 10, width, height);
                    name.setFont(Util.getBigFont());
                    this.add(name);
                } else if (p.getColor().equals(Color.BLACK)) {
                    name.setForeground(Util.BLACK);
                    name.setIcon(GUI.getImageLoader().getCommonImages().getBlackNameScroll(width, height));
                    name.setBounds(560, 160, width, height);
                    name.setFont(Util.getBigFont());
                    this.add(name);
                } else if (p.getColor().equals(Color.GREEN)) {
                    name.setForeground(Util.GREEN);
                    name.setIcon(GUI.getImageLoader().getCommonImages().getGreenNameScroll(width, height));
                    name.setBounds(560, 515, width, height);
                    name.setFont(Util.getBigFont());
                    this.add(name);
                } else if (p.getColor().equals(Color.BLUE)) {
                    name.setForeground(Util.BLUE);
                    name.setIcon(GUI.getImageLoader().getCommonImages().getBlueNameScroll(width, height));
                    name.setBounds(462 - width / 2, 650, width, height);
                    name.setFont(Util.getBigFont());
                    this.add(name);
                } else if (p.getColor().equals(Color.WHITE)) {
                    name.setForeground(Util.WHITE);
                    name.setIcon(GUI.getImageLoader().getCommonImages().getWhiteNameScroll(width, height));
                    name.setBounds(370 - width, 515, width, height);
                    name.setFont(Util.getBigFont());
                    this.add(name);
                } else if (p.getColor().equals(Color.YELLOW)) {
                    name.setForeground(Util.YELLOW);
                    name.setIcon(GUI.getImageLoader().getCommonImages().getYellowNameScroll(width, height));
                    name.setBounds(370 - width, 160, width, height);
                    name.setFont(Util.getBigFont());
                    this.add(name);
                }
            }
        }
        setPreferredSize(screenSize);
        setLayout(null);
        repaintButtons = true;
        renderComputerMoves();
    }

    // 1 indexed
    public static Position getPixels(int row, int column, int RADIUS) {
        double START_X = (698 / 1.5);
        double START_Y = (112 / 1.5);

        double[] columnWidths = {
                0, 1, 2, 3, 12, 11, 10, 9, 8, 9, 10, 11, 12, 3, 2, 1, 0
        };
        for (int i = 0; i < columnWidths.length; i++) {
            columnWidths[i] /= 2;
        }

        double adjustedColumn = column - columnWidths[row];

        if (column - columnWidths[row] / 2 >= 3) {
            START_X += 5;
        }

        return new Position((int) (adjustedColumn * (60 / 1.5) + START_X - RADIUS / 2), (int) (row * (53 / 1.5) + START_Y - RADIUS / 2));
    }

    private boolean save() {
        int confirmed = JOptionPane.showConfirmDialog(GamePanel.this,
                "Do you want to save your game?",
                "Save", JOptionPane.YES_NO_OPTION);
        if (confirmed == JOptionPane.YES_OPTION) {
            JFileChooser fileChooser = new JFileChooser() {
                @Override
                public void approveSelection() {
                    File file = getSelectedFile();
                    if (file.exists() && getDialogType() == SAVE_DIALOG) {
                        int result = JOptionPane.showConfirmDialog(this, "A save file with this name already exists. Do you want to overwrite it?", "Duplicate file name", JOptionPane.YES_NO_CANCEL_OPTION);
                        if (result == JOptionPane.YES_OPTION) {
                            super.approveSelection();
                        } else if (result == JOptionPane.CANCEL_OPTION) {
                            super.cancelSelection();
                        }
                    } else {
                        super.approveSelection();
                    }
                }
            };
            fileChooser.setFileFilter(new FileNameExtensionFilter("chcr", "chcr"));
            fileChooser.setSelectedFile(new File("save.chcr"));
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    if (!file.getPath().endsWith(".chcr")) {
                        GameLoader.writeGameToFile(game, file.getPath() + ".chcr");
                    } else {
                        GameLoader.writeGameToFile(game, file.getPath());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JOptionPane.showMessageDialog(GamePanel.this, "File saved successfully", "Saved", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
            return false;
        }
        return true;
    }

    private void renderComputerMoves() {
        if (game.gameOver()) {
            repaint();
        } else if (game.getCurrentPlayer().isComputer()) {
            game.getTurn();
            repaintButtons = true;
            repaint();
            Timer timer = new Timer(100, actionEvent -> renderComputerMoves());
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void style(JButton button) {
        button.setContentAreaFilled(false);
        button.setFont(Util.getFont().deriveFont(18f));
        button.setFocusPainted(false);
    }

    private void styleDisabled(JButton button) {
        button.setForeground(Color.GRAY);
        button.setEnabled(false);
    }

    private void initButtons() {
        JButton end = new JButton("End Turn");
        end.setBounds(BUTTON_LEFT, BUTTON_BOTTOM - SPACING * 4, 254, BUTTON_HEIGHT);
        style(end);
        if (!game.canEndTurn() || game.gameOver() || game.getCurrentPlayer() instanceof ComputerStrategy) {
            styleDisabled(end);
        }
        end.addActionListener(endAction);
        this.add(end);
        JButton undo;
        if (game.canUndoMini()) {
            undo = new JButton("Undo Move");
        } else if (game.canUndoTurns()) {
            undo = new JButton("Undo Turn");
        } else {
            undo = new JButton("Undo");
        }
        undo.setBounds(BUTTON_LEFT, BUTTON_BOTTOM - SPACING * 3, 254, BUTTON_HEIGHT);
        style(undo);
        if (((!game.canUndoMini() && !game.canUndoTurns()) || game.gameOver())
                || game.getCurrentPlayer() instanceof ComputerStrategy) {
            styleDisabled(undo);
        }
        undo.addActionListener(undoAction);
        this.add(undo);
        JButton save = new JButton("Save");
        save.setBounds(BUTTON_LEFT, BUTTON_BOTTOM - SPACING * 2, 254, BUTTON_HEIGHT);
        save.addActionListener(saveAction);
        style(save);
        if (game.winningPlayer() != null) {
            styleDisabled(save);
        }
        this.add(save);
        JButton quit = new JButton("Quit");
        quit.setBounds(BUTTON_LEFT, BUTTON_BOTTOM - SPACING, 254, BUTTON_HEIGHT);
        quit.addActionListener(quitAction);
        style(quit);
        this.add(quit);
        JButton exit = new JButton("Exit");
        exit.setBounds(BUTTON_LEFT, BUTTON_BOTTOM, 254, BUTTON_HEIGHT);
        exit.addActionListener(exitAction);
        style(exit);
        this.add(exit);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(GUI.getImageLoader().getCommonImages().getBackground(), 0, 0, null);
        g.drawImage(GUI.getImageLoader().getGamePanelImages().getBoard(), 0, 0, null);
        g.drawImage(GUI.getImageLoader().getGamePanelImages().getBigScroll(), 0, 0, null);

        g.setFont(Util.getFont().deriveFont(17f));
        if (game.gameOver()) {
            g.drawString(game.winningPlayer().getName() + " won!", BUTTON_LEFT - 10, 100);
        } else {
            if (game.getCurrentPlayer().isComputer()) {
                g.drawString(game.getCurrentPlayer().getName(), BUTTON_LEFT - 10, 100);
                g.drawString("is thinking", BUTTON_LEFT - 10, 130);
            } else {
                if (g.getFontMetrics().stringWidth(game.getCurrentPlayer().getName()) >= 270) {
                    g.drawString(game.getCurrentPlayer().getName() + "'s", BUTTON_LEFT - 10, 100);
                    g.drawString("turn", BUTTON_LEFT - 10, 120);
                } else {
                    g.drawString(game.getCurrentPlayer().getName() + "'s turn", BUTTON_LEFT - 10, 100);
                }
            }
        }
        g.drawString("Turn " + game.getTurns(), BUTTON_LEFT - 10, 200);
        g.drawString("Move " + game.getMoves(), BUTTON_LEFT - 10, 230);
        if (repaintButtons) {
            for (int i = 0; i < this.getComponentCount(); i++) {
                if (this.getComponents()[i] instanceof JButton) {
                    this.remove(i);
                    i--;
                }
            }

            // not highlighted
            game.getNonClickablePegs().forEach((position, color) -> this.add(new PegButton(position, color, false)));

            for (JLabel label : highlightedButtons) {
                GamePanel.this.remove(label);
            }
            highlightedButtons.clear();

            game.getClickablePegs().forEach((position) -> this.add(new PegButton(position, game.getCurrentPlayer().getColor(), true)));

            for (JLabel label : highlightedButtons) {
                this.add(label);
            }

            if (!game.getCurrentPlayer().isComputer() && !game.gameOver()) {
                for (Position p : game.getPossibleMoves()) {
                    this.add(new HighlightButton(p));
                }
            }
            initButtons();
            repaintButtons = false;
        }
    }

    class PegButton extends JButton {
        private final Ellipse2D border;

        public PegButton(Position pos, Color color, boolean highlighted) {
            // Image is a square with a circle
            ImageIcon icon = GUI.getImageLoader().getGamePanelImages().getImageIcon(color, highlighted);
            int RADIUS = icon.getIconHeight();
            this.setFocusPainted(false);
            this.setIcon(icon);
            this.setBorderPainted(false);
            this.setContentAreaFilled(false);

            Position pixels = getPixels(pos.getRow(), pos.getColumn(), RADIUS);
            this.setBounds(pixels.getRow(), pixels.getColumn(), RADIUS, RADIUS);

            this.border = new Ellipse2D.Float(0, 0, RADIUS, RADIUS);
            if (highlighted) {
                JLabel highlightIcon = new JLabel(GUI.getImageLoader().getGamePanelImages().getButtonHighlight());
                highlightIcon.setBounds(pixels.getRow() - (highlightIcon.getIcon().getIconWidth() - icon.getIconWidth()) / 2,
                        pixels.getColumn() - (highlightIcon.getIcon().getIconHeight() - icon.getIconHeight()) / 2,
                        highlightIcon.getIcon().getIconWidth(), highlightIcon.getIcon().getIconHeight());
                highlightedButtons.add(highlightIcon);

                this.addActionListener(actionEvent -> {
                    GamePanel.this.repaintButtons = true;
                    GamePanel.this.repaint();
                    game.select(pos);
                });
            }
        }

        public boolean contains(int x, int y) {
            return border.contains(x, y);
        }
    }

    class HighlightButton extends JButton {
        private final Ellipse2D border;

        public HighlightButton(Position position) {
            // Image is a square with a circle
            ImageIcon icon = GUI.getImageLoader().getGamePanelImages().getPositionHighlight();
            int RADIUS = icon.getIconHeight();
            this.setFocusPainted(false);
            this.setIcon(icon);
            this.setBorderPainted(false);
            this.setContentAreaFilled(false);

            Position pixels = getPixels(position.getRow(), position.getColumn(), RADIUS);

            this.setBounds(pixels.getRow(), pixels.getColumn(), RADIUS, RADIUS);

            border = new Ellipse2D.Float(0, 0, RADIUS, RADIUS);

            this.addActionListener(actionEvent -> {
                GamePanel.this.repaintButtons = true;
                GamePanel.this.repaint();
                game.movePeg(position);
            });
        }

        public boolean contains(int x, int y) {
            return border.contains(x, y);
        }
    }
}
