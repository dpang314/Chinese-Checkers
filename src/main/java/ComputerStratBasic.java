import java.awt.Color;

public class ComputerStratBasic extends Player{

	public ComputerStratBasic(Color color, String playerName) {
		super(color, playerName);
		
	}

	@Override
	public Move getMove(Board board) {
		int[][] bestMove= new int[2][2]; //{{r1, c1}, {r2, c2}}
		for (int i=0; i<posArr.size(); i++) {
			bestMove = goodMove(posArr.get(i), bestMove);
		}
		
		return new Move(new Position(bestMove[0][0], bestMove[0][1]), new Position(bestMove[1][0], bestMove[1][1]));
	}
	
	private int[][] goodMove(Position Pos, int[][] bestMove){
		int row = Pos.getRow(), col = Pos.getColumn();
		
		return bestMove;
	};

}
