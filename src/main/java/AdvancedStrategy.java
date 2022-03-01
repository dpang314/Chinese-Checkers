import java.awt.Color;
import java.util.ArrayList;

public class AdvancedStrategy extends Player {
	private int dist;
	private double[] pAvgs = new double[10];
	private Position[]pegPos = new Position[10];
	private Board testBoard;
	
	//number r and length l of random walks, respectively;
	//change as desired
	private int r = 10;
	private int l = 10;
	
	//Tracks best ending position of every peg
	private Position[] endPos = new Position[10];
	
	public AdvancedStrategy(Color color, String playerName) {
		super(color, playerName);
		//fills peg arrays
		for (Position pos : ) {
			
		}
	}
	
	private void randomWalk(Peg peg) {
		//initialize pegdistance
		int pegDistance = ;
		
		int temp = dist - pegDistance; //we need pegDistance variable
		int minDist = Integer.MAX_VALUE;
		for (int i = 1; i<=r; i++) {
			Position beginningPos = peg.getPos();
			for (int j = 1; j<=l; j++) {
				//Sets initial arraylist of possible moves it can make
				ArrayList<Position> moves = testBoard.possibleMoves(beginningPos,false);
				beginningPos = moves.get((int)(Math.random()*moves.size()));
				
			}
			int rwDist;
			if(rwDist < minDist) {
				minDist = rwDist;
				//endPos[peg] = 
			}
			pegDistance += rwDist;
		}
		pegDistance /= r;
		pAvgs[peg] =  pegDistance;

	}
	
	public Move getMove(Board board){
		testBoard = board.clone();
		
		//keeps record to make sure peg indices line up
		//with pAvgs indices since move only takes positions
		int pegIndex = 0;
		Peg[] pegHolder = new Peg[10];
		
		for (Peg[] pegArr : testBoard.getPegs()) {
			for (Peg peg : pegArr) {
				if (peg.getOwner()==this) {
					randomWalk(peg);
					pegHolder[pegIndex] = peg;
					pegIndex++;
				}
			}
		}
		
		double min = Double.MAX_VALUE;
		int optIndex = 0;
		for (int i = 0; i<10; i++) {
			if (pAvgs[i] < min) {
				min = pAvgs[i];
				optIndex = i;
			}
		}
		
		//MAY REQUIRE CHANGE IF WE NEED TO UPDATE THE SCREEN PER INDIVIDUAL MOVE
		return new Move(pegHolder[optIndex].getPos(),endPos[optIndex],this);
	}
	
	public boolean isHuman() {
		return false;
	}
	
}
