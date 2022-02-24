import java.awt.Color;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.tree.*;

public class SimpleAdvancedStrategy extends Player {

	Board boardAtTurnStart = null;
	
	public SimpleAdvancedStrategy() {
		this(null,null);
	}
	
	public SimpleAdvancedStrategy(Color color, String playerName) {
		super(color, playerName);
	}

	@Override
	public Move getMove(Board board) {
		boardAtTurnStart=board;
		
		return null;
	}
	
	private DefaultMutableTreeNode getJumpChain(DefaultMutableTreeNode node) {
		
		//stores the position passed in via the node
		Position passedPos = (Position)node.getUserObject();
		
		//iterates over possible jumps that could be made from the passed position
		for(Position p : boardAtTurnStart.possibleMoves(passedPos, true)) {
			
			//creates Position array from object path (weird casting issue)
			Object[] objPath = node.getUserObjectPath();
			Position[] path = Arrays.copyOf(objPath, objPath.length, Position[].class);
			
			//returns subtree for the possible jump, assuming it hasn't been touched already
			if(!alreadyTraversedInPath(path, p)) {
				DefaultMutableTreeNode nextNode = new DefaultMutableTreeNode(p);
				node.add(nextNode);
				nextNode = getJumpChain(nextNode);
			}
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
	
	private void printPosChain(Position[] p) {
		for(int i = 0; i<p.length; i++) {
			System.out.println(i + ": " + p[i]);
		}
		System.out.println();
	}
	private void printPosChain(ArrayList<Position> p) {
		for(int i = 0; i<p.size(); i++) {
			System.out.println(i + ": " + p.get(i));
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		SimpleAdvancedStrategy myStrategy = new SimpleAdvancedStrategy();
		
		Board b = new Board();
		b.fillPos(new Position(14,1));
		
		myStrategy.boardAtTurnStart=b;
		
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode();
		Position rootPos = new Position(15,1);
		rootNode.setUserObject(rootPos);
		
		rootNode = myStrategy.getJumpChain(rootNode);
		
		System.out.println("ROOT: " + rootNode.getUserObject());
		System.out.println();
	}
}
