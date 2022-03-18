package checkers.menu;

import checkers.GUI;
import checkers.Util;

import javax.swing.*;
import javax.swing.text.*;
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

    private class LimitedInput extends JTextField {
        private final int maxCharacters = 18;

        public LimitedInput() {
            AbstractDocument doc = (AbstractDocument) this.getDocument();
            doc.setDocumentFilter(new DocumentFilter() {
                private void updateError() {
                    if (nameInput.getText().length() == 0) {
                        error.setText("Name must be at least 1 character");
                        error.setVisible(true);
                        save.setEnabled(false);
                        save.setForeground(Color.GRAY);
                    } else if (nameInput.getText().length() > 20) {
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
                public void replace(FilterBypass fb, int offs, int length,
                                    String str, AttributeSet a) throws BadLocationException {
                    if (str.length() + offs > maxCharacters) {
                        int overflow = str.length() + offs - maxCharacters;
                        str = str.substring(0, str.length() - overflow);
                    }
                    super.replace(fb, offs, length, str, a);
                    updateError();
                }

                @Override
                public void insertString(FilterBypass fb, int offs,
                                         String str, AttributeSet a)
                        throws BadLocationException {
                    // If input length less than or equal to 18
                    if ((fb.getDocument().getLength() + str.length()) <= maxCharacters) {
                        super.insertString(fb, offs, str, a);
                    }
                    updateError();
                }
            });
        }


    }

    public PlayerOptionsPanel(int playerNumber, MenuPanel.PlayerButton playerButton) {
        defaultHumanName = "Human Player " + playerNumber;
        defaultComputerName = "Computer Player " + playerNumber;
        setPreferredSize(new Dimension(1280, 720));

        humanSelect = new JButton();
        humanSelect.setBounds(775, 200, 20, 20);
        humanSelect.setContentAreaFilled(false);
        humanSelect.setBorderPainted(false);
        humanSelect.setIcon(GUI.getImageLoader().getMenuPanelImages().getEmptyButton());
        humanSelect.addActionListener(actionEvent -> PlayerOptionsPanel.this.humanSelectAction());
        this.add(humanSelect);

        computerSelect = new JButton();
        computerSelect.setBounds(775, 240, 20, 20);
        computerSelect.setContentAreaFilled(false);
        computerSelect.setBorderPainted(false);
        computerSelect.setIcon(GUI.getImageLoader().getMenuPanelImages().getEmptyButton());
        computerSelect.addActionListener(actionEvent -> PlayerOptionsPanel.this.computerSelectAction());
        this.add(computerSelect);

        noneSelect = new JButton();
        noneSelect.setBounds(775, 280, 20, 20);
        noneSelect.setContentAreaFilled(false);
        noneSelect.setBorderPainted(false);
        noneSelect.setIcon(GUI.getImageLoader().getMenuPanelImages().getEmptyButton());
        noneSelect.addActionListener(actionEvent -> PlayerOptionsPanel.this.noneSelectAction());
        this.add(noneSelect);

        JLabel human = new JLabel("Human");
        human.setBounds(830, 200, 300, 20);
        human.setFont(Util.getBigFont());
        human.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                PlayerOptionsPanel.this.humanSelectAction();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
            }
        });
        this.add(human);

        JLabel computer = new JLabel("Computer");
        computer.setBounds(830, 240, 300, 20);
        computer.setFont(Util.getBigFont());
        computer.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                PlayerOptionsPanel.this.computerSelectAction();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
            }
        });
        this.add(computer);

        JLabel none = new JLabel("None");
        none.setBounds(830, 280, 300, 20);
        none.setFont(Util.getBigFont());
        none.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                PlayerOptionsPanel.this.noneSelectAction();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
            }
        });
        this.add(none);

        nameInstruct = new JLabel("Enter your name:");
        nameInstruct.setFont(Util.getBigFont());
        nameInstruct.setBounds(852, 295, 300, 60);
        this.add(nameInstruct);

        nameInput = new LimitedInput();
        nameInput.setBounds(790, 345, 255, 30);

        this.nameInput.setVisible(false);
        this.add(nameInput);

        error = new JTextField("");
        error.setBounds(780, 365, 255, 15);
        error.setOpaque(false);
        error.setBorder(null);
        error.setForeground(Color.RED);
        this.add(error);

        comInstruct = new JLabel("Set Difficulty:");
        comInstruct.setBounds(865, 370, 300, 60);
        comInstruct.setFont(Util.getBigFont());
        this.add(comInstruct);

        String[] feed = {"Easier", "Harder"};
        difficultySelect = new JComboBox<>(feed);
        difficultySelect.setBounds(790, 420, 255, 30);
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
        save.setBounds(820, 465, 200, 50);
        save.addActionListener(actionEvent -> {
            PlayerOptionsPanel.this.name = nameInput.getText();
            playerButton.close(selected);
        });
        this.add(save);

        this.setVisible(true);
        this.setOpaque(false);
        this.setLayout(new BorderLayout());
        selected = Util.PlayerType.NONE;
        repaintButtons = true;
        repaint();
    }

    private void humanSelectAction() {
        nameInput.setVisible(false);
        this.setSelected(Util.PlayerType.HUMAN);
    }

    private void computerSelectAction() {
        selected = Util.PlayerType.COMPUTER_HARD;
        this.setSelected(Util.PlayerType.COMPUTER_HARD);
    }

    private void noneSelectAction() {
        this.setSelected(Util.PlayerType.NONE);
    }

    public String getName() {
        return name;
    }

    public void open() {
        this.setVisible(true);
    }

    public void setSelected(Util.PlayerType playerType) {
        this.selected = playerType;
        if (playerType.equals(Util.PlayerType.HUMAN)) {
            this.name = defaultHumanName;
        } else if (playerType.equals(Util.PlayerType.COMPUTER_EASY)) {
            this.name = defaultComputerName;
        } else if (playerType.equals(Util.PlayerType.COMPUTER_HARD)) {
            this.name = defaultComputerName;
        }
        nameInput.setText(this.name);
        this.repaintButtons = true;
        this.repaint();
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
                comInstruct.setVisible(false);
                difficultySelect.setVisible(false);
                nameInput.setVisible(true);
                nameInstruct.setVisible(true);
            } else if (selected.equals(Util.PlayerType.COMPUTER_EASY) || selected.equals(Util.PlayerType.COMPUTER_HARD)) {
                humanSelect.setIcon(GUI.getImageLoader().getMenuPanelImages().getEmptyButton());
                computerSelect.setIcon(GUI.getImageLoader().getMenuPanelImages().getFilledButton());
                noneSelect.setIcon(GUI.getImageLoader().getMenuPanelImages().getEmptyButton());
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
                difficultySelect.setVisible(false);
                comInstruct.setVisible(false);
                nameInput.setVisible(false);
                nameInstruct.setVisible(false);
            }
        }
    }
}
