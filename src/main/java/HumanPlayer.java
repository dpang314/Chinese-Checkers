import java.awt.Color;
import java.util.ArrayList;

public class HumanPlayer extends Player{	
	private Color color;
	private String playerName;
	private int winReg;
	public ArrayList<Position> posArr = new ArrayList<Position>();
	
	public HumanPlayer(Color color, String playerName) {
		super(color, playerName);
	};
	public Move getMove(Board board) {
		return null;
	}
	public Color getColor() { return color; }
	public String getName() { return playerName; }
	public void addInitalPos(Position pos) {
		posArr.add(pos);
	}
	public void assignWinReg(int n) {
		winReg=n;
	}
	public int getWR() {
		return winReg;
	}
}