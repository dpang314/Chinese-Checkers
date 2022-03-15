import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
public class ComputerStratBasic2 extends ComputerStrategy{
	private char dir;
	private int[] WRP; //{row, col}
	//private ArrayList<Position> prevPos;
	private boolean newTurn, endTurn, isAdjPos;
	private ArrayList <Position> moveQue;
	private Position[] winReg;
	private Move prevMove;
	public ComputerStratBasic2(Color color, String playerName) {
		super(color, playerName);
		//System.out.println(color);
		WRP = new int[2];
		//prevPos = new ArrayList<Position>();
		moveQue = new ArrayList<Position>();
		//newBM = false;
		newTurn = true; endTurn=false; isAdjPos=false;
		//numWiTurn=1;
		prevMove=null;
		winReg = getWR();
		if (color.equals(Color.green)) {dir='l'; WRP[0] = 4; WRP[1] = 0;}
		else if (color.equals(Color.black)) {dir='l'; WRP[0] = 12; WRP[1] = 0;}
		else if (color.equals(Color.white)) {dir='r'; WRP[0] = 4; WRP[1] = 12;}
		else if (color.equals(Color.yellow)) {dir='r'; WRP[0] = 12; WRP[1] = 12;}
		else if (color.equals(Color.blue)) {dir='u'; WRP[0] = 0; WRP[1] = 0;}
		else {dir='d'; WRP[0] = 16; WRP[1] = 0;}
		System.out.println(new Position(10, 0).adj(5));
	}
	public Move getMove(Board board) {
		//System.out.println("getting move");
		Random rand = new Random();
		winReg = getWR();
//		if (endTurn) {
//			endTurn=false;
//			newTurn=true;
//			moveQue.clear();
//			return null;
//		}
		if (newTurn) {
			isAdjPos=false;
			//System.out.println("New Turn");
			//Random rand = new Random();
			ArrayList<Position> tpf = new ArrayList<Position>();
			for (Position pos: posArr) {
				if (board.possibleMoves(pos, false).size()>0) {
					tpf.add(pos);
				}
			}
			//System.out.println("Pegs that can move: "+tpf);
			Position p = tpf.get(rand.nextInt(tpf.size()));
			//System.out.println("picked pos: "+p);
			ArrayList<Position> toPickFrom = getMoveablePos(p, board, false);
			//System.out.println("moveablePos: "+toPickFrom);
			while (toPickFrom.size()==0) {
				//System.out.println("In Loop, picked pos: "+p);
				p = tpf.get(rand.nextInt(8));
				toPickFrom = getMoveablePos(p, board, false);
			}
			Position p2 = getBestPos(toPickFrom, board);//toPickFrom.get(rand.nextInt(toPickFrom.size()));
			if (indexOf(board.possibleAdjacentMoves(p), p2)!=-1) {
				isAdjPos=true;
			}
			prevMove = new Move(p, p2, this);
			newTurn = false;
			//System.out.println("New Turn Move: "+prevMove);
			return prevMove;
//			moveQue.add(p);
//			moveQue.add(p2);
//			while (board.possibleMoves(p2, true).size()>0) {
//				ArrayList<Position> pm2 = getMoveablePos(p2, board);
//				if (pm2.size()>0) {
//					p2 = toPickFrom.get(rand.nextInt(toPickFrom.size()));
//					moveQue.add(p2);
//				}
//			}
//			System.out.println("MQ: "+moveQue);
		}
		if (isAdjPos) {
			newTurn=true;
			return null;
		}
		if (getMoveablePos(prevMove.getEndPosition(), board, true).size()>0) {
			ArrayList<Position> toPickFrom = getMoveablePos(prevMove.getEndPosition(), board, true);
			Position p2 = getBestPos(toPickFrom, board);//toPickFrom.get(rand.nextInt(toPickFrom.size()));
			if (indexOf(board.possibleAdjacentMoves(prevMove.getEndPosition()), p2)!=-1) {
				isAdjPos=true;
			}
			prevMove = new Move(prevMove.getEndPosition(), p2, this);
			//System.out.println("Move: "+prevMove);
			return prevMove;
		} else {
			newTurn=true;
			//moveQue.clear();
			return null;
		}
//		if (moveQue.size()==2) {
//			endTurn=true;
//			prevMove = new Move(moveQue.get(0), moveQue.get(1), this);
//			return prevMove;
//		}
//		prevMove = new Move(moveQue.get(0), moveQue.get(1), this);
//		moveQue.remove(0);
//		return prevMove;
	}
	private ArrayList<Position> getMoveablePos(Position p, Board board, boolean jumpOnly){
		ArrayList<Position> toPickFrom = new ArrayList<Position>();
		ArrayList<Position> pm = board.possibleMoves(p, jumpOnly);
		//System.out.println("pos moves: "+pm);
		for (Position pos:pm) {
			//System.out.println("og pos: "+ p+", dwrp: "+distanceToWRP(p, board));
			//System.out.println("new pos: "+ pos+", dwrp: "+distanceToWRP(pos, board));
			if (distanceToWRP(pos, board)<=distanceToWRP(p, board)) {
				if (prevMove==null || !pos.equals(prevMove.getStartPosition())) {
					toPickFrom.add(pos);
				}
			}
		}
		return toPickFrom;
	}
	private Position getBestPos(ArrayList<Position> pm, Board board) {
		int dist  = 1000;
		Position bestPos = null;
		for (Position p: pm) {
			int d2 = distanceToWRP(p, board);
			if (d2<dist) {
				dist=d2;
				bestPos = p;
			}
		}
		return bestPos;
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
				if (newP.adj(5)!=null) {newP = newP.adj(5);}
				else {newP = newP.adj(4);}
				dist+=1;
			}
			while (WRP[0]<newP.getRow()) {
				if (newP.adj(1)!=null) {newP = newP.adj(1);}
				else {newP = newP.adj(2);}
				dist+=1;
			}
//			if (newP.getRow()!=WRP[0]) {
//				System.out.println("Problem pos: "+newP);
//				System.out.println("get TL: "+newP.adj(5));
//				System.out.println("get BL: "+newP.adj(1));
//				newP=null;
//				newP.getRow();
//			}
			dist+=newP.getColumn();
		}
		if (dir=='r') {
			while (WRP[0]>newP.getRow()) {
				if (newP.adj(4)!=null) {newP = newP.adj(4);}
				else {newP = newP.adj(5);}
				dist+=1;
			}
			while ( WRP[0]<newP.getRow()) {
				if (newP.adj(2)!=null) {newP = newP.adj(2);}
				else {newP = newP.adj(1);}
				dist+=1;
			}
//			if (newP.getRow()!=WRP[0]) {
//				System.out.println("Problem pos: "+newP);
//				System.out.println("get TR: "+newP.adj(4));
//				System.out.println("get BR: "+newP.adj(2));
//				newP=null;
//				newP.getRow();
//			}
			dist+=WRP[1]-newP.getColumn();
		}
		if (dir=='u') {
			while (!(WRP[0]==newP.getRow()&&WRP[1]==newP.getColumn())) {
				while (!(newP.getRow()==1 && newP.getColumn()==1) && (Board.rowWidths[newP.getRow()])/2>=newP.getColumn() && !(WRP[0]==newP.getRow()&&WRP[1]==newP.getColumn())) {
					if (newP.adj(2)!=null) {
						newP = newP.adj(2);
					} else if (newP.adj(3)!=null){
						newP = newP.adj(3);
					} else {
						System.out.println("bad case: "+newP);
					}
					dist+=1;
				}
				while ((newP.getRow()==1 && newP.getColumn()==1) || (Board.rowWidths[newP.getRow()])/2<newP.getColumn() && !(WRP[0]==newP.getRow()&&WRP[1]==newP.getColumn())) {
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
