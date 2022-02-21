import java.awt.Color;

public class ComputerStratBasic extends Player{
	private int[][] winLine = new int[2][2]; //{{x1, x2}, {y1, y2}}
	public ComputerStratBasic(Color color, String playerName) {
		super(color, playerName);
		winLine[0][0] = getWR()==1 || getWR()==2 ? 10 : (getWR()==4 || getWR()==3? 1 :  4);
		winLine[0][1] = getWR()==1 || getWR()==2 ? 10 : (getWR()==4 || getWR()==3? 1 :  4);
	}

	@Override
	public Move getMove(Board board) {
		int[][] bestMove= new int[2][2]; //{{r1, c1}, {r2, c2}}
		for (int i=0; i<posArr.size(); i++) {
			bestMove = goodMove(posArr.get(i), bestMove);
		}
		
		return new Move(new Position(bestMove[0][0], bestMove[0][1]), new Position(bestMove[1][0], bestMove[1][1]), this);
	}
	
	private int[][] goodMove(Position Pos, int[][] bestMove){
		int row = Pos.getRow(), col = Pos.getColumn();
		
		return bestMove;
	};

}
