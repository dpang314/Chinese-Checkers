import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener{
	private Game game;
	private MenuPanel menuPanel;
	private GamePanel gamePanel;
	private JFrame frame;
	
	public GUI() {
		CustomFont.load();
//		game = new Game(null, false);
		menuPanel = new MenuPanel();
//		gamePanel = new GamePanel(game);
		frame = new JFrame();
		frame.setContentPane(menuPanel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("swap")) {
			frame.setContentPane(gamePanel);
			frame.pack();
		}
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
