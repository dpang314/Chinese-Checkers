import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

public class Board {
	
	private Peg[][] boardPos= {{null}, {null, null},{null, null, null},{null, null, null, null},{null, null, null, null, null, null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null, null, null, null, null},{null, null, null, null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null, null, null},{null, null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null, null, null},{null, null, null, null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null, null, null, null, null},{null, null, null, null, null, null, null, null, null, null, null, null, null},
			{null, null, null, null},{null, null, null},{null, null},{null}};
	
	//only so that the various directional position methods can be static
	private static final int[] rowWidths = {1,2,3,4,13,12,11,10,9,10,11,12,13,4,3,2,1};
	
	private Position[] homeR = {new Position(0, 0), 
								new Position(1, 0), new Position(1, 1), 
								new Position(2, 0), new Position(2, 1), new Position(2, 2),
								new Position(3, 0), new Position(3, 1), new Position(3, 2), new Position(3, 3)};
	private Position[] homeBk = {new Position(4, 9), new Position(4, 10), new Position(4, 11), new Position(4, 12), 
								new Position(5, 9), new Position(5, 10), new Position(5, 11), 
								new Position(6, 9), new Position(6, 10), 
								new Position(7, 9)};
	private Position[] homeG = {new Position(12,9),new Position(12,10),new Position(12,11),new Position(12,12),
								new Position(11,9),new Position(11,10),new Position(11,11),
								new Position(10,9),new Position(10,10),
								new Position(9,9)};
	private Position[] homeY = {
		new Position(4,0),new Position(4,1),new Position(4,2),new Position(4,3),
		new Position(5,0),new Position(5,1),new Position(5,2),
		new Position(6,0),new Position(6,1),
		new Position(7,0)
	};
	private Position[] homeW = {
		new Position(12,0),new Position(12,1),new Position(12,2),new Position(12,3),
		new Position(11,0),new Position(11,1),new Position(11,2),
		new Position(10,0),new Position(10,1),
		new Position(9,0)
	};
	private Position[] homeBu = {
		new Position(16, 0), 
		new Position(15, 0), new Position(15, 1), 
		new Position(14, 0), new Position(14, 1), new Position(14, 2),
		new Position(13, 0), new Position(13, 1), new Position(13, 2), new Position(13, 3)
	};
	private Position[][] homeAll = {homeR, homeBu, homeBk, homeW, homeG, homeY};
	
	public Board(Player[] players) {
		for (int i=0; i<players.length; i++) {
			populateReg(homeAll[i], players[i]);
		}
	};
	
	public boolean canMove(Position startPos, Position targetPos, boolean ongoingTurn) { 
		if (possibleMoves(startPos, ongoingTurn).contains(targetPos)) {
			return true;
		}
		return false; 
	}
	
	public boolean playerPeg (Player player, Position pos) {
		boolean ret;
		
		//checks if a peg at a position is owned by a specified player
		
		Peg checkedPeg = boardPos[pos.getRow()][pos.getColumn()];
		return checkedPeg!=null && checkedPeg.getOwner()==player;
	}
	
	private void updatePeg (Position startPos, Position targetPos) {
		
		//pointer to the peg being moved
		Peg movingPeg = boardPos[startPos.getRow()][startPos.getColumn()];
		
		//stores moving peg in target position
		boardPos[targetPos.getRow()][targetPos.getColumn()] = movingPeg;
		
		//removes moving peg from starting position
		boardPos[startPos.getRow()][startPos.getColumn()] = null;
		
	};
	
	public Position[] getHomeRegion(Color c) {
		
		//waiting for color vars in GUI
		return null;
	}
	
	public ArrayList<Position> possibleMoves(Position startPos, boolean constraint) {  
		//constraint true if ongoing turn, can only jump
		ArrayList<Position> PM = new ArrayList<Position>();
		Position p = getTL(startPos);
		if (boardPos[p.getRow()][p.getColumn()]==null) {
			if (!constraint) {PM.add(p);}
		} else {
			p = getTL(p);
			if (boardPos[p.getRow()][p.getColumn()]==null) {PM.add(p);}
		}
		p = getTR(startPos);
		if (boardPos[p.getRow()][p.getColumn()]==null) {
			if (!constraint) {PM.add(p);}
		} else {
			p = getTR(p);
			if (boardPos[p.getRow()][p.getColumn()]==null) {PM.add(p);}
		}
		p = getR(startPos);
		if (boardPos[p.getRow()][p.getColumn()]==null) {
			if (!constraint) {PM.add(p);}
		} else {
			p = getR(p);
			if (boardPos[p.getRow()][p.getColumn()]==null) {PM.add(p);}
		}
		p = getBR(startPos);
		if (boardPos[p.getRow()][p.getColumn()]==null) {
			if (!constraint) {PM.add(p);}
		} else {
			p = getBR(p);
			if (boardPos[p.getRow()][p.getColumn()]==null) {PM.add(p);}
		}
		p = getBL(startPos);
		if (boardPos[p.getRow()][p.getColumn()]==null) {
			if (!constraint) {PM.add(p);}
		} else {
			p = getBL(p);
			if (boardPos[p.getRow()][p.getColumn()]==null) {PM.add(p);}
		}
		p = getL(startPos);
		if (boardPos[p.getRow()][p.getColumn()]==null) {
			if (!constraint) {PM.add(p);}
		} else {
			p = getL(p);
			if (boardPos[p.getRow()][p.getColumn()]==null) {PM.add(p);}
		}
		return PM; }
	
	public void move(Move move) {
		Position startPos = move.getStartPosition();
		Position endPos = move.getEndPosition();
		boardPos[endPos.getRow()][endPos.getColumn()] = boardPos[startPos.getRow()][startPos.getColumn()]; //check copy vs ref
		boardPos[startPos.getRow()][startPos.getColumn()] = null;
	}
	public Peg[][] getPeg() { //possibly unnecessary
		return null; 
	}
	
	private void populateReg(Position[] region, Player p) {
		for (int i=0; i<region.length; i++) {
			boardPos[region[i].getRow()][region[i].getColumn()] = new Peg(p);
		}
	}
	
	//ret means "the Position to be RETurned" in all subsequent methods
	
	public static Position getTL(Position p) {
		Position ret = null;
		
		int rootRowSize = rowWidths[p.getRow()];
		int rootIndex = p.getColumn();
		
		int aboveRowSize=-1;
		try {aboveRowSize=rowWidths[p.getRow()-1];} finally {}
		
		try {
			ret = new Position(p.getRow()-1,diagNodeIndex(rootRowSize,rootIndex,aboveRowSize,left));
		} finally {return ret;}
	}
	
	public static Position getTR(Position p) {
		Position ret = null;
		
		int rootRowSize = rowWidths[p.getRow()];
		int rootIndex = p.getColumn();
		
		int aboveRowSize=-1;
		try {aboveRowSize=rowWidths[p.getRow()-1];} finally {}
		
		try {
			ret = new Position(p.getRow()-1,diagNodeIndex(rootRowSize,rootIndex,aboveRowSize,right));
		} finally {return ret;}
	}
	
	public static Position getR(Position p) {
		Position ret=null;
		
		int rootRowSize = rowWidths[p.getRow()];
		
		//generates new position to the right
		int index = p.getColumn()+1;
		
		//checks that it is within the row
		if(!(index<0 || index>=rootRowSize)) {
			ret = new Position(p.getRow(),index);
		}
		
		return ret;
	}
	
	public static Position getBR(Position p) {
		Position ret = null;
		
		int rootRowSize = rowWidths[p.getRow()];
		int rootIndex = p.getColumn();
		
		int aboveRowSize=-1;
		try {aboveRowSize=rowWidths[p.getRow()+1];} catch(ArrayIndexOutOfBoundsException e) {}
		
		try {
			ret = new Position(p.getRow()+1,diagNodeIndex(rootRowSize,rootIndex,aboveRowSize,right));
		} finally{return ret;}
	}
	
	public static Position getBL(Position p) {
		Position ret = null;
		
		int rootRowSize = rowWidths[p.getRow()];
		int rootIndex = p.getColumn();
		
		int aboveRowSize=-1;
		try {aboveRowSize=rowWidths[p.getRow()+1];} catch(ArrayIndexOutOfBoundsException e) {}
		
		try {
			ret = new Position(p.getRow()+1,diagNodeIndex(rootRowSize,rootIndex,aboveRowSize,left));
		} finally {return ret;}
	}
	
	public static Position getL(Position p) {
		Position ret = null;
		
		int rootRowSize = rowWidths[p.getRow()];
		
		//generates new position to the left
		int index = p.getColumn()-1;
		
		//checks that it is within the row
		if(!(index<0 || index>=rootRowSize)) {
			ret = new Position(p.getRow(),index);
		}
		
		return ret;
	}

	private static final boolean left = false, right = true;
	private static int diagNodeIndex(int rootRowSize, int rootIndex, int branchRowSize, boolean direction) {
		/*
		 * IMPORTANT METHOD VERY IMPORTANT DO NOT MESS WITH THIS METHOD
		 * 
		 * Returns: the index (position in row, starting at 0)
		 * of a node that is to the top-left or bottom-left of the root.
		 * 
		 * Parameter: rootRowSize is the size of the row containing the root node
		 * Parameter: rootIndex is the index of the root node within its row
		 * Parameter: branchRowSize is the size of the row containing the branch node
		 * Parameter: direction is whether to go left or right. false->left , true->right
		 * 
		 * Precondition: the two rows have horizontal symmetry
		 * 
		 * Precondition: one row has an odd number of indices,
		 * while the other has an even number of indices.
		 */
		
		if(rootIndex<0 || rootRowSize<1 || rootIndex>=rootRowSize)
		{
			throw new RuntimeException("NONEXISTANT ROOT NODE");
		}
		//makes sure root index can exist for specified root row size
		
		double rootRowDepth = (double)rootRowSize / 2.0;
		//the distance from the center of the row to the outside of the row
		
		double rootIndexOffset = rootRowDepth - rootIndex;
		//The distance from the center of the root row to the root node
		
		double branchRowDepth = (double)branchRowSize / 2.0;
		//the distance from the center of the row to the outside of the row
		
		int branchIndex = (int)Math.floor(branchRowDepth-rootIndexOffset);
		//the depth of the branch row minus the distance to the branch node
		
		branchIndex += (direction==left)?0:1;
		//adds 1 if it's a right diagonal
		
		if(branchIndex<0 || (branchIndex+1)>branchRowSize)
		{
			throw new RuntimeException("NONEXISTANT BRANCH NODE");
		}
		//throws exception if branch node cannot exist
		
		return branchIndex;
	}
}
