package checkers.game.board;

import checkers.game.player.Player;

import java.io.Serializable;

public class Move implements Serializable {
	private Player owner;
	private Position startPosition;
	private Position endPosition;
	
	public Move(Position startPosition, Position endPosition, Player owner) {
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		this.owner = owner;
	}

	public Player getOwner(){
	    return owner;
	}
	
	public Position getStartPosition() {
		return startPosition;
	}
	
	public Position getEndPosition() {
		return endPosition;
	}
	public String toString() {
		return "[" + owner.getName() + "]" + " " + startPosition + " to " + endPosition;
	}
}