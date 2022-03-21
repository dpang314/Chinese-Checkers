package checkers.resources;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GameLoaderTest {

    @Test
    void writeGameToFile() {
    }

    @Test
    @DisplayName("File with .chcr extension but invalid data should throw error")
    void readGameFromFile() {
        assertThrows(IOException.class, () -> GameLoader.readGameFromFile("./test/resources/BadSave.chcr"));
    }
}