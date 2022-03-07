import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Player implements Serializable {
	private Color color;
	private String playerName;
	private int winReg;
	public ArrayList<Position> posArr;
	
	public Player(Color color, String playerName) {
		this.color=color;
		this.playerName=playerName;
		posArr = new ArrayList<Position>();
	};
	public abstract Move getMove(Board board);
	public void setColor(Color c) { this.color = c; };
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
