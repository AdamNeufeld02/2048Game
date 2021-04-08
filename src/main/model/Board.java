package model;

import exceptions.IndexException;
import exceptions.SizeException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;

// Represents a square board of side length size. Each cell contains a value all initialized to be empty.
public class Board implements Writable {
    private ArrayList<Cell> cells;      // The list of all cells within a board.
    private int size;                   // The size of one side of the board. All boards are perfect squares.

    // EFFECTS: Constructs a new board of size 4.
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

    // EFFECTS: returns the cell based at the given index on the board or throws exception if index is not on board
    public Cell getCellAt(int index) throws IndexException {
        if (!inBounds(index)) {
            throw new IndexException();
        }
        return (cells.get(index));
    }

    // EFFECTS: returns all empty cells
    public ArrayList<Cell> getEmptyCells() {
        ArrayList<Cell> emptyCells = new ArrayList<>();
        Cell cell;
        for (int i = 0; i < size * size; i++) {
            try {
                cell = getCellAt(i);
                if (cell.getValue() == 0) {
                    emptyCells.add(cell);
                }
            } catch (IndexException e) {
                e.printStackTrace();
            }
        }
        return emptyCells;
    }

    // EFFECTS: Returns the highest value of any cell on the board.
    public int getHighestValue() {
        Cell cell;
        int highest = 0;
        for (int i = 0; i < size * size; i++) {
            try {
                cell = getCellAt(i);
                if (cell.getValue() > highest) {
                    highest = cell.getValue();
                }
            } catch (IndexException e) {
                e.printStackTrace();
            }
        }
        return highest;
    }

    // Setters
    // EFFECTS: Throws SizeException if size is less than 1.
    public void setSize(int size) throws SizeException {
        if (size < 1) {
            throw new SizeException();
        }
        this.size = size;
        initializeBoard();
    }

    // EFFECTS Sets the Cell at given index or throws exception if index is not on board.
    public void setCell(int index, int value) throws IndexException {
        Cell cell = getCellAt(index);
        cell.setValue(value);
    }

    // MODIFIES: this
    // EFFECTS: swaps the two cells at the given index
    public void swapCells(int index1, int index2) throws IndexException {
        if (!inBounds(index1) || !inBounds(index2)) {
            throw new IndexException();
        }
        Collections.swap(cells, index1, index2);
    }

    // MODIFIES: this
    // EFFECTS: Merges the two cells at the given indexes and returns the value as an int. Or throws IndexException if
    //          given indexes are not on the board.
    public int mergeCells(int index1, int index2) throws IndexException {
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        json.put("size", size);
        for (Cell c : cells) {
            jsonArray.put(c.toJson());
        }

        json.put("cells", jsonArray);

        return json;
    }

    // MODIFIES: this
    // EFFECTS: Adds or removes cells until the board contains enough to fill a board of the correct size. Or throws
    //          Size exception if the size is less than 1.
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
