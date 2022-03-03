import java.awt.Color;
import java.util.ArrayList;

public abstract class Player {	
	private Color color;
	private String playerName;
	private int winReg;
	private ArrayList<Position> posArr;
	
	public Player(Color color, String playerName) {
		this.color=color;
		this.playerName=playerName;
		posArr = new ArrayList<Position>();
	};
	public abstract Move getMove(Board board);
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
