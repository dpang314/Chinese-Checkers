import java.awt.Color;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import javax.swing.tree.*; //it's really more like a vine

public class QuinnStrategy extends Player {

	Board boardAtTurnStart = null;
	Stack<Move> optimalJumpChain;
	Position[] optimalSpotChain;
	double currentBestValue = 0;
	int currentFastestPath;
	
	//determines if it is the same turn still
	private boolean moveCalculated;
	
	public QuinnStrategy() {
		this(null,null);
	}
	
	public QuinnStrategy(Color color, String playerName) {
		super(color, playerName);
	}

	@Override
	public Move getMove(Board board) {
		boardAtTurnStart=board;
		
		return null;
	}
	
	private Stack<Move> createMoveChain(Position[] path) {
		Stack<Move> ret = new Stack<Move>();
		ret.push(null);
		for(int i = path.length-1; i>0; i++) {
			Move newJump = new Move(path[i+1],path[i],this);
			ret.push(newJump);
		}
		return ret;
	}
	
	//recursive algorithm to figure out the possible jump chain following the given one
	private DefaultMutableTreeNode getJumpChain(DefaultMutableTreeNode node) {
		
		//stores the position passed in via the node
		Position passedPos = (Position)node.getUserObject();
		
		//creates Position array from object path (weird casting issue)
		Position[] path = Arrays.copyOf(node.getUserObjectPath(), node.getDepth(), Position[].class);
		
		//iterates over possible jumps that could be made from the passed position
		for(Position p : boardAtTurnStart.possibleMoves(passedPos, true)) {
			
			//returns subtree for the possible jump, assuming it hasn't been touched already
			boolean touchedYet = alreadyTraversedInPath(path, p);
			if(!touchedYet) {
				DefaultMutableTreeNode nextNode = new DefaultMutableTreeNode(p);
				node.removeAllChildren();
				node.add(nextNode);
				nextNode = getJumpChain(nextNode);
			}
		}
		
		//gets the root position (original position at start of turn)
		Position rootPos = (Position) ((DefaultMutableTreeNode) node.getRoot()).getUserObject();
		
		//checks value of the current position difference
		Move currentGrandMove = new Move(rootPos,passedPos, this);
		double currentValue = grandMoveValue(currentGrandMove,getObjPos(Color.RED));
		
		//sees how many jumps it would take to get here
		int currentChainLength = node.getDepth();
		
		//compares current grand move value to current best grand move value
		double valueComp = percentDiff(currentBestValue,currentValue);
		
		//if the move is better or equivalent but faster, it becomes the current optimal chain
		boolean betterMove = valueComp>2.0;
		boolean sameMoveFaster = Math.abs(valueComp)<=2.0 && currentChainLength<currentFastestPath;
		if(betterMove||sameMoveFaster) { 
			optimalSpotChain = path;
			currentBestValue = currentValue;
			currentFastestPath = currentChainLength;
		}
		
		return node;
	}
	
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
		if(c==Color.BLACK) {
			return new Position(4,12);
		} else if (c==Color.GREEN) {
			return new Position(12,12);
		} else if (c==Color.BLUE) {
			return new Position(16,0);
		} else if (c==Color.WHITE) {
			return new Position(12,0);
		} else if (c==Color.YELLOW) {
			return new Position(4,0);
		} else if (c==Color.RED) {
			return new Position (0,0);
		} else {
			return null;
		}
	}
	
	private static double grandMoveValue(Move move, Position obj) {
		return scaledDist(move.getStartPosition(),obj)-scaledDist(move.getEndPosition(),obj);
	}
	
	private static double percentDiff(double a, double b) {
		double numDiff = a-b;
		return numDiff/a * 100;
	}
	
	private static double scaledDist(Position p, Position obj) {
		
		//distance beyond which getting closer becomes more valuable
		final double valueThreshold = 9;
		final double scale = 1.30;
		
		//objective point
		Point2D.Double objPoint = getPoint(obj);
		
		//finds distance, if it goes beyond the range, it scales quadratically
		double distance = objPoint.distance(getPoint(p));
		if(distance >= valueThreshold) {
			double ybump = -((scale/20)*Math.pow(valueThreshold, 2)-valueThreshold);
			distance = (scale/20) * Math.pow(distance, 2) + ybump;
		}
		return distance;
	}
	
	//for testing
	private static void printPosChain(Position[] p) {
		for(int i = 0; i<p.length; i++) {
			System.out.println(i + ": " + p[i]);
		}
		System.out.println();
	}
	
	//for testing
	private static void printPosChain(ArrayList<Position> p) {
		for(int i = 0; i<p.size(); i++) {
			System.out.println(i + ": " + p.get(i));
		}
		System.out.println();
	}
	
	//for testing
	private static void printNode(TreeNode n) {
		System.out.println("TOP-NODE: " + n);
		for(int c = 0; c<n.getChildCount(); c++) {
			System.out.println("   CHILD-" + c + ": " + n.getChildAt(c));
		}
	}
	
	public static void main(String[] args) {
		QuinnStrategy myStrategy = new QuinnStrategy();
		
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode();
		Position rootPos = new Position(10,6);
		rootNode.setUserObject(rootPos);
		
		Board b = new Board();
		b.fillPos(new Position(7,4));
		b.fillPos(new Position(9,5));
		myStrategy.boardAtTurnStart=b;
		
		Position obj = getObjPos(Color.RED);
	}
}
