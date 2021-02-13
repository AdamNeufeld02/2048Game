package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NumberMergeGameTest {
    private NumberMergeGame game;

    @BeforeEach
    public void setup() {
        game = new NumberMergeGame();
    }

    @Test
    public void testGetGoal() {
        assertEquals(2048, game.getGoal());

        game.setGoal(4096);

        assertEquals(4096, game.getGoal());
    }

    @Test
    public void testIsGameOverEmptyBoard() {
        assertFalse(game.isGameOver());
    }

    @Test
    public void testIsGameOverFullBoard() {
        Board board = game.getBoard();
        for (int i = 0; i < 16; i++) {
            board.setCell(i, 2);
        }
        assertFalse(game.isGameOver());
    }

    @Test
    public void testIsGameOverFullBoardGameOver() {
        Board board = game.getBoard();
        for (int i = 0; i < 16; i++) {
            board.setCell(i, i + 1);
        }
        assertTrue(game.isGameOver());
    }

    @Test
    public void testIsGameOverVerticalMerges() {
        Board board = game.getBoard();
        for (int i = 0; i < 16; i++) {
            board.setCell(i, i + 1);
        }
        board.setCell(8, 5);

        assertFalse(game.isGameOver());
    }

    @Test
    public void testIsGameWonNotWon() {
        assertFalse(game.isGameWon());
    }

    @Test
    public void testIsGameWonGameWon() {
        Board board = game.getBoard();
        board.setCell(9, 2048);
        assertTrue(game.isGameWon());
        game.setGoal(4096);
        board.setCell(10, 4096);
        assertTrue(game.isGameWon());
    }

    @Test
    public void testInsertRandomNumber() {
        Board board = game.getBoard();
        Cell cell;
        int twos = 0;
        int fours = 0;
        ArrayList<Cell> emptyCells;

        for (int i = 0; i < 16; i ++) {
            game.insertRandomNumber();
        }

        emptyCells = board.getEmptyCells();

        for (int i = 0; i < 16; i++) {
            cell = board.getCellAt(i);
            if (cell.getValue() == 2) {
                twos++;
            } else {
                fours++;
            }
        }

        assertFalse(fours == 0);
        assertFalse(twos == 0);
        assertEquals(0, emptyCells.size());
    }

    @Test
    public void testCheckMergeRow() {
        Board board = game.getBoard();
        board.setCell(6, 8);
        board.setCell(7, 8);

        assertTrue(game.checkMergeRow(6, 7));
        assertFalse(game.checkMergeRow(5, 6));
        assertFalse(game.checkMergeRow(7, 8));
        assertFalse(game.checkMergeRow(15, 16));
        assertFalse(game.checkMergeRow(-1, 0));
        assertFalse(game.checkMergeRow(9, 13));
    }

    @Test
    public void testCheckMergeColumn() {
        Board board = game.getBoard();
        board.setCell(6, 16);
        board.setCell(10, 16);

        assertTrue(game.checkMergeColumn(6, 10));
        assertFalse(game.checkMergeColumn(2,6));
        assertFalse(game.checkMergeColumn(10, 14));
        assertFalse(game.checkMergeColumn(15, 19));
        assertFalse(game.checkMergeColumn(-4, 0));
        assertFalse(game.checkMergeColumn(0, 1));
    }

    @Test
    public void testSimpleMoveRight() {
        Board board = game.getBoard();
        Cell cell;

        board.setCell(0, 4);
        board.setCell(3, 4);
        board.setCell(12,2);
        board.setCell(15, 4);

        game.moveRight();

        assertEquals(1, game.getMoves());
        assertEquals(8, game.getScore());

        cell = board.getCellAt(3);
        assertEquals(8,cell.getValue());
        cell = board.getCellAt(14);
        assertEquals(2, cell.getValue());
        cell = board.getCellAt(15);
        assertEquals(4, cell.getValue());
    }

    @Test
    public void testSimpleMoveLeft() {
        Board board = game.getBoard();
        Cell cell;
        board.setCell(0, 4);
        board.setCell(3, 4);
        board.setCell(12,2);
        board.setCell(15, 4);

        game.moveLeft();

        assertEquals(1, game.getMoves());
        assertEquals(8, game.getScore());

        cell = board.getCellAt(0);
        assertEquals(8,cell.getValue());
        cell = board.getCellAt(12);
        assertEquals(2, cell.getValue());
        cell = board.getCellAt(13);
        assertEquals(4, cell.getValue());
    }

    @Test
    public void testSimpleMoveUp() {
        Board board = game.getBoard();
        Cell cell;
        board.setCell(0, 4);
        board.setCell(3, 4);
        board.setCell(12,2);
        board.setCell(15, 4);

        game.moveUp();

        assertEquals(1, game.getMoves());
        assertEquals(8, game.getScore());

        cell = board.getCellAt(0);
        assertEquals(4,cell.getValue());
        cell = board.getCellAt(4);
        assertEquals(2, cell.getValue());
        cell = board.getCellAt(3);
        assertEquals(8, cell.getValue());
    }

    @Test
    public void testSimpleMoveDown() {
        Board board = game.getBoard();
        Cell cell;
        board.setCell(0, 4);
        board.setCell(3, 4);
        board.setCell(12,2);
        board.setCell(15, 4);

        game.moveDown();

        assertEquals(1, game.getMoves());
        assertEquals(8, game.getScore());

        cell = board.getCellAt(8);
        assertEquals(4,cell.getValue());
        cell = board.getCellAt(12);
        assertEquals(2, cell.getValue());
        cell = board.getCellAt(15);
        assertEquals(8, cell.getValue());
    }

    @Test
    public void testMoveRightNoSpace() {
        Board board = game.getBoard();
        board.setSize(5);

        for (int i = 0; i < 5; i ++) {
            board.setCell(i, i);
        }
        game.moveRight();

        assertEquals(0, game.getScore());
        assertEquals(0, game.getMoves());
    }

    @Test
    public void testMoveLeftNoSpace() {
        Board board = game.getBoard();
        board.setSize(5);

        for (int i = 0; i < 5; i ++) {
            board.setCell(i, i + 1);
        }
        game.moveLeft();

        assertEquals(0, game.getScore());
        assertEquals(0, game.getMoves());
    }

    @Test
    public void testMoveUpNoSpace() {
        Board board = game.getBoard();
        board.setSize(5);

        for (int i = 0; i < 5; i ++) {
            board.setCell(i * 5, i + 1);
        }
        game.moveUp();

        assertEquals(0, game.getScore());
        assertEquals(0, game.getMoves());
    }

    @Test
    public void testMoveDownNoSpace() {
        Board board = game.getBoard();
        board.setSize(5);

        for (int i = 0; i < 5; i ++) {
            board.setCell(i * 5, i + 1);
        }
        game.moveDown();

        assertEquals(0, game.getScore());
        assertEquals(0, game.getMoves());
    }
}
