package checkers.game.board;

import checkers.game.Board;
import checkers.game.player.HarderStrategy;
import checkers.game.player.HumanPlayer;
import checkers.game.player.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {
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

    private static Stream<Position> providePositions() {
        ArrayList<Position> positions = new ArrayList<>();
        for (int i = 0; i < Board.rowWidths.length; i++) {
            for (int j = 0; j < Board.rowWidths[i]; j++) {
                positions.add(new Position(i, j));
            }
        }
        return positions.stream();
    }

    @DisplayName("Should store reference to same player object")
    @ParameterizedTest(name = "{index} => Peg of player=''{0}''")
    @MethodSource("providePlayers")
    void getOwner(Player testPlayer) {
        Move testMove = new Move(new Position(-1, -1), new Position(-1, -1), testPlayer);
        assertSame(testPlayer, testMove.getPlayer());
    }

    @DisplayName("Should store reference to same position object")
    @ParameterizedTest(name  = "{index} => Position=''{0}''")
    @MethodSource("providePositions")
    void getStartPosition(Position testPosition) {
        Move testMove = new Move(testPosition, testPosition, new HumanPlayer(Color.RED, "Test Human"));
        assertSame(testMove.getStart(), testPosition);
    }

    @Test
    void getEndPosition() {
    }
}