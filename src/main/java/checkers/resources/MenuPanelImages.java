package checkers.resources;

import checkers.Util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class MenuPanelImages {
    private HashMap<String, ImageIcon> cloudIcons = new HashMap<>();

    public MenuPanelImages() throws IOException {
        loadCloudIcons();
        filledButton = new ImageIcon(ImageIO.read(new File("src/main/resources/images/menu/filledRadioButton.png"))
                .getScaledInstance(28,28, Image.SCALE_DEFAULT));

        emptyButton = new ImageIcon(ImageIO.read(new File("src/main/resources/images/menu/emptyRadioButton.png"))
                .getScaledInstance(20,20,Image.SCALE_DEFAULT));

        smallScroll = ImageIO.read(new File("src/main/resources/images/menu/smallScroll.PNG"))
                .getScaledInstance(1280, 720, Image.SCALE_DEFAULT);

        // start load exit
        BufferedImage startImage = ImageIO.read(new File("src/main/resources/images/menu/start.PNG"));
        start = new ImageIcon(startImage.getScaledInstance((int)(startImage.getWidth() / 1.5), (int)(startImage.getHeight()/ 1.5), Image.SCALE_DEFAULT));

        BufferedImage loadImage = ImageIO.read(new File("src/main/resources/images/menu/load.PNG"));
        load = new ImageIcon(loadImage.getScaledInstance((int)(loadImage.getWidth() / 1.5), (int)(loadImage.getHeight()/ 1.5), Image.SCALE_DEFAULT));

        BufferedImage menuImage = ImageIO.read(new File("src/main/resources/images/menu/exit.PNG"));
        exit = new ImageIcon(menuImage.getScaledInstance((int)(menuImage.getWidth() / 1.5), (int)(menuImage.getHeight()/ 1.5), Image.SCALE_DEFAULT));

        // visuals like scrolls dragons logos

        ImageIcon dr = new ImageIcon("src/main/resources/images/menu/dragon.PNG");
        dragon = dr.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);

        ImageIcon lb = new ImageIcon("src/main/resources/images/menu/logoBig.PNG");
        logoBig = lb.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);

        ImageIcon ls = new ImageIcon("src/main/resources/images/menu/ogoSmall.PNG");
        logoSmall = ls.getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
    }

    private void addImageIcon(String name) throws IOException {
        BufferedImage cloud = ImageIO.read(new File("src/main/resources/images/menu/clouds/" + name + ".PNG"));
        cloudIcons.put(name, new ImageIcon(cloud.getScaledInstance((int)(cloud.getWidth() / 1.5), (int)(cloud.getHeight() / 1.5), Image.SCALE_DEFAULT)));
    }

    private void loadCloudIcons() throws IOException {
        addImageIcon("p1Hum");
        addImageIcon("p2Hum");
        addImageIcon("p3Hum");
        addImageIcon("p4Hum");
        addImageIcon("p5Hum");
        addImageIcon("p6Hum");

        addImageIcon("p1Com");
        addImageIcon("p2Com");
        addImageIcon("p3Com");
        addImageIcon("p4Com");
        addImageIcon("p5Com");
        addImageIcon("p6Com");

        addImageIcon("p1None");
        addImageIcon("p2None");
        addImageIcon("p3None");
        addImageIcon("p4None");
        addImageIcon("p5None");
        addImageIcon("p6None");

        addImageIcon("p1Def");
        addImageIcon("p2Def");
        addImageIcon("p3Def");
        addImageIcon("p4Def");
        addImageIcon("p5Def");
        addImageIcon("p6Def");
    }

    private ImageIcon emptyButton;
    //	https://cdn1.iconfinder.com/data/icons/interface-59/24/radio-button-off-unchecked-round-circle-512.PNG
    private ImageIcon filledButton;
    //	https://cdn1.iconfinder.com/data/icons/thin-ui-1/100/Noun_Project_100Icon_1px_grid_thin_ic_radio_btn_full-512.PNG
    private Image smallScroll;
    private ImageIcon start, load, exit;
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

    public ImageIcon getStart() {
        return start;
    }

    public ImageIcon getLoad() {
        return load;
    }

    public ImageIcon getExit() {
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
            return cloudIcons.get("p" + number + "Com");
        } else if (playerType.equals(Util.PlayerType.HUMAN)) {
            return cloudIcons.get("p" + number + "Hum");
        } else if (playerType.equals(Util.PlayerType.NONE)) {
            return cloudIcons.get("p" + number + "None");
        } else if (playerType.equals(Util.PlayerType.DEFAULT)) {
            return cloudIcons.get("p" + number + "Def");
        }
        throw new RuntimeException("Image not found");
    }
}