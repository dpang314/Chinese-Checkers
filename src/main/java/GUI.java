import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
	private MenuPanel menuPanel;
	private GamePanel gamePanel;
	private Game game;
	private JFrame frame;
	private static ImageLoader imageLoader = new ImageLoader();
	private static String toGame = "Switch to game panel";
	private static String toMenu = "Switch to menu panel";

	public GUI() {
		frame = new JFrame();
		switchToMenuPanel();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static ImageLoader getImageLoader() {
		return imageLoader;
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
		frame.setContentPane(new PlayerOptionsPanel(1));
		frame.pack();
	}

	public void close() {
		frame.dispose();
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame.setDefaultLookAndFeelDecorated(true);
				new GUI();
			}
    });
  }
}
