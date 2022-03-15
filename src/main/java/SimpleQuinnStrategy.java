import java.awt.Color;

/*
 * -------------------------------------------
 * READ THIS BEFORE CHANGING STUFF HAPHAZARDLY
 * -------------------------------------------
 * 
 * Does the same thing as QuinnStrategy,
 * but there's a random check for whether
 * or not to even consider each move
 * so it's random whether or not it will find the 'best'
 * move though it will always find a move that is fine-ish.
 * 
 * 
 * THE STUPIDITY INT
 * 
 * increasing the 'stupidity' int will make the probability
 * of looking at each possible move lower, thereby causing it 
 * to look at fewer of the possible moves and decrease the
 * likelihood of it finding an optimal one. TLDR: bigger number=
 * more stupid
 * 
 * 
 * THE FORCE-CHECKING INT
 * 
 * There is an int called "forceCheckInterval" that will
 * force the strategy to check every nth move to prevent 
 * the (astronomically unlikely) case in which the strategy
 * never passes the random check to look at any move,
 * and is instead caught with its pants down due to not having
 * any move to make.
 * 
 * The higher the int, the stupider the strategy
 * 
 * IF THIS INT IS ABOVE 14, A CRASH BECOMES POSSIBLE,
 * ALBEIT STILL ASTRONOMICALLY IMPROBABLE:
 * 
 * (1 - ((stupidity-1)/(stupidity))^forceCheckInterval) chance
 */

public class SimpleQuinnStrategy extends QuinnStrategy {

	public int stupidity = 8;
	//read above for more info
	
	public int forceCheckInterval = 7;
	//don't exceed 14 please, see above for more info
	
	public SimpleQuinnStrategy(Color color, String playerName) {
		super(color, playerName);
	}
	
	private int forceCheckMove = 0;
	
	protected void checkAndUpdateIfOptimal(Position[] path) throws Exception {
		forceCheckMove++;
		forceCheckMove%=forceCheckInterval;
		if(boundedRandom(0,stupidity)==0 || forceCheckMove==0) {
			super.checkAndUpdateIfOptimal(path);
		}
	}
	
	private static int boundedRandom(int lowerBound, int upperBound) {
		return (int)(Math.random()*(upperBound-lowerBound+1) + lowerBound); 
	}
	
}