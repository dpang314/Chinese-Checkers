package checkers.game.player;

import java.awt.*;

public abstract class ComputerStrategy extends Player {
    public ComputerStrategy(Color color, String playerName) {
        super(color, playerName);
    }

    @Override
    public boolean isComputer() {
        return true;
    }
}
