import java.util.Stack;
import java.awt.Color;

public class Game {
  //Stores all players in the game
	private Player[] players;
  //Stores which player's turn it is
  private Player currentPlayer;
  //List of all moves for the purpose of undoing
	private Stack<Move> history;
  //Number of turns
	private int turn;
  //Stores the game board
	private Board board;
  //Current player index
	private int playerTurn = 0;
  //First player turn
	private int first = 0;
  //determines if there are more than one human player
  private boolean humans = false;
  //number of actual players
  private int numPlayers;
  //tracks the final start and end position of the peg you moved on your turn
  //this is the move that is added to the history stack
  private Move finalMove = null;
  //tracks moves within one turn; cleared at the end of every turn
  private Stack<Move> miniHistory;

	public Game(Player[] player, boolean shuffle) {
		//Sets players array to the one passed to it
	    this.players = player;
	    
	    //counts actual players
	    int l = 0;
	    for (Player p : players) {
	    	if (p != null)
	    		l++;
	    }
	    numPlayers = l;
	    
	    //Determines whether there are multiple human players
	    //for the purposes of undo
	    int z=0;
	    for (Player p : player){
//	      if (p.isHuman())
//	        z++;
	    }
	    if (z>1)
	      humans = true;
	    
	    //sets history and turn counter to base form, creates a board with given players
	    this.history = new Stack<Move>();
	    this.turn = 0;
	    this.board = new Board(players);
	
	    //Shuffles turn order
	    if (shuffle) {
	      //Second players array with all null elements
	      Player[] players2 = new Player[]{null,null,null,null,null,null};
	      //Iterates through every space in players2
	      for (int i = 0; i < 6; i++){
	        //creates a random index k and assigns an element from players to players2
	        //in that random index; checks to make sure only null spaces in players2 are
	        //being occupied
	        int k = (int)Math.random()*6;
	        while (!(players2[k]==null)){
	          k = (int)Math.random()*6;
	        }
	        //Once this is over, every player in players should be assigned to a null space
	        //in players2
	
	        //If 2 or more null spaces in players are assigned to the same 
	        //null space in players2 it doesn't matter because all of players2's elements
	        //were null to begin with
	        players2[k]=players[i];
	      }
	      //Sets players to players2 so the game will now reference the random turn order
	      players = players2;
	
	      //If shuffled, the first element will NOT necessarily be a player; so it will
	      //iterate through the array until it finds a real player to set the first player
	      //to
	      int i = 0;
	      while (players[i]==null){
	        i++;
	      }
	      currentPlayer = players[i];
	      playerTurn = i;
	      first = i;
	    }
	    //otherwise, the first player should be the first element
	    else
	      currentPlayer = players[0];

	};
	
  
  
	public void endTurn() {
		//Doesn't add a move to the stack if
		//there is no player in the slot
		if (finalMove!=null)
			history.add(finalMove);
		//Resets final move and clears minihistory stack
		//at the end of every turn
		finalMove = null;
		for (int i = 0; i<miniHistory.size();i++) {
			miniHistory.remove(i);
		}
		//If not at the limit
		if (playerTurn<5) {
			playerTurn++;
			currentPlayer = players[playerTurn];
		}
		else {
			playerTurn = 0;
			currentPlayer = players[0];
		}
		if (playerTurn == first)
			turn++;
		
	}
	
	public void save() {
		
	}
	
	public void undo() {
		Move undoingMove;
		//If the player has made a move this turn, undoes only the FinalMove without
		//referencing history
		if (miniHistory.size()>0) {
			//Creates a move to pass to board which is just a reverse of the 
			//current turn's move
			undoingMove = new Move(finalMove.getEndPosition(),
					finalMove.getStartPosition(), currentPlayer);
			board.move(undoingMove);
			for (int i = 0; i<miniHistory.size();i++) {
				miniHistory.remove(i);
			}
		}
		//If there were no moves made this turn, and there are no other human players,
		//undoes to the beginning of your previous turn.
		else if (!humans) {
				//Undoes every move which was not made by the current 
				//player between the current turn and the start of the 
				//current player's last turn
				int k = history.capacity()-1;
				//iterates through each real player's move since the start of the
				//current player's previous turn
				for (int i = 0; i<numPlayers; i++){
					Move undidMove = history.get(k-i);
					undoingMove = new Move(undidMove.getEndPosition(),undidMove.getStartPosition()
							,undidMove.getOwner());
					board.move(undoingMove);
					history.remove(k-i);
			    }
				//Sets turn counter back however many turns were skipped
				turn-=numPlayers;
		}
	}
	
	
	
	public void movePeg(Move move) {
		board.move(move);
		//adds the move to the mini stack for this turn
		miniHistory.add(move);
		//sets the finalMove to the current start and end position of the peg
		//over the entire turn
		finalMove = new Move(miniHistory.get(0).getStartPosition(),
				miniHistory.get(miniHistory.size()-1).getEndPosition()
				,currentPlayer);
	};
  
  //(1)red to (4)blue
  //(6)yellow to (3)green
  //(2)black to (5)white
  //If corresponding corner is filled and it isnt filled with its own players peg, return current player
	public Player winningPlayer() {
    //current player's color
    Color currentColor = currentPlayer.getColor();
    //Color of opposite player - determined below based on current player color
    Color assignedColor = Color.BLUE;
    //Goal region position array
    Position[] goal;

    if (currentColor.equals(Color.RED)){
      assignedColor = Color.BLUE;
    }
    else if (currentColor.equals(Color.BLUE))
      assignedColor = Color.RED;
    else if (currentColor.equals(Color.YELLOW))
      assignedColor = Color.GREEN;
    else if (currentColor.equals(Color.GREEN))
      assignedColor = Color.YELLOW;
    else if (currentColor.equals(Color.BLACK))
      assignedColor = Color.WHITE;
    else if (currentColor.equals(Color.WHITE))
      assignedColor = Color.BLACK;

   goal = board.getHomeRegion(assignedColor);

    //Checks every position in the goal region - if they are all filled by
    //current player's pegs, they win; otherwise return null
    for (Position p : goal) {
//      if (!playerPeg(currentPlayer, p)) {
 //       return null;
 //     }
    }
    return currentPlayer;
	};

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public Player[] getPlayers() { 
		return players;
	};
	public Board getBoard() { 
		return board;
	};
}
