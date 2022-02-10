
public class Board {
	public static boolean[][] empty; // All false, shape of board
	
	public boolean[][] getFilled() {
		boolean[][] board = empty.clone();
		// Fill
		return board;
	}
	
	public Board() {};
	public boolean canMove(Position startPos, Position targetPos) { return false; };
	public boolean playerPeg (Player player, Position startPos) { return false; };
	public void updatePeg (Position startPos, Position targetPos) {};
	public Position[] possibleMoves (Position startPos, boolean constraint) { return null; };
	public void move(Position startPos, Position endPos) {};
	public Peg[][] getPeg() { return null; };
}
