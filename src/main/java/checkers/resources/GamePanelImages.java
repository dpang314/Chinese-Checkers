package checkers.resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GamePanelImages {
    private ImageIcon blackPeg, bluePeg, greenPeg, redPeg, whitePeg, yellowPeg;
    private ImageIcon redPegHighlighted, blackPegHighlighted, greenPegHighlighted, bluePegHighlighted, whitePegHighlighted, yellowPegHighlighted;
    private ImageIcon positionHighlight;
    private Image board, bigScroll;

    public GamePanelImages() throws IOException {
        loadPegs();
        positionHighlight = new ImageIcon(ImageIO.read(new File("./src/main/resources/images/game/highlight.PNG"))
                .getScaledInstance(32, 32, Image.SCALE_DEFAULT));
        board = ImageIO.read(new File("./src/main/resources/images/game/board.PNG"))
                .getScaledInstance(1280, 720, Image.SCALE_DEFAULT);
        bigScroll = ImageIO.read(new File("./src/main/resources/images/game/bigScroll.PNG"))
                .getScaledInstance(1280, 720, Image.SCALE_DEFAULT);
    }

    private void loadPegs() {
        try {
            Image black = ImageIO.read(new File("src/main/resources/images/game/pegs/pegBlack.PNG"))
                    .getScaledInstance(24, 24, Image.SCALE_DEFAULT);
            Image blue = ImageIO.read(new File("src/main/resources/images/game/pegs/pegBlue.PNG"))
                    .getScaledInstance(24, 24, Image.SCALE_DEFAULT);
            Image green = ImageIO.read(new File("src/main/resources/images/game/pegs/pegGreen.PNG"))
                    .getScaledInstance(24, 24, Image.SCALE_DEFAULT);
            Image red = ImageIO.read(new File("src/main/resources/images/game/pegs/pegRed.PNG"))
                    .getScaledInstance(24, 24, Image.SCALE_DEFAULT);
            Image white = ImageIO.read(new File("src/main/resources/images/game/pegs/pegWhite.PNG"))
                    .getScaledInstance(24, 24, Image.SCALE_DEFAULT);
            Image yellow = ImageIO.read(new File("src/main/resources/images/game/pegs/pegYellow.PNG"))
                    .getScaledInstance(24, 24, Image.SCALE_DEFAULT);

            Image blackH = ImageIO.read(new File("src/main/resources/images/game/pegs/pegBlackH.PNG"))
                    .getScaledInstance(24, 24, Image.SCALE_DEFAULT);
            Image blueH = ImageIO.read(new File("src/main/resources/images/game/pegs/pegBlueH.PNG"))
                    .getScaledInstance(24, 24, Image.SCALE_DEFAULT);
            Image greenH = ImageIO.read(new File("src/main/resources/images/game/pegs/pegGreenH.PNG"))
                    .getScaledInstance(24, 24, Image.SCALE_DEFAULT);
            Image redH = ImageIO.read(new File("src/main/resources/images/game/pegs/pegRedH.PNG"))
                    .getScaledInstance(24, 24, Image.SCALE_DEFAULT);
            Image whiteH = ImageIO.read(new File("src/main/resources/images/game/pegs/pegWhiteH.PNG"))
                    .getScaledInstance(24, 24, Image.SCALE_DEFAULT);
            Image yellowH = ImageIO.read(new File("src/main/resources/images/game/pegs/pegYellowH.PNG"))
                    .getScaledInstance(24, 24, Image.SCALE_DEFAULT);

            blackPeg = new ImageIcon(black);
            bluePeg = new ImageIcon(blue);
            greenPeg = new ImageIcon(green);
            redPeg = new ImageIcon(red);
            whitePeg = new ImageIcon(white);
            yellowPeg = new ImageIcon(yellow);

            blackPegHighlighted = new ImageIcon(blackH);
            bluePegHighlighted = new ImageIcon(blueH);
            greenPegHighlighted = new ImageIcon(greenH);
            redPegHighlighted = new ImageIcon(redH);
            whitePegHighlighted = new ImageIcon(whiteH);
            yellowPegHighlighted = new ImageIcon(yellowH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ImageIcon getImageIcon(Color color, boolean highlighted) {
        if (color.equals(Color.RED)) {
            return highlighted ? redPegHighlighted : redPeg;
        } else if (color.equals(Color.BLACK)) {
            return highlighted ? blackPegHighlighted : blackPeg;
        } else if (color.equals(Color.BLUE)) {
            return highlighted ? bluePegHighlighted : bluePeg;
        } else if (color.equals(Color.GREEN)) {
            return highlighted ? greenPegHighlighted : greenPeg;
        } else if (color.equals(Color.WHITE)) {
            return highlighted ? whitePegHighlighted : whitePeg;
        } else if (color.equals(Color.YELLOW)) {
            return highlighted ? yellowPegHighlighted : yellowPeg;
        }
        return null;
    }

    public ImageIcon getPositionHighlight() {
        return positionHighlight;
    }

    public Image getBoard() {
        return board;
    }

    public Image getBigScroll() {
        return bigScroll;
    }
}