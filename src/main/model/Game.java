package model;

public class Game {
    private Board board;
    private int score;
    private int moves;
    private int goal;

    public Game() {

    }

    // getters
    public Board getBoard() {
        return board;
    }

    // MODIFIES: this
    // EFFECTS: moves all pieces on the board in the given direction adhering to all rules of the game
    public void move(String direction) {

    }

    // EFFECTS: Returns true if the game is over.
    public Boolean isGameOver() {
        return false;
    }

    // MODIFIES: this
    // EFFECTS: inserts a 2 or 4 in one of the empty cells
    public void insertRandomNumber() {

    }

}
