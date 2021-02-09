package model;

// Class representation of a single cell in a game. The value of a cell is always 2 or greater.
// A cell with a value of 0 is considered empty.
public class Cell {
    private int value;      // The value held within a single cell.

    // EFFECTS: Creates an empty cell
    public Cell() {
        value = 0;
    }

    //Getters
    public int getValue() {
        return value;
    }

    //Setters
    public void setValue(int value) {
        this.value = value;
    }

    // MODIFIES: this
    // EFFECTS: Clears the cell of the current value
    public void clearCell() {
        this.value = 0;
    }

    // MODIFIES: this, cell
    // EFFECTS: merges the two cells values and sets this to empty.
    public int mergeCells(Cell cell) {
        int newVal = value + cell.getValue();
        cell.setValue(newVal);
        value = 0;
        return newVal;
    }

    // EFFECTS: Returns true if the cell is empty.
    public Boolean isEmpty() {
        return value == 0;
    }
}
