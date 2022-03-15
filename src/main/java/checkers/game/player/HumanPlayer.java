package checkers.game.player;

import checkers.game.board.Move;
import checkers.game.Board;

import java.awt.Color;
import java.io.Serializable;

public class HumanPlayer extends Player implements Serializable {
	public HumanPlayer(Color color, String playerName) {
		super(color, playerName);
	};
	public Move getMove(Board board) {
		return null;
	}

	@Override
	public boolean isComputer() {
		return false;
	}
}