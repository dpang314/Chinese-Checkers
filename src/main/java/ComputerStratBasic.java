import java.awt.Color;
import java.util.ArrayList;

public class ComputerStratBasic extends Player{
	private int[][] winLine = new int[2][2]; //{{x1, x2}, {y1, y2}}
	private char dir;
	private int[] WRP = new int[2]; //{row, col}
	public ComputerStratBasic(Color color, String playerName) {
		super(color, playerName);
		winLine[0][0] = getWR()==1 || getWR()==2 ? 9 : (getWR()==4 || getWR()==0? 0 :  3);
		winLine[0][1] = getWR()==1 || getWR()==2 ? 9 : (getWR()==4 || getWR()==0? 3 :  0);
		winLine[1][0] = getWR()==1 || getWR()==5 ? 4 : (getWR()==4 || getWR()==2? 9 :  (getWR()==0 ? 0 : 13));
		winLine[1][1] = getWR()==1 || getWR()==5 ? 7 : (getWR()==4 || getWR()==2? 12 :  (getWR()==0 ? 3 : 16));
		if (color.equals(Color.yellow)) {dir='l'; WRP[0] = 4; WRP[1] = 0;}
		else if (color.equals(Color.white)) {dir='l'; WRP[0] = 12; WRP[1] = 0;}
		else if (color.equals(Color.black)) {dir='r'; WRP[0] = 4; WRP[1] = 12;}
		else if (color.equals(Color.green)) {dir='r'; WRP[0] = 12; WRP[1] = 12;}
		else if (color.equals(Color.red)) {dir='u'; WRP[0] = 0; WRP[1] = 0;}
		else {dir='d'; WRP[0] = 16; WRP[1] = 0;}
	}

	@Override
	public Move getMove(Board board) {
		int[][] bestMove= new int[3][2]; //{{r1, c1}, {r2, c2} {weight, 0}}
		for (int i=0; i<posArr.size(); i++) {
			bestMove = goodMove(posArr.get(i), bestMove, board, false);
		}
		if (distanceToWR(new Position(bestMove[0][0], bestMove[0][1]), board)<distanceToWR(new Position(bestMove[1][0], bestMove[1][1]), board)) {
			for (int i=0; i<posArr.size(); i++) {
				bestMove = goodMove2(posArr.get(i), bestMove, board, false);
			}
		}
		return new Move(new Position(bestMove[0][0], bestMove[0][1]), new Position(bestMove[1][0], bestMove[1][1]), this);
	}
	
	private int[][] goodMove(Position Pos, int[][] bestMove, Board board, boolean jumpOnly){
		int row = Pos.getRow(), col = Pos.getColumn();
		ArrayList<Position> posMoves= board.possibleMoves(Pos, jumpOnly);
		for (Position p : posMoves) {
			if (board.possibleMoves(p, true).size()>0) {
				bestMove=goodMove(p, bestMove, board, true);
			} else {
				int newWeight;
				if (dir=='l'||dir=='r') {
					if (winLine[1][0]<p.getRow() && winLine[1][1]>p.getRow()){
						int x1 = (getColor().equals(Color.yellow)||getColor().equals(Color.black)) ? (winLine[0][0]-(winLine[1][0]-p.getRow())): (winLine[0][0]+(winLine[1][0]-p.getRow()));
						if (dir=='l') {
								newWeight=p.getColumn()-(x1);
							} else {
								newWeight=x1-p.getColumn();
							}
					}else {
						newWeight=distanceToWR(p, board);
					}
				}else {
					if (winLine[0][0]<p.getRow() && winLine[0][1]>p.getRow()) {
						if (dir=='u') {
							newWeight=p.getRow()-winLine[1][1];
						} else {
							newWeight=winLine[1][0]-p.getRow();
						}
					}
					else{
						newWeight=distanceToWR(p, board);
					}
				}
				if (newWeight<bestMove[2][0]) {
					bestMove[0][0] = row;bestMove[0][1] = col;
					bestMove[1][0] = p.getRow();bestMove[1][1] = p.getColumn();
					bestMove[2][0] = newWeight;
				}
			} 
		}
		return bestMove;
	};
	private int[][] goodMove2(Position Pos, int[][] bestMove, Board board, boolean jumpOnly){
		int row = Pos.getRow(), col = Pos.getColumn();
		ArrayList<Position> posMoves= board.possibleMoves(Pos, jumpOnly);
		for (Position p : posMoves) {
			if (board.possibleMoves(p, true).size()>0) {
				bestMove=goodMove2(p, bestMove, board, true);
			} else {
				int newWeight = distanceToWRP(p, board);
				if (newWeight<bestMove[2][0]) {
					bestMove[0][0] = row;bestMove[0][1] = col;
					bestMove[1][0] = p.getRow();bestMove[1][1] = p.getColumn();
					bestMove[2][0] = newWeight;
				}
			} 
		}
		return bestMove;
	}
	private int distanceToWR(Position p, Board board) {
		int dist=0;
		Position newP = new Position(p.getRow(), p.getColumn());
		if (winLine[1][0]>p.getRow() && (dir=='l'||dir=='r')) {
			while (winLine[1][0]>newP.getRow()) {
				if (dir=='l') {newP = newP.adj(5);} //BL
				else {newP = newP.adj(4);} //BR
				dist+=1;
			}
			int x1 = (getColor().equals(Color.yellow)||getColor().equals(Color.black)) ? (winLine[0][0]-(winLine[1][0]-p.getRow())): (winLine[0][0]+(winLine[1][0]-p.getRow()));
			if (dir=='l') {
					dist+=p.getColumn()-(x1);
			}else {
					dist+=x1-p.getColumn();
			}
		}
		else if (winLine[1][1]<p.getRow() && (dir=='l'||dir=='r')) {
			while (winLine[1][1]<newP.getRow()) {
				if (dir=='l') {newP = newP.adj(1);} //TL
				else {newP = newP.adj(2);} //TR
				dist+=1;
			}
			int x1 = (getColor().equals(Color.yellow)||getColor().equals(Color.black)) ? (winLine[0][0]-(winLine[1][0]-p.getRow())): (winLine[0][0]+(winLine[1][0]-p.getRow()));
			if (dir=='l') {
					dist+=p.getColumn()-(x1);
			}else {
					dist+=x1-p.getColumn();
			}
		}
		else if (winLine[0][0]>p.getColumn() && (dir=='u'||dir=='d')) {
			while (winLine[0][0]>p.getColumn()) {
				if (dir=='u') {newP = newP.adj(2);} //TR
				else {newP = newP.adj(4);} //BR
				dist+=1;
			}
			if (dir=='u') {
				dist+=p.getRow()-winLine[1][1];
			} else {
				dist+=winLine[1][0]-p.getRow();
			}
		}
		return dist;
	}
	private int distanceToWRP(Position p, Board board) {
		int dist=0;
		Position newP = new Position(p.getRow(), p.getColumn());
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
			while (WRP[1]>newP.getRow()) {
				newP = newP.adj(2);
				dist+=1;
			}
			while (WRP[1]<newP.getRow()) {
				newP = newP.adj(1);
				dist+=1;
			}
			dist+=newP.getRow();
		}
		if (dir=='d') {
			while (WRP[1]>newP.getRow()) {
				newP = newP.adj(4);
				dist+=1;
			}
			while (WRP[1]<newP.getRow()) {
				newP = newP.adj(5);
				dist+=1;
			}
			dist+=newP.getRow();
		}
		return dist;
	}
}
