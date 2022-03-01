public class Peg implements Cloneable{
	private Player owner;
	private Position pos;
	
	public Peg clone() {
		return new Peg(this.owner);
	}
	
	//only for testing, don't use this peg
	public Peg() {}
	
	public Peg(Player owner) {
		this.owner=owner;
	};
	
	public Player getOwner() {
		return owner;
	}
	
	public Position getPos() {
		return pos;
	}
}
