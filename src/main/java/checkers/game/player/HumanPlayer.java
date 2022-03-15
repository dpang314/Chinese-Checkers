package checkers.game.player;

import checkers.game.Board;
import checkers.game.board.Move;

import java.awt.*;
import java.io.Serializable;

public class HumanPlayer extends Player implements Serializable {
    public HumanPlayer(Color color, String playerName) {
        super(color, playerName);
    }

	public Move getMove(Board board) {
        return null;
    }

    @Override
    public boolean isComputer() {
        return false;
    }
}