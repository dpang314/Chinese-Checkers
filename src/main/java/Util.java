import java.awt.*;
import java.io.File;

public class Util {
    public static Font getBigFont() {
        return bigFont;
    }

    public static Font getPlayerFont() {
        return playerFont;
    }

    public static enum PlayerType {
        COMPUTER_EASY,
        COMPUTER_HARD,
        HUMAN,
        SELECTING, // If previously none, but now initializing type
        NONE
    }

    private static Font font = load();
    private static Font bigFont;
    private static Font playerFont;

    public static Font load() {
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/CarterOne-Regular.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        bigFont = font.deriveFont(15f);
        playerFont = font.deriveFont(28f);
        return font;
    }

    public static Font getFont() {
        return font;
    }
}
