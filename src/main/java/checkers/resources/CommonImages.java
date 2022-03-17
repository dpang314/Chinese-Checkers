package checkers.resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CommonImages {
    private Image background;
    private ImageIcon cloudTransition;

    public CommonImages() throws IOException {
        background = ImageIO.read(new File("src/main/resources/images/background.PNG")).getScaledInstance(1280,720,Image.SCALE_DEFAULT);
        cloudTransition = new ImageIcon("src/main/resources/images/clouds.gif");
    }

    public ImageIcon getCloudTransition() {
        cloudTransition.getImage().flush();
        return cloudTransition;
    }

    public Image getBackground() {
        return background;
    }
}