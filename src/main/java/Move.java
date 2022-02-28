
public class Move {
  private Player owner;
	private Position startPosition;
	private Position endPosition;
	
	public Move(Position startPosition, Position endPosition, Player owner1) {
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		this.owner = owner1;
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
		return "[" + owner + "]" + " " + startPosition + " to " + endPosition;
	}
}