package checkers.game.player;

import checkers.game.Board;
import checkers.game.board.Move;
import checkers.game.board.Position;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Player implements Serializable {
    public ArrayList<Position> posArr;
    private Color color;
    private final String playerName;
    private Position[] winReg;
    private int winRegInt;

    public Player(Color color, String playerName) {
        this.color = color;
        this.playerName = playerName;
        posArr = new ArrayList<Position>();
    }

	public abstract Move getMove(Board board);

    public abstract boolean isComputer();

    public Color getColor() {
        return color;
    }

	public void setColor(Color c) {
        this.color = c;
    }

    public String getName() {
        return playerName;
    }

    public void addInitalPos(Position pos) {
        posArr.add(pos);
    }

    public void assignWinReg(Position[] WR) {
        winReg = WR;
        //System.out.println("WR from P: "+winReg[0]);
    }

    public void assignWinRegInt(int WR) {
        winRegInt = WR;
    }

    public Position[] getWR() {
        return winReg;
    }

    public int getWRInt() {
        return winRegInt;
    }
}
