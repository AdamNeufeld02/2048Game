package persistence;

import exceptions.IndexException;
import exceptions.SizeException;
import jdk.nashorn.internal.ir.debug.JSONWriter;
import model.Board;
import model.Cell;
import model.NumberMergeGame;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

//TODO citation: code taken and modified from JsonWriterTest.java package in JsonSerializationDemo

public class JsonWriterTest {

    @Test
    public void testWriterInvalidFile() {
        try {
            NumberMergeGame game = new NumberMergeGame();
            JsonWriter writer = new JsonWriter("./data/noSuchFileExists\0.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    public void testWriterDefaultGame() {
        try {
            NumberMergeGame game = new NumberMergeGame();
            JsonWriter writer = new JsonWriter("./data/testWriterDefaultGame.json");
            writer.open();
            writer.write(game);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterDefaultGame.json");
            game = reader.read();
            Board board = game.getBoard();
            assertEquals(2048, game.getGoal());
            assertEquals(0, game.getScore());
            assertEquals(0, game.getMoves());
            assertEquals(16, board.getEmptyCells().size());
        } catch (IOException e) {
            fail("Unexpected Exception.");
        }
    }

    @Test
    public void testWriterGeneralGame() {
        try {
            NumberMergeGame game = new NumberMergeGame();
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralGame.json");
            Board board = game.getBoard();
            game.setMoves(10);
            game.setScore(200);
            game.setGoal(4096);
            board.setSize(5);
            for (int i = 0; i < 5; i++) {
                board.setCell(i, i + 1);
            }
            writer.open();
            writer.write(game);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralGame.json");
            game = reader.read();
            board = game.getBoard();
            Cell cell;
            assertEquals(10, game.getMoves());
            assertEquals(200, game.getScore());
            assertEquals(4096, game.getGoal());
            assertEquals(5, board.getSize());
            assertEquals(20, board.getEmptyCells().size());
            for(int i = 0; i < 5; i++) {
                cell = board.getCellAt(i);
                assertEquals(i + 1, cell.getValue());
            }
        } catch (IOException e) {
            fail("Unexpected IOException");
        } catch (SizeException e) {
            fail("Unexpected SizeException Thrown.");
        } catch (IndexException e) {
            fail("Unexpected IndexException.");
        }
    }
}
