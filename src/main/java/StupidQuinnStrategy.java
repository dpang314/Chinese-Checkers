import java.awt.Color;

public class StupidQuinnStrategy extends QuinnStrategy {

	public StupidQuinnStrategy(Color color, String playerName) {
		super(color, playerName);
	}
	
	protected void checkAndUpdateIfOptimal(Position[] path) {
		if(boundedRandom(0,1)==1) {
			checkAndUpdateIfOptimal(path);
		}
	}
	
	private static int boundedRandom(int lowerBound, int upperBound) {
		return (int)(Math.random()*(upperBound-lowerBound+1) + lowerBound); 
	}
	
}
