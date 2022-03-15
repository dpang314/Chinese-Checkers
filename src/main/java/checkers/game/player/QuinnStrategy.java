package checkers.game.player;/*
 * Quinn Hancock
 * Chinese Checkers
 * Group Project 2022
 * Red Team
 */

import checkers.game.board.Move;
import checkers.game.board.Position;
import checkers.game.Board;

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
 */

/*
 * FAQ
 * 	Q: will you change it so it looks at future moves too?
 * 	A: no
 */

public class QuinnStrategy extends ComputerStrategy implements Serializable {

	private static final long serialVersionUID = 0xFECE;

	/*
	 * The distance from the objective position
	 * after which moving pegs in that outer zone
	 * becomes more valueable. Designed to encourage
	 * moving faraway pegs before close pegs to
	 * prevent peg stranding
	 */
	protected static double valueThreshold = 10;
	
	/*
	 * How much it starts scaling up in that threshold,
	 * higher values mean it's more valuable to move 
	 * the pegs in that area
	 */
	protected static double scale = 1;
	
	//a method to set these aforementioned weighting values
	protected static void setWeighting(double valueThreshold, double scale) {
		QuinnStrategy.valueThreshold = valueThreshold;
		QuinnStrategy.scale = scale; 
	}

	//the board it sees
	Board boardAtTurnStart = null;
	
	//a queue of the jumps it wants to make
	ArrayDeque<Move> optimalJumpChain;
	
	//determines if it is the same turn still
	private boolean moveCalculated = false;
	
	//holds the position that the strategy is aiming for
	private Position obj;
	
	public QuinnStrategy(Color color, String playerName) {
		super(color, playerName);
	}

	@Override
	public Move getMove(Board board) {
		
		/* 
		 * if it's the first time the strategy has been used
		 * the objective towards which the pegs are moving
		 * is updated
		 */
		if(obj == null) {
			this.setObjPos();
		}
		
		//if no move has been found yet, set the board state and find a move
		if(!moveCalculated) {
			boardAtTurnStart=board;
			try {
				for(Position p : this.posArr) {
					investigateMoves(p);
				}
			} 
			/*
			 * this catch block will
			 * trigger if an
			 * explicitly defined
			 * fringe case is found
			 */
			catch (Exception e) {}
			
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
		
		//polls the jumpc
		Move move = optimalJumpChain.poll();
		
		return move;
		
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
		
		/*
		 * checks if the path is optimal
		 * assuming that the current path
		 * is not just a single position
		 * (which it would be if this node is
		 * a root node)
		 */
		if(!node.isRoot()) {	
			checkAndUpdateIfOptimal(path);
		}		
		return node;
	}
	
	Position[] optimalSpotChain = null;
	double currentBestValue = -Double.MAX_VALUE;
	int currentFastestPath = 0;

	protected void checkAndUpdateIfOptimal(Position[] path) throws Exception {
		
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
		boolean betterMove = valueComp>1.0;
		boolean sameMoveFaster = Math.abs(valueComp)<=2.0 && currentChainLength<currentFastestPath;
		
		boolean isFringeCaseMove = isFringeCase(currentGrandMove);
		if(betterMove||sameMoveFaster||isFringeCaseMove) { 
			//things that it does if the move is optimal
			
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
	
	/*
	 * looks at the win region of this player
	 * (which is assigned by the game or board
	 * or something idk) and sees what the color is,
	 * then assigns the objective position.
	 * The objective position is the farthest/deepest
	 * position in the win region.
	 */
	private void setObjPos() {
		
		Position[] winReg = this.getWR();
		
		if(winReg[0].equals(Board.homeBk[0])) {
			this.obj = new Position(4,12);
		} else if (winReg[0].equals(Board.homeG[0])) {
			this.obj = new Position(12,12);
		} else if (winReg[0].equals(Board.homeBu[0])) {
			this.obj = new Position(16,0);
		} else if (winReg[0].equals(Board.homeW[0])) {
			this.obj = new Position(12,0);
		} else if (winReg[0].equals(Board.homeY[0])) {
			this.obj = new Position(4,0);
		} else if (winReg[0].equals(Board.homeR[0])) {
			this.obj = new Position (0,0);
		}
	}
	
	/*
	 * a method that returns the distance-weighted
	 * value of a possibly techincally invalid super-
	 * move from the peg's starting position to its
	 * ending position. This doesn't involve in-between
	 * jumps or steps or anything. "Move" is only a
	 * parameter because it's a nice representation of
	 * a jump from an initial position to an final position
	 * of a single peg.
	 */
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
	
	// ----- ANNOYING FRINGE CASE METHOD(S) -----
	
	private boolean isFringeCase(Move m) {
		return checkFringeCase0(m) ||
				checkFringeCase1(m);
	}
	
	private boolean checkFringeCase0(Move m) {
		Position start = m.getStartPosition();
		Position end = m.getEndPosition();
		
		Position nuisance = new Position(4,6);
		Position reqObj = new Position(0,0);
		
		boolean fillCond = boardAtTurnStart.isOccupied(reqObj) &&
							boardAtTurnStart.isOccupied(reqObj.getBL()) &&
							boardAtTurnStart.isOccupied(reqObj.getBR()) &&
							boardAtTurnStart.isOccupied(reqObj.getBL().getBL()) &&
							boardAtTurnStart.isOccupied(reqObj.getBL().getBR()) &&
							boardAtTurnStart.isOccupied(reqObj.getBL().getBR()) &&
							boardAtTurnStart.isOccupied(nuisance.getTL()) &&
							boardAtTurnStart.isOccupied(nuisance.getTR()) &&
							boardAtTurnStart.isOccupied(nuisance) &&
							(!boardAtTurnStart.isOccupied(nuisance.getTR().getR()) ||
							!boardAtTurnStart.isOccupied(nuisance.getTR().getL()));
		
		if(!fillCond) {
			return false;
		}
		
		if(!boardAtTurnStart.isOccupied(nuisance.getTR().getR()) && start.equals(nuisance.getTL()) && end.equals(nuisance.getTR().getR())) {
			return true;
		}
		if(!boardAtTurnStart.isOccupied(nuisance.getTL().getL()) && start.equals(nuisance.getTR()) && end.equals(nuisance.getTL().getL())) {
			return true;
		}
		
		return false;
	}
	
	private boolean checkFringeCase1(Move m) {
		Position start = m.getStartPosition();
		Position end = m.getEndPosition();
		
		Position nuisance = new Position(12,6);
		Position reqObj = new Position(16,0);
		
		boolean fillCond = boardAtTurnStart.isOccupied(reqObj) &&
							boardAtTurnStart.isOccupied(reqObj.getTL()) &&
							boardAtTurnStart.isOccupied(reqObj.getTR()) &&
							boardAtTurnStart.isOccupied(reqObj.getTL().getTL()) &&
							boardAtTurnStart.isOccupied(reqObj.getTL().getTR()) &&
							boardAtTurnStart.isOccupied(reqObj.getTL().getTR()) &&
							boardAtTurnStart.isOccupied(nuisance.getBL()) &&
							boardAtTurnStart.isOccupied(nuisance.getBR()) &&
							boardAtTurnStart.isOccupied(nuisance) &&
							(!boardAtTurnStart.isOccupied(nuisance.getBR().getR()) ||
							!boardAtTurnStart.isOccupied(nuisance.getBR().getL()));
		
		if(!fillCond) {
			return false;
		}
		
		if(!boardAtTurnStart.isOccupied(nuisance.getBR().getR()) && start.equals(nuisance.getBL()) && end.equals(nuisance.getBR().getR())) {
			return true;
		}
		if(!boardAtTurnStart.isOccupied(nuisance.getBL().getL()) && start.equals(nuisance.getBR()) && end.equals(nuisance.getBL().getL())) {
			return true;
		}
		
		return false;
	}
	
}