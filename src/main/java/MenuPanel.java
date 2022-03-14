import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.Timer;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

// Marty
public class MenuPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private boolean shuffle = false;
	private boolean scrolled;

	private JTextField name;

	private JComboBox difficulty;


	private PlayerButton P1;
	private PlayerButton P2;
	private PlayerButton P3;
	private PlayerButton P4;
	private PlayerButton P5;
	private PlayerButton P6;
	private JButton start;
	private JButton load;
	private JButton exit;
	private JButton done;
	private JButton shuffler;

	private Font bigFont;
	private Font playerFont;
	private GUI gui;

	private class PlayerButton extends JButton {
		private Util.PlayerType playerType = Util.PlayerType.NONE;
		private PlayerOptionsPanel playerOptionsPanel;

		public PlayerButton(int playerNumber) {
//			ImageIcon icon = getPlayerCloudIcon(playerNumber, playerType);
//			this.setIcon(icon);
			this.setFocusPainted(false);
			this.setBorderPainted(false);
			this.setContentAreaFilled(false);


			this.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ImageIcon i = GUI.getImageLoader().getMenuPanelImages().getPlayerCloudIcon(playerNumber, playerType);
					PlayerButton.this.setIcon(i);
				}
			});
		}

		public void setPlayerType(Util.PlayerType playerType) {
			this.playerType = playerType;
		}
	}

	MenuPanel(GUI gui) {
		this.gui = gui;
		setPreferredSize(new Dimension(1280,720));

		this.setLayout(null);

		PlayerOptionsPanel p = new PlayerOptionsPanel(1);
		p.setLayout(new BorderLayout());
		p.setBounds(0,0, 1280, 720);
		this.add(p);
//		shuffler = new JButton("");
//		shuffler.setBounds(335,115,20,20);
//		shuffler.setActionCommand("Shuffler");
//		shuffler.addActionListener(this);
//		shuffler.setContentAreaFilled(false);
//		shuffler.setBorderPainted(false);
//		this.add(shuffler);
//
////		P1 = new PlayerButton(1);
////		P1.setBounds(100, 125, 180, 80);
////		this.add(P1);
////
////		P2 = new PlayerButton(2);
////		P2.setBounds(340,180,180,80);
////		this.add(P2);
////
////		P3 = new PlayerButton(3);
////		P3.setBounds(158,258,180,80);
////		this.add(P3);
////
////		P4 = new PlayerButton(4);
////		P4.setBounds(405,312,180,80);
////		this.add(P4);
////
////		P5 = new PlayerButton(5);
////		P5.setBounds(225,410,180,80);
////		this.add(P5);
////
////		P6 = new PlayerButton(6);
////		P6.setBounds(458,450,180,80);
////		this.add(P6);
//
//		start = new JButton("");
//		start.setBounds(540,605,180,80);
//		start.setActionCommand("startB");
//		start.addActionListener(this);
//		start.setContentAreaFilled(false);
//		start.setBorderPainted(false);
//		this.add(start);
//
//		load = new JButton("");
//		load.setBounds(195,597,180,80);
//		load.setActionCommand("loadB");
//		load.addActionListener(this);
//		load.setContentAreaFilled(false);
//		load.setBorderPainted(false);
//		this.add(load);
//
//		exit = new JButton("");
//		exit.setBounds(872,597,180,80);
//		exit.setActionCommand("exitB");
//		exit.addActionListener(this);
//		exit.setContentAreaFilled(false);
//		exit.setBorderPainted(false);
//		this.add(exit);
//
//		nameInstruct = new JLabel("Enter your name:");
//		nameInstruct.setFont(bigFont);
//		nameInstruct.setBounds(813,382,300,60);
//		this.add(nameInstruct);
//		nameInstruct.setVisible(false);
//
//		comInstruct = new JLabel("Set Difficulty:");
//		comInstruct.setBounds(817,382,300,60);
//		comInstruct.setFont(bigFont);
//		this.add(comInstruct);
//		comInstruct.setVisible(false);
//
//		shuffleLabel = new JLabel("Shuffle Colors");
//		shuffleLabel.setBounds(380,110,200,30);
//		shuffleLabel.setFont(bigFont);
//		this.add(shuffleLabel);
//		shuffleLabel.setVisible(true);
//
//		//////////////////////////////////////////////
//
//		String feed[] = {"Easier", "Harder"};
//		difficulty = new JComboBox(feed);
//		difficulty.setBounds(750,430,255,30);
//		this.add(difficulty);
//		difficulty.setActionCommand("difficulty");
//		difficulty.addActionListener(this);
//		difficulty.setVisible(false);
//
//		name = new JTextField("   ");
//		name.setBounds(750,430,255,30);
//		this.add(name);
//		name.setActionCommand("name");
//		name.addActionListener(this);
//		name.setVisible(false);
//
//		///////////////////////////////////////////////
//
//		done = new JButton("Done");
//		done.setBounds(770,465,200,50);
//		done.setFont(bigFont);
//		this.add(done);
//		done.setActionCommand("done");
//		done.addActionListener(this);
//		done.setContentAreaFilled(false);
//		done.setVisible(false);
//
//		playerNum = new JLabel("");
//		playerNum.setBounds(820,200,250,30);
//		playerNum.setFont(playerFont);
//		this.add(playerNum);
//		playerNum.setVisible(false);
		repaint();

	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		setFont(bigFont);
		g.drawImage(GUI.getImageLoader().getCommonImages().getBackground(), 0, 0, this);
	}
	public void actionPerformed(ActionEvent e) {
		String eventName = e.getActionCommand();
		String dif;
		String text;
		
		if (eventName.equals("loadB")) {
			// loads save
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					".chcr save file", "chcr");
			chooser.setFileFilter(filter);
			chooser.setAcceptAllFileFilterUsed(false);
			int returnVal = chooser.showOpenDialog(null);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				try {
					Game game = GameLoader.readGameFromFile(chooser.getSelectedFile().getPath());
					gui.switchToGamePanel(game);
				} catch (IOException ex) {
					// TODO display error message
					ex.printStackTrace();
				}
			}
		}
		
		if (eventName.equals("exitB")) {
			// TODO Insert exit are you sure start up.
			System.exit(0);
		}
		
		if (eventName.equals("Shuffler")) {
			if (shuffle) {
				shuffle = false;
			} else if (!shuffle) {
				shuffle = true;
			}
		}
		repaint();
	}
	private void getImages() {

	}
	private void drawImages(Graphics g) {
		if (!scrolled) {
			g.drawImage(GUI.getImageLoader().getMenuPanelImages().getLogoBig(), 0,0,this);
			g.drawImage(GUI.getImageLoader().getMenuPanelImages().getDragon(),0,0,this);
		} 
		if (scrolled) {
			g.drawImage(GUI.getImageLoader().getMenuPanelImages().getSmallScroll(),0,0,this);
			g.drawImage(GUI.getImageLoader().getMenuPanelImages().getLogoSmall(),0,0,this);
		}
		
		g.drawImage(GUI.getImageLoader().getMenuPanelImages().getStart(),0,0,this);
		g.drawImage(GUI.getImageLoader().getMenuPanelImages().getLoad(),0,0,this);
		g.drawImage(GUI.getImageLoader().getMenuPanelImages().getExit(),0,0,this);
	}
}

	
