import javax.imageio.ImageIO;
import javax.swing.*;
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
import java.util.concurrent.TimeUnit;

public class GamePanel extends JPanel {
	private Game game;
	private GUI gui;
	private int WIDTH, HEIGHT;
	private Position selectedPosition;
	private boolean moved = false;
	private boolean movedAdjacent;

	private JButton end, undo, save, quit, exit;
	private Image backgroundImage, boardImage, menuImage;



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

	private ImageIcon blackPeg, bluePeg, greenPeg, redPeg, whitePeg, yellowPeg;
	private ImageIcon redPegHighlighted, blackPegHighlighted, greenPegHighlighted, bluePegHighlighted, whitePegHighlighted, yellowPegHighlighted;
	private ImageIcon positionHighlight;

	private void initIcons() {
		try {
			Image black = ImageIO.read(new File("images/board/pegBlack.PNG"))
					.getScaledInstance(24, 24, Image.SCALE_DEFAULT);
			Image blue = ImageIO.read(new File("images/board/pegBlue.PNG"))
					.getScaledInstance(24, 24, Image.SCALE_DEFAULT);
			Image green = ImageIO.read(new File("images/board/pegGreen.PNG"))
					.getScaledInstance(24, 24, Image.SCALE_DEFAULT);
			Image red = ImageIO.read(new File("images/board/pegRed.PNG"))
					.getScaledInstance(24, 24, Image.SCALE_DEFAULT);
			Image white = ImageIO.read(new File("images/board/pegWhite.PNG"))
					.getScaledInstance(24, 24, Image.SCALE_DEFAULT);
			Image yellow = ImageIO.read(new File("images/board/pegYellow.PNG"))
					.getScaledInstance(24, 24, Image.SCALE_DEFAULT);

			Image blackH = ImageIO.read(new File("images/board/pegBlackH.PNG"))
					.getScaledInstance(24, 24, Image.SCALE_DEFAULT);
			Image blueH = ImageIO.read(new File("images/board/pegBlueH.PNG"))
					.getScaledInstance(24, 24, Image.SCALE_DEFAULT);
			Image greenH = ImageIO.read(new File("images/board/pegGreenH.PNG"))
					.getScaledInstance(24, 24, Image.SCALE_DEFAULT);
			Image redH = ImageIO.read(new File("images/board/pegRedH.PNG"))
					.getScaledInstance(24, 24, Image.SCALE_DEFAULT);
			Image whiteH = ImageIO.read(new File("images/board/pegWhiteH.PNG"))
					.getScaledInstance(24, 24, Image.SCALE_DEFAULT);
			Image yellowH = ImageIO.read(new File("images/board/pegYellowH.PNG"))
					.getScaledInstance(24, 24, Image.SCALE_DEFAULT);

			this.blackPeg = new ImageIcon(black);
			this.bluePeg = new ImageIcon(blue);
			this.greenPeg = new ImageIcon(green);
			this.redPeg = new ImageIcon(red);
			this.whitePeg = new ImageIcon(white);
			this.yellowPeg = new ImageIcon(yellow);

			this.blackPegHighlighted = new ImageIcon(blackH);
			this.bluePegHighlighted = new ImageIcon(blueH);
			this.greenPegHighlighted = new ImageIcon(greenH);
			this.redPegHighlighted = new ImageIcon(redH);
			this.whitePegHighlighted = new ImageIcon(whiteH);
			this.yellowPegHighlighted = new ImageIcon(yellowH);

			this.positionHighlight = new ImageIcon(ImageIO.read(new File("./images/board/highlight.PNG")).getScaledInstance(32, 32, Image.SCALE_DEFAULT));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private ImageIcon getImageIcon(Color color, boolean highlighted) {
		if (color.equals(Color.RED)) {
			return highlighted ? redPegHighlighted : redPeg;
		} else if (color.equals(Color.BLACK)) {
			return highlighted ? blackPegHighlighted : blackPeg;
		} else if (color.equals(Color.BLUE)) {
			return highlighted ? bluePegHighlighted : bluePeg;
		} else if (color.equals(Color.GREEN)) {
			return highlighted ? greenPegHighlighted : greenPeg;
		} else if (color.equals(Color.WHITE)) {
			return highlighted ? whitePegHighlighted : whitePeg;
		} else if (color.equals(Color.YELLOW)) {
			return highlighted ? yellowPegHighlighted : yellowPeg;
		}
		return null;
	}



	class PegButton extends JButton {
		private Ellipse2D border;
		private boolean highlighted;

		public PegButton(Position pos, Color color, boolean highlighted) {
			this.highlighted = highlighted;
			// Image is a square with a circle
			ImageIcon icon = getImageIcon(color, highlighted);
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
						GamePanel.this.selectedPosition = pos;
						GamePanel.this.repaintButtons = true;
						GamePanel.this.repaint();
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

		public HighlightButton(Position position, boolean adjacent) {
			// Image is a square with a circle
			ImageIcon icon = positionHighlight;
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
					game.movePeg(new Move(selectedPosition, position, game.getCurrentPlayer()));
					GamePanel.this.repaintButtons = true;
					GamePanel.this.repaint();
					selectedPosition = position;
					moved = true;
					if (adjacent) {
						movedAdjacent = true;
					}
				}
			});
		}

		public boolean contains(int x, int y) {
			return border.contains(x, y);
		}
	}

	private void save() {
		int confirmed = JOptionPane.showConfirmDialog(GamePanel.this,
				"Do you want to save your game?",
				"Save", JOptionPane.YES_NO_OPTION);
		if (confirmed == JOptionPane.YES_OPTION) {
			JFileChooser fileChooser = new JFileChooser();
			if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				try {
					GameLoader.writeGameToFile(game, file.getPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private boolean gameOver = false;

	private void renderComputerMoves() {
		if (game.winningPlayer() != null) {
			gameOver = true;
			repaint();
		} else if (game.getCurrentPlayer() instanceof QuinnStrategy || game.getCurrentPlayer() instanceof ArushiStrategy || game.getCurrentPlayer() instanceof ComputerStratBasic) {
			Move move = game.getTurn();
			if (move == null) {
				reset();
				game.endTurn();
				repaintButtons = true;
				repaint();
				renderComputerMoves();
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

	private void reset() {
		selectedPosition = null;
		moved = false;
		movedAdjacent = false;
	}

	private final ActionListener endAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			reset();
			game.endTurn();
			repaintButtons = true;
			repaint();
			renderComputerMoves();
		}
	};

	private final ActionListener undoAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			reset();
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
				save();
				gui.switchToMenuPanel();
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
				save();
				gui.close();
			}
		}
	};

	private static final int BUTTON_LEFT = 920;
	private static final int BUTTON_BOTTOM = 570;
	private static final int SPACING = 80;
	private static final int BUTTON_HEIGHT = 60;

	private void style(JButton button) {
		button.setContentAreaFilled(false);
		button.setFont(CustomFont.getFont().deriveFont(18f));
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
		if (!this.moved || gameOver) {
			styleDisabled(end);
		}
		end.addActionListener(endAction);
		this.add(end);
		undo = new JButton("Undo");
		undo.setBounds(BUTTON_LEFT, BUTTON_BOTTOM - SPACING * 3, 254, BUTTON_HEIGHT);
		style(undo);
		if (!this.moved || gameOver) {
			styleDisabled(undo);
		}
		undo.addActionListener(undoAction);
		this.add(undo);
		save = new JButton("Save");
		save.setBounds(BUTTON_LEFT, BUTTON_BOTTOM - SPACING * 2, 254, BUTTON_HEIGHT);
		save.addActionListener(saveAction);
		style(save);
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
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setLayout(null);
		initIcons();

		try {
			boardImage = ImageIO.read(new File("./images/board/board.PNG"))
					.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT);
			backgroundImage = ImageIO.read(new File("./images/background.PNG"))
					.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT);
			menuImage = ImageIO.read(new File("./images/menu.PNG"))
					.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		repaintButtons = true;
		renderComputerMoves();
	}


	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(backgroundImage, 0, 0, null);
		g.drawImage(boardImage, 0, 0, null);
		g.drawImage(menuImage, 0, 0, null);

		g.setFont(CustomFont.getFont().deriveFont(20f));
		if (gameOver) {
			g.drawString(game.winningPlayer().getName() + " won!", BUTTON_LEFT, 100);
		} else {
			g.drawString(game.getCurrentPlayer().getName() + "'s turn", BUTTON_LEFT, 100);
		}
		if (repaintButtons) {
			for (int i = 0; i < this.getComponentCount(); i++) {
				if (this.getComponents()[i] instanceof JButton) {
					this.remove(i);
					i--;
				}
			}


			for (int i = 0; i < game.getPlayers().length; i++) {
				if (game.getPlayers()[i] != null) {
					for (Position pos : game.getPlayers()[i].posArr) {
						PegButton pb = new PegButton(pos, game.getPlayers()[i].getColor(),
								(moved == false && game.getPlayers()[i].getColor().equals(game.getCurrentPlayer().getColor())) ||
										(moved && !movedAdjacent && pos.equals(selectedPosition)));
						this.add(pb);
					}
				}
			}
			if (selectedPosition != null) {
				if (moved) {
					if (!movedAdjacent) {
						ArrayList<Position> jump = game.getBoard().possibleJumpMoves(selectedPosition);
						for (int i = 0; i < jump.size(); i++) {
							this.add(new HighlightButton(jump.get(i), false));
						}
					}
				} else {
					ArrayList<Position> jump = game.getBoard().possibleJumpMoves(selectedPosition);
					ArrayList<Position> adjacent = game.getBoard().possibleAdjacentMoves(selectedPosition);
					for (int i = 0; i < jump.size(); i++) {
						this.add(new HighlightButton(jump.get(i), false));
					}
					for (int i = 0; i < adjacent.size(); i++) {
						this.add(new HighlightButton(adjacent.get(i), true));
					}
				}
			}
			initButtons();
			repaintButtons = false;
		}
	};
}
