import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.geom.*;
import java.awt.Color;

public class GamePanel extends JPanel {
	Game game;
	Image backgroundImage;
	Image boardImage;
	Image menuImage;
	private Peg selectedPeg;
	private JButton end, undo, save, quit, exit;
	int BUTTON_LEFT = 920;
	int BUTTON_BOTTOM = 570;
	int SPACING = 80;
	int BUTTON_HEIGHT = 60;
	
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

	class PegButton extends JButton {
		private int RADIUS;
		private Ellipse2D border;
		public PegButton(int row, int column, ImageIcon normal) {
			// Image is a square with a circle
			RADIUS = normal.getIconHeight();
			this.setFocusPainted(false);
			this.setBounds(row * 100, column * 100, RADIUS, RADIUS);
			this.setIcon(normal);
			this.setBorderPainted(false);
	        this.setContentAreaFilled(false);
	        this.setRolloverIcon(normal);
			border = new Ellipse2D.Float(0, 0, RADIUS, RADIUS);
		}
		
		public boolean contains(int x, int y) {
			return border.contains(x, y);
		}
	}
	
	GamePanel(Game game) {
		this.game = game;
		setPreferredSize(new Dimension(1280, 720));
		setLayout(null);
		end = new JButton("End Turn");
		end.setBounds(920, BUTTON_BOTTOM - SPACING * 4, 254, BUTTON_HEIGHT);
		style(end);
		styleDisabled(end);
		this.add(end);
		undo = new JButton("Undo");
		undo.setBounds(920, BUTTON_BOTTOM - SPACING * 3, 254, BUTTON_HEIGHT);
		style(undo);
		styleDisabled(undo);
		this.add(undo);
		save = new JButton("Save");
		save.setBounds(920, BUTTON_BOTTOM - SPACING * 2, 254, BUTTON_HEIGHT);
		style(save);
		this.add(save);
		quit = new JButton("Quit");
		quit.setBounds(920, BUTTON_BOTTOM - SPACING, 254, BUTTON_HEIGHT);
		style(quit);
		this.add(quit);
		exit = new JButton("Exit");
		exit.setBounds(920, BUTTON_BOTTOM, 254, BUTTON_HEIGHT);
		style(exit);
		this.add(exit);
		BufferedImage background = null;
		BufferedImage menu = null;		
		BufferedImage board = null;
		Image pegImage = null;
		try {
			board = ImageIO.read(new File("./images/board.PNG"));
			background = ImageIO.read(new File("./images/background.PNG"));
			menu = ImageIO.read(new File("./images/menu.PNG"));
			pegImage = ImageIO.read(new File("./images/peg.PNG"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		backgroundImage = background.getScaledInstance(1280, 720, Image.SCALE_DEFAULT);
		boardImage = board.getScaledInstance(1280, 720, Image.SCALE_DEFAULT);
		menuImage = menu.getScaledInstance(1280, 720, Image.SCALE_DEFAULT);		 
		pegImage = pegImage.getScaledInstance(24, 24, Image.SCALE_DEFAULT);
		ImageIcon pegIcon = new ImageIcon(pegImage);
		PegButton peg = new PegButton(1, 1, pegIcon);
		this.add(peg);
	}
	
	private boolean changed = false;
	private boolean moved = false;
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (changed) {
			if (moved) {
				this.remove(end);
				styleEnabled(end);
				this.add(end);
				this.remove(undo);
				styleEnabled(undo);
				this.add(end);
			}
		}
		//end, undo, save, quit, exit;
		this.revalidate();
		g.drawImage(backgroundImage, 0, 0, null);
		g.drawImage(boardImage, 0, 0, null);
		g.drawImage(menuImage, 0, 0, null);
		
	};
}
