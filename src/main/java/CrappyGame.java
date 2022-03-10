import java.awt.Color;
public class CrappyGame {
	private static QuinnStrategy blue, white, yellow, red, black, green;
	private static Board board;
	private static Player[] players;
	private static int msDelay = 0;
	
	private static void setupGame() {
		
		blue = new QuinnStrategy(Color.BLUE, "BLUE");
		white = new QuinnStrategy(Color.WHITE, "WHITE");
		yellow = new QuinnStrategy(Color.YELLOW, "YELLOW");
		red = new QuinnStrategy(Color.RED, "RED");
		black = new QuinnStrategy(Color.BLACK, "BLACK");
		green = new QuinnStrategy(Color.GREEN, "GREEN");
		
		
		{
		/*
		 * PLEASE DON'T CHANGE ANYTHING
		 * BESIDES THIS BLOCK OF CODE
		 * UNLESS YOU KNOW WHAT YOU'RE DOING
		 */	
		msDelay = 0; //the delay between each move, in ms
		players = new Player[] {blue, red};
		
		}
		
		
		board = new Board(players);
		
	}
	
	//performs a move for a single player
	private static void doMove(Player p, boolean verbose) {
		Move playerMove;
		
		//gets and executes moves as long as the return value is not null
		do {
			
			if(p.getWR()==null) break;
			playerMove = p.getMove(board);
			board.move(playerMove);
			if(verbose&&playerMove!=null) board.printBoard();
			
		} while (playerMove!=null);
		
		try {
			Thread.sleep(msDelay);
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
		
	}
	
	private static Player gameEnd(Board b) {
		for(Player player: players) {
			
			boolean filledByOpp = false;
			boolean unfilledPosition = false;
			
			for(Position p : Board.getWinRegion(player.getColor())) {
				Peg peg = b.getPegs()[p.getRow()][p.getColumn()];
				if(peg==null) {
					unfilledPosition = true;
					break;
				}
				if(peg.getOwner().getColor().equals(player.getColor())) {
					filledByOpp = true;
				}
			}
			
			if(!unfilledPosition && filledByOpp)
			return player;
		}
		return null;
	}
	
	public static int doGame(boolean verbose) {
		setupGame();

		int turns=0;
		Player winner = null;
		
		while(winner==null) {
			for(Player player : players) {
				doMove(player, verbose);
				winner = gameEnd(board);
				if(winner!=null) break;
			}
			turns++;
		}
		
		return turns;
	}
	
	public static void main(String[] args) {
		System.out.println(doGame(true));
	}
}
