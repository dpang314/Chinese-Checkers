import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class PlayerOptionsPanel extends JPanel {
    private static enum Selected {
        HUMAN,
        COMPUTER,
        NONE
    }

    private static enum Difficulty {
        EASY,
        HARD
    }

    private String defaultHumanName;
    private String defaultComputerName;

    private JButton humanSelect, computerSelect, noneSelect;
    private JComboBox difficultySelect;
    private JTextField nameInput;
    private Selected selected = Selected.NONE;
    private String name = "";
    private Difficulty difficulty;
    private boolean repaintButtons = false;

    public PlayerOptionsPanel(int playerNumber) {
        defaultHumanName = "Human Player " + playerNumber;
        defaultComputerName = "Computer Player " + playerNumber;
        setPreferredSize(new Dimension(1280, 720));

        humanSelect = new JButton();
        humanSelect.setBounds(745,260,20,20);
        humanSelect.setContentAreaFilled(false);
        humanSelect.setBorderPainted(false);
        humanSelect.setIcon(GUI.getImageLoader().getMenuPanelImages().getEmptyButton());
        humanSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                selected = Selected.HUMAN;
                name = defaultHumanName;
                nameInput.setText(name);
                repaintButtons = true;
                repaint();
            }
        });
        this.add(humanSelect);

        computerSelect = new JButton();
        computerSelect.setBounds(745,310,20,20);
        computerSelect.setContentAreaFilled(false);
        computerSelect.setBorderPainted(false);
        computerSelect.setIcon(GUI.getImageLoader().getMenuPanelImages().getEmptyButton());
        computerSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                selected = Selected.COMPUTER;
                name = defaultComputerName;
                nameInput.setText(name);
                repaintButtons = true;
                repaint();
            }
        });
        this.add(computerSelect);

        noneSelect = new JButton();
        noneSelect.setBounds(745,360,20,20);
        noneSelect.setContentAreaFilled(false);
        noneSelect.setBorderPainted(false);
        noneSelect.setIcon(GUI.getImageLoader().getMenuPanelImages().getEmptyButton());
        noneSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                selected = Selected.NONE;
                name = "";
                repaintButtons = true;
                repaint();
            }
        });
        this.add(noneSelect);

        JLabel human = new JLabel("Human");
        human.setBounds(800,260,300,20);
        human.setFont(Util.getBigFont());
        this.add(human);

        JLabel computer = new JLabel("Computer");
        computer.setBounds(800,310,300,20);
        computer.setFont(Util.getBigFont());
        this.add(computer);

        JLabel none = new JLabel("None");
        none.setBounds(800,360,300,20);
        none.setFont(Util.getBigFont());
        this.add(none);

        String feed[] = {"Easy", "Hard"};
        difficultySelect = new JComboBox(feed);
        difficultySelect.setBounds(750,430,255,30);
        difficultySelect.setVisible(false);
        difficultySelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (difficultySelect.getSelectedIndex() == 0) {
                    difficulty = Difficulty.HARD;
                } else if (difficultySelect.getSelectedIndex() == 1) {
                    difficulty = Difficulty.EASY;
                }
            }
        });
        difficultySelect.setSelectedIndex(0);
        this.add(difficultySelect);

        nameInput = new JTextField("   ");
        nameInput.setBounds(750,430,255,30);
        nameInput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                name = nameInput.getText();
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                name = nameInput.getText();
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {

            }
        });
        this.nameInput.setVisible(false);
        this.add(nameInput);

        this.setVisible(true);
        this.setOpaque(false);
        this.setLayout(new BorderLayout());
        repaintButtons = true;
        repaint();
    }

    private void reset() {
        difficulty = Difficulty.EASY;
        difficultySelect.setSelectedIndex(0);
        difficultySelect.setVisible(false);
//        nameInput.setText("");
//        nameInput.setVisible(false);
//        name = "";
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(GUI.getImageLoader().getMenuPanelImages().getSmallScroll(), 0, 0, null);
        if (repaintButtons) {
            repaintButtons = false;
            if (selected.equals(Selected.HUMAN)) {
                humanSelect.setIcon(GUI.getImageLoader().getMenuPanelImages().getFilledButton());
                computerSelect.setIcon(GUI.getImageLoader().getMenuPanelImages().getEmptyButton());
                noneSelect.setIcon(GUI.getImageLoader().getMenuPanelImages().getEmptyButton());
                reset();
                nameInput.setVisible(true);
            } else if (selected.equals(Selected.COMPUTER)) {
                humanSelect.setIcon(GUI.getImageLoader().getMenuPanelImages().getEmptyButton());
                computerSelect.setIcon(GUI.getImageLoader().getMenuPanelImages().getFilledButton());
                noneSelect.setIcon(GUI.getImageLoader().getMenuPanelImages().getEmptyButton());
                reset();
                difficultySelect.setVisible(true);
            } else if (selected.equals(Selected.NONE)) {
                humanSelect.setIcon(GUI.getImageLoader().getMenuPanelImages().getEmptyButton());
                computerSelect.setIcon(GUI.getImageLoader().getMenuPanelImages().getEmptyButton());
                noneSelect.setIcon(GUI.getImageLoader().getMenuPanelImages().getFilledButton());
                reset();
            }
        }
    }
}
