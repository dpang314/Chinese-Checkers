package checkers.game.board;

import checkers.game.player.HarderStrategy;
import checkers.game.player.HumanPlayer;
import checkers.game.player.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

class PegTest {
    private static Stream<Arguments> providePlayers() {
        return Stream.of(
                Arguments.of(new HumanPlayer(Color.RED, "")),
                Arguments.of(new HumanPlayer(Color.BLACK, "")),
                Arguments.of(new HumanPlayer(Color.GREEN, "")),
                Arguments.of(new HumanPlayer(Color.BLUE, "")),
                Arguments.of(new HumanPlayer(Color.WHITE, "")),
                Arguments.of(new HumanPlayer(Color.YELLOW, "")),
                Arguments.of(new HarderStrategy(Color.RED, "")),
                Arguments.of(new HarderStrategy(Color.BLACK, "")),
                Arguments.of(new HarderStrategy(Color.GREEN, "")),
                Arguments.of(new HarderStrategy(Color.BLUE, "")),
                Arguments.of(new HarderStrategy(Color.WHITE, "")),
                Arguments.of(new HarderStrategy(Color.YELLOW, ""))
        );
    }

    @DisplayName("Clone should create new object")
    @ParameterizedTest(name = "{index} => Peg of player=''{0}''")
    @MethodSource("providePlayers")
    void testClone(Player testPlayer) {
        Peg testPeg = new Peg(testPlayer);
        Peg clonePeg = testPeg.clone();
        // Assert same checks whether both references refer to the same object
        // as opposed to calling .equals, which can be overridden
        assertNotSame(testPeg, clonePeg);

        // Should have the same owner reference
        assertSame(testPeg.getOwner(), clonePeg.getOwner());
    }

    @DisplayName("Should store reference to same player object")
    @ParameterizedTest(name = "{index} => Peg of player=''{0}''")
    @MethodSource("providePlayers")
    void getOwner(Player testPlayer) {
        Peg testPeg = new Peg(testPlayer);
        assertSame(testPlayer, testPeg.getOwner());
    }
}