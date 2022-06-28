package checkers.game.board;

import checkers.game.player.Player;

import java.io.Serializable;

public class Move implements Serializable {
    private final Player player;
    private final Position start;
    private final Position end;

    public Move(Position start, Position end, Player player) {
        this.start = start;
        this.end = end;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Position getStart() {
        return start;
    }

    public Position getEnd() {
        return end;
    }

    public String toString() {
        return "[" + player.getName() + "]" + " " + start + " to " + end;
    }
}