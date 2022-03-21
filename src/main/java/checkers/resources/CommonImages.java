package checkers.resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CommonImages {
    private final Image background;
    private final ImageIcon cloudTransition;
    private final Image redNameScroll;
    private final Image blackNameScroll;
    private final Image greenNameScroll;
    private final Image blueNameScroll;
    private final Image whiteNameScroll;
    private final Image yellowNameScroll;

    public CommonImages() throws IOException {
        background = ImageIO.read(new File("src/main/resources/images/background.PNG")).getScaledInstance(1280, 720, Image.SCALE_DEFAULT);
        cloudTransition = new ImageIcon("src/main/resources/images/clouds.gif");
        redNameScroll = ImageIO.read(new File("src/main/resources/images/nameScroll/red.PNG"));
        blackNameScroll = ImageIO.read(new File("src/main/resources/images/nameScroll/black.PNG"));
        greenNameScroll = ImageIO.read(new File("src/main/resources/images/nameScroll/green.PNG"));
        blueNameScroll = ImageIO.read(new File("src/main/resources/images/nameScroll/blue.PNG"));
        whiteNameScroll = ImageIO.read(new File("src/main/resources/images/nameScroll/white.PNG"));
        yellowNameScroll = ImageIO.read(new File("src/main/resources/images/nameScroll/yellow.PNG"));
    }

    public ImageIcon getCloudTransition() {
        cloudTransition.getImage().flush();
        return cloudTransition;
    }

    public ImageIcon getRedNameScroll(int width, int height) {
        return new ImageIcon(redNameScroll.getScaledInstance(width, height, Image.SCALE_DEFAULT));
    }

    public ImageIcon getBlackNameScroll(int width, int height) {
        return new ImageIcon(blackNameScroll.getScaledInstance(width, height, Image.SCALE_DEFAULT));
    }

    public ImageIcon getGreenNameScroll(int width, int height) {
        return new ImageIcon(greenNameScroll.getScaledInstance(width, height, Image.SCALE_DEFAULT));
    }

    public ImageIcon getBlueNameScroll(int width, int height) {
        return new ImageIcon(blueNameScroll.getScaledInstance(width, height, Image.SCALE_DEFAULT));
    }

    public ImageIcon getWhiteNameScroll(int width, int height) {
        return new ImageIcon(whiteNameScroll.getScaledInstance(width, height, Image.SCALE_DEFAULT));
    }

    public ImageIcon getYellowNameScroll(int width, int height) {
        return new ImageIcon(yellowNameScroll.getScaledInstance(width, height, Image.SCALE_DEFAULT));
    }

    public Image getBackground() {
        return background;
    }
}