package model;
import exceptions.IndexException;
import exceptions.SizeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    private Board board;

    @BeforeEach
    public void setup() {
        board = new Board();
    }

    @Test
    public void testGetHighestValue() {
        try {
            board.setCell(0, 4);
            board.setCell(1, 8);
            board.setCell(2, 16);
            board.setCell(3, 8);
            board.setCell(15, 128);
        } catch (IndexException e) {
            fail("Unexpected IndexException.");
        }

        assertEquals(128, board.getHighestValue());
    }
    @Test
    public void testInitializeBoard() {
        Cell cell;
        for (int i = 0; i < 15; i++) {
            try {
                cell = board.getCellAt(i);
                assertEquals(0, cell.getValue());
            } catch (IndexException e) {
                fail("Unexpected IndexException.");
            }
        }
    }

    @Test
    public void testSetCellIllegalIndex() {
        try {
            board.setCell(16, 9);
            fail("Exception Expected.");
        } catch (IndexException e) {
            // pass
        }
    }

    @Test
    public void testGetCellIllegalIndex() {
        try {
            board.getCellAt(-1);
            fail("Exception Expected.");
        } catch (IndexException e) {
            // pass
        }
    }

    @Test
    public void testSwapCells() {
        Cell cell1;
        Cell cell2;

        try {
            board.setCell(3, 16);
            board.setCell(15, 4);

            board.swapCells(3, 15);

            cell1 = board.getCellAt(3);
            cell2 = board.getCellAt(15);
            assertEquals(4, cell1.getValue());
            assertEquals(16, cell2.getValue());
        } catch (IndexException e) {
            fail("Unexpected IndexException.");
        }
    }

    @Test
    public void testSwapCellsIllegalIndex() {
        try {
            board.swapCells(16, 2);
            fail("Exception Expected.");
        } catch (IndexException e) {
            // pass
        }

        try {
            board.swapCells(3, 17);
            fail("Exception Expected.");
        } catch (IndexException e) {
            // pass
        }
    }

    @Test
    public void testSetSizeGreater() {
        Cell cell;
        try {
            board.setSize(5);
        } catch (SizeException e) {
            fail("Unexpected Exception Thrown.");
        }
        assertEquals(5, board.getSize());
        for(int i = 0; i < 25; i++) {
            try {
                cell = board.getCellAt(i);
                assertEquals(0, cell.getValue());
            } catch (IndexException e) {
                fail("Unexpected IndexException.");
            }
        }
    }

    @Test
    public void testSetSizeLessThan() {
        Cell cell;
        try {
            board.setSize(3);
        } catch (SizeException e) {
            fail("Unexpected Exception Thrown.");
        }
        assertEquals(3, board.getSize());
        for(int i = 0; i < 9; i++) {
            try {
                cell = board.getCellAt(i);
                assertEquals(0, cell.getValue());
            } catch (IndexException e) {
                fail("Unexpected IndexException.");
            }
        }
    }

    @Test
    public void testSetSizeSame() {
        Cell cell;
        try {
            board.setSize(4);
        } catch (SizeException e) {
            fail("Unexpected Exception Thrown.");
        }
        assertEquals(4, board.getSize());
        for(int i = 0; i < 16; i++) {
            try {
                cell = board.getCellAt(i);
                assertEquals(0, cell.getValue());
            } catch (IndexException e) {
                fail("Unexpected IndexException.");
            }
        }
    }

    @Test
    public void testSetSizeIllegalSize() {
        try {
            board.setSize(0);
            fail("Exception Expected.");
        } catch (SizeException e) {
            // Pass
        }
    }

    @Test
    public void testGetEmptyCells() {
        try {
            ArrayList<Cell> emptyCells;
            board.setCell(0, 4);
            board.setCell(1, 16);
            board.setCell(15, 2);

            emptyCells = board.getEmptyCells();

            assertEquals(13, emptyCells.size());
        } catch (IndexException e) {
            fail("Unexpected IndexException.");
        }
    }

    @Test
    public void testGetRow() {
        try {
            board.setSize(5);
        } catch (SizeException e) {
            fail("Unexpected Exception Thrown.");
        }
        for (int c = 0; c < 5; c++) {
            for (int r = 0; r < 5; r++) {
                assertEquals(c, board.getRow(c * 5 + r));
            }
        }
    }

    @Test
    public void testGetColumn() {
        try {
            board.setSize(5);
        } catch (SizeException e) {
            fail("Unexpected Exception Thrown.");
        }
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                assertEquals(c, board.getColumn(r * 5 + c));
            }
        }
    }

    @Test
    public void testCheckRow() {
        try {
            board.setSize(5);
        } catch (SizeException e) {
            fail("Unexpected Exception Thrown.");
        }
        assertFalse(board.checkRow(3, 12));
        assertTrue(board.checkRow(5, 9));
    }

    @Test
    public void testCheckColumn() {
        try {
            board.setSize(5);
        } catch (SizeException e) {
            fail("Unexpected Exception Thrown.");
        }
        assertFalse(board.checkColumn(1, 24));
        assertTrue(board.checkColumn(3, 23));
    }

    @Test
    public void testMergeCells() {
        try {
            Cell cell1 = board.getCellAt(0);
            Cell cell2 = board.getCellAt(1);

            cell1.setValue(4);
            cell2.setValue(4);
            assertEquals(8, board.mergeCells(0, 1));

            assertEquals(0, cell1.getValue());
            assertEquals(8, cell2.getValue());
        } catch (IndexException e) {
            fail("Unexpected IndexException.");
        }
    }

    @Test
    public  void testMergeCellsIllegalIndex() {
        try {
            board.mergeCells(16, 1);
            fail("Exception Expected.");
        } catch (IndexException e) {
            // pass
        }
        try {
            board.mergeCells(1, 16);
            fail("Exception Expected.");
        } catch (IndexException e) {
            // pass
        }
    }

    @Test
    public void testInBounds() {
        assertFalse(board.inBounds(-1));
        assertTrue(board.inBounds(0));
        assertTrue(board.inBounds(15));
        assertFalse(board.inBounds(16));

        try {
            board.setSize(5);
        } catch (SizeException e) {
            fail("Unexpected Exception Thrown.");
        }

        assertTrue(board.inBounds(24));
        assertFalse(board.inBounds(25));
    }
}
