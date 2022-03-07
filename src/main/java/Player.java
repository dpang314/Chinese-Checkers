import java.awt.Color;
import java.util.ArrayList;

public abstract class Player {	
	private Color color;
	private String playerName;
	private Position[] winReg;
	private int winRegInt;
	public ArrayList<Position> posArr;
	
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
	public void assignWinReg(Position[] WR) {
		winReg=WR;
	}
	public void assignWinRegInt(int WR) {
		winRegInt=WR;
	}
	public Position[] getWR() {
		return winReg;
	}
	public int getWRInt() {
		return winRegInt;
	}
}
