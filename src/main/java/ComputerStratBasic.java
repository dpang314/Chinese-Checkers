import java.awt.Color;
import java.util.ArrayList;

public class ComputerStratBasic extends Player{
	private int[][] winLine = new int[2][2]; //{{x1, x2}, {y1, y2}}
	public ComputerStratBasic(Color color, String playerName) {
		super(color, playerName);
		winLine[0][0] = getWR()==1 || getWR()==2 ? 9 : (getWR()==4 || getWR()==0? 0 :  3);
		winLine[0][1] = getWR()==1 || getWR()==2 ? 9 : (getWR()==4 || getWR()==0? 3 :  0);
		winLine[1][0] = getWR()==1 || getWR()==5 ? 4 : (getWR()==4 || getWR()==2? 9 :  (getWR()==0 ? 0 : 13));
		winLine[1][1] = getWR()==1 || getWR()==5 ? 7 : (getWR()==4 || getWR()==2? 12 :  (getWR()==0 ? 3 : 16));
	}

	@Override
	public Move getMove(Board board) {
		int[][] bestMove= new int[3][2]; //{{r1, c1}, {r2, c2} {weight, 0}}
		for (int i=0; i<posArr.size(); i++) {
			bestMove = goodMove(posArr.get(i), bestMove, board, false);
		}
		
		return new Move(new Position(bestMove[0][0], bestMove[0][1]), new Position(bestMove[1][0], bestMove[1][1]), this);
	}
	
	private int[][] goodMove(Position Pos, int[][] bestMove, Board board, boolean jumpOnly){
		int row = Pos.getRow(), col = Pos.getColumn();
		ArrayList<Position> posMoves= board.possibleMoves(Pos, jumpOnly);
		for (Position p : posMoves) {
			if (board.possibleMoves(p, true).size()>0) {
				
			} else {
				
			} //use weighting systems
		}
		return bestMove;
	};
}
