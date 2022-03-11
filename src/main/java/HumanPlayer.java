import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

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