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
    private final HashMap<String, ImageIcon> cloudIcons = new HashMap<>();
    private final ImageIcon emptyButton;
    private final ImageIcon filledButton;
    private final ImageIcon start;
    private final ImageIcon load;
    private final ImageIcon exit;
    private final ImageIcon dragon;
    private final ImageIcon logoBig;
    private final ImageIcon scrollOpenAnimated;
    private final ImageIcon scrollCloseAnimated;
    private final ImageIcon dragonAnimated;
    public MenuPanelImages() throws IOException {
        loadCloudIcons();
        //	https://cdn1.iconfinder.com/data/icons/thin-ui-1/100/Noun_Project_100Icon_1px_grid_thin_ic_radio_btn_full-512.PNG
        filledButton = new ImageIcon(ImageIO.read(new File("src/main/resources/images/menu/filledRadioButton.png"))
                .getScaledInstance(28, 28, Image.SCALE_DEFAULT));

        //	https://cdn1.iconfinder.com/data/icons/interface-59/24/radio-button-off-unchecked-round-circle-512.PNG
        emptyButton = new ImageIcon(ImageIO.read(new File("src/main/resources/images/menu/emptyRadioButton.png"))
                .getScaledInstance(20, 20, Image.SCALE_DEFAULT));

        // start load exit
        BufferedImage startImage = ImageIO.read(new File("src/main/resources/images/menu/start.PNG"));
        start = new ImageIcon(startImage.getScaledInstance((int) (startImage.getWidth() / 1.5), (int) (startImage.getHeight() / 1.5), Image.SCALE_DEFAULT));

        BufferedImage loadImage = ImageIO.read(new File("src/main/resources/images/menu/load.PNG"));
        load = new ImageIcon(loadImage.getScaledInstance((int) (loadImage.getWidth() / 1.5), (int) (loadImage.getHeight() / 1.5), Image.SCALE_DEFAULT));

        BufferedImage menuImage = ImageIO.read(new File("src/main/resources/images/menu/exit.PNG"));
        exit = new ImageIcon(menuImage.getScaledInstance((int) (menuImage.getWidth() / 1.5), (int) (menuImage.getHeight() / 1.5), Image.SCALE_DEFAULT));

        // visuals like scrolls dragons logos

        dragon = new ImageIcon(ImageIO.read(new File("src/main/resources/images/menu/dragon.png")).getScaledInstance(1280, 720, Image.SCALE_DEFAULT));

        logoBig = new ImageIcon(ImageIO.read(new File("src/main/resources/images/menu/logoBig.png"))
                .getScaledInstance(1280, 720, Image.SCALE_DEFAULT));

        scrollOpenAnimated = new ImageIcon("src/main/resources/images/menu/scrollOpen.gif");
        scrollCloseAnimated = new ImageIcon("src/main/resources/images/menu/scrollClose.gif");
        dragonAnimated = new ImageIcon("src/main/resources/images/menu/dragon.gif");
    }

    private void addImageIcon(String name) throws IOException {
        BufferedImage cloud = ImageIO.read(new File("src/main/resources/images/menu/clouds/" + name + ".PNG"));
        cloudIcons.put(name, new ImageIcon(cloud.getScaledInstance((int) (cloud.getWidth() / 1.5), (int) (cloud.getHeight() / 1.5), Image.SCALE_DEFAULT)));
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

    public ImageIcon getEmptyButton() {
        return emptyButton;
    }

    public ImageIcon getFilledButton() {
        return filledButton;
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

    public ImageIcon getDragon() {
        return dragon;
    }

    public ImageIcon getLogoBig() {
        return logoBig;
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

    public ImageIcon getScrollOpenAnimated() {
        scrollOpenAnimated.getImage().flush();
        return scrollOpenAnimated;
    }

    public ImageIcon getScrollCloseAnimated() {
        scrollCloseAnimated.getImage().flush();
        return scrollCloseAnimated;
    }

    public ImageIcon getDragonAnimated() {
        dragonAnimated.getImage().flush();
        return dragonAnimated;
    }
}