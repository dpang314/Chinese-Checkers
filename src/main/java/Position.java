
public class Position {
	private int row, column;
	public static final int LEFT=0, TOP_LEFT=1, TOP_RIGHT=2, RIGHT=3, BOTTOM_RIGHT=4, BOTTOM_LEFT=5;
	public static final int[] directions = {LEFT,TOP_LEFT,TOP_RIGHT,RIGHT,BOTTOM_RIGHT,BOTTOM_LEFT};
	
	public Position adj(int direction) {
		
		switch(direction) {
		case (LEFT) : {
			return this.getL();
		}
		case (TOP_LEFT) : {
			return this.getTL();
		}
		case (TOP_RIGHT) : {
			return this.getTR();
		}
		case (RIGHT) : {
			return this.getR();
		}
		case (BOTTOM_RIGHT) : {
			return this.getBR();
		}
		case (BOTTOM_LEFT) : {
			return this.getBL();
		}
		default : {
			return null;	
		}
		}
	}
	public boolean isAdjacentPos(Position p2) {
		for(int direction : Position.directions) {
			if (this.adj(direction)!=null && this.adj(direction).equals(p2)) {
				return true;
			}
		}
		return false;
	}
	public Position(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public String toString() {
		return "ROW: " + row + ", COL: " +column; 
	}
	public boolean equals(Position p) {
		return this.getRow()==p.getRow() && this.getColumn()==p.getColumn();
	}
	
	
	private Position getTL() {
		Position ret = null;
		
		int rootRowSize = Board.rowWidths[this.getRow()];
		int rootIndex = this.getColumn();
		
		int aboveRowSize=-1;
		try {aboveRowSize=Board.rowWidths[this.getRow()-1];} finally {}
		
		try {
			ret = new Position(this.getRow()-1,diagNodeIndex(rootRowSize,rootIndex,aboveRowSize,left));
		} finally {return ret;}
	}
	
	private Position getTR() {
		Position ret = null;
		
		int rootRowSize = Board.rowWidths[this.getRow()];
		int rootIndex = this.getColumn();
		
		int aboveRowSize=-1;
		try {aboveRowSize=Board.rowWidths[this.getRow()-1];} finally {}
		
		try {
			ret = new Position(this.getRow()-1,diagNodeIndex(rootRowSize,rootIndex,aboveRowSize,right));
		} finally {return ret;}
	}
	
	private Position getR() {
		Position ret=null;
		
		int rootRowSize = Board.rowWidths[this.getRow()];
		
		//generates new position to the right
		int index = this.getColumn()+1;
		
		//checks that it is within the row
		if(!(index<0 || index>=rootRowSize)) {
			ret = new Position(this.getRow(),index);
		}
		
		return ret;
	}
	
	private Position getBR() {
		Position ret = null;
		
		int rootRowSize = Board.rowWidths[this.getRow()];
		int rootIndex = this.getColumn();
		
		int aboveRowSize=-1;
		try {aboveRowSize=Board.rowWidths[this.getRow()+1];} catch(ArrayIndexOutOfBoundsException e) {}
		
		try {
			ret = new Position(this.getRow()+1,diagNodeIndex(rootRowSize,rootIndex,aboveRowSize,right));
		} finally{return ret;}
	}
	
	private Position getBL() {
		Position ret = null;
		
		int rootRowSize = Board.rowWidths[this.getRow()];
		int rootIndex = this.getColumn();
		
		int aboveRowSize=-1;
		try {aboveRowSize=Board.rowWidths[this.getRow()+1];} catch(ArrayIndexOutOfBoundsException e) {}
		
		try {
			ret = new Position(this.getRow()+1,diagNodeIndex(rootRowSize,rootIndex,aboveRowSize,left));
		} finally {return ret;}
	}
	
	private Position getL() {
		Position ret = null;
		
		int rootRowSize = Board.rowWidths[this.getRow()];
		
		//generates new position to the left
		int index = this.getColumn()-1;
		
		//checks that it is within the row
		if(!(index<0 || index>=rootRowSize)) {
			ret = new Position(this.getRow(),index);
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
		
		branchIndex += (direction==left)? 0 : 1;
		//adds 1 if it's a right diagonal
		
		if(branchIndex<0 || (branchIndex+1)>branchRowSize)
		{
			throw new RuntimeException("NONEXISTANT BRANCH NODE");
		}
		//throws exception if branch node cannot exist
		
		return branchIndex;
	}
}
