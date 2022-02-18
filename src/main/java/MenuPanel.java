import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;
// Marty
public class MenuPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private Player[] players;
	private boolean shuffle;
	private Image background;
	// images
	private Image plyN1;
	private Image plyR1;
	private Image plyG1;
	private Image plyD1;
	private Image plyN2;
	private Image plyR2;
	private Image plyG2;
	private Image plyD2;	
	private Image plyN3;
	private Image plyR3;
	private Image plyG3;
	private Image plyD3;
	private Image plyN4;
	private Image plyR4;
	private Image plyG4;
	private Image plyD4;	
	private Image plyN5;
	private Image plyR5;
	private Image plyG5;
	private Image plyD5;	
	private Image plyN6;
	private Image plyR6;
	private Image plyG6;
	private Image plyD6;
	private Image startI;
	private Image loadI;
	private Image exitI;
	private Image dragon;
	private Image logoBig;
	private Image logoSmall;
	private Image scroll;
	private Image arrow;
	// 0 = dark, 1 = Def, 2 = Human, 3 = Com,
	private int p1Int = 2;
	private int p2Int = 3;
	private int p3Int = 0;
	private int p4Int = 0;
	private int p5Int = 0;
	private int p6Int = 0;
	private int subject = 0;
	
	private boolean scrolled = false;
	private boolean humSelect = false;
	private boolean comSelect = false;
	private boolean nonSelect = false;
	private boolean ready = false;
	
	private JLabel none;
	private JLabel computer;
	private JLabel human;
	private JLabel playerNum;
	private JLabel nameInstruct;
	
	private JTextField name;
	
	private JComboBox difficulty;
	
	private JRadioButton hum;
	private JRadioButton com;
	private JRadioButton non;
	
	private JButton P1;
	private JButton P2;
	private JButton P3;
	private JButton P4;
	private JButton P5;
	private JButton P6;
	private JButton start;
	private JButton load;
	private JButton exit;
	private JButton done;
	
	private Font bigFont;
	
	MenuPanel() {
		setPreferredSize(new Dimension(1280,720));
		bigFont = new Font("Serif", Font.BOLD, 35);
		players = new Player[6];
		this.setLayout(null);
		P1 = new JButton("");
		P1.setBounds(100,125,180,80);
		P1.setActionCommand("player1");
		P1.addActionListener(this);
		P1.setContentAreaFilled(false);
		P1.setBorderPainted(false);
		this.add(P1);
		
		P2 = new JButton("");
		P2.setBounds(340,180,180,80);
		P2.setActionCommand("player2");
		P2.addActionListener(this);
		P2.setContentAreaFilled(false);
		P2.setBorderPainted(false);
		this.add(P2);
		P3 = new JButton("");
		P3.setBounds(158,258,180,80);
		P3.setActionCommand("player3");
		P3.addActionListener(this);
		P3.setContentAreaFilled(false);
		P3.setBorderPainted(false);
		this.add(P3);
		P4 = new JButton("");
		P4.setBounds(405,312,180,80);
		P4.setActionCommand("player4");
		P4.addActionListener(this);
		P4.setContentAreaFilled(false);
		P4.setBorderPainted(false);
		this.add(P4);
		P5 = new JButton("");
		P5.setBounds(225,410,180,80);
		P5.setActionCommand("player5");
		P5.addActionListener(this);
		P5.setContentAreaFilled(false);
		P5.setBorderPainted(false);
		this.add(P5);
		P6 = new JButton("");
		P6.setBounds(458,450,180,80);
		P6.setActionCommand("player6");
		P6.addActionListener(this);
		P6.setContentAreaFilled(false);
		P6.setBorderPainted(false);
		this.add(P6);
		
		start = new JButton("");
		start.setBounds(540,605,180,80);
		start.setActionCommand("startB");
		start.addActionListener(this);
		start.setContentAreaFilled(false);
		start.setBorderPainted(false);
		this.add(start);
		
		load = new JButton("");
		load.setBounds(195,597,180,80);
		load.setActionCommand("loadB");
		load.addActionListener(this);
		load.setContentAreaFilled(false);
		load.setBorderPainted(false);
		this.add(load);
		
		exit = new JButton("");
		exit.setBounds(872,597,180,80);
		exit.setActionCommand("exitB");
		exit.addActionListener(this);
		exit.setContentAreaFilled(false);
		exit.setBorderPainted(false);
		this.add(exit);
		
		hum = new JRadioButton();
		hum.setBounds(745,275,20,20);
		hum.setActionCommand("hum");
		hum.addActionListener(this);
		this.add(hum);
		hum.setVisible(false);
		
		non = new JRadioButton();
		non.setBounds(745,340,20,20);
		non.setActionCommand("non");
		non.addActionListener(this);
		this.add(non);
		non.setVisible(false);
		
		com = new JRadioButton();
		com.setBounds(745,405,20,20);
		com.setActionCommand("com");
		com.addActionListener(this);
		this.add(com);
		com.setVisible(false);
		
		human = new JLabel("Human");
		human.setBounds(800,275,300,60);
		this.add(human);
		human.setVisible(false);
		
		computer = new JLabel("Computer");
		computer.setBounds(820,355,300,60);
		this.add(computer);
		computer.setVisible(false);
		
		none = new JLabel("None");
		none.setBounds(820,355,300,60);
		this.add(none);
		none.setVisible(false);
		
		nameInstruct = new JLabel("Enter your name:");
		nameInstruct.setBounds(820,355,300,60);
		this.add(nameInstruct);
		none.setVisible(false);
		
		name = new JTextField();
		name.setBounds(600,500,250,70);
		this.add(name);
		name.setActionCommand("name");
		name.addActionListener(this);
		name.setVisible(false);
		
		difficulty = new JComboBox();
		difficulty.setBounds(600,500,250,70);
		this.add(difficulty);
		difficulty.setActionCommand("difficulty");
		difficulty.addActionListener(this);
		difficulty.setVisible(false);
		
		done = new JButton();
		done.setBounds(850, 580, 40, 90);
		this.add(done);
		done.setActionCommand("done");
		done.addActionListener(this);
		done.setVisible(false);
		
		getImages();
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setFont(bigFont);
		g.drawImage(background,0,0,this);
		drawImages(g);
		reaction(g);
		if (shuffle) {
			// shuffle the status
		}
		
	}
	public void actionPerformed(ActionEvent e) {
		String eventName = e.getActionCommand();
		String dif;
		String text;
		if (eventName.equals("player1")) {
			subject = 1;
			if (scrolled) {
				scrolled = false;
			} else if (!scrolled) {
				scrolled = true;
			}
		}
		if (eventName.equals("player2")) {
			System.out.println("Player 2");
			subject = 2;
		}
		if (eventName.equals("player3")) {
			System.out.println("Player 3");
			subject = 3;
		}
		if (eventName.equals("player4")) {
			System.out.println("Player 4");
			subject = 4;
		}
		if (eventName.equals("player5")) {
			System.out.println("Player 5");
			subject = 5;
		}
		if (eventName.equals("player6")) {
			System.out.println("Player 6");
			subject = 6;
		}
		
		if (eventName.equals("startB")) {
			System.out.println("Start");
			// set the game screen
			// pass data from players
		}
		
		if (eventName.equals("loadB")) {
			System.out.println("Load");
			// load code
		}
		
		if (eventName.equals("exitB")) {
			System.out.println("Exit");
			// are you sure
		}
		if (eventName.equals("hum")) {
			humSelect = true;
			comSelect = false;
			nonSelect = false;
		}
		if (eventName.equals("com")) {
			comSelect = true;
			humSelect = false;
			nonSelect = false;
		}
		if (eventName.equals("non")) {
			nonSelect = true;
			humSelect = false;
			comSelect = false;
		}
		if (eventName.equals("difficulty")) {
			done.setVisible(true);
		}
		// Major Done Button
		if (eventName.equals("done")) {
			if (comSelect) {
				dif = (String) difficulty.getSelectedItem();
				if (dif.equals("Easier")) {
					// create computer player
					// set the difficulty to Easier
				} else if (dif.equals("Harder")) {
					// create computer player
					// set the difficulty to Harder
				}
			}
			else if (humSelect) {
				text = name.getText();
				// create human player
				// set player name to whatever the text is
			}
		}
		
	}
	private void getImages() {
		// background
		ImageIcon bg = new ImageIcon("images/bg.png");
		background = bg.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		
		// 4 button colors for 1 - 6
		ImageIcon p1N = new ImageIcon("images/p1None.PNG");
		plyN1 = p1N.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p1R = new ImageIcon("images/p1 Com.png");
		plyR1 = p1R.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p1G = new ImageIcon("images/p1Hum.png");
		plyG1 = p1G.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p1D = new ImageIcon("images/p1Def.png");
		plyD1 = p1D.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		
		ImageIcon p2N = new ImageIcon("images/p2None.png");
		plyN2 = p2N.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p2R = new ImageIcon("images/p2Com.png");
		plyR2 = p2R.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p2G = new ImageIcon("images/p2Hum.png");
		plyG2 = p2G.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p2D = new ImageIcon("images/p2Def.png");
		plyD2 = p2D.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		
		ImageIcon p3N = new ImageIcon("images/p3None.png");
		plyN3 = p3N.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p3R = new ImageIcon("images/p3Com.png");
		plyR3 = p3R.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p3G = new ImageIcon("images/p3Hum.png");
		plyG3 = p3G.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p3D = new ImageIcon("images/p3Def.png");
		plyD3 = p3D.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		
		ImageIcon p4N = new ImageIcon("images/p4None.png");
		plyN4 = p4N.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p4R = new ImageIcon("images/p4Com.png");
		plyR4 = p4R.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p4G = new ImageIcon("images/p4Hum.png");
		plyG4 = p4G.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p4D = new ImageIcon("images/p4Def.png");
		plyD4 = p4D.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);

		ImageIcon p5N = new ImageIcon("images/p5None.png");
		plyN5 = p5N.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p5R = new ImageIcon("images/p5Com.png");
		plyR5 = p5R.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p5G = new ImageIcon("images/p5Hum.png");
		plyG5 = p5G.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p5D = new ImageIcon("images/p5Def.png");
		plyD5 = p5D.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		
		ImageIcon p6N = new ImageIcon("images/p6None.png");
		plyN6 = p6N.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p6R = new ImageIcon("images/p6Com.png");
		plyR6 = p6R.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p6G = new ImageIcon("images/p6Hum.png");
		plyG6 = p6G.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p6D = new ImageIcon("images/p6Def.png");
		plyD6 = p6D.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		
		// start load exit
		ImageIcon st = new ImageIcon("images/buttonStart.png");
		startI = st.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		
		ImageIcon ld = new ImageIcon("images/buttonLoad.png");
		loadI = ld.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		
		ImageIcon xt = new ImageIcon("images/buttonExit.png");
		exitI = xt.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		
		// visuals like scrolls dragons logos
		
		ImageIcon dr = new ImageIcon("images/dragon.png");
		dragon = dr.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		
		ImageIcon lb = new ImageIcon("images/logoBig.png");
		logoBig = lb.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		
		ImageIcon ls = new ImageIcon("images/logoSmall.png");
		logoSmall = ls.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		
		ImageIcon sc = new ImageIcon("images/scrollMenu.png");
		scroll = sc.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
////////////////////////////////////////////////////////////////////////////////////		
		ImageIcon ar = new ImageIcon("images/arrow.png");
		arrow = ar.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
	}
	private void drawImages(Graphics g) {

		repaint();
		if (!scrolled) {
			g.drawImage(logoBig,0,0,this);
			g.drawImage(dragon,0,0,this);
		} 
		if (scrolled) {
			g.drawImage(scroll,0,0,this);
			g.drawImage(logoSmall,0,0,this);
		}
		
		g.drawImage(startI,0,0,this);
		g.drawImage(loadI,0,0,this);
		g.drawImage(exitI,0,0,this);
		
		if (p1Int == 0) {
			g.drawImage(plyN1,0,0,this);
		} else if (p1Int == 1) {
			g.drawImage(plyD1,0,0,this);
		} else if (p1Int == 2) {
			g.drawImage(plyG1,0,0,this);
		} else if (p1Int == 3) {
			g.drawImage(plyR1,0,0,this);
		}
		
		if (p2Int == 0) {
			g.drawImage(plyN2,0,0,this);
		} else if (p2Int == 1) {
			g.drawImage(plyD2,0,0,this);
		} else if (p2Int == 2) {
			g.drawImage(plyG2,0,0,this);
		} else if (p2Int == 3) {
			g.drawImage(plyR2,0,0,this);
		}
		
		if (p3Int == 0) {
			g.drawImage(plyN3,0,0,this);
		} else if (p3Int == 1) {
			g.drawImage(plyD3,0,0,this);
		} else if (p3Int == 2) {
			g.drawImage(plyG3,0,0,this);
		} else if (p3Int == 3) {
			g.drawImage(plyR3,0,0,this);
		}
		
		if (p4Int == 0) {
			g.drawImage(plyN4,0,0,this);
		} else if (p4Int == 1) {
			g.drawImage(plyD4,0,0,this);
		} else if (p4Int == 2) {
			g.drawImage(plyG4,0,0,this);
		} else if (p4Int == 3) {
			g.drawImage(plyR4,0,0,this);
		}
		
		if (p5Int == 0) {
			g.drawImage(plyN5,0,0,this);
		} else if (p5Int == 1) {
			g.drawImage(plyD5,0,0,this);
		} else if (p5Int == 2) {
			g.drawImage(plyG5,0,0,this);
		} else if (p5Int == 3) {
			g.drawImage(plyR5,0,0,this);
		}
		
		if (p6Int == 0) {
			g.drawImage(plyN6,0,0,this);
		} else if (p6Int == 1) {
			g.drawImage(plyD6,0,0,this);
		} else if (p6Int == 2) {
			g.drawImage(plyG6,0,0,this);
		} else if (p6Int == 3) {
			g.drawImage(plyR6,0,0,this);
		}
		
	}
	private void reaction(Graphics g) {
		if (scrolled) {
			non.setVisible(true);
			com.setVisible(true);
			hum.setVisible(true);
			none.setVisible(true);
			human.setVisible(true);
			computer.setVisible(true);
			
		}
		if (humSelect) {
			name.setVisible(true);
			difficulty.setVisible(false);
		}
		if (comSelect) {
			difficulty.setVisible(true);
			name.setVisible(false);
		}
		if (nonSelect) {
			name.setVisible(false);
			difficulty.setVisible(false);
		}
	}
}
