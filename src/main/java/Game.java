import java.io.Serializable;
import java.util.*;
import java.awt.Color;
import java.util.concurrent.TimeUnit;

public class Game implements Serializable {
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
  //tracks moves within one turn; cleared at the end of every turn
  private Stack<Move> miniHistory = new Stack<Move>();

  private final static Color[][] colorAssignments = {
		  {}, {},
		  { Color.RED, Color.BLUE },
		  { Color.RED, Color.GREEN, Color.WHITE },
		  { Color.BLACK, Color.GREEN, Color.WHITE, Color.YELLOW }, {},
		  { Color.RED, Color.BLACK, Color.GREEN, Color.BLUE, Color.WHITE, Color.YELLOW }
  };

	public Game(Player[] player, boolean shuffle) {
		//Sets players array to the one passed to it
	    this.players = player;
	    
	    //counts actual players
	    int l = 0;
		int z=0;
	    for (Player p : players) {
	    	if (p != null) {
				l++;
				if (!p.isComputer()) {
					z++;
				}
			}
	    }
	    numPlayers = l;
		//Determines whether there are multiple human players
		//for the purposes of undo
	    if (z>1)
			humans = true;

	    //sets history and turn counter to base form, creates a board with given players
	    this.history = new Stack<Move>();
	    this.turn = 0;
	    int count = 0;
		for (int i = 0; i < players.length; i++) {
			if (players[i] != null) {
				if (players[i] instanceof HumanPlayer) {
					players[i] = new HumanPlayer(colorAssignments[numPlayers][count], players[i].getName());
				}
				else if (players[i] instanceof ComputerStratBasic2) {
					players[i] = new ComputerStratBasic2(colorAssignments[numPlayers][count], players[i].getName());
				}
				else if (players[i] instanceof QuinnStrategy) {
					players[i] = new QuinnStrategy(colorAssignments[numPlayers][count], players[i].getName());
				}
				count++;
			}
		}
	    //Shuffles turn order
	    if (shuffle) {
            //Second players array 
            Player[] players2 = players;
            Random rand = new Random();
            for (int x = players2.length - 1; x > 0; x--) {
              int index = rand.nextInt(x + 1);
              // Simple swap
              Player a = players2[index];
              players2[index] = players2[x];
              players2[x] = a;
            }

            //Sets players to players2 so the game will now reference the random turn order
            players = players2;
            currentPlayer = players[0];
            playerTurn = 0;
        }
        //otherwise, the first player should be the first element
        else
            currentPlayer = players[0];
	    
		
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

		this.board = new Board(players);
	};

	public Move getTurn() {
		Move move = currentPlayer.getMove(board);
		return move;
	}
  
	public void endTurn() {
		initiallySelected = null;
		jumped = false;
		//Doesn't add a move to the stack if
		//there is no player in the slot
		history.add(new Move(miniHistory.get(0).getStartPosition(),
				miniHistory.get(miniHistory.size()-1).getEndPosition()
				,currentPlayer));
		//Resets final move and clears minihistory stack
		//at the end of every turn
		miniHistory.clear();
		//If not at the limit
		playerTurn++;
		playerTurn %= 6;
		while (players[playerTurn] == null) {
			playerTurn++;
			playerTurn %= 6;
		}
		currentPlayer = players[playerTurn];

		if (playerTurn == first)
			turn++;
		
	}

	public boolean canUndoMini() {
		return !miniHistory.isEmpty();
	}

	public boolean canUndoTurns() {
		// Makes sure there is only 1 human player and the current player
		// has made at least 1 earlier turn
		return !humans && history.size() >= numPlayers;
	}
	
	private boolean jumped = false;
	
	// If multiple human players, can only undo minihistory
	// If minihistory empty, undo entire turns
	public void undo() {
		initiallySelected = null;
		Move undoingMove;
		//If the player has made a move this turn, undoes only the FinalMove without
		//referencing history
		if (canUndoMini()) {
			//Creates a move to pass to board which is just a reverse of the 
			//current turn's move
			Move lastMove = miniHistory.pop();
			undoingMove = new Move(lastMove.getEndPosition(),
					lastMove.getStartPosition(), currentPlayer);
			board.move(undoingMove, true);
			if (miniHistory.size()==0) {
				jumped = false;
			}
		}
		//If there were no moves made this turn, and there are no other human players,
		//undoes to the beginning of your previous turn.
		else if (canUndoTurns()) {
			for (int i = 0; i < numPlayers; i++) {
				Move undidMove = history.pop();
				undoingMove = new Move(undidMove.getEndPosition(),undidMove.getStartPosition()
						,undidMove.getOwner());
				board.move(undoingMove, true);
			}
			miniHistory.clear();
			//Sets turn counter back however many turns were skipped
			turn-=numPlayers;
		}
	}

	

	public boolean movePeg(Move move) {
		if (initiallySelected == null && miniHistory.isEmpty()) {
			initiallySelected = move.getEndPosition();
			if (board.possibleJumpMoves(move.getStartPosition()) != null &&
					board.possibleJumpMoves(move.getStartPosition()).contains(move.getEndPosition())) {
				jumped = true;
			}
		} else {
			Position lastPosition = miniHistory.peek().getEndPosition();
			// Not moving same peg
			if (!move.getStartPosition().equals(lastPosition)) {
				throw new RuntimeException("Invalid move. startPos is not equal to the end position of the last move.");
			} else if (!jumped) {
				throw new RuntimeException("Invalid move. Can't move again after moving to an adjacent position.");
			} else if (!board.possibleMoves(lastPosition, true).contains(move.getEndPosition())) {
				throw new RuntimeException("Invalid move. Move is not a possible jump.");
			}
		}
		board.move(move, false);
		//adds the move to the mini stack for this turn
		miniHistory.add(move);
		return true;
	};

	public boolean movePeg(Position endPosition) {
		// First move of turn
		Position startPosition = selectedPosition();
		if (board.possibleJumpMoves(startPosition) != null &&
				board.possibleJumpMoves(startPosition).contains(endPosition)) {
			jumped = true;
		}
		Move move = new Move(startPosition, endPosition, currentPlayer);
		board.move(move, false);
		//adds the move to the mini stack for this turn
		miniHistory.add(move);
		initiallySelected = null;
		return true;
	};

	Position initiallySelected = null;

    public Position selectedPosition() {
		if (miniHistory.isEmpty()) {
			return initiallySelected != null ? initiallySelected : null;
		}
		return miniHistory.peek().getEndPosition();
    }

	public Position startPosition() {
		if (miniHistory.isEmpty()) {
			return initiallySelected != null ? initiallySelected : null;
		}
		return miniHistory.get(0).getStartPosition();
	}

	public void select(Position p) {
		initiallySelected = p;
	}

	public boolean canEndTurn() {
		return !miniHistory.isEmpty() && !selectedPosition().equals(startPosition());
	}

	public ArrayList<Position> getPossibleMoves() {
		if (selectedPosition() == null ||
				!miniHistory.isEmpty() && !jumped) return new ArrayList<Position>();
		if (miniHistory.isEmpty()) {
			return board.possibleMoves(selectedPosition(), false);
		} else {
			return board.possibleMoves(selectedPosition(), true);
		}
	}

	public HashMap<Position, Color> getNonClickablePegs() {
		HashMap<Position, Color> ret = new HashMap<>();
		for (int i = 0; i < players.length; i++) {
			if (players[i] != null) {
				for (Position p : players[i].posArr) {
					if ((playerTurn != i) ||
							(selectedPosition() != null && !miniHistory.isEmpty() && !p.equals(selectedPosition()))) {
						ret.put(p, players[i].getColor());
					}
				}
			}
		}
		return ret;
	}

	public ArrayList<Position> getClickablePegs() {
		if (selectedPosition() == null) return currentPlayer.posArr;
		else if (miniHistory.isEmpty()) {
			ArrayList<Position> ret = new ArrayList<>();
			ret.addAll(players[playerTurn].posArr);
			return ret;
		} else {
			ArrayList<Position> ret = new ArrayList<>();
			ret.add(selectedPosition());
			return ret;
		}
	}
  
  //(1)red to (4)blue
  //(6)yellow to (3)green
  //(2)black to (5)white
  //If corresponding corner is filled and it isnt filled with its own players peg, return current player
	public Player winningPlayer() {
    //current player's color
    Color currentColor = currentPlayer.getColor();
    //Goal region position array
    Position[] goal = Board.getWinRegion(currentColor);

    //Checks every position in the goal region - if they are all filled by
    //current player's pegs, they win; otherwise return null
		Player ret = currentPlayer;
		for (Position p : goal) {
			if (!currentPlayer.posArr.contains(p)) {
				ret = null;
			}
		}
    	return ret;
	};

    public boolean gameOver() {
    	return winningPlayer() != null;
	}

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
