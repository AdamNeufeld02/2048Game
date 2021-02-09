package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CellTest {
    private Cell cell;

    @BeforeEach
    public void setup() {
        cell = new Cell();
    }

    @Test
    public void testSetValue() {
        cell.setValue(128);
        assertEquals(128, cell.getValue());
    }

    @Test
    public void testSetManyValues() {
        for (int i = 2; i < 500; i++){
            cell.setValue(i);
            assertEquals(i, cell.getValue());
        }
    }

    @Test
    public void testClearCell() {
        cell.setValue(2048);
        assertEquals(2048, cell.getValue());

        cell.clearCell();
        assertEquals(0, cell.getValue());
    }

    @Test
    public void testMergeCells() {
        Cell cell2 = new Cell();

        cell.setValue(128);
        cell2.setValue(128);

        assertEquals(256, cell.mergeCells(cell2));
        assertEquals(0, cell.getValue());
        assertEquals(256, cell2.getValue());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(cell.isEmpty());

        cell.setValue(256);
        assertFalse(cell.isEmpty());

    }
}