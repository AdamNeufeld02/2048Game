package model;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    private Board board;        // The board that the game is being played on
    private int score;          // The score of the current game
    private int moves;          // Number of moves that have happened since the start of the game
    private int goal;           // The goal to reach with in the game

    public Game() {
        board = new Board();
        score = 0;
        moves = 0;
        goal = 2048;
    }

    // getters
    public Board getBoard() {
        return board;
    }

    public int getScore() {
        return score;
    }

    public int getMoves() {
        return moves;
    }

    // setters
    public void setGoal(int goal) {
        this.goal = goal;
    }

    // MODIFIES: this
    // EFFECTS: moves all pieces on the board to the right merging any adjacent identical pieces
    public void moveRight() {
        int size = board.getSize();
        int index;
        int emptyCells;
        Cell currentCell;

        for (int r = 0; r < size; r++) {
            emptyCells = 0;

            for (int c = size - 1; c >= 0; c--) {
                index = r * size + c;
                currentCell = board.getCellAt(index);

                if (currentCell.isEmpty()) {
                    emptyCells++;
                } else {
                    board.swapCells(index, index + emptyCells);

                    if (checkMergeRow(index + emptyCells, index + emptyCells + 1)) {
                        score += board.mergeCells(index + emptyCells, index + emptyCells + 1);
                        emptyCells++;
                    }
                }
            }
        }
        moves += 1;
    }

    // MODIFIES: this
    // EFFECTS: moves all pieces on the board to the left merging any adjacent identical pieces
    public void moveLeft() {
        int size = board.getSize();
        int index;
        int emptyCells;
        Cell currentCell;

        for (int r = 0; r < size; r++) {
            emptyCells = 0;

            for (int c = 0; c < size; c++) {
                index = r * size + c;
                currentCell = board.getCellAt(index);

                if (currentCell.isEmpty()) {
                    emptyCells++;
                } else {
                    board.swapCells(index, index - emptyCells);

                    if (checkMergeRow(index - emptyCells, index - emptyCells - 1)) {
                        score += board.mergeCells(index - emptyCells, index - emptyCells - 1);
                        emptyCells++;
                    }
                }
            }
        }
        moves += 1;
    }

    // MODIFIES: this
    // EFFECTS: moves all pieces on the board up merging any adjacent identical pieces
    public void moveUp() {
        int size = board.getSize();
        int index;
        int emptyCells;
        Cell currentCell;

        for (int c = 0; c < size; c++) {
            emptyCells = 0;

            for (int r = 0; r < size; r++) {
                index = r * size + c;
                currentCell = board.getCellAt(index);

                if (currentCell.isEmpty()) {
                    emptyCells++;
                } else {
                    board.swapCells(index, index - size * emptyCells);

                    if (checkMergeColumn(index - size * emptyCells, index - size * emptyCells - size)) {
                        score += board.mergeCells(index - size * emptyCells, index - size * emptyCells - size);
                        emptyCells++;
                    }
                }
            }
        }
        moves += 1;
    }

    // MODIFIES: this
    // EFFECTS: moves all pieces on the board down merging any adjacent identical pieces
    public void moveDown() {
        int size = board.getSize();
        int index;
        int emptyCells;
        Cell currentCell;

        for (int c = 0; c < size; c++) {
            emptyCells = 0;

            for (int r = size - 1; r >= 0; r--) {
                index = r * size + c;
                currentCell = board.getCellAt(index);

                if (currentCell.isEmpty()) {
                    emptyCells++;
                } else {
                    board.swapCells(index, index + size * emptyCells);

                    if (checkMergeColumn(index + size * emptyCells, index + size * emptyCells + size)) {
                        score += board.mergeCells(index + size * emptyCells, index + size * emptyCells + size);
                        emptyCells++;
                    }
                }
            }
        }
        moves += 1;
    }

    // EFFECTS: Returns true if the game is over.
    public boolean isGameOver() {
        ArrayList<Cell> emptyCells = board.getEmptyCells();
        int size = board.getSize();
        if (emptyCells.size() == 0) {
            for (int i = 0; i < size * size; i++) {
                if (checkMergeRow(i, i + 1) || checkMergeColumn(i, i + size)) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    // EFFECTS: Returns true if you have reached the goal
    public boolean isGameWon() {
        return goal <= board.getHighestValue();
    }

    // REQUIRES: Must be at least one empty cell on the board.
    // MODIFIES: this
    // EFFECTS: inserts a 2 or 4 in one of the empty cells
    public void insertRandomNumber() {
        Random rand = new Random();
        Cell cell;
        ArrayList<Cell> emptyCells = board.getEmptyCells();
        int size = emptyCells.size();
        int randIndex = rand.nextInt(size);
        int randValue = 2 * (rand.nextInt(2) + 1);

        cell = emptyCells.get(randIndex);
        cell.setValue(randValue);
    }

    // MODIFIES: this
    // EFFECTS: checks if the given two indexes are on the same row and if they contain cells that can be merged.
    public boolean checkMergeRow(int index1, int index2) {
        Cell cell1;
        Cell cell2;
        if (board.checkRow(index1, index2) && board.inBounds(index1) && board.inBounds(index2)) {
            cell1 = board.getCellAt(index1);
            cell2 = board.getCellAt(index2);
            return (cell1.getValue() == cell2.getValue());
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: checks if the given two indexes are in bounds in the same column and if they contain identical values.
    public boolean checkMergeColumn(int index1, int index2) {
        Cell cell1;
        Cell cell2;
        if (board.checkColumn(index1, index2) && board.inBounds(index1) && board.inBounds(index2)) {
            cell1 = board.getCellAt(index1);
            cell2 = board.getCellAt(index2);
            return (cell1.getValue() == cell2.getValue());
        } else {
            return false;
        }
    }
}
