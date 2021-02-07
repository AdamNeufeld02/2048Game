package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardTest {
    private Board board;

    @BeforeEach
    public void setup() {
        board = new Board();
    }

    @Test
    public void testGetHighestValue() {
        board.setCell(0, 4);
        board.setCell(1, 8);
        board.setCell(2, 16);
        board.setCell(3, 8);
        board.setCell(15, 128);

        assertEquals(128, board.getHighestValue());
    }
    @Test
    public void testInitializeBoard() {
        Cell cell;
        for (int i = 0; i < 15; i++) {
            cell = board.getCellAt(i);
            assertEquals(0, cell.getValue());
        }
    }

    @Test
    public void testSwapCells() {
        Cell cell1;
        Cell cell2;
        board.setCell(3, 16);
        board.setCell(15, 4);

        board.swapCells(3, 15);

        cell1 = board.getCellAt(3);
        cell2 = board.getCellAt(15);
        assertEquals(4, cell1.getValue());
        assertEquals(16, cell2.getValue());
    }

    @Test
    public void testSetSizeGreater() {
        Cell cell;
        board.setSize(5);
        assertEquals(5, board.getSize());
        for(int i = 0; i < 25; i++) {
            cell = board.getCellAt(i);
            assertEquals(0, cell.getValue());
        }
    }

    @Test
    public void testSetSizeLessThan() {
        Cell cell;
        board.setSize(3);
        assertEquals(3, board.getSize());
        for(int i = 0; i < 9; i++) {
            cell = board.getCellAt(i);
            assertEquals(0, cell.getValue());
        }
    }

    @Test
    public void testSetSizeSame() {
        Cell cell;
        board.setSize(4);
        assertEquals(4, board.getSize());
        for(int i = 0; i < 16; i++) {
            cell = board.getCellAt(i);
            assertEquals(0, cell.getValue());
        }
    }

    @Test
    public void testGetEmptyCells() {
        ArrayList<Cell> emptyCells;
        board.setCell(0, 4);
        board.setCell(1, 16);
        board.setCell(15, 2);

        emptyCells = board.getEmptyCells();

        assertEquals(13, emptyCells.size());
    }

}
