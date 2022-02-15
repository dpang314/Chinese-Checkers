
public class Position {
	private int row, column;
	
	public Position(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public String toString() {
		return "ROW: " + row + ", COL: " +column; 
	}
}
