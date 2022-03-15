package checkers.game.board;

import checkers.game.player.HumanPlayer;
import checkers.game.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class PegTest {
    Player testPlayer;
    Peg testPeg;

    @BeforeEach
    void setUp() {
        testPlayer = new HumanPlayer(Color.RED, "Tester");
        testPeg = new Peg(testPlayer);
    }

    @Test
    void testClone() {

    }

    @Test
    void getOwner() {
        assertEquals(testPlayer, testPeg.getOwner());
    }
}