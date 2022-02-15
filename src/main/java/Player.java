import java.awt.Color;

public abstract class Player {	
	private Color color;
	private String playerName;


	public Player(Color color, String playerName) {};
	public abstract Move getMove(Board board);
	public Color getColor() { return color; };
	public String getName() { return playerName; };
  public String type(){
    return "human";
  }

}
