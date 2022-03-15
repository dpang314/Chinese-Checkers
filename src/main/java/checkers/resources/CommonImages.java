package checkers.resources;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CommonImages {
    private Image background;

    public CommonImages() throws IOException {
        background = ImageIO.read(new File("src/main/resources/images/background.PNG")).getScaledInstance(1280,720,Image.SCALE_DEFAULT);
    }

    public Image getBackground() {
        return background;
    }
}