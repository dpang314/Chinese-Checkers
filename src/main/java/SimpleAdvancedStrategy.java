import java.awt.Color;
import java.awt.geom.*;

public class SimpleAdvancedStrategy extends Player {

	public SimpleAdvancedStrategy(Color color, String playerName) {
		super(color, playerName);
	}

	@Override
	public Move getMove(Board board) {
		return null;
	}
	
	private static Point2D.Double getPoint(Position p) {
		final int centerRow = 8;
		
		//distance between row and middle-row, multiplied by cos30 to get vertical distance
		double y = (centerRow-p.getRow()) * (Math.cos(Math.PI/6));
		
		//distance from center of row to index
		double x = p.getColumn()-((double)(Board.rowWidths[p.getRow()]-1)/2);
		
		return new Point2D.Double(x,y);
	}
}
