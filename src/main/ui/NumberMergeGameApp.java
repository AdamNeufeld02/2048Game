package ui;


import java.io.IOException;
import java.util.Scanner;
import model.Board;
import model.Cell;
import model.NumberMergeGame;
import persistence.JsonReader;
import persistence.JsonWriter;

public class NumberMergeGameApp {

    private static final String JSON_STORE = "./data/game.json";
    private NumberMergeGame game;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // MODIFIES: this
    // EFFECTS: Starts the game and initializes fields.
    public NumberMergeGameApp() {
        game = new NumberMergeGame();
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        setupGame();
    }

    // EFFECTS: Provides an interface for the user to customize their game.
    public void setupGame() {
        String choice = "";
        while (!choice.equals("q")) {
            System.out.println("Would you like to:");
            System.out.println("\t - Customize Board (b)");
            System.out.println("\t - Change the goal (g)");
            System.out.println("\t - Load a game (l)");
            System.out.println("\t - Start a new game (Start)");
            System.out.println("\t - Quit (q)");

            choice = input.nextLine();
            choice = choice.toLowerCase();

            if (choice.equals("b")) {
                customizeBoard();
            } else if (choice.equals("g")) {
                changeGoal();
            } else if (choice.equals("l")) {
                loadGame();
            } else if (choice.equals("start")) {
                playGame();
            }
        }

    }

    // MODIFIES: this
    // EFFECTS: Allows the user to change the size of their board.
    public void customizeBoard() {
        Board board = game.getBoard();
        String choice;
        int size;
        int newSize;

        do {
            size = board.getSize();
            System.out.println("The current size of the board is " + size);
            System.out.println("What would you like the new size to be?");
            newSize = input.nextInt();
            input.nextLine();
            board.setSize(newSize);

            System.out.println("Your new board looks like this");
            printBoard(board);

            System.out.println("Are you happy with this? (y/n)");
            choice = input.nextLine();
        } while (!choice.equals("y"));
    }

    // MODIFIES: this
    // EFFECTS: Allows the user to change the goal of their game.
    public void changeGoal() {
        String choice;
        int goal;
        int newGoal;

        do {
            goal = game.getGoal();
            System.out.println("The current goal is " + goal);
            System.out.println("What would you like the new goal to be?");
            newGoal = input.nextInt();
            input.nextLine();
            game.setGoal(newGoal);

            System.out.println("The goal is now " + game.getGoal());

            System.out.println("Are you happy with this? (y/n)");
            choice = input.nextLine();
        } while (!choice.equals("y"));
    }

    // EFFECTS: Plays the game following all appropriate rules.
    public void playGame() {

        Board board = game.getBoard();
        game.insertRandomNumber();
        game.insertRandomNumber();

        printScoreBoard();
        printBoard(board);

        while (true) {
            if (!nextTurn()) {
                break;
            }

            printScoreBoard();
            printBoard(board);

            if (game.isGameOver()) {
                gameOver();
                break;
            } else if (game.isGameWon()) {
                if (!gameWon()) {
                    break;
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Allows the user to make a move to change the board. Returns true if the user doesn't want to quit
    //          else returns false.
    public boolean nextTurn() {
        String move;
        System.out.println("Next Move or Quit(q):");
        move = input.nextLine();

        if (move.equals("r")) {
            game.moveRight();
            return true;
        } else if (move.equals("l")) {
            game.moveLeft();
            return true;
        } else if (move.equals("u")) {
            game.moveUp();
            return true;
        } else if (move.equals("d")) {
            game.moveDown();
            return true;
        } else if (move.equals("q")) {
            quitGame();
            return false;
        } else {
            System.out.println("Invalid move");
            return true;
        }
    }

    // EFFECTS: Prints out the given board onto the console.
    public void printBoard(Board board) {
        Cell cell;
        int size = board.getSize();
        int index;
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                index = row * size + column;
                cell = board.getCellAt(index);
                System.out.print("|");
                if (cell.isEmpty()) {
                    System.out.print("     ");
                } else {
                    System.out.printf("%5d", cell.getValue());
                }
                System.out.print("|");
            }
            System.out.print("\n");
        }

    }

    // MODIFIES: this
    // EFFECTS: Handles the game over event and allows the user to start a new game if they wish.
    public void gameOver() {
        String choice;
        System.out.println("Game Over");
        System.out.println("Your final score was " + game.getScore());
        System.out.println("Would you like to start a new game? (y/n)");
        choice = input.nextLine();
        if (choice.equals("y")) {
            game = new NumberMergeGame();
            setupGame();
        } else {
            System.out.println("Thanks for playing!");
        }
    }

    // MODIFIES: this
    // EFFECTS: Handles the game won event and allows the user to continue with an increased goal.
    public boolean gameWon() {
        String choice;
        int goal = game.getGoal();
        System.out.println("You won!");
        System.out.println("Your score was " + game.getScore());
        System.out.println("Would you like to continue? (y/n)");
        choice = input.nextLine();
        if (choice.equals("y")) {
            game.setGoal(2 * goal);
            System.out.println("Your new goal is " + game.getGoal());
            return true;
        } else {
            System.out.println("Thanks for playing");
            return false;
        }
    }

    // EFFECTS: Prints out the score board containing the current score as well as moves made.
    public void printScoreBoard() {
        int score = game.getScore();
        int moves = game.getMoves();
        System.out.println("\n\n______________________________");
        System.out.printf("%-15s %15s", "|Score: " + score, "Moves: " + moves + "|\n");
        System.out.println("------------------------------");
    }

    // EFFECTS: Quits the game and offers to save the game for the player.
    public void quitGame() {
        String choice;
        System.out.println("Would you like to save the game (y/n)? (All unsaved progress will be lost)");
        choice = input.nextLine();
        choice = choice.toLowerCase();
        if (choice.equals("y")) {
            saveGame();
        }
    }

    // MODIFIES: this
    // EFFECTS: Loads the game from file
    public void loadGame() {
        try {
            game = jsonReader.read();
            System.out.println("Loaded game from " + JSON_STORE);
            playGame();
        } catch (IOException e) {
            System.out.println("Unable to read from file " + JSON_STORE);
        }
    }

    // EFFECTS: Saves the game to file
    public void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();
            System.out.println("Saved game to " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to save game to " + JSON_STORE);
        }
    }
}
