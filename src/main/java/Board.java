import java.awt.Color;
import java.util.ArrayList;

public class Board implements Cloneable {
	
	public Board clone() {
		//EXTREMELY NECESSARY FOR ENCAPSULATION
		
		Board b = new Board();
		
		Peg[][] newState = boardPos.clone();
		for(int r = 0; r<newState.length; r++) {
			for(int c = 0; c<newState[r].length; r++) {
				newState[r][c] = newState[r][c].clone();
			}
		}
		
		b.boardPos = newState;
		
		return b;
	}
	
	private Peg[][] boardPos= {{null}, {null, null},{null, null, null},{null, null, null, null},{null, null, null, null, null, null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null, null, null, null, null},{null, null, null, null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null, null, null},{null, null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null, null, null},{null, null, null, null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null, null, null, null, null},{null, null, null, null, null, null, null, null, null, null, null, null, null},
			{null, null, null, null},{null, null, null},{null, null},{null}};

	public Peg[][] getPegs() {
		return boardPos;
	}
	
	//only so that the various directional position methods can be static
	public static final int[] rowWidths = {1,2,3,4,13,12,11,10,9,10,11,12,13,4,3,2,1};
	
	public static final Position[] homeR = {new Position(0, 0), 
								new Position(1, 0), new Position(1, 1), 
								new Position(2, 0), new Position(2, 1), new Position(2, 2),
								new Position(3, 0), new Position(3, 1), new Position(3, 2), new Position(3, 3)};
	public static final Position[] homeBk = {new Position(4, 9), new Position(4, 10), new Position(4, 11), new Position(4, 12), 
								new Position(5, 9), new Position(5, 10), new Position(5, 11), 
								new Position(6, 9), new Position(6, 10), 
								new Position(7, 9)};
	public static final Position[] homeG = {new Position(12,9),new Position(12,10),new Position(12,11),new Position(12,12),
								new Position(11,9),new Position(11,10),new Position(11,11),
								new Position(10,9),new Position(10,10),
								new Position(9,9)};
	public static final Position[] homeY = {
		new Position(4,0),new Position(4,1),new Position(4,2),new Position(4,3),
		new Position(5,0),new Position(5,1),new Position(5,2),
		new Position(6,0),new Position(6,1),
		new Position(7,0)
	};
	public static final Position[] homeW = {
		new Position(12,0),new Position(12,1),new Position(12,2),new Position(12,3),
		new Position(11,0),new Position(11,1),new Position(11,2),
		new Position(10,0),new Position(10,1),
		new Position(9,0)
	};
	public static final Position[] homeBu = {
		new Position(16, 0), 
		new Position(15, 0), new Position(15, 1), 
		new Position(14, 0), new Position(14, 1), new Position(14, 2),
		new Position(13, 0), new Position(13, 1), new Position(13, 2), new Position(13, 3)
	};
	public static final Position[][] homeAll = {homeR, homeBu, homeBk, homeW, homeG, homeY};
	
	public Board(Player[] players) {
		for (int i=0; i<players.length; i++) {
			if (players[i] != null) {
				populateReg(homeAll[i], players[i]);
				int WR = i>=3 ? i-3 : 1+3;
				players[i].assignWinReg(WR);
			}
		}
	}
	
	public Board() {};
	
	public boolean canMove(Position startPos, Position targetPos, boolean ongoingTurn) { 
		if (possibleMoves(startPos, ongoingTurn).contains(targetPos)) {
			return true;
		}
		return false; 
	}
	
	public boolean playerPeg (Player player, Position pos) {
		
		//checks if a peg at a position is owned by a specified player
		
		Peg checkedPeg = boardPos[pos.getRow()][pos.getColumn()];
		return checkedPeg!=null && checkedPeg.getOwner()==player;
	}
	
	public boolean isOccupied(Position p) {
		return boardPos[p.getRow()][p.getColumn()]!=null;
	}
	
	public void fillPos(Position p) {
		//only for testing, please don't implement
		
		boardPos[p.getRow()][p.getColumn()] = new Peg();
	}
	
	public Position[] getHomeRegion(Color c) {
		
		Position[] ret = null;
		
		if (c==Color.RED) {
			ret = homeR;
		} else if (c==Color.BLACK) {
			ret = homeBk;
		} else if (c==Color.GREEN) {
			ret = homeG;
		} else if (c==Color.BLUE) {
			ret = homeBu;
		} else if (c==Color.WHITE) {
			ret = homeW;
		} else if (c==Color.YELLOW) {
			ret = homeY;
		}
		
		return ret;
		
	}

	public ArrayList<Position> possibleMoves(Position p, boolean jumpOnly) {  
		//constraint true if ongoing turn, can only jump
		
		//to be returned
		ArrayList<Position> ret = new ArrayList<Position>();
		
		//adds jump moves (will always be available)
		ret.addAll(possibleJumpMoves(p));
		
		//if adjacent positions are available, they are added
		if(!jumpOnly) {
			
			//adds adjacent position in each direction
			for(int direction : Position.directions) {
				Position check = p.adj(direction);
				
				//makes sure position exists & is open
				if(check!=null && !isOccupied(check)) {
					ret.add(check);
				}
			}
		}
		
		return ret;
	}
	
	public ArrayList<Position> possibleJumpMoves(Position p) {
		//jump moves will always be available
		
		//to be returned
		ArrayList<Position> ret = new ArrayList<Position>();
		
		//checks in each direction
		for(int direction : Position.directions) {
			Position check = p.adj(direction);
			
			//ensures that adjacent space is filled
			if(check!=null && isOccupied(check)) {
				
				//adds outer space if it's open
				check = check.adj(direction);
				if(!isOccupied(check)) {
					ret.add(check);
				}
			}
		}
		
		return ret;
	}
	
	public void move(Move move) {
		Position startPos = move.getStartPosition();
		Position endPos = move.getEndPosition();
		boardPos[endPos.getRow()][endPos.getColumn()] = boardPos[startPos.getRow()][startPos.getColumn()]; //check copy vs ref
		boardPos[startPos.getRow()][startPos.getColumn()] = null;
	}
	
	private void populateReg(Position[] region, Player p) {
		for (int i=0; i<region.length; i++) {
			boardPos[region[i].getRow()][region[i].getColumn()] = new Peg(p);
//			p.addInitalPos(new Position(region[i].getRow(), region[i].getColumn()));
		}
	}
}
