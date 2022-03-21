package checkers;

import checkers.game.Game;
import checkers.game.player.ComputerStrategy;
import checkers.game.player.EasierStrategy;
import checkers.game.player.HarderStrategy;

import java.awt.*;

public class StrategyEvaluator {
    public static void play(int games, boolean shuffle, ComputerStrategy[] computers) {
        int[] wins = new int[computers.length];
        for (int i = 0; i < computers.length; i++) {
            if (computers[i] != null) {
                computers[i].setName("Computer " + i + ": " + computers[i].getName());
            }
        }
        for (int i = 0; i < games; i++) {
            Game game = new Game(computers, shuffle);
            while (!game.gameOver()) {
                game.getTurn();
            }
            for (int j = 0; j < computers.length; j++) {
                if (computers[j] != null && computers[j].getName().equals(game.winningPlayer().getName())) {
                    wins[j]++;
                }
            }
        }
        for (int i = 0; i < wins.length; i++) {
            if (computers[i] != null) {
                System.out.println(computers[i].getName() + ": " + wins[i] + " wins");
            }
        }
    }

    public static void main(String[] args) {
        ComputerStrategy[] computers = {
                new HarderStrategy(Color.RED, "Harder Strategy"),
                new EasierStrategy(Color.RED, "Easier Strategy"),
                null, null, null, null
        };
        play(100, false, computers);
    }
}
