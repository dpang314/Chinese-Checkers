import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ImageLoader {
    private CommonImages commonImages;
    private MenuPanelImages menuPanelImages;
    private GamePanelImages gamePanelImages;

    // Images were designed for 1080p, but project will use 720p
    private static final double SCALE = 1.5;

    public ImageLoader() {
        try {
            commonImages = new CommonImages();
            menuPanelImages = new MenuPanelImages();
            gamePanelImages = new GamePanelImages();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public CommonImages getCommonImages() {
        return commonImages;
    }

    public MenuPanelImages getMenuPanelImages() {
        return menuPanelImages;
    }

    public GamePanelImages getGamePanelImages() {
        return gamePanelImages;
    }
}
