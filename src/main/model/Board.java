package model;

import java.util.ArrayList;
import java.util.Collections;

public class Board {
    private ArrayList<Cell> cells;
    private int size;

    public Board() {
        cells = new ArrayList<>();
        size = 4;
        initializeBoard();
    }

    // Getters
    public int getSize() {
        return size;
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

    // REQUIRES: size > 0
    // MODIFIES: this
    // EFFECTS: Adds or removes cells until the board contains enough to fill a board of the correct size
    private void initializeBoard() {
        if (cells.size() > size * size) {
            removeCells();
        } else if (cells.size() < size * size) {
            addCells();
        }
    }

    // REQUIRES: this.size * this.size < cells.size()
    // MODIFIES: this
    // EFFECTS: removes cells until the board is of correct size according to the field.
    private void removeCells() {
        while (size * size < cells.size()) {
            cells.remove(cells.size() - 1);
        }
    }

    // REQUIRES: this.size * this.size > cells.size()
    // MODIFIES: this
    // EFFECTS: adds cells until the board is of correct size according to the field.
    private void addCells() {
        while (size * size > cells.size()) {
            Cell cell = new Cell();
            cells.add(cell);
        }

    }
}
