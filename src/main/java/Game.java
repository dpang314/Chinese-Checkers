import java.util.Stack;

public class Game {
	private Player[] players;
	private Stack<Move> history;
	private int turn;
	private Board board;
	
	public Game(Player[] player, boolean shuffle) {};
	
	public void endTurn() {};
	public void save() {};
	public void undo() {};
	public void movePeg() {};
	public Player gameOver() { return null; };
	public Player[] getPlayers() { return null; };
	public Board getBoard() { return null; };
}
