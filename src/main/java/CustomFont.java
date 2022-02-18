import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;

public class CustomFont {
	private static Font font;
	
	public static void load() {
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/CarterOne-Regular.ttf"));
		} catch (Exception e) {
		     e.printStackTrace();
		}
	}
	
	public static Font getFont() {
		return font;
	}
}
