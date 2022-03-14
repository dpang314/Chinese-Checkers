import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class MenuPanelImages {
    private HashMap<String, ImageIcon> cloudIcons = new HashMap<>();

    public MenuPanelImages() throws IOException {
        loadCloudIcons();
        filledButton = new ImageIcon(ImageIO.read(new File("images/menu/filledRadioButton.png"))
                .getScaledInstance(20,20, Image.SCALE_DEFAULT));

        emptyButton = new ImageIcon(ImageIO.read(new File("images/menu/emptyRadioButton.png"))
                .getScaledInstance(20,20,Image.SCALE_DEFAULT));

        smallScroll = ImageIO.read(new File("images/menu/smallScroll.PNG"))
                .getScaledInstance(1280, 720, Image.SCALE_DEFAULT);

        // start load exit
        start = ImageIO.read(new File("images/menu/start.PNG"))
                .getScaledInstance(1280, 720, Image.SCALE_DEFAULT);

        load = ImageIO.read(new File("images/menu/load.PNG"))
                .getScaledInstance(1280, 720, Image.SCALE_DEFAULT);

        exit = ImageIO.read(new File("images/menu/exit.PNG"))
                .getScaledInstance(1280, 720, Image.SCALE_DEFAULT);

        // visuals like scrolls dragons logos

        ImageIcon dr = new ImageIcon("images/menu/dragon.PNG");
        dragon = dr.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);

        ImageIcon lb = new ImageIcon("images/menu/logoBig.PNG");
        logoBig = lb.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);

        ImageIcon ls = new ImageIcon("images/menu/ogoSmall.PNG");
        logoSmall = ls.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
    }

    private void addImageIcon(String name) throws IOException {
        cloudIcons.put(name, new ImageIcon(ImageIO.read(new File("images/menu/clouds/" + name + ".PNG"))
                .getScaledInstance(24, 24, Image.SCALE_DEFAULT)));
    }

    private void loadCloudIcons() throws IOException {
        //			addImageIcon("player1Human");
        //			addImageIcon("player2Human");
        //			addImageIcon("player3Human");
        //			addImageIcon("player4Human");
        //			addImageIcon("player5Human");
        //			addImageIcon("player6Human");
        //
        //			addImageIcon("player1Computer");
        //			addImageIcon("player2Computer");
        //			addImageIcon("player3Computer");
        //			addImageIcon("player4Computer");
        //			addImageIcon("player5Computer");
        //			addImageIcon("player6Computer");
        //
        //			addImageIcon("player1None");
        //			addImageIcon("player2None");
        //			addImageIcon("player3None");
        //			addImageIcon("player4None");
        //			addImageIcon("player5None");
        //			addImageIcon("player6None");
        //
        //			addImageIcon("player1Selecting");
        //			addImageIcon("player2Selecting");
        //			addImageIcon("player3Selecting");
        //			addImageIcon("player4Selecting");
        //			addImageIcon("player5Selecting");
        //			addImageIcon("player6Selecting");
    }

    private ImageIcon emptyButton;
    //	https://cdn1.iconfinder.com/data/icons/interface-59/24/radio-button-off-unchecked-round-circle-512.PNG
    private ImageIcon filledButton;
    //	https://cdn1.iconfinder.com/data/icons/thin-ui-1/100/Noun_Project_100Icon_1px_grid_thin_ic_radio_btn_full-512.PNG
    private Image smallScroll;
    private Image start;
    private Image load;
    private Image exit;
    private Image dragon;
    private Image logoBig;
    private Image logoSmall;

    public ImageIcon getEmptyButton() {
        return emptyButton;
    }

    public ImageIcon getFilledButton() {
        return filledButton;
    }

    public Image getSmallScroll() {
        return smallScroll;
    }

    public Image getStart() {
        return start;
    }

    public Image getLoad() {
        return load;
    }

    public Image getExit() {
        return exit;
    }

    public Image getDragon() {
        return dragon;
    }

    public Image getLogoBig() {
        return logoBig;
    }

    public Image getLogoSmall() {
        return logoSmall;
    }

    public ImageIcon getPlayerCloudIcon(int number, Util.PlayerType playerType) {
        if (playerType.equals(Util.PlayerType.COMPUTER_EASY) || playerType.equals(Util.PlayerType.COMPUTER_HARD)) {
            return cloudIcons.get("player" + number + "Computer");
        } else if (playerType.equals(Util.PlayerType.HUMAN)) {
            return cloudIcons.get("player" + number + "Human");
        } else if (playerType.equals(Util.PlayerType.NONE)) {
            return cloudIcons.get("player" + number + "None");
        } else if (playerType.equals(Util.PlayerType.SELECTING)) {
            return cloudIcons.get("player" + number + "Selecting");
        }
        throw new RuntimeException("Image not found");
    }
}