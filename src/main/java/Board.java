import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

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
	public static final ArrayList<Position[]> homeAll = new ArrayList<Position[]>();
	
	public Board(Player[] players) {
		Collections.addAll(homeAll, homeR, homeBu, homeBk, homeW, homeG, homeY);
		for (int i=0; i<players.length; i++) {
			populateReg(getHomeRegion(players[i].getColor()), players[i]);
			int WR = (homeAll.indexOf(getHomeRegion(players[i].getColor()))%2==0) ? homeAll.indexOf(getHomeRegion(players[i].getColor()))+1 : homeAll.indexOf(getHomeRegion(players[i].getColor()))-1;
			players[i].assignWinReg(WR);
		}
	}
	
	//for testing only
	public Board() {};
	
	public boolean canMove(Position startPos, Position targetPos, boolean ongoingTurn) { 	
		for(Position p : possibleMoves(startPos, ongoingTurn)) {
			if (p.equals(targetPos)) {
				return true;
			}
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

	
	public void fillPos(Position[] arr) {
		for (Position p : arr) {
			this.fillPos(p);
		}
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
	public static Position[] getWinRegion(int i) {
		return homeAll.get(i);
	}
	public ArrayList<Position> possibleMoves(Position p, boolean jumpOnly) {  
		//constraint true if ongoing turn, can only jump
		
		//to be returned
		ArrayList<Position> ret = new ArrayList<Position>();
		
		//adds jump moves (will always be available)
		ret.addAll(possibleJumpMoves(p));
		
		//if adjacent positions are available, they are added
		if(!jumpOnly) {
			ret.addAll(possibleAdjacentMoves(p));
		}
		
		return ret;
	}
	
	public ArrayList<Position> possibleAdjacentMoves(Position p) {
		ArrayList<Position> ret = new ArrayList<Position>();
		
		//adds adjacent position in each direction
		for(int direction : Position.directions) {
			Position check = p.adj(direction);
			
			//makes sure position exists & is open
			if(check!=null && !isOccupied(check)) {
				ret.add(check);
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
				if(check!=null && !isOccupied(check)) {
					ret.add(check);
				}
			}
		}
		
		return ret;
	}
	public static int indexOf(ArrayList<Position> PP, Position check) {
		for (int i=0; i<PP.size(); i++) {
			if (PP.get(i).equals(check)) {
				return i;
			}
		}
		return -1;
	}
	public void move(Move move) {
		Position startPos = move.getStartPosition();
		Position endPos = move.getEndPosition();

		//checks that the peg exists and can move to the specified location
		if(boardPos[startPos.getRow()][startPos.getColumn()]==null || !canMove(startPos,endPos,false)) {
			throw new RuntimeException("Invalid move. startPos is null or cannot move there.");
		}
		
		boardPos[endPos.getRow()][endPos.getColumn()] = boardPos[startPos.getRow()][startPos.getColumn()]; //check copy vs ref
		boardPos[startPos.getRow()][startPos.getColumn()] = null;
		
		//updates the move-maker's array of positions
		move.getOwner().posArr.set(indexOf(move.getOwner().posArr, startPos), endPos);
	}
	
	private void populateReg(Position[] region, Player p) {
		for (int i=0; i<region.length; i++) {
			boardPos[region[i].getRow()][region[i].getColumn()] = new Peg(p);
			p.addInitalPos(new Position(region[i].getRow(), region[i].getColumn()));
		}
	}
	
	//a template for printing out the board, dollar signs are
	//regex placeholders
	private static final String[] printerTemplate = {
		"             $",
		"            $ $",
		"           $ $ $",
		"          $ $ $ $",
		"$ $ $ $ $ $ $ $ $ $ $ $ $",
		" $ $ $ $ $ $ $ $ $ $ $ $",
		"  $ $ $ $ $ $ $ $ $ $ $",
		"   $ $ $ $ $ $ $ $ $ $",
		"    $ $ $ $ $ $ $ $ $",
		"   $ $ $ $ $ $ $ $ $ $",
		"  $ $ $ $ $ $ $ $ $ $ $",
		" $ $ $ $ $ $ $ $ $ $ $ $",
		"$ $ $ $ $ $ $ $ $ $ $ $ $",
		"          $ $ $ $",
		"           $ $ $",
		"            $ $",
		"             $",
	};
	
	//replaces a line from the template with the info from 
	//the board
	private String replaceLine(int row) {
		String ret = printerTemplate[row];
		for(int i = 0; i<rowWidths[row]; i++) {
			ret = ret.replaceFirst("\\$", isOccupied(new Position(row,i))?"🅑":"ⵔ");
		}
		return ret;
	}
	
	public void printBoard() {
		for(int i = 0; i<boardPos.length; i++) {
			System.out.println(replaceLine(i));
		}
		System.out.println();
	}

}
