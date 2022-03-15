package checkers.game.board;

import checkers.game.player.Player;

import java.io.Serializable;

public class Peg implements Cloneable, Serializable {
    private Player owner;
    private Position pos;

    //only for testing, don't use this peg
    public Peg() {
    }

    public Peg(Player owner) {
        this.owner = owner;
    }

    public Peg clone() {
        return new Peg(this.owner);
    }

    public Player getOwner() {
        return owner;
    }

    public Position getPos() {
        return pos;
    }
}