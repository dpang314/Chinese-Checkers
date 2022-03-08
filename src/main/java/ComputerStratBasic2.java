import java.awt.Color;
import java.util.ArrayList;
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
		if (endTurn) {
			endTurn=false;
			prevMove=null;
			return null;
		}
		if (newTurn) {
			for (int i=0; i<posArr.size(); i++) {
				if (!posArr.get(i).equals(new Position(WRP[0], WRP[1]))) {
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
		}
		
		return null;
	}
	private int indexOf(ArrayList<Position> PP, Position check) {
		for (int i=0; i<PP.size(); i++) {
			if (PP.get(i).equals(check)) {
				return i;
			}
		}
		return -1;
	}
	private ArrayList<Position> goodMove2(Position Pos, Board board, boolean jumpOnly){
		ArrayList<Position> bestMove= new ArrayList<Position>(); //{{r1, c1}, {r2, c2} {weight, distance travelled}}
		bestMove[2][0]=1000;
		
		return bestMove;
	}

}
