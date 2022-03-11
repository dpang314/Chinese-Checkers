import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
public class ComputerStratBasic2 extends Player{
	private char dir;
	private int[] WRP; //{row, col}
	private ArrayList<Position> prevPos;
	private boolean newBM, newTurn, endTurn;
	private int numWiTurn;
	private ArrayList <Position> moveQue;
	private Position[] winReg;
	private Move prevMove;
	public ComputerStratBasic2(Color color, String playerName) {
		super(color, playerName);
		System.out.println(color);
		WRP = new int[2];
		prevPos = new ArrayList<Position>(); moveQue = new ArrayList<Position>();
		newBM = false; newTurn = true; endTurn=false;
		numWiTurn=1;
		winReg = getWR();
		if (color.equals(Color.green)) {dir='l'; WRP[0] = 4; WRP[1] = 0;}
		else if (color.equals(Color.black)) {dir='l'; WRP[0] = 12; WRP[1] = 0;}
		else if (color.equals(Color.white)) {dir='r'; WRP[0] = 4; WRP[1] = 12;}
		else if (color.equals(Color.yellow)) {dir='r'; WRP[0] = 12; WRP[1] = 12;}
		else if (color.equals(Color.blue)) {dir='u'; WRP[0] = 0; WRP[1] = 0;}
		else {dir='d'; WRP[0] = 16; WRP[1] = 0;}
	}
	public Move getMove(Board board) {
		System.out.println("getting move");
		winReg = getWR();
		if (endTurn) {
			endTurn=false;
			newTurn=true;
			return null;
		}
		if (newTurn) {
			Random rand = new Random();
			ArrayList<Position> tpf = new ArrayList<Position>();
			for (Position pos: posArr) {
				if (board.possibleMoves(pos, false).size()>0) {
					tpf.add(pos);
				}
			}
			Position p = tpf.get(rand.nextInt(tpf.size()));
			System.out.println("picked pos: "+p);
			//ArrayList<Position> pm = board.possibleMoves(p, false);
			ArrayList<Position> toPickFrom = getMoveablePos(p, board);
			while (toPickFrom.size()==0) {
				p = posArr.get(rand.nextInt(8));
				toPickFrom = getMoveablePos(p, board);
			}
			Position p2 = toPickFrom.get(rand.nextInt(toPickFrom.size()));
			moveQue.add(p);
			moveQue.add(p2);
			while (board.possibleMoves(p2, true).size()>0) {
				ArrayList<Position> pm2 = getMoveablePos(p2, board);
				if (pm2.size()>0) {
					p2 = toPickFrom.get(rand.nextInt(toPickFrom.size()));
					moveQue.add(p2);
				}
			}
		}
		newTurn = false;
		if (moveQue.size()==2) {
			endTurn=true;
			prevMove = new Move(moveQue.get(0), moveQue.get(1), this);
			return prevMove;
		}
		prevMove = new Move(moveQue.get(0), moveQue.get(1), this);
		moveQue.remove(0);
		return prevMove;
	}
	private ArrayList<Position> getMoveablePos(Position p, Board board){
		ArrayList<Position> toPickFrom = new ArrayList<Position>();
		ArrayList<Position> pm = board.possibleMoves(p, false);
		for (Position pos:pm) {
			if (distanceToWRP(pos, board)>=distanceToWRP(p, board)) {
				if (!pos.equals(prevMove.getStartPosition())) {
					toPickFrom.add(pos);
				}
			}
		}
		return toPickFrom;
	}
	private int indexOf(ArrayList<Position> PP, Position check) {
		for (int i=0; i<PP.size(); i++) {
			if (PP.get(i).equals(check)) {
				return i;
			}
		}
		return -1;
	}
	private int distanceToWRP(Position p, Board board) {
		int dist=0;
		Position newP = new Position(p.getRow(), p.getColumn());
		//System.out.println("position: "+newP);
		if (dir=='l') {
			while (WRP[0]>newP.getRow()) {
				newP = newP.adj(5);
				dist+=1;
			}
			while (WRP[0]<newP.getRow()) {
				newP = newP.adj(1);
				dist+=1;
			}
			dist+=newP.getColumn();
		}
		if (dir=='r') {
			while (WRP[0]>newP.getRow()) {
				newP = newP.adj(4);
				dist+=1;
			}
			while (WRP[0]<newP.getRow()) {
				newP = newP.adj(2);
				dist+=1;
			}
			dist+=WRP[1]-newP.getColumn();
		}
		if (dir=='u') {
			while (!(WRP[0]==newP.getRow()&&WRP[1]==newP.getColumn())) {
				while ((Board.rowWidths[newP.getRow()])/2>=newP.getColumn()) {
					if (newP.adj(2)!=null) {
						newP = newP.adj(2);
					} else {
						newP = newP.adj(3); 
					}
					dist+=1;
				}
				while ((Board.rowWidths[newP.getRow()])/2<newP.getColumn()) {
					if (newP.adj(1)!=null) {
						newP = newP.adj(1);
					} else {
						newP = newP.adj(0); 
					}
					dist+=1;
				}
			}
			//dist+=newP.getRow();
		}
		if (dir=='d') {
			//System.out.println(Board.rowWidths[newP.getRow()]/2+", "+newP.getColumn());
			while (!(WRP[0]==newP.getRow()&&WRP[1]==newP.getColumn())) {
				while ((Board.rowWidths[newP.getRow()])/2>=newP.getColumn()) {
					if (newP.adj(4)!=null) {
						newP = newP.adj(4);
					} else {
						newP = newP.adj(3); 
					}
					if (newP==null) {
						return dist;
					}
					dist+=1;
				}
				while ((Board.rowWidths[newP.getRow()])/2<newP.getColumn()) {
					if (newP.adj(5)!=null) {
						newP = newP.adj(5);
					} else {
						newP = newP.adj(0); 
					}
					if (newP==null) {
						return dist;
					}
					dist+=1;
				}
			}
		}
		return dist;
	}

}
