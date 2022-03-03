import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.geom.*;
import java.awt.Color;
import java.util.ArrayList;

public class GamePanel extends JPanel {
	private Game game;
	private int WIDTH, HEIGHT;
	private Peg selectedPeg;
	private boolean moved;
	private JButton end, undo, save, quit, exit;
	private Image backgroundImage, boardImage, menuImage;
	int BUTTON_LEFT = 920;
	int BUTTON_BOTTOM = 570;
	int SPACING = 80;
	int BUTTON_HEIGHT = 60;
	boolean repaintButtons = false;
	
	private void style(JButton button) {
		button.setContentAreaFilled(false);
		button.setFont(CustomFont.getFont().deriveFont(18f));
		button.setFocusPainted(false);
	}
	
	private void styleDisabled(JButton button) {
		button.setForeground(Color.GRAY);
		button.setEnabled(false);
	}
	
	private void styleEnabled(JButton button) {
		button.setForeground(Color.BLACK);
		button.setEnabled(true);
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

			this.positionHighlight = new ImageIcon(ImageIO.read(new File("./images/board/highlight.PNG")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private ImageIcon getImageIcon(Color color, boolean highlighted) {
		if (color.equals(Color.RED)) {
			return highlighted ? redPeg : redPegHighlighted;
		} else if (color.equals(Color.BLACK)) {
			return highlighted ? blackPeg : blackPegHighlighted;
		} else if (color.equals(Color.BLUE)) {
			return highlighted ? bluePeg : bluePegHighlighted;
		} else if (color.equals(Color.GREEN)) {
			return highlighted ? greenPeg : greenPegHighlighted;
		} else if (color.equals(Color.WHITE)) {
			return highlighted ? whitePeg : whitePegHighlighted;
		} else if (color.equals(Color.YELLOW)) {
			return highlighted ? yellowPeg : yellowPegHighlighted;
		}
		return null;
	}



	class PegButton extends JButton {
		private Ellipse2D border;
		private boolean highlighted;
		private Peg peg;

		public PegButton(Peg peg, int row, int column, boolean highlighted) {
			this.highlighted = highlighted;
			this.peg = peg;
			// Image is a square with a circle
			ImageIcon icon = getImageIcon(peg.getOwner().getColor(), highlighted);
			int RADIUS = icon.getIconHeight();
//			this.setFocusPainted(false);
//			this.setIcon(icon);
//			this.setBorderPainted(false);
//	        this.setContentAreaFilled(false);

			Position pixels = getPixels(row, column, RADIUS);
			this.setBounds(pixels.getRow(), pixels.getColumn(), RADIUS, RADIUS);

			this.border = new Ellipse2D.Float(0, 0, RADIUS, RADIUS);
			if (highlighted) {
				this.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						GamePanel.this.selectedPeg = peg;
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

		public HighlightButton(Position position) {
			// Image is a square with a circle
			ImageIcon icon = positionHighlight;
			int RADIUS = icon.getIconHeight();
			this.setFocusPainted(false);
			this.setIcon(icon);
			this.setBorderPainted(false);
			this.setContentAreaFilled(false);
			//this.setRolloverIcon(icon);

			Position pixels = getPixels(position.getRow(), position.getColumn(), RADIUS);

			this.setBounds(pixels.getRow(), pixels.getColumn(), RADIUS, RADIUS);

			border = new Ellipse2D.Float(0, 0, RADIUS, RADIUS);

			this.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					game.movePeg(new Move(selectedPeg.getPos(), position, game.getCurrentPlayer()));
				}
			});
		}

		public boolean contains(int x, int y) {
			return border.contains(x, y);
		}
	}

	private ActionListener quitAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			int confirmed = JOptionPane.showConfirmDialog(GamePanel.this,
					"Are you sure you want to exit the program?",
					"Exit", JOptionPane.YES_NO_OPTION);
			if (confirmed == JOptionPane.YES_OPTION) {
				save();
			}
		}
	};

	private ActionListener endAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			selectedPeg = null;
			moved = false;
		}
	};

	private ActionListener undoAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			game.undo();
		}
	};

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

	private ActionListener saveAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			save();
		}
	};

	private void initButtons() {
		end = new JButton("End Turn");
		end.setBounds(BUTTON_LEFT, BUTTON_BOTTOM - SPACING * 4, 254, BUTTON_HEIGHT);
		style(end);
		if (this.moved) {
			styleDisabled(end);
		}
		end.addActionListener(endAction);
		this.add(end);
		undo = new JButton("Undo");
		undo.setBounds(BUTTON_LEFT, BUTTON_BOTTOM - SPACING * 3, 254, BUTTON_HEIGHT);
		style(undo);
		styleDisabled(undo);
		undo.addActionListener(undoAction);
		this.add(undo);
		save = new JButton("Save");
		save.setBounds(BUTTON_LEFT, BUTTON_BOTTOM - SPACING * 2, 254, BUTTON_HEIGHT);
		save.addActionListener(saveAction);
		style(save);
		save.addActionListener(saveAction);
		this.add(save);
		quit = new JButton("Quit");
		quit.setBounds(BUTTON_LEFT, BUTTON_BOTTOM - SPACING, 254, BUTTON_HEIGHT);
		quit.addActionListener(quitAction);
		style(quit);
		this.add(quit);
		exit = new JButton("Exit");
		exit.setBounds(BUTTON_LEFT, BUTTON_BOTTOM, 254, BUTTON_HEIGHT);
		style(exit);
		this.add(exit);
	}

	GamePanel(Game game, int WIDTH, int HEIGHT) {

		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.game = game;
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setLayout(null);
		initIcons();
		initButtons();

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
	}


	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (repaintButtons) {
			for (int i = 0; i < this.getComponentCount(); i++) {
				if (this.getComponents()[i] instanceof PegButton || this.getComponents()[i] instanceof HighlightButton) {
//					this.remove(i);
//					i--;
				}
			}
			Peg[][] pegs = game.getBoard().getPegs();
			for (int i = 0; i < pegs.length; i++) {
				for (int j = 0; j < pegs[i].length; j++) {
					if (pegs[i][j] != null) {
						PegButton pb = new PegButton(pegs[i][j], i, j,selectedPeg == null && pegs[i][j].getOwner().getColor().equals(game.getCurrentPlayer().getColor()));
						this.add(pb);
					}
				}
			}
			if (selectedPeg != null) {
				ArrayList<Position> highlighted = game.getBoard().possibleMoves(selectedPeg.getPos(), false);
				for (int i = 0; i < highlighted.size(); i++) {
					this.add(new HighlightButton(highlighted.get(i)));
				}
			}
			repaintButtons = false;
		}

		//g.drawString(game.getCurrentPlayer().getName(), BUTTON_LEFT, BUTTON_BOTTOM);

		g.drawImage(backgroundImage, 0, 0, null);
		g.drawImage(boardImage, 0, 0, null);
		g.drawImage(menuImage, 0, 0, null);

	};
}
