import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloTest {

    @Test
    @DisplayName("Add two integers")
    void add() {
        assertEquals(3, Hello.add(1, 2));
        assertEquals(3, Hello.add(4, 2));
    }
}