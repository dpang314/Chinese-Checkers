import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

// Marty
public class MenuPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private boolean shuffle = false;
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
	private Image shuffly;
	private Image arrow;

	// Sources
	private Image emptyButton;
//	https://cdn1.iconfinder.com/data/icons/interface-59/24/radio-button-off-unchecked-round-circle-512.PNG

	private Image filledButton;
//	https://cdn1.iconfinder.com/data/icons/thin-ui-1/100/Noun_Project_100Icon_1px_grid_thin_ic_radio_btn_full-512.PNG

	// 0 = dark, 1 = Def, 2 = Human, 3 = Com,
	private int p1Int = 2;
	private int p2Int = 3;
	private int p3Int = 0;
	private int p4Int = 0;
	private int p5Int = 0;
	private int p6Int = 0;
	private int subject = 0;

	private String playerHolder = "";

	private ArrayList <Integer> typesAl = new ArrayList <Integer>(6);
	private ArrayList <String> namesAl = new ArrayList <String>(6);
	private Player[] players;
	private int[] types = {0,0,0,0,0,0};
	private String[] names = new String[6];
	private boolean scrolled = false;
	private boolean humSelect = false;
	private boolean comSelect = false;
	private boolean nonSelect = false;
	private boolean doneStopper = false;
	private boolean humanFill = false;
	private boolean compFill = false;
	private boolean noneFill = false;

	private JLabel none;
	private JLabel computer;
	private JLabel human;
	private JLabel playerNum;
	private JLabel nameInstruct;
	private JLabel comInstruct;
	private JLabel shuffleLabel;

	private JTextField name;

	private JComboBox difficulty;

	private JButton hum;
	private JButton com;
	private JButton non;

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
	private JButton shuffler;

	private Font bigFont;
	private Font playerFont;
	private GUI gui;

	Timer timer;
	MenuPanel(GUI gui) {
		this.gui = gui;
		setPreferredSize(new Dimension(1280,720));
		bigFont = CustomFont.getFont().deriveFont(15f);
		playerFont = CustomFont.getFont().deriveFont(28f);
		players = new Player[6];

		this.setLayout(null);

		shuffler = new JButton("");
		shuffler.setBounds(335,115,20,20);
		shuffler.setActionCommand("Shuffler");
		shuffler.addActionListener(this);
		shuffler.setContentAreaFilled(false);
		shuffler.setBorderPainted(false);
		this.add(shuffler);

		P1 = new JButton("");
		P1.setBounds(100,125,180,80);
		P1.setActionCommand("player1");
		P1.addActionListener(this);
		P1.setContentAreaFilled(false);
		P1.setBorderPainted(false);
		this.add(P1);
		playerHolder = "Player 1";

		P2 = new JButton("");
		P2.setBounds(340,180,180,80);
		P2.setActionCommand("player2");
		P2.addActionListener(this);
		P2.setContentAreaFilled(false);
		P2.setBorderPainted(false);
		this.add(P2);
		playerHolder = "Player 2";

		P3 = new JButton("");
		P3.setBounds(158,258,180,80);
		P3.setActionCommand("player3");
		P3.addActionListener(this);
		P3.setContentAreaFilled(false);
		P3.setBorderPainted(false);
		this.add(P3);
		playerHolder = "Player 3";

		P4 = new JButton("");
		P4.setBounds(405,312,180,80);
		P4.setActionCommand("player4");
		P4.addActionListener(this);
		P4.setContentAreaFilled(false);
		P4.setBorderPainted(false);
		this.add(P4);
		playerHolder = "Player 4";

		P5 = new JButton("");
		P5.setBounds(225,410,180,80);
		P5.setActionCommand("player5");
		P5.addActionListener(this);
		P5.setContentAreaFilled(false);
		P5.setBorderPainted(false);
		this.add(P5);
		playerHolder = "Player 5";

		P6 = new JButton("");
		P6.setBounds(458,450,180,80);
		P6.setActionCommand("player6");
		P6.addActionListener(this);
		P6.setContentAreaFilled(false);
		P6.setBorderPainted(false);
		this.add(P6);
		playerHolder = "Player 6";

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

		/////////////////////////////////////////
		// DOPE STUFF

		hum = new JButton();
		hum.setBounds(745,260,20,20);
		hum.setActionCommand("hum");
		hum.addActionListener(this);
		hum.setContentAreaFilled(false);
		hum.setBorderPainted(false);
		this.add(hum);
		hum.setVisible(false);

		com = new JButton();
		com.setBounds(745,310,20,20);
		com.setActionCommand("com");
		com.addActionListener(this);
		com.setContentAreaFilled(false);
		com.setBorderPainted(false);
		this.add(com);
		com.setVisible(false);

		non = new JButton();
		non.setBounds(745,360,20,20);
		non.setActionCommand("non");
		non.addActionListener(this);
		non.setContentAreaFilled(false);
		non.setBorderPainted(false);
		this.add(non);
		non.setVisible(false);

		/////////////////////////////////////////////

		human = new JLabel("Human");
		human.setBounds(800,260,300,20);
		human.setFont(bigFont);
		this.add(human);
		human.setVisible(false);

		computer = new JLabel("Computer");
		computer.setBounds(800,310,300,20);
		computer.setFont(bigFont);
		this.add(computer);
		computer.setVisible(false);

		none = new JLabel("None");
		none.setBounds(800,360,300,20);
		none.setFont(bigFont);
		this.add(none);
		none.setVisible(false);

		//////////////////////////////////////////////

		nameInstruct = new JLabel("Enter your name:");
		nameInstruct.setFont(bigFont);
		nameInstruct.setBounds(813,382,300,60);
		this.add(nameInstruct);
		nameInstruct.setVisible(false);

		comInstruct = new JLabel("Set Difficulty:");
		comInstruct.setBounds(817,382,300,60);
		comInstruct.setFont(bigFont);
		this.add(comInstruct);
		comInstruct.setVisible(false);

		shuffleLabel = new JLabel("Shuffle Colors");
		shuffleLabel.setBounds(380,110,200,30);
		shuffleLabel.setFont(bigFont);
		this.add(shuffleLabel);
		shuffleLabel.setVisible(true);

		//////////////////////////////////////////////

		String feed[] = {"Easier", "Harder"};
		difficulty = new JComboBox(feed);
		difficulty.setBounds(750,430,255,30);
		this.add(difficulty);
		difficulty.setActionCommand("difficulty");
		difficulty.addActionListener(this);
		difficulty.setVisible(false);

		name = new JTextField("   ");
		name.setBounds(750,430,255,30);
		this.add(name);
		name.setActionCommand("name");
		name.addActionListener(this);
		name.setVisible(false);

		///////////////////////////////////////////////

		done = new JButton("Done");
		done.setBounds(770,465,200,50);
		done.setFont(bigFont);
		this.add(done);
		done.setActionCommand("done");
		done.addActionListener(this);
		done.setContentAreaFilled(false);
		done.setVisible(false);

		playerNum = new JLabel("");
		playerNum.setBounds(820,200,250,30);
		playerNum.setFont(playerFont);
		this.add(playerNum);
		playerNum.setVisible(false);


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
			scrolled = true;
			p1Int = 1;
			playerHolder = "Player 1";
			P2.setVisible(false);
			P3.setVisible(false);
			P4.setVisible(false);
			P5.setVisible(false);
			P6.setVisible(false);
		}
		if (eventName.equals("player2")) {
			subject = 2;
			scrolled = true;
			p2Int = 1;
			playerHolder = "Player 2";
			P1.setVisible(false);
			P3.setVisible(false);
			P4.setVisible(false);
			P5.setVisible(false);
			P6.setVisible(false);
		}
		if (eventName.equals("player3")) {
			subject = 3;
			scrolled = true;
			p3Int = 1;
			playerHolder = "Player 3";
			P1.setVisible(false);
			P2.setVisible(false);
			P4.setVisible(false);
			P5.setVisible(false);
			P6.setVisible(false);
		}
		if (eventName.equals("player4")) {
			subject = 4;
			scrolled = true;
			p4Int = 1;
			playerHolder = "Player 4";
			P1.setVisible(false);
			P2.setVisible(false);
			P3.setVisible(false);
			P5.setVisible(false);
			P6.setVisible(false);
		}
		if (eventName.equals("player5")) {
			subject = 5;
			scrolled = true;
			p5Int = 1;
			playerHolder = "Player 5";
			P1.setVisible(false);
			P2.setVisible(false);
			P3.setVisible(false);
			P4.setVisible(false);
			P6.setVisible(false);
		}
		if (eventName.equals("player6")) {
			subject = 6;
			scrolled = true;
			p6Int = 1;
			playerHolder = "Player 6";
			P1.setVisible(false);
			P2.setVisible(false);
			P3.setVisible(false);
			P4.setVisible(false);
			P5.setVisible(false);
		}

		if (eventName.equals("startB")) {
			for (int x = 0; x < 6; x++) {
				if (names[x] != null) {
					namesAl.add(names[x]);
				}
				if (types[x] != 0) {
					typesAl.add(types[x]);
				}
			}
			int numPlayers = typesAl.size();
			// set the game screen
			// pass data from players
			//players[0] = new HumanPlayer(Color.RED,"hi");
			//players[1] = new HumanPlayer(Color.BLUE,"test");
//			players[0] = new QuinnStrategy(Color.RED, "test1");
//			players[1] = new QuinnStrategy(Color.BLUE, "test");
//			players[2] = new QuinnStrategy(Color.GREEN, "test2");
//			players[3] = new QuinnStrategy(Color.YELLOW, "test2");
//			players[4] = new QuinnStrategy(Color.BLACK, "test2");
//			players[5] = new QuinnStrategy(Color.WHITE, "test2");
			players[0] = new ComputerStratBasic2(Color.RED, "Basic3");
			players[0] = new ComputerStratBasic2(Color.RED, "Simple");
			players[1] = new QuinnStrategy(Color.BLUE, "Quinn");

			gui.switchToGamePanel(players, shuffle);
		}
		
		if (eventName.equals("loadB")) {
			// load code
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					".chcr save file", "chrc");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(null);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				try {
					Game game = GameLoader.readGameFromFile(chooser.getSelectedFile().getPath());
					gui.switchToGamePanel(game);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		
		if (eventName.equals("exitB")) {
			// Insert exit are you sure start up.
			System.exit(0);
		}
		
		if (eventName.equals("Shuffler")) {
			if (shuffle) {
				shuffle = false;
			} else if (!shuffle) {
				shuffle = true;
			}
		}
		///////////////////////////////////////////////////
		
		if (eventName.equals("hum")) {
			humSelect = true;
			comSelect = false;
			nonSelect = false;
			humanFill = true;
			compFill = false;
			noneFill = false;
			doneStopper = true;
		}
		if (eventName.equals("com")) {
			comSelect = true;
			humSelect = false;
			nonSelect = false;
			compFill = true;
			humanFill = false;
			noneFill = false;
			doneStopper = true;
			name.setText("   ");
		}
		if (eventName.equals("non")) {
			nonSelect = true;
			humSelect = false;
			comSelect = false;
			noneFill = true;
			humanFill = false;
			compFill = false;
		}
		
		/////////////////////////////////////////////
		
		// BOXES MAJOR WORK REMAINING
		if (eventName.equals("difficulty")) {
			doneStopper = false;
		}
		text = name.getText();
		if (text.equals("   ")) {
		} else {
			doneStopper = false;
		}
		
		// I can't figure out how to make the done button visible like cmon man
		
		
		// all of the stuff below this works
		
		// Major Done Button
		if (eventName.equals("done")) {
			
			if (playerHolder.equals("Player 1")) {
				subject = 1;
			}
			if (playerHolder.equals("Player 2")) {
				subject = 2;
			}
			if (playerHolder.equals("Player 3")) {
				subject = 3;
			}
			if (playerHolder.equals("Player 4")) {
				subject = 4;
			}
			if (playerHolder.equals("Player 5")) {
				subject = 5;
			}
			if (playerHolder.equals("Player 6")) {
				subject = 6;
			}
			if (comSelect) {
				checkSubject(3);
				dif = (String) difficulty.getSelectedItem();
				if (dif.equals("Easier")) {
					players[subject - 1] = new ComputerStratBasic2(Color.RED, "Computer " + (subject - 1 ));
					
					// create computer player
					// set the difficulty to Easier
				
					
				} else if (dif.equals("Harder")) {
					players[subject - 1] = new QuinnStrategy(Color.RED, "Computer " + (subject - 1 ));
					
					// create computer player
					// set the difficulty to Harder
				}

			}
			else if (humSelect) {
				checkSubject(2);
				text = name.getText();
				players[subject - 1] = new HumanPlayer(Color.RED, text);
				humSelect = false;
			} else if (nonSelect == true) {
				checkSubject(0);
			}
			
			scrolled = false;
			subject = 0;
			comSelect = false;
			nonSelect = false;
			humSelect = false;
			name.setVisible(false);
			nameInstruct.setVisible(false);
			difficulty.setVisible(false);
			comInstruct.setVisible(false);
			name.setText("   ");
			humanFill = false;
			compFill = false;
			noneFill = false;
			P1.setVisible(true);
			P2.setVisible(true);
			P3.setVisible(true);
			P4.setVisible(true);
			P5.setVisible(true);
			P6.setVisible(true);
			
		}
		repaint();
	}
	private void getImages() {
		// background
		ImageIcon bg = new ImageIcon("images/bg.PNG");
		background = bg.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		
		ImageIcon fb = new ImageIcon("images/filledRadioButton.ong");
		filledButton = fb.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT);
		
		ImageIcon eb = new ImageIcon("images/emptyRadioButton.png");
		emptyButton = eb.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT);
		
		// 4 button colors for 1 - 6
		ImageIcon p1N = new ImageIcon("images/p1None.PNG");
		plyN1 = p1N.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p1R = new ImageIcon("images/p1 Com.PNG");
		plyR1 = p1R.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p1G = new ImageIcon("images/p1Hum.PNG");
		plyG1 = p1G.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p1D = new ImageIcon("images/p1Def.PNG");
		plyD1 = p1D.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		
		ImageIcon p2N = new ImageIcon("images/p2None.PNG");
		plyN2 = p2N.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p2R = new ImageIcon("images/p2Com.PNG");
		plyR2 = p2R.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p2G = new ImageIcon("images/p2Hum.PNG");
		plyG2 = p2G.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p2D = new ImageIcon("images/p2Def.PNG");
		plyD2 = p2D.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		
		ImageIcon p3N = new ImageIcon("images/p3None.PNG");
		plyN3 = p3N.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p3R = new ImageIcon("images/p3Com.PNG");
		plyR3 = p3R.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p3G = new ImageIcon("images/p3Hum.PNG");
		plyG3 = p3G.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p3D = new ImageIcon("images/p3Def.PNG");
		plyD3 = p3D.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		
		ImageIcon p4N = new ImageIcon("images/p4None.PNG");
		plyN4 = p4N.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p4R = new ImageIcon("images/p4Com.PNG");
		plyR4 = p4R.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p4G = new ImageIcon("images/p4Hum.PNG");
		plyG4 = p4G.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p4D = new ImageIcon("images/p4Def.PNG");
		plyD4 = p4D.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);

		ImageIcon p5N = new ImageIcon("images/p5None.PNG");
		plyN5 = p5N.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p5R = new ImageIcon("images/p5Com.PNG");
		plyR5 = p5R.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p5G = new ImageIcon("images/p5Hum.PNG");
		plyG5 = p5G.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p5D = new ImageIcon("images/p5Def.PNG");
		plyD5 = p5D.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		
		ImageIcon p6N = new ImageIcon("images/p6None.PNG");
		plyN6 = p6N.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p6R = new ImageIcon("images/p6Com.PNG");
		plyR6 = p6R.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p6G = new ImageIcon("images/p6Hum.PNG");
		plyG6 = p6G.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		ImageIcon p6D = new ImageIcon("images/p6Def.PNG");
		plyD6 = p6D.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		
		// start load exit
		ImageIcon st = new ImageIcon("images/buttonStart.PNG");
		startI = st.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		
		ImageIcon ld = new ImageIcon("images/buttonLoad.PNG");
		loadI = ld.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		
		ImageIcon xt = new ImageIcon("images/buttonExit.PNG");
		exitI = xt.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		
		// visuals like scrolls dragons logos
		
		ImageIcon dr = new ImageIcon("images/dragon.PNG");
		dragon = dr.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		
		ImageIcon lb = new ImageIcon("images/logoBig.PNG");
		logoBig = lb.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		
		ImageIcon ls = new ImageIcon("images/logoSmall.PNG");
		logoSmall = ls.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		
		ImageIcon sc = new ImageIcon("images/scrollMenu.PNG");
		scroll = sc.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
////////////////////////////////////////////////////////////////////////////////////		
		ImageIcon ar = new ImageIcon("images/arrow.PNG");
		arrow = ar.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
	}
	private void drawImages(Graphics g) {

		
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
		if (shuffle) {
			g.drawImage(filledButton,335,115,this);
		} else if (!shuffle) {
			g.drawImage(emptyButton,335,115,this);
		}
		if (scrolled) {
			non.setVisible(true);
			com.setVisible(true);
			hum.setVisible(true);
			none.setVisible(true);
			human.setVisible(true);
			computer.setVisible(true);
			playerNum.setText(playerHolder);
			playerNum.setVisible(true);
			g.drawImage(emptyButton,745,260,this);
			g.drawImage(emptyButton,745,310,this);
			g.drawImage(emptyButton,745,360,this);
		}
		if (!scrolled) {
			done.setVisible(false);
			non.setVisible(false);
			com.setVisible(false);
			hum.setVisible(false);
			none.setVisible(false);
			human.setVisible(false);
			computer.setVisible(false);
			playerNum.setVisible(false);
			doneStopper = true;
		}
		
		if (humSelect) {
			nameInstruct.setVisible(true);
			comInstruct.setVisible(false);
			name.setVisible(true);
			difficulty.setVisible(false);
		}
		if (comSelect) {
			nameInstruct.setVisible(false);
			comInstruct.setVisible(true);
			difficulty.setVisible(true);
			name.setVisible(false);
		}
		if (nonSelect) {
			nameInstruct.setVisible(false);
			comInstruct.setVisible(false);
			name.setVisible(false);
			difficulty.setVisible(false);
			doneStopper = false;
		}
		if (humanFill) {
			g.drawImage(filledButton,745,260,this);
		}
		if (compFill) {
			g.drawImage(filledButton,745,310,this);
		}
		if (noneFill) {
			g.drawImage(filledButton,745,360,this);
		}
		if (!doneStopper) {
			done.setVisible(true);
		} else {
			done.setVisible(false);
		}
	}
	private void checkSubject(int type) {
		if (subject == 1) {
			p1Int = type;
		}
		if (subject == 2) {
			p2Int = type;
		}
		if (subject == 3) {
			p3Int = type;
		}
		if (subject == 4) {
			p4Int = type;
		}
		if (subject == 5) {
			p5Int = type;
		}
		if (subject == 6) {
			p6Int = type;
		}
	}
	private Color checkColor(int subject, int size) {
		if (size == 6) {
			if (subject == 1) {
				return Color.RED;
			} else if (subject == 2) {
				return Color.BLUE;
			} else if (subject == 3) {
				return Color.YELLOW;
			} else if (subject == 4) {
				return Color.GREEN;
			} else if (subject == 5) {
				return Color.BLACK;
			} else if (subject == 6) {
				return Color.WHITE;
			}
		} else if (size == 4) {
			if (subject == 1) {
				return Color.YELLOW;
			} else if (subject == 2) {
				return Color.GREEN;
			} else if (subject == 3) {
				return Color.BLACK;
			} else if (subject == 4) {
				return Color.WHITE;
			}
		} else if (size == 2) {
			if (subject == 1) {
				return Color.RED;
			} else if (subject == 2) {
				return Color.BLUE;
			}
		}
		
		return null;
	}
	private void setColors() {
		if (players[0] != null && players[1] != null && players[2] != null && players[3] != null && players[4] != null && players[5] != null ){ // 6 players
			
		}
		
	}
}

	
