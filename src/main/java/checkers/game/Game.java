package checkers.game;

import checkers.game.board.Move;
import checkers.game.board.Peg;
import checkers.game.board.Position;
import checkers.game.player.EasierStrategy;
import checkers.game.player.HarderStrategy;
import checkers.game.player.HumanPlayer;
import checkers.game.player.Player;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Stack;

public class Game implements Serializable {
    private final static Color[][] colorAssignments = {{}, {}, {Color.RED, Color.BLUE}, {Color.RED, Color.GREEN, Color.WHITE}, {Color.BLACK, Color.GREEN, Color.WHITE, Color.YELLOW}, {}, {Color.RED, Color.BLACK, Color.GREEN, Color.BLUE, Color.WHITE, Color.YELLOW}};
    //List of all moves for the purpose of undoing
    private final Stack<Move> history;
    //Stores the game board
    private final Board board;
    //number of actual players
    private final int numPlayers;
    //tracks the final start and end position of the peg you moved on your turn
    //this is the move that is added to the history stack
    //tracks moves within one turn; cleared at the end of every turn
    private final Stack<Move> miniHistory = new Stack<>();
    Position initiallySelected = null;
    //Stores all players in the game
    private Player[] players;
    //Stores which player's turn it is
    private Player currentPlayer;
    //Current player index
    private int playerTurn;
    //First player turn
    //determines if there are more than one human player
    private boolean humans = false;
    private boolean jumped = false;

    public Game(Player[] player, boolean shuffle) {
        //Sets players array to the one passed to it
        this.players = player;

        //counts actual players
        int l = 0;
        int z = 0;
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
        if (z > 1) humans = true;

        //sets history and turn counter to base form, creates a board with given players
        this.history = new Stack<>();

        Player[] players2 = players;
        //Shuffles turn order
        if (shuffle) {
            //Second players array
            Random rand = new Random();
            for (int x = players2.length - 1; x > 0; x--) {
                int index = rand.nextInt(x + 1);
                // Simple swap
                Player a = players2[index];
                players2[index] = players2[x];
                players2[x] = a;
            }
        }

        //Sets players to players2 so the game will now reference the random turn order
        int count = 0;
        for (int i = 0; i < players2.length; i++) {
            if (players2[i] != null) {
                if (players2[i] instanceof HumanPlayer) {
                    players2[i] = new HumanPlayer(colorAssignments[numPlayers][count], players2[i].getName());
                } else if (players2[i] instanceof EasierStrategy) {
                    players2[i] = new EasierStrategy(colorAssignments[numPlayers][count], players2[i].getName());
                } else if (players2[i] instanceof HarderStrategy) {
                    players2[i] = new HarderStrategy(colorAssignments[numPlayers][count], players2[i].getName());
                }
                count++;
            }
        }
        players = players2;


        //If shuffled, the first element will NOT necessarily be a player; so it will
        //iterate through the array until it finds a real player to set the first player
        //to
        int i = 0;
        while (players[i] == null) {
            i++;
        }
        currentPlayer = players[i];
        playerTurn = i;

        this.board = new Board(players);
    }

    public void getTurn() {
        Move move = currentPlayer.getMove(board);
        if (move == null) {
            this.endTurn();
        } else {
            this.movePeg(move);
        }
    }

    public void endTurn() {
        initiallySelected = null;
        jumped = false;
        //Doesn't add a move to the stack if
        //there is no player in the slot
        history.add(new Move(miniHistory.get(0).getStartPosition(), miniHistory.get(miniHistory.size() - 1).getEndPosition(), currentPlayer));
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

    }

    public boolean canUndoMini() {
        return !miniHistory.isEmpty();
    }

    public boolean canUndoTurns() {
        // Makes sure there is only 1 human player and the current player
        // has made at least 1 earlier turn
        return !humans && history.size() >= numPlayers;
    }

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
            undoingMove = new Move(lastMove.getEndPosition(), lastMove.getStartPosition(), currentPlayer);
            board.move(undoingMove, true);
            if (miniHistory.size() == 0) {
                jumped = false;
            }
        }
        //If there were no moves made this turn, and there are no other human players,
        //undoes to the beginning of your previous turn.
        else if (canUndoTurns()) {
            for (int i = 0; i < numPlayers; i++) {
                Move undidMove = history.pop();
                undoingMove = new Move(undidMove.getEndPosition(), undidMove.getStartPosition(), undidMove.getOwner());
                board.move(undoingMove, true);
            }
            miniHistory.clear();
            //Sets turn counter back however many turns were skipped
        }
    }

    public void movePeg(Move move) {
        if (initiallySelected == null && miniHistory.isEmpty()) {
            initiallySelected = move.getEndPosition();
            if (board.possibleJumpMoves(move.getStartPosition()) != null && board.possibleJumpMoves(move.getStartPosition()).contains(move.getEndPosition())) {
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
    }

    public void movePeg(Position endPosition) {
        // First move of turn
        Position startPosition = selectedPosition();
        if (board.possibleJumpMoves(startPosition) != null && board.possibleJumpMoves(startPosition).contains(endPosition)) {
            jumped = true;
        }
        Move move = new Move(startPosition, endPosition, currentPlayer);
        board.move(move, false);
        //adds the move to the mini stack for this turn
        miniHistory.add(move);
        initiallySelected = null;
    }

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
        if (selectedPosition() == null || !miniHistory.isEmpty() && !jumped) return new ArrayList<>();
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
                for (Position p : players[i].getPosArr()) {
                    if ((playerTurn != i) || (selectedPosition() != null && !miniHistory.isEmpty() && !p.equals(selectedPosition()))) {
                        ret.put(p, players[i].getColor());
                    }
                }
            }
        }
        return ret;
    }

    public ArrayList<Position> getClickablePegs() {
        if (selectedPosition() == null) return currentPlayer.getPosArr();
        else if (miniHistory.isEmpty()) {
            return new ArrayList<>(players[playerTurn].getPosArr());
        } else {
            ArrayList<Position> ret = new ArrayList<>();
            ret.add(selectedPosition());
            return ret;
        }
    }

    //(1)red to (4)blue
    //(6)yellow to (3)green
    //(2)black to (5)white
    //If corresponding corner is filled, and it isn't filled with its own players peg, return current player
    public Player winningPlayer() {
        //current player's color
        boolean filledByOpp = false;
        boolean unfilledPosition = false;

        for (Position p : Board.getWinRegion(currentPlayer.getColor())) {
            Peg peg = board.getPegs()[p.getRow()][p.getColumn()];
            if (peg == null) {
                unfilledPosition = true;
                break;
            }
            if (peg.getOwner().getColor().equals(currentPlayer.getColor())) {
                filledByOpp = true;
            }
        }

        if (!unfilledPosition && filledByOpp) return currentPlayer;

        return null;
    }

    public boolean gameOver() {
        return winningPlayer() != null;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player[] getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }

    public int getTurns() {
        if (history.empty()) {
            return 0;
        }
        return history.size();
    }

    public int getMoves() {
        if (miniHistory.empty()) {
            return 0;
        }
        return miniHistory.size();
    }
}
