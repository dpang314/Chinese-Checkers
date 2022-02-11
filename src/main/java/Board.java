import java.awt.Color;

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
	
	public Board(Player[] players) {
		if (players.length>0) {
			populateReg(homeR, players[0]);
		}
		
		//boardPos
	};
	public boolean canMove(Position startPos, Position targetPos) { return false; };
	
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
		
	}
	
	public Position[] possibleMoves (Position startPos, boolean constraint) { return null; };
	public void move(Position startPos, Position endPos) {};
	public Peg[][] getPeg() { return null; };
	private void populateReg(Position[] region, Player p) {
		for (int i=0; i<region.length; i++) {
			boardPos[region[i].getRow()][region[i].getColumn()] = new Peg(p);
		}
	}
}

