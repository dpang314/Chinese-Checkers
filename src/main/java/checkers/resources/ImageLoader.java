package checkers.resources;

import java.io.IOException;

public class ImageLoader {
    private CommonImages commonImages;
    private MenuPanelImages menuPanelImages;
    private GamePanelImages gamePanelImages;

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
