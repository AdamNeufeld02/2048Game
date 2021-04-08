package persistence;

import exceptions.IndexException;
import exceptions.SizeException;
import model.Board;
import model.NumberMergeGame;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

//TODO citation: code taken and modified from JsonReader.java package in JsonSerializationDemo

// A reader to read games stored as JSON data in a file
public class JsonReader {

    private String source;

    // MODIFIES: this
    // EFFECTS: Constructs a reader to from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads the game data from the file and returns it
    // throws IOException if an error occurs while reading the data.
    public NumberMergeGame read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGame(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses game from JSON object and returns it
    private NumberMergeGame parseGame(JSONObject jsonObject) {
        NumberMergeGame game = new NumberMergeGame();
        JSONObject board;

        int goal = jsonObject.getInt("goal");
        int score = jsonObject.getInt("score");
        int moves = jsonObject.getInt("moves");
        board = jsonObject.getJSONObject("board");

        game.setGoal(goal);
        game.setScore(score);
        game.setMoves(moves);

        addBoard(game, board);
        return game;
    }

    // MODIFIES: game
    // EFFECTS: parses board from JSON object and adds them to game
    private void addBoard(NumberMergeGame game, JSONObject jsonObject) {
        Board board = game.getBoard();
        int size = jsonObject.getInt("size");
        JSONArray jsonArray = jsonObject.getJSONArray("cells");
        int i = 0;

        try {
            board.setSize(size);
        } catch (SizeException e) {
            e.printStackTrace();
        }

        for (Object json : jsonArray) {
            JSONObject nextCell = (JSONObject) json;
            addCell(board, nextCell, i);
            i++;
        }
    }

    // MODIFIES: board
    // EFFECTS: parses cells from JSON object and adds it to board
    private void addCell(Board board, JSONObject jsonObject, int index) {
        int value = jsonObject.getInt("value");
        try {
            board.setCell(index, value);
        } catch (IndexException e) {
            e.printStackTrace();
        }
    }


}
