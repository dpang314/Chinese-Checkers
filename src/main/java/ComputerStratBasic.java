import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class ComputerStratBasic extends Player{
	//private int[][] winLine; //{{x1, x2}, {y1, y2}}
	private char dir;
	private int[] WRP; //{row, col}
	private ArrayList<Position> prevPos;
	private boolean newBM, newTurn, endTurn;
	private int numWiTurn;
	private ArrayList <Position> moveQue;
	private Position[] winReg;
	private Move prevMove;
//	={new Position(16, 0), 
//			new Position(15, 0), new Position(15, 1), 
//			new Position(14, 0), new Position(14, 1), new Position(14, 2),
//			new Position(13, 0), new Position(13, 1), new Position(13, 2), new Position(13, 3)};
	public ComputerStratBasic(Color color, String playerName) {
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
		//System.out.println(new Position(0, 0).equals(new Position(0, 0)));
	}
	
	@Override //pass null when done turn
	public Move getMove(Board board) {
		if (endTurn) {
			endTurn=false;
			prevMove=null;
			return null;
		}
		if (newTurn) {
			prevPos.clear();
			moveQue.clear();
			int[][] bestMove= new int[3][2]; //{{r1, c1}, {r2, c2} {weight, distance travelled}}
			bestMove[2][0]=1000;
			for (int i=0; i<posArr.size(); i++) {
				if (!posArr.get(i).equals(new Position(0, 0))) {
					prevPos.clear();//
					newBM = false;
					bestMove = goodMove2(posArr.get(i), bestMove, board, false);
					if(newBM) {
						bestMove[0][0] = posArr.get(i).getRow();
						bestMove[0][1] = posArr.get(i).getColumn();
						moveQue.add(0, new Position(bestMove[0][0], bestMove[0][1]));
					}
				}
			}
			moveQue = reOrderMQ(moveQue, board);
			System.out.println("MQ: "+moveQue);
			System.out.println("from: "+bestMove[0][0]+","+bestMove[0][1]+" to: "+bestMove[1][0]+","+bestMove[1][1]);
			ArrayList<Position> pm = board.possibleMoves(new Position(bestMove[0][0], bestMove[0][1]), false);
			if (indexOf(pm, new Position(bestMove[1][0], bestMove[1][1]))!=-1) {
				System.out.println("in here");
				numWiTurn=1;
				newTurn = true;
				if (moveQue.size()==1){
					endTurn=true;
					numWiTurn=1;
					newTurn = true;
				}
				prevMove = new Move(new Position(bestMove[0][0], bestMove[0][1]), new Position(bestMove[1][0], bestMove[1][1]), this);
				return prevMove;
			}
		}
		newTurn = false;
		if (numWiTurn==moveQue.size()) {
			prevMove=null;
			return null;
		}
		Position ret = moveQue.get(numWiTurn-1);
		Position ret2 = moveQue.get(numWiTurn);
		if (numWiTurn+1<moveQue.size()) {
			numWiTurn+=1;
		} else if (moveQue.size()==1){
			endTurn=true;
			numWiTurn=1;
			newTurn = true;
		}
		else {
			numWiTurn=1;
			newTurn = true;
		}
		if (indexOf(board.possibleMoves(ret, true), ret2)==-1) {
			numWiTurn=1;
			newTurn = true;
			prevMove=null;
			return null;
		}
		System.out.println("out here: "+indexOf(board.possibleMoves(ret, true), ret2));
		prevMove = new Move(ret, ret2, this);
		return prevMove;
	}
	private int indexOf(ArrayList<Position> PP, Position check) {
		for (int i=0; i<PP.size(); i++) {
			if (PP.get(i).equals(check)) {
				return i;
			}
		}
		return -1;
	}
	private int indexOf(Position[] PP, Position check) {
		if (PP==null) {
			return -1;
		}
		for (int i=0; i<PP.length; i++) {
			if (PP[i].equals(check)) {
				return i;
			}
		}
		return -1;
	}
	private ArrayList<Position> reOrderMQ(ArrayList <Position> moveQue, Board b1) {
		ArrayList <Position> mq = new ArrayList<Position>();
		for (Position p:moveQue) {
			if (indexOf(mq, p)==-1) {
				mq.add(p);
			}
		}
		if (mq.size()>2) {
			for (int i=0; i<mq.size(); i++) {
				ArrayList<Position> pm = b1.possibleMoves(mq.get(i), true);
				for (int j=i+1; j<mq.size(); j++) {
					if (indexOf(pm, mq.get(j))!=-1) {
						Position temp = new Position(mq.get(j).getRow(), mq.get(j).getColumn());
						mq.remove(j);
						mq.add(i+1, temp);
					}
				}
			}
		}
		return mq;
	}
	private int[][] goodMove2(Position Pos, int[][] bestMove, Board board, boolean jumpOnly){
		if (indexOf(prevPos, Pos)==-1) {
			prevPos.add(Pos);
			//System.out.println("added to PP: "+Pos);
			int row = Pos.getRow(), col = Pos.getColumn();
			//System.out.println("start pos: "+Pos);
			ArrayList<Position> posMoves= board.possibleMoves(Pos, jumpOnly);
			//System.out.println("posMoves"+posMoves);
			for (Position p : posMoves) {
				if (indexOf(prevPos, p)==-1 && p!=null) {
					//System.out.println("possible pos: "+p);
					ArrayList<Position> pM2 = new ArrayList<Position>();
					if (!Pos.isAdjacentPos(p) && !(p.getRow()==WRP[0] && p.getColumn()==WRP[1])) {
						pM2 = board.possibleMoves(p, true);
						//System.out.println("possible pos 2: "+pM2);
						//for (Position p2 : pM2) {
						for (int i=0; i<pM2.size(); i++) {
							if (indexOf(prevPos, pM2.get(i))!=-1) {
								pM2.remove(i);
								//System.out.println("removed: "+pM2.remove(i));
								//System.out.println("Pm2 len = "+pM2.size());
							}
							//else{System.out.println("kept as possible move: "+pM2.get(i));}
						}
					}
					if (pM2.size()>0) {
						bestMove=goodMove2(p, bestMove, board, true);
						if (newBM) {
							//System.out.println("adding to MQ: "+p+", "+new Position(bestMove[1][0], bestMove[1][1]));
							moveQue.add(p);
							moveQue.add(new Position(bestMove[1][0], bestMove[1][1]));
						}
					} else {
						//int distTraveled = distanceToWRP(Pos, board);
						int newWeight = distanceToWRP(p, board);
						//System.out.println("distance: "+newWeight);
						//System.out.println("PP(0): "+prevPos.get(0));
						if (indexOf(winReg, prevPos.get(0))!=-1) {newWeight+=3;}
						if (!(indexOf(winReg, Pos)!=-1 && indexOf(winReg, p)==-1)) {
							if (distanceToWRP(Pos, board)>=distanceToWRP(p, board)) {
								if (newWeight<bestMove[2][0] || (newWeight==bestMove[2][0] && distanceToWRP(Pos, board)>bestMove[2][1])) {
								//if (newWeight<bestMove[2][0] || (newWeight==bestMove[2][0] && distanceToWRP(Pos, board)>bestMove[2][1]) || (indexOf(winReg, new Position(bestMove[0][0], bestMove[0][1]))!=-1 && distanceToWRP(Pos, board)>bestMove[2][1])) {
									//System.out.println("new best move: "+Pos+" to "+p+" with w: "+newWeight);
									newBM=true;
									//bestMove[0][0] = row;bestMove[0][1] = col;
									bestMove[1][0] = p.getRow();bestMove[1][1] = p.getColumn();
									bestMove[2][0] = newWeight;
									bestMove[2][1] = distanceToWRP(new Position(row, col), board);
								}
							}
						}
					} 
				}
			}
		}
		return bestMove;
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
	public boolean hasWon(Board board, Color WR) {
		   Position[] HR = board.getHomeRegion(WR);
		    for (Position p :HR) {
		      if (!board.playerPeg(this, p)) {
		    	  return false;
		      }
		    }
		return true;
	}
//	public static void main(String[] args) {
//		ComputerStratBasic c1 = new ComputerStratBasic(Color.red, "Hannah");
//		Player[] players = {c1};
//		Board b1 = new Board(players);//players);
//		//System.out.println(c1.distanceToWRP(new Position(4, 6), b1));
//	}
//		//System.out.println()
////		ComputerStratBasic c1 = new ComputerStratBasic(Color.red, "Hannah");
////		Position[] poses = {new Position(6, 2), new Position(5, 3), new Position(4, 5), new Position(3, 0), new Position(3, 2), new Position(1, 1)};
////		for (Position p:poses) {
////			b1.fillPos(p);
////			c1.posArr.add(p);
////		}
//		while(!c1.hasWon(b1, Color.red)) {
//			b1.printBoard();
//			b1.move(c1.getMove(b1));
//			
//		}
//		//System.out.println("WR: "+c1.getWR());
////		b1.printBoard();
//////		Move m  = c1.getMove(b1);
//////		while (m!=null) {
//////			System.out.println("Move: "+m);
//////			b1.move(m);
//////			b1.printBoard();
//////			m  = c1.getMove(b1);
//////		}
////		for (int i=0; i<9; i++) {
////			Move m  = c1.getMove(b1);
////			if (m!=null) {
////				b1.move(m);
////				b1.printBoard();
////			}
////		}
//		//b1.move(c1.getMove(b1));
//		//System.out.println(c1.getMove(b1));
//		//b1.printBoard();
//	}
}
