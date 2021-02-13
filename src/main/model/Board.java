package model;

import java.util.ArrayList;
import java.util.Collections;

// Represents a square board of side length size. Each cell contains a value all initialized to be empty.
public class Board {
    protected ArrayList<Cell> cells;      // The list of all cells within a board.
    private int size;                   // The size of one side of the board. All boards are perfect squares.

    public Board() {
        cells = new ArrayList<>();
        size = 4;
        initializeBoard();
    }

    // Getters
    public int getSize() {
        return size;
    }

    // EFFECTS: Returns the row the given index is in
    public int getRow(int index) {
        return index / size;
    }

    // EFFECTS: returns the column the given index is in
    public int getColumn(int index) {
        return index % size;
    }

    // REQUIRES: index must be less than the length of cells
    // EFFECTS: returns the cell based at the given index on the board
    public Cell getCellAt(int index) {
        return (cells.get(index));
    }

    // EFFECTS: returns all empty cells
    public ArrayList<Cell> getEmptyCells() {
        ArrayList<Cell> emptyCells = new ArrayList<>();
        Cell cell;
        for (int i = 0; i < size * size; i++) {
            cell = getCellAt(i);
            if (cell.getValue() == 0) {
                emptyCells.add(cell);
            }
        }
        return emptyCells;
    }

    // EFFECTS: Returns the highest value of any cell on the board.
    public int getHighestValue() {
        Cell cell;
        int highest = 0;
        for (int i = 0; i < size * size; i++) {
            cell = getCellAt(i);
            if (cell.getValue() > highest) {
                highest = cell.getValue();
            }
        }
        return highest;
    }

    // Setters
    public void setSize(int size) {
        this.size = size;
        initializeBoard();
    }

    public void setCell(int index, int value) {
        Cell cell = getCellAt(index);
        cell.setValue(value);
    }

    // REQUIRES: both indexes must be within the range of the board
    // MODIFIES: this
    // EFFECTS: swaps the two cells at the given index
    public void swapCells(int index1, int index2) {
        Collections.swap(cells, index1, index2);
    }

    // REQUIRES: Both indexes must be within the range of the board
    // MODIFIES: this
    // EFFECTS: Merges the two cells at the given indexes and returns the value as an int
    public int mergeCells(int index1, int index2) {
        Cell cell1 = getCellAt(index1);
        Cell cell2 = getCellAt(index2);
        return  cell1.mergeCells(cell2);
    }

    // EFFECTS: Returns true if both index are within the same row on the board
    public Boolean checkRow(int index1, int index2) {
        return getRow(index1) == getRow(index2);
    }

    // EFFECTS: Returns true if both index are within the same column on the board
    public Boolean checkColumn(int index1, int index2) {
        return getColumn(index1) == getColumn(index2);
    }

    // EFFECTS: returns true if the given index is in the bounds of the board.
    public Boolean inBounds(int index) {
        return index >= 0 && index < size * size;
    }

    // REQUIRES: size > 0
    // MODIFIES: this
    // EFFECTS: Adds or removes cells until the board contains enough to fill a board of the correct size
    private void initializeBoard() {
        if (cells.size() > size * size) {
            removeCells();
        } else  {
            addCells();
        }
    }

    // MODIFIES: this
    // EFFECTS: removes cells until the board is of correct size according to the field.
    private void removeCells() {
        while (size * size < cells.size()) {
            cells.remove(cells.size() - 1);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds cells until the board is of correct size according to the field.
    private void addCells() {
        while (size * size > cells.size()) {
            Cell cell = new Cell();
            cells.add(cell);
        }

    }
}
