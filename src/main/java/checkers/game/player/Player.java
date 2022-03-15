package checkers.game.player;

import checkers.game.Board;
import checkers.game.board.Move;
import checkers.game.board.Position;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Player implements Serializable {
    private final String playerName;
    public ArrayList<Position> posArr;
    private Color color;
    private Position[] winReg;

    public Player(Color color, String playerName) {
        this.color = color;
        this.playerName = playerName;
        posArr = new ArrayList<>();
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

    public void addInitialPos(Position pos) {
        posArr.add(pos);
    }

    public void assignWinReg(Position[] WR) {
        winReg = WR;
        //System.out.println("WR from P: "+winReg[0]);
    }

    public Position[] getWR() {
        return winReg;
    }

}
