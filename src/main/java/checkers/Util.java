package checkers;

import java.awt.*;
import java.io.File;

public class Util {
    public static final Color RED = new Color(173, 61, 49, 255);
    public static final Color BLACK = new Color(20, 18, 14, 255);
    public static final Color GREEN = new Color(95, 119, 82, 255);
    public static final Color BLUE = new Color(49, 43, 78, 255);
    public static final Color WHITE = new Color(131, 126, 124, 255);
    public static final Color YELLOW = new Color(216, 158, 77, 255);
    private static Font bigFont;
    private static final Font font = load();

    public static Font getBigFont() {
        return bigFont;
    }

    public static Font load() {
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/fonts/CarterOne-Regular.ttf"));
            bigFont = font.deriveFont(15f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return font;
    }

    public static Font getFont() {
        return font;
    }

    public enum PlayerType {
        COMPUTER_EASY,
        COMPUTER_HARD,
        HUMAN,
        DEFAULT, // If previously none, but now initializing type
        NONE
    }

    public static String colorToString(Color color) {
        if (color.equals(Color.RED)) {
            return "Red";
        } else if (color.equals(Color.BLACK)) {
            return "Black";
        } else if (color.equals(Color.GREEN)) {
            return "Green";
        } else if (color.equals(Color.BLUE)) {
            return "Blue";
        } else if (color.equals(Color.WHITE)) {
            return "White";
        } else if (color.equals(Color.YELLOW)) {
            return "Yellow";
        }
        return null;
    }
}
