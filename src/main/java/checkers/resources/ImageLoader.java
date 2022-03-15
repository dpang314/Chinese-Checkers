package checkers.resources;

import java.io.IOException;

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
