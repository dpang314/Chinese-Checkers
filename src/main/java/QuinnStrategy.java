/*
 * Quinn Hancock
 * Chinese Checkers
 * Group Project 2022
 * Red Team
 */

import java.awt.Color;
import java.awt.geom.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.ArrayDeque;
import javax.swing.tree.*;

/*
 * HOW IT WORKS
 *  
 * iterates over the positions of owned pegs,
 * recursively pathing over the moves that each one could take
 * and deciding which move chain is the best based on how far it
 * goes relative to the corner of the target triangle. Distances
 * are weighted with a quadratic scalar to prioritize moving
 * the pegs that are farthest away.
 * 
 * FAQ
 * 	Q: will you change it so it looks at future moves too?
 * 	A: no
 */

public class QuinnStrategy extends Player implements Serializable {

	//the board it sees
	Board boardAtTurnStart = null;
	
	//a queue of the jumps it wants to make
	ArrayDeque<Move> optimalJumpChain;
	
	//determines if it is the same turn still
	private boolean moveCalculated = false;
	
	//holds the position that the strategy is aiming for
<<<<<<< Updated upstream
	private Position obj = getObjPos(this.getColor());
=======
	private Position obj;
>>>>>>> Stashed changes
	
	public QuinnStrategy(Color color, String playerName) {
		super(color, playerName);
	}
	
	public void assignWinReg(int n) {
		super.assignWinReg(n);
		this.obj = getObjPos(this.getWR());
	}
	
	//only for testing
	public QuinnStrategy() {
		this(null,null);
	}

	@Override
	public Move getMove(Board board) {
		
		//if no move has been found yet, set the board state and find a move
		if(!moveCalculated) {
			boardAtTurnStart=board;
			try {
				for(Position p : this.posArr) {
					investigateMoves(p);
				}
			} catch (Exception e) {}
			
			optimalJumpChain = createMoveQueue(optimalSpotChain);
			moveCalculated = true;
		}
		
		//if the turn is over, reset
		if(optimalJumpChain.size() == 0) {
			moveCalculated = false;
			optimalSpotChain = null;
			currentBestValue = -Double.MAX_VALUE;
			currentFastestPath = 0;
		}
		
		return optimalJumpChain.poll();
		
	}
	
	//creates a queue of moves by iterating over the positions and looking at two at a time
	private ArrayDeque<Move> createMoveQueue(Position[] path) {
		ArrayDeque<Move> ret = new ArrayDeque<Move>();
		for(int i = 0; i<path.length-1; i++) {
			Move newJump = new Move(path[i],path[i+1],this);
			ret.add(newJump);
		}
		return ret;
	}
	
	//investigates all moves for a given position
	private void investigateMoves(Position pos) throws Exception {
		for(Position p : boardAtTurnStart.possibleAdjacentMoves(pos)) {
			checkAndUpdateIfOptimal(new Position[] {pos,p});
		}
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(pos);
		investigateSubJumps(root);
	}
	
	//recursive algorithm to analyze the possible jump chain following the given one
	private DefaultMutableTreeNode investigateSubJumps(DefaultMutableTreeNode node) throws Exception {
		
		//stores the position passed in via the node
		Position passedPos = (Position)node.getUserObject();
		
		//creates Position array from object path (weird casting issue)
		Position[] path = Arrays.copyOf(node.getUserObjectPath(), node.getLevel()+1, Position[].class);
		
		//iterates over possible jumps that could be made from the passed position
		for(Position p : boardAtTurnStart.possibleMoves(passedPos, true)) {
			
			//investigates subtree for the possible jump, assuming it hasn't been touched already
			boolean touchedYet = alreadyTraversedInPath(path, p);
			
			if(!touchedYet) {
				DefaultMutableTreeNode nextNode = new DefaultMutableTreeNode(p);
				node.removeAllChildren();
				node.add(nextNode);
				
				nextNode = investigateSubJumps(nextNode);
			}
		}
		
		if(!node.isRoot()) {	
			checkAndUpdateIfOptimal(path);
		}		
		return node;
	}
	
	Position[] optimalSpotChain = null;
	double currentBestValue = -Double.MAX_VALUE;
	int currentFastestPath = 0;

	private void checkAndUpdateIfOptimal(Position[] path) throws Exception {
		
		//gets the root position (original position at start of turn)
		Position rootPos = path[0];
		Position endPos = path[path.length-1];

		//checks value of the current position difference
		Move currentGrandMove = new Move(rootPos, endPos, this);
		double currentValue = grandMoveValue(currentGrandMove);

		//sees how many jumps it would take to get here
		int currentChainLength = path.length-1;

		//compares current grand move value to current best grand move value
		double valueComp = percentDiff(currentBestValue,currentValue);
		
		//if the move is better or equivalent but faster, it becomes the current optimal chain
		boolean betterMove = valueComp>2.0;
		boolean sameMoveFaster = Math.abs(valueComp)<=2.0 && currentChainLength<currentFastestPath;
		
		boolean isFringeCaseMove = checkFringeCase0(currentGrandMove);
		if(betterMove||sameMoveFaster||isFringeCaseMove) { 
			optimalSpotChain = path;
			currentBestValue = currentValue;
			currentFastestPath = currentChainLength;
			if(isFringeCaseMove) {
				throw new Exception("Important specified fringe case found.");
			}
		}
	}
	
	//checks if a position has already been touched in a jump path
	private boolean alreadyTraversedInPath(Position[] path, Position check) {
		boolean ret = false;
		
		for(int i = 0; i<path.length-1; i++) {
			if(path[i].equals(check)) {
				ret = true;
			}
		}
		
		return ret;
	}
	
	private static Point2D.Double getPoint(Position p) {
		final int centerRow = 8;
		
		//distance between row and middle-row, multiplied by cos30 to get vertical distance
		double y = (centerRow-p.getRow()) * (Math.cos(Math.PI/6));
		
		//distance from center of row to index
		double x = p.getColumn()-((double)(Board.rowWidths[p.getRow()]-1)/2);
		
		return new Point2D.Double(x,y);
	}
	
	private static Position getObjPos(Color c) {
		
		Position[] winReg = Board.getWinRegion(c);
		
		if(winReg[0]==Board.homeBk[0]) {
			return new Position(4,12);
		} else if (winReg[0]==Board.homeG[0]) {
			return new Position(12,12);
		} else if (winReg[0]==Board.homeBu[0]) {
			return new Position(16,0);
		} else if (winReg[0]==Board.homeW[0]) {
			return new Position(12,0);
		} else if (winReg[0]==Board.homeY[0]) {
			return new Position(4,0);
		} else if (winReg[0]==Board.homeR[0]) {
			return new Position (0,0);
		} else {
			return null;
		}
	}
	
	private double grandMoveValue(Move move) {
		return scaledDist(move.getStartPosition(),obj)-scaledDist(move.getEndPosition(),obj);
	}
	
	//returns a double representing the percent difference between a and b
	private static double percentDiff(double a, double b) {
		double numDiff = b-a;
		return numDiff/Math.abs(a) * 100;
	}
	
	//applies a quadratic scalar to the found distance
	private static double scaledDist(Position p, Position obj) {
		
		//distance beyond which getting closer becomes more valuable
		final double valueThreshold = 7;
		final double scale = 100;
		final int power = 2;
		
		//objective point
		Point2D.Double objPoint = getPoint(obj);
		
		//finds distance, if it goes beyond the range, it scales quadratically
		double distance = objPoint.distance(getPoint(p));
		if(distance >= valueThreshold) {
			double ybump = -((scale/20)*Math.pow(valueThreshold, power)-valueThreshold);
			distance = (scale/20) * Math.pow(distance, power) + ybump;
		}
		return distance;
	}
	
	// ----- ANNOYING FRINGE CASE METHODS -----
	private boolean checkFringeCase0(Move m) {
		Position start = m.getStartPosition();
		Position end = m.getEndPosition();
		
		Position nuisance = new Position(4,6);
		
		if(!(start.equals(nuisance) &&
			boardAtTurnStart.isOccupied(start.getTL()) &&
			boardAtTurnStart.isOccupied(start.getTR()))) 
		{
			return false;
		}
		
		boolean left = boardAtTurnStart.isOccupied(start.getTL().getL());
		if(!left && end.equals(start.getL())) { 
			return true;
		} else if (left && end.equals(start.getR())) {
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {	
		
		Board b = new Board();
		QuinnStrategy myStrategy = new QuinnStrategy(Color.BLUE, "Quinn");
		b.fillPos(Board.homeBk);
		b.fillPos(Board.homeY);
		b.fillPos(Board.homeW);
		b.fillPos(Board.homeG);
				
		for(Position p : Board.homeBu) {
			myStrategy.posArr.add(p);
			b.fillPos(p);
		}
		
		System.out.println("GAME START!");
		b.printBoard();
		
		//prints move stack
		Move move;
		do {
			move = myStrategy.getMove(b);
			
			if(move!=null) {
				b.move(move);
				b.printBoard();
				System.out.println("TURN MADE!");
			} else {
//				System.out.println("TURN ENDED!");
			}
		try {
			Thread.sleep(75);
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
		} while (true);	
	}
}