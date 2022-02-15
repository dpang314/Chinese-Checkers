import java.awt.Color;
import java.util.ArrayList;

public class Board {
	
	private Peg[][] boardPos= {{null}, {null, null},{null, null, null},{null, null, null, null},{null, null, null, null, null, null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null, null, null, null, null},{null, null, null, null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null, null, null},{null, null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null, null, null},{null, null, null, null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null, null, null, null, null},{null, null, null, null, null, null, null, null, null, null, null, null, null},
			{null, null, null, null},{null, null, null},{null, null},{null}};
	
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
	private Position[][] homeAll = {homeR, homeBk, homeG, homeW, homeBu};
	
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
	
	public boolean playerPeg (Player player, Position startPos) {
		
		Peg checkedPeg = boardPos[startPos.getRow()][startPos.getColumn()];
		return checkedPeg.getOwner()==player;
		
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
		int sRow = startPos.getRow(); int sCol = startPos.getColumn();
		//constraint true if ongoing turn, can only jump
		ArrayList<Position> PM = new ArrayList<Position>();
		ArrayList<Position> Around = getSurrPos(startPos);
		if (!constraint) {
			PM = Around;
		}
		for (Position p: Around) { //checking 5 jump
			if (p.getRow()==sRow) {
				if(startPos.getRow()+6<boardPos[sRow].length) {
					//if ()
				}
			}
		}
		return null; }
	
	public void move(Position startPos, Position endPos) {
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
	
	private ArrayList<Position> getSurrPos(Position p) {
		//precondition: the given position exists on a chinese checkers board
		//and would point to a valid position on boardPos
		ArrayList<Position> ret = new ArrayList<Position>();
		
		int aboveRowSize=-1;
		try {aboveRowSize=boardPos[p.getRow()-1].length;} catch(ArrayIndexOutOfBoundsException e) {}
		
		int belowRowSize=-1;
		try {belowRowSize=boardPos[p.getRow()+1].length;} catch(ArrayIndexOutOfBoundsException e) {}
		
		int rootRowSize = boardPos[p.getRow()].length;
		int rootIndex = p.getColumn();
		
		//adds positions to the right + left
		Position temp = new Position(p.getRow(), p.getColumn()+1);
		try {
			Peg asdf = boardPos[temp.getRow()][temp.getColumn()];
			/*
			 * "ASDF" isn't actually important, it's just to
			 * make sure that the location to which ASDF would point
			 * exists without throwing an arrayindexoutofboundsexception.
			 * the shortest way to do this was using an assignment statement
			 */
			ret.add(temp);
		} catch (ArrayIndexOutOfBoundsException e) {}
		
		temp = new Position(p.getRow(), p.getColumn()-1);
		try {
			Peg asdf = boardPos[temp.getRow()][temp.getColumn()];
			ret.add(temp);
		} catch (ArrayIndexOutOfBoundsException e) {}
		
		//adds positions to the top
		try {
			ret.add(new Position(p.getRow()-1,diagNodeIndex(rootRowSize,rootIndex,aboveRowSize,left)));
		} catch (Exception e) {}
		try {
			ret.add(new Position(p.getRow()-1,diagNodeIndex(rootRowSize,rootIndex,aboveRowSize,right)));
		} catch (Exception e) {}
		
		//adds positions to the bottom
		try {
			ret.add(new Position(p.getRow()+1,diagNodeIndex(rootRowSize,rootIndex,belowRowSize,left)));
		} catch (Exception e) {}
		try {
			ret.add(new Position(p.getRow()+1,diagNodeIndex(rootRowSize,rootIndex,belowRowSize,right)));
		} catch (Exception e) {}
		
		return ret;
	}
	
	private static final boolean left = false, right = true;
	private static int diagNodeIndex(int rootRowSize, int rootIndex, int branchRowSize, boolean direction) {
		/*
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
		
		if(rootIndex<0 || rootRowSize<1 || rootIndex>=rootRowSize) {
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
		
		if(branchIndex<0 || (branchIndex+1)>branchRowSize) {throw new RuntimeException("NONEXISTANT BRANCH NODE");}
		//throws exception if branch node cannot exist
		
		return branchIndex;
	}
}
