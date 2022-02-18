import java.awt.Color;
import java.util.ArrayList;

public abstract class Player {	
	private Color color;
	private String playerName;
	private ArrayList<Position> posArr = new ArrayList<Position>();
	
	public Player(Color color, String playerName) {
		this.color=color;
		this.playerName=playerName;
		
	};
	public abstract Move getMove();
	public Color getColor() { return color; }
	public String getName() { return playerName; }
	public void addInitalPos(Position pos) {
		posArr.add(pos);
	}
}
