import java.awt.*;
import java.io.File;

public class Util {
    public static Font getBigFont() {
        return bigFont;
    }

    public static Font getPlayerFont() {
        return playerFont;
    }

    public static final Color RED = new Color(173,61,49,255);
    public static final Color BLACK = new Color(20, 18, 14, 255);
    public static final Color GREEN = new Color(95, 119, 82, 255);
    public static final Color BLUE = new Color(90,85,125,255);
    public static final Color WHITE = new Color(225,208,190,255);
    public static final Color YELLOW = new Color(216,158,77,255);


    public static enum PlayerType {
        COMPUTER_EASY,
        COMPUTER_HARD,
        HUMAN,
        DEFAULT, // If previously none, but now initializing type
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
