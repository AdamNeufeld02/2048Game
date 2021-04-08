package persistence;

import exceptions.IndexException;
import model.Board;
import model.Cell;
import model.NumberMergeGame;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

//TODO citation: code taken and modified from JsonReader.java package in JsonSerializationDemo

public class JsonReaderTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/goodTryPal.json");
        try {
            NumberMergeGame game = reader.read();
            fail("Exception expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderDefaultGame() {
        JsonReader reader = new JsonReader("./data/testReaderDefaultGame.json");
        try {
            NumberMergeGame game = reader.read();
            Board board = game.getBoard();
            assertEquals(2048, game.getGoal());
            assertEquals(0, game.getMoves());
            assertEquals( 0, game.getScore());
            assertEquals(16, board.getEmptyCells().size());
        } catch (IOException e) {
            fail("Unexpected Exception");
        }
    }

    @Test
    public void testReaderGeneralGame() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralGame.json");
        try {
            NumberMergeGame game = reader.read();
            Board board = game.getBoard();
            Cell cell;
            assertEquals(1024, game.getGoal());
            assertEquals(64, game.getScore());
            assertEquals(10, game.getMoves());
            assertEquals(3, board.getEmptyCells().size());

            for (int i = 0; i < 6; i++) {
                cell = board.getCellAt(i);
                assertEquals(i + 1, cell.getValue());
            }
        } catch (IOException e) {
            fail("Unexpected IOException");
        } catch (IndexException e) {
            fail("Unexpected IndexException.");
        }
    }
}
