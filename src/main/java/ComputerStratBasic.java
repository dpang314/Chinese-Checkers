import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class ComputerStratBasic extends Player{
	private int[][] winLine; //{{x1, x2}, {y1, y2}}
	private char dir;
	private int[] WRP; //{row, col}
	private ArrayList<Position> prevPos;
	private boolean newBM, newTurn;
	private int numWiTurn;
	private ArrayList <Position> moveQue;
	public ComputerStratBasic(Color color, String playerName) {
		super(color, playerName);
		winLine = new int[2][2];
		WRP = new int[2];
		prevPos = new ArrayList<Position>(); moveQue = new ArrayList<Position>();
		newBM = false; newTurn = true;
		numWiTurn=1;
		winLine[0][0] = getWR()==1 || getWR()==2 ? 9 : (getWR()==4 || getWR()==0? 0 :  3);
		winLine[0][1] = getWR()==1 || getWR()==2 ? 9 : (getWR()==4 || getWR()==0? 3 :  0);
		winLine[1][0] = getWR()==1 || getWR()==5 ? 4 : (getWR()==4 || getWR()==2? 9 :  (getWR()==0 ? 0 : 13));
		winLine[1][1] = getWR()==1 || getWR()==5 ? 7 : (getWR()==4 || getWR()==2? 12 :  (getWR()==0 ? 3 : 16));
		if (color.equals(Color.green)) {dir='l'; WRP[0] = 4; WRP[1] = 0;}
		else if (color.equals(Color.black)) {dir='l'; WRP[0] = 12; WRP[1] = 0;}
		else if (color.equals(Color.white)) {dir='r'; WRP[0] = 4; WRP[1] = 12;}
		else if (color.equals(Color.yellow)) {dir='r'; WRP[0] = 12; WRP[1] = 12;}
		else if (color.equals(Color.blue)) {dir='u'; WRP[0] = 0; WRP[1] = 0;}
		else {dir='d'; WRP[0] = 16; WRP[1] = 0;}
	}
	
	@Override //pass null when done turn
	public Move getMove(Board board) {
		if (newTurn) {
			prevPos.clear();
			moveQue.clear();
			int[][] bestMove= new int[3][2]; //{{r1, c1}, {r2, c2} {weight, distance travelled}}
			bestMove[2][0]=1000;
			for (int i=0; i<posArr.size(); i++) {
				newBM = false;
				bestMove = goodMove2(posArr.get(i), bestMove, board, false);
				if(newBM) {
					bestMove[0][0] = posArr.get(i).getRow();
					bestMove[0][1] = posArr.get(i).getColumn();
					moveQue.add(0, new Position(bestMove[0][0], bestMove[0][1]));
				}
			}
			moveQue = reOrderMQ(moveQue, board);
			System.out.println("MQ: "+moveQue);
			System.out.println("from: "+bestMove[0][0]+","+bestMove[0][1]+" to: "+bestMove[1][0]+","+bestMove[1][1]);
			ArrayList<Position> pm = board.possibleMoves(new Position(bestMove[0][0], bestMove[0][1]), false);
			if (indexOf(pm, new Position(bestMove[1][0], bestMove[1][1]))!=-1) {
				numWiTurn=1;
				newTurn = true;
				return new Move(new Position(bestMove[0][0], bestMove[0][1]), new Position(bestMove[1][0], bestMove[1][1]), this);
			}
		}
		newTurn = false;
		if (numWiTurn==moveQue.size()) {
			return null;
		}
		Position ret = moveQue.get(numWiTurn-1);
		Position ret2 = moveQue.get(numWiTurn);
		if (numWiTurn+1<moveQue.size()) {
			numWiTurn+=1;
		} else {
			numWiTurn=1;
			newTurn = true;
		}
		return new Move(ret, ret2, this);
	}
	private int indexOf(ArrayList<Position> PP, Position check) {
		for (int i=0; i<PP.size(); i++) {
			if (PP.get(i).equals(check)) {
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
			//System.out.println("PP: "+prevPos);
			int row = Pos.getRow(), col = Pos.getColumn();
			System.out.println("start pos: "+Pos);
			ArrayList<Position> posMoves= board.possibleMoves(Pos, jumpOnly);
			System.out.println("posMoves"+posMoves);
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
							System.out.println("adding to MQ: "+p+", "+new Position(bestMove[1][0], bestMove[1][1]));
							moveQue.add(p);
							moveQue.add(new Position(bestMove[1][0], bestMove[1][1]));
						}
					} else {
						int newWeight = distanceToWRP(p, board);
						//System.out.println("distance: "+newWeight);
						if (newWeight<bestMove[2][0] || (newWeight==bestMove[2][0] && distanceToWRP(Pos, board)>bestMove[2][1])) {
							System.out.println("new best move: "+Pos+" to "+p+" with w: "+newWeight);
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
		return bestMove;
	}
	private boolean moreCenter(Position p1, Position p2) { //if p1 is more center than p2
		if (dir=='u'||dir=='d') {
			if (p1.getColumn()>p2.getColumn()) {
				return true;
			}
		} else if (getWR()==1 || getWR()==5) {
			if (p1.getRow()<p2.getColumn()) {
				return true;
			}
		} else {
			if (p1.getRow()>p2.getColumn()) {
				return true;
			}
		}
		return false;
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
			while ((board.rowWidths[newP.getRow()])/2>newP.getColumn()) {
				if (newP.adj(2)!=null) {
					newP = newP.adj(2);
				} else {
					newP = newP.adj(3); 
				}
				dist+=1;
			}
			while ((board.rowWidths[newP.getRow()])/2<newP.getColumn()) {
				if (newP.adj(1)!=null) {
					newP = newP.adj(1);
				} else {
					newP = newP.adj(0); 
				}
				dist+=1;
			}
			dist+=newP.getRow();
		}
		if (dir=='d') {
			while ((board.rowWidths[newP.getRow()])/2>newP.getColumn()) {
				if (newP.adj(4)!=null) {
					newP = newP.adj(4);
				} else {
					newP = newP.adj(3); 
				}
				dist+=1;
			}
			while ((board.rowWidths[newP.getRow()])/2<newP.getColumn()) {
				if (newP.adj(5)!=null) {
					newP = newP.adj(5);
				} else {
					newP = newP.adj(0); 
				}
				dist+=1;
			}
//			dist+=newP.getRow();
//			while (WRP[1]>newP.getRow()) {
//				newP = newP.adj(4);
//				dist+=1;
//			}
//			while (WRP[1]<newP.getRow()) {
//				newP = newP.adj(5);
//				dist+=1;
//			}
//			dist+=newP.getRow();
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
	public static void main(String[] args) {
		//ComputerStratBasic c1 = new ComputerStratBasic(Color.red, "Hannah");
		//Player[] players = {c1};
		Board b1 = new Board();//players);
		//System.out.println()
		ComputerStratBasic c1 = new ComputerStratBasic(Color.blue, "Hannah");
		Position[] poses = {new Position(6, 2), new Position(5, 3), new Position(4, 5), new Position(3, 0), new Position(3, 2), new Position(1, 1)};
		for (Position p:poses) {
			b1.fillPos(p);
			c1.posArr.add(p);
		}
//		while(!c1.hasWon(b1, Color.blue)) {
//			b1.printBoard();
//			b1.move(c1.getMove(b1));
//			
//		}
		//System.out.println("WR: "+c1.getWR());
		b1.printBoard();
//		Move m  = c1.getMove(b1);
//		while (m!=null) {
//			System.out.println("Move: "+m);
//			b1.move(m);
//			b1.printBoard();
//			m  = c1.getMove(b1);
//		}
		for (int i=0; i<6; i++) {
			Move m  = c1.getMove(b1);
			if (m!=null) {
				b1.move(m);
				b1.printBoard();
			}
		}
		//b1.move(c1.getMove(b1));
		//System.out.println(c1.getMove(b1));
		//b1.printBoard();
	}
}
