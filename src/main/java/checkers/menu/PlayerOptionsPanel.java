package checkers.menu;

import checkers.GUI;
import checkers.Util;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PlayerOptionsPanel extends JPanel {
    private final String defaultHumanName;
    private final String defaultComputerName;

    private final JButton humanSelect;
    private final JButton computerSelect;
    private final JButton noneSelect;
    private final JButton save;
    private final JComboBox<String> difficultySelect;
    private final JTextField nameInput;
    private final JTextField error;
    private final JLabel nameInstruct;
    private final JLabel comInstruct;
    private Util.PlayerType selected;
    private String name = "";
    private boolean repaintButtons;

    public PlayerOptionsPanel(int playerNumber, MenuPanel.PlayerButton playerButton) {
        defaultHumanName = "Human Player " + playerNumber;
        defaultComputerName = "Computer Player " + playerNumber;
        setPreferredSize(new Dimension(1280, 720));

        humanSelect = new JButton();
        humanSelect.setBounds(745, 190, 20, 20);
        humanSelect.setContentAreaFilled(false);
        humanSelect.setBorderPainted(false);
        humanSelect.setIcon(GUI.getImageLoader().getMenuPanelImages().getEmptyButton());
        humanSelect.addActionListener(actionEvent -> humanSelectAction());
        this.add(humanSelect);

        computerSelect = new JButton();
        computerSelect.setBounds(745, 230, 20, 20);
        computerSelect.setContentAreaFilled(false);
        computerSelect.setBorderPainted(false);
        computerSelect.setIcon(GUI.getImageLoader().getMenuPanelImages().getEmptyButton());
        computerSelect.addActionListener(actionEvent -> computerSelectAction());
        this.add(computerSelect);

        noneSelect = new JButton();
        noneSelect.setBounds(745, 270, 20, 20);
        noneSelect.setContentAreaFilled(false);
        noneSelect.setBorderPainted(false);
        noneSelect.setIcon(GUI.getImageLoader().getMenuPanelImages().getEmptyButton());
        noneSelect.addActionListener(actionEvent -> noneSelectAction());
        this.add(noneSelect);

        JLabel human = new JLabel("Human");
        human.setBounds(800, 190, 300, 20);
        human.setFont(Util.getBigFont());
        human.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                humanSelectAction();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {}

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {}

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {}

            @Override
            public void mouseExited(MouseEvent mouseEvent) {}
        });
        this.add(human);

        JLabel computer = new JLabel("Computer");
        computer.setBounds(800, 230, 300, 20);
        computer.setFont(Util.getBigFont());
        computer.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                computerSelectAction();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {}

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {}

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {}

            @Override
            public void mouseExited(MouseEvent mouseEvent) {}
        });
        this.add(computer);

        JLabel none = new JLabel("None");
        none.setBounds(800, 270, 300, 20);
        none.setFont(Util.getBigFont());
        none.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                noneSelectAction();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {}

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {}

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {}

            @Override
            public void mouseExited(MouseEvent mouseEvent) {}
        });
        this.add(none);

        nameInstruct = new JLabel("Enter your name:");
        nameInstruct.setFont(Util.getBigFont());
        nameInstruct.setBounds(813, 280, 300, 60);
        this.add(nameInstruct);

        nameInput = new JTextField("");
        nameInput.setBounds(750, 330, 255, 30);
        nameInput.getDocument().addDocumentListener(new DocumentListener() {
            private void updateError() {
                if (name.length() == 0) {
                    error.setText("Name must be at least 1 character");
                    error.setVisible(true);
                    save.setEnabled(false);
                    save.setForeground(Color.GRAY);
                } else if (name.length() > 20) {
                    error.setText("Max name length is 20 characters");
                    error.setVisible(true);
                    save.setEnabled(false);
                    save.setForeground(Color.GRAY);
                } else {
                    error.setVisible(false);
                    save.setEnabled(true);
                    save.setForeground(Color.BLACK);
                }
            }

            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                name = nameInput.getText();
                updateError();
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                name = nameInput.getText();
                updateError();
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
            }
        });
        this.nameInput.setVisible(false);
        this.add(nameInput);

        error = new JTextField("");
        error.setBounds(750, 365, 255, 15);
        error.setOpaque(false);
        error.setBorder(null);
        error.setForeground(Color.RED);
        this.add(error);

        comInstruct = new JLabel("Set Difficulty:");
        comInstruct.setBounds(817, 370, 300, 60);
        comInstruct.setFont(Util.getBigFont());
        this.add(comInstruct);

        String[] feed = {"Easier", "Harder"};
        difficultySelect = new JComboBox<>(feed);
        difficultySelect.setBounds(750, 420, 255, 30);
        difficultySelect.setVisible(false);
        difficultySelect.addActionListener(actionEvent -> {
            if (difficultySelect.isPopupVisible()) {
                if (difficultySelect.getSelectedIndex() == 0) {
                    selected = Util.PlayerType.COMPUTER_EASY;
                } else if (difficultySelect.getSelectedIndex() == 1) {
                    selected = Util.PlayerType.COMPUTER_HARD;
                }
            }
        });
        difficultySelect.setSelectedIndex(1);
        this.add(difficultySelect);

        save = new JButton("Save");
        save.setContentAreaFilled(false);
        save.setFont(Util.getBigFont());
        save.setFocusPainted(false);
        save.setBounds(770, 465, 200, 50);
        save.addActionListener(actionEvent -> playerButton.close(selected));
        this.add(save);

        this.setVisible(true);
        this.setOpaque(false);
        this.setLayout(new BorderLayout());
        selected = Util.PlayerType.NONE;
        repaintButtons = true;
        repaint();
    }

    private void humanSelectAction() {
        selected = Util.PlayerType.HUMAN;
        name = defaultHumanName;
        nameInput.setText(name);
        repaintButtons = true;
        repaint();
    }

    private void computerSelectAction() {
        if (computerSelect.isVisible()) {
            selected = Util.PlayerType.COMPUTER_HARD;
            name = defaultComputerName;
            nameInput.setText(name);
            repaintButtons = true;
            repaint();
        }
    }

    private void noneSelectAction() {
        selected = Util.PlayerType.NONE;
        name = "";
        repaintButtons = true;
        repaint();
    }

    public String getName() {
        return name;
    }

    public void open() {
        this.setVisible(true);
    }

    public void setSelected(Util.PlayerType playerType) {
        this.selected = playerType;
        this.repaintButtons = true;
        if (playerType.equals(Util.PlayerType.HUMAN)) {
            name = defaultHumanName;
        } else if (playerType.equals(Util.PlayerType.COMPUTER_EASY)) {
            name = defaultComputerName;
        } else if (playerType.equals(Util.PlayerType.COMPUTER_HARD)) {
            name = defaultComputerName;
        }
        nameInput.setText(name);
        this.repaint();
    }

    private void reset() {
        difficultySelect.setSelectedIndex(1);
        difficultySelect.setVisible(false);
        comInstruct.setVisible(false);
        nameInput.setVisible(false);
        nameInstruct.setVisible(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (repaintButtons) {
            repaintButtons = false;
            if (selected.equals(Util.PlayerType.HUMAN)) {
                humanSelect.setIcon(GUI.getImageLoader().getMenuPanelImages().getFilledButton());
                computerSelect.setIcon(GUI.getImageLoader().getMenuPanelImages().getEmptyButton());
                noneSelect.setIcon(GUI.getImageLoader().getMenuPanelImages().getEmptyButton());
                reset();
                nameInput.setVisible(true);
                nameInstruct.setVisible(true);
            } else if (selected.equals(Util.PlayerType.COMPUTER_EASY) || selected.equals(Util.PlayerType.COMPUTER_HARD)) {
                humanSelect.setIcon(GUI.getImageLoader().getMenuPanelImages().getEmptyButton());
                computerSelect.setIcon(GUI.getImageLoader().getMenuPanelImages().getFilledButton());
                noneSelect.setIcon(GUI.getImageLoader().getMenuPanelImages().getEmptyButton());
                reset();
                if (selected.equals(Util.PlayerType.COMPUTER_EASY)) {
                    difficultySelect.setSelectedIndex(0);
                } else if (selected.equals(Util.PlayerType.COMPUTER_HARD)) {
                    difficultySelect.setSelectedIndex(1);
                }
                difficultySelect.setVisible(true);
                nameInput.setVisible(true);
                nameInstruct.setVisible(true);
                comInstruct.setVisible(true);
            } else if (selected.equals(Util.PlayerType.NONE)) {
                humanSelect.setIcon(GUI.getImageLoader().getMenuPanelImages().getEmptyButton());
                computerSelect.setIcon(GUI.getImageLoader().getMenuPanelImages().getEmptyButton());
                noneSelect.setIcon(GUI.getImageLoader().getMenuPanelImages().getFilledButton());
                reset();
            }
        }
    }
}
