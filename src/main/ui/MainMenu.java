package ui;

import persistence.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// The Main menu of the Game contains buttons for loading starting and changing the board
public class MainMenu extends JFrame {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    public static final int BUTTON_HEIGHT = 50;
    public static final int BUTTON_WIDTH = 100;
    private JsonReader jsonReader;
    private NumberMergeGameApp gameApp;
    private JButton start;
    private JButton load;
    private JButton setSize;

    // Constructs the main menu where the user can customize the game and load game.
    public MainMenu(NumberMergeGameApp gameApp) {
        super("Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gameApp = gameApp;
        start = new JButton("Start");
        load = new JButton("Load");
        setSize = new JButton("Set Size");
        jsonReader = new JsonReader(gameApp.JSON_STORE);
        setSize(WIDTH, HEIGHT);
        start.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        load.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        setSize.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        setLayout(new FlowLayout());
        ActionListener bh = new ButtonHandler();
        add(start);
        add(load);
        add(setSize);

        start.addActionListener(bh);
        load.addActionListener(bh);
        setSize.addActionListener(bh);
    }

    // EFFECTS: Attempts to load the game from gameApp.JSON_STORE and begins to play the game if successful.
    private void loadGame() {
        try {
            gameApp.setGame(jsonReader.read());
            JOptionPane.showMessageDialog(new JFrame(), "Loaded the game from " + gameApp.JSON_STORE);
            gameApp.playGame();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(new JFrame(), "Unable to read from file " + gameApp.JSON_STORE);
        }
    }

    // EFFECTS: Prompts the user for a new size, will not except anything except an integer.
    private void setBoardSize() {
        String input = JOptionPane.showInputDialog("Enter the new Size");
        int size;
        try {
            size = Integer.parseInt(input);
            gameApp.getGame().getBoard().setSize(size);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(new JFrame(), "Please enter an integer.");
        }
    }

    // Action listener for the main menu button options
    private class ButtonHandler implements ActionListener {

        // EFFECTS: Handles each button event on the main menu.
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == start) {
                gameApp.startGame();
            } else if (e.getSource() == load) {
                loadGame();
            } else if (e.getSource() == setSize) {
                setBoardSize();
            }
        }
    }
}
