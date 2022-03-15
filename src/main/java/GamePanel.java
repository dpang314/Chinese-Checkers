import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.geom.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class GamePanel extends JPanel {
	private Game game;
	private GUI gui;
	private int WIDTH, HEIGHT;

	private JButton end, undo, save, quit, exit;
	boolean repaintButtons = false;

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

		return new Position((int) (adjustedColumn * (60 / 1.5) + START_X - RADIUS / 2), (int) (row * (53 / 1.5) + START_Y - RADIUS / 2));
	}

	class PegButton extends JButton {
		private Ellipse2D border;
		private boolean highlighted;

		public PegButton(Position pos, Color color, boolean highlighted) {
			this.highlighted = highlighted;
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
				this.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						GamePanel.this.repaintButtons = true;
						GamePanel.this.repaint();
						game.select(pos);
					}
				});
			}
		}
		
		public boolean contains(int x, int y) {
			return border.contains(x, y);
		}
	}

	class HighlightButton extends JButton {
		private Ellipse2D border;

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

			this.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					game.movePeg(position);
					GamePanel.this.repaintButtons = true;
					GamePanel.this.repaint();
				}
			});
		}

		public boolean contains(int x, int y) {
			return border.contains(x, y);
		}
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
		if (game.winningPlayer() != null) {
			repaint();
		} else if (game.getCurrentPlayer().isComputer()) {
			Move move = game.getTurn();
			if (move == null) {
				game.endTurn();
				repaintButtons = true;
				repaint();
				Timer timer = new Timer(100, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						renderComputerMoves();
					}
				});
				timer.setRepeats(false);
				timer.start();
			} else {
				game.movePeg(move);
				repaintButtons = true;
				repaint();
				Timer timer = new Timer(100, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						renderComputerMoves();
					}
				});
				timer.setRepeats(false);
				timer.start();
			}
		}
	}

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

	private final ActionListener saveAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			save();
		}
	};

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

	private static final int BUTTON_LEFT = 920;
	private static final int BUTTON_BOTTOM = 570;
	private static final int SPACING = 80;
	private static final int BUTTON_HEIGHT = 60;

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
		end = new JButton("End Turn");
		end.setBounds(BUTTON_LEFT, BUTTON_BOTTOM - SPACING * 4, 254, BUTTON_HEIGHT);
		style(end);
		if (!game.canEndTurn() || game.getCurrentPlayer() instanceof ComputerStrategy) {
			styleDisabled(end);
		}
		end.addActionListener(endAction);
		this.add(end);
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
		save = new JButton("Save");
		save.setBounds(BUTTON_LEFT, BUTTON_BOTTOM - SPACING * 2, 254, BUTTON_HEIGHT);
		save.addActionListener(saveAction);
		style(save);
		if (game.winningPlayer() != null) {
			styleDisabled(save);
		}
		this.add(save);
		quit = new JButton("Quit");
		quit.setBounds(BUTTON_LEFT, BUTTON_BOTTOM - SPACING, 254, BUTTON_HEIGHT);
		quit.addActionListener(quitAction);
		style(quit);
		this.add(quit);
		exit = new JButton("Exit");
		exit.setBounds(BUTTON_LEFT, BUTTON_BOTTOM, 254, BUTTON_HEIGHT);
		exit.addActionListener(exitAction);
		style(exit);
		this.add(exit);
	}

	GamePanel(GUI gui, Game game, int WIDTH, int HEIGHT) {
		this.gui = gui;
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.game = game;
		Player[] players = game.getPlayers();
		// TODO center the labels
		for (Player p : players) {
			if (p != null) {
				if (p.getColor().equals(Color.RED)) {
					JLabel name = new JLabel(p.getName());
					name.setForeground(Color.RED);
					name.setBounds(410, 30, 1000, 30);
					name.setFont(Util.getBigFont());
					this.add(name);
				} else if (p.getColor().equals(Color.BLACK)) {
					JLabel name = new JLabel(p.getName());
					name.setForeground(Color.BLACK);
					name.setBounds(560, 170, 1000, 30);
					name.setFont(Util.getBigFont());
					this.add(name);
				} else if (p.getColor().equals(Color.GREEN)) {
					JLabel name = new JLabel(p.getName());
					name.setForeground(Color.GREEN);
					name.setBounds(560, 515, 1000, 30);
					name.setFont(Util.getBigFont());
					this.add(name);
				} else if (p.getColor().equals(Color.BLUE)) {
					JLabel name = new JLabel(p.getName());
					name.setForeground(Color.BLUE);
					name.setBounds(410, 650, 1000, 30);
					name.setFont(Util.getBigFont());
					this.add(name);
				} else if (p.getColor().equals(Color.WHITE)) {
					JLabel name = new JLabel(p.getName());
					name.setForeground(Color.WHITE);
					name.setBounds(210, 515, 1000, 30);
					name.setFont(Util.getBigFont());
					this.add(name);
				} else if (p.getColor().equals(Color.YELLOW)) {
					JLabel name = new JLabel(p.getName());
					name.setForeground(Color.YELLOW);
					name.setBounds(210, 170, 1000, 30);
					name.setFont(Util.getBigFont());
					this.add(name);
				}
			}
		}
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setLayout(null);
		repaintButtons = true;
		renderComputerMoves();
	}


	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(GUI.getImageLoader().getCommonImages().getBackground(), 0, 0, null);
		g.drawImage(GUI.getImageLoader().getGamePanelImages().getBoard(), 0, 0, null);
		g.drawImage(GUI.getImageLoader().getGamePanelImages().getBigScroll(), 0, 0, null);

		g.setFont(Util.getFont().deriveFont(18f));
		if (game.gameOver()) {
			g.drawString(game.winningPlayer().getName() + " won!", BUTTON_LEFT - 10, 100);
		} else {
			if (game.getCurrentPlayer().isComputer()) {
				g.drawString(game.getCurrentPlayer().getName(), BUTTON_LEFT - 10, 100);
				g.drawString("is thinking", BUTTON_LEFT - 10, 130);
			} else {
				g.drawString(game.getCurrentPlayer().getName() + "'s turn", BUTTON_LEFT - 10, 100);
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
			game.getNonClickablePegs().forEach((position, color) -> {
				this.add(new PegButton(position, color, false));
			});

			game.getClickablePegs().forEach((position) -> {
				this.add(new PegButton(position, game.getCurrentPlayer().getColor(), true));
			});
			if (!game.getCurrentPlayer().isComputer()) {
				for (Position p : game.getPossibleMoves()) {
					this.add(new HighlightButton(p));
				}
			}
			initButtons();
			repaintButtons = false;
		}
	};
}
