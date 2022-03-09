import java.awt.Color;

public class CrappyGame {
	
	static QuinnStrategy blue = new QuinnStrategy(Color.BLUE, "BLUE"), white = new QuinnStrategy(Color.WHITE, "WHITE"), yellow = new QuinnStrategy(Color.YELLOW, "YELLOW"), red = new QuinnStrategy(Color.RED, "RED"), black = new QuinnStrategy(Color.BLACK, "BLACK"), green = new QuinnStrategy(Color.GREEN, "GREEN");
	
	/*
	 * PLEASE DON'T CHANGE ANYTHING
	 * BESIDES THIS BLOCK OF CODE
	 * UNLESS YOU KNOW WHAT YOU'RE DOING
	 */
	
	//****************************\\
	
	static int msDelay = 500;
	
	static Player[] players = new Player[]
		{blue,red};
	
	//****************************//
	
	public static void main(String[] args) {
		
		Board b = new Board(players);
		
		Move blueMove;
		Move whiteMove;
		Move yellowMove;
		Move redMove;
		Move blackMove;
		Move greenMove;
		
		while(true) {
		
			do {
				if(blue.getWR()==null) break;
				blueMove = blue.getMove(b);
				b.move(blueMove);
				b.printBoard();
			} while (blueMove!=null);
			
			do {
				if(white.getWR()==null) break;
				whiteMove = white.getMove(b);
				b.move(whiteMove);
				b.printBoard();
			} while (whiteMove!=null);
			
			do {
				if(yellow.getWR()==null) break;
				yellowMove = yellow.getMove(b);
				b.move(yellowMove);
				b.printBoard();
			} while (yellowMove!=null);
			
			do {
				if(red.getWR()==null) break;
				redMove = red.getMove(b);
				b.move(redMove);
				b.printBoard();
			} while (redMove!=null);
			
			do {
				if(black.getWR()==null) break;
				blackMove = black.getMove(b);
				b.move(blackMove);
				b.printBoard();
			} while (blackMove!=null);
			
			do {
				if(green.getWR()==null) break;
				greenMove = green.getMove(b);
				b.move(greenMove);
				b.printBoard();
			} while (greenMove!=null);
			
			try {
				Thread.sleep(msDelay);
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
			}
		}
	}
}
