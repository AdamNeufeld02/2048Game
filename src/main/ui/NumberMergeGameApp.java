package ui;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import model.NumberMergeGame;
import persistence.JsonWriter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javax.swing.*;


// The NumberMergeGame Ui includes a main menu and a game frame.
public class NumberMergeGameApp extends JFrame {

    public static final String JSON_STORE = "./data/game.json";
    private NumberMergeGame game;
    private JsonWriter jsonWriter;
    private GamePanel gamePanel;
    private ScorePanel scorePanel;
    private JMenu menu;
    private JMenuBar mb;
    private JMenuItem save;
    private MainMenu mainMenu;

    // MODIFIES: this
    // EFFECTS: Starts the game and initializes fields.
    public NumberMergeGameApp() {
        super("2048");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game = new NumberMergeGame();
        jsonWriter = new JsonWriter(JSON_STORE);
        mainMenu = new MainMenu(this);
        mainMenu.setVisible(true);
        addKeyListener(new KeyHandler());
    }

    public void setGame(NumberMergeGame game) {
        this.game = game;
    }

    public NumberMergeGame getGame() {
        return game;
    }

    // MODIFIES: this
    // EFFECTS: Initializes the game frame to the chosen size and adds all components
    public void initializeGameFrame() {
        gamePanel = new GamePanel(game);
        scorePanel = new ScorePanel(game);
        menu = new JMenu("Menu");
        mb = new JMenuBar();
        save = new JMenuItem("Save");
        menu.add(save);
        mb.add(menu);
        this.setJMenuBar(mb);
        save.addActionListener(new MenuHandler());
        add(gamePanel);
        add(scorePanel, BorderLayout.NORTH);
        pack();
        centreOnScreen();
    }

    // REQUIRES: Game must be a brand new game
    // MODIFIES: this
    // EFFECTS: starts the game
    public void startGame() {
        game.insertRandomNumber();
        game.insertRandomNumber();
        playGame();
    }

    // MODIFIES: this
    // EFFECTS: Closes the menu and showcases the game frame.
    public void playGame() {
        mainMenu.setVisible(false);
        initializeGameFrame();
        this.setVisible(true);
        repaint();
    }

    // EFFECTS: Saves the game to file
    public void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();
            JOptionPane.showMessageDialog(new JFrame(), "Saved game to " + JSON_STORE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(new JFrame(), "Unable to save game to " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS:  frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }

    // MODIFIES: this
    // EFFECTS: Prints out the game won event clears the board brings user back to main menu
    private void gameWon() {
        String won = "You win!\nScore: " + game.getScore() + "\nMoves: " + game.getMoves();

        // Audio from: https://soundbible.com/1003-Ta-Da.html
        playAudio("./data/Ta_Da.wav");

        JOptionPane.showMessageDialog(new JFrame(), won);
        mainMenu.setVisible(true);
        this.setVisible(false);
        game = new NumberMergeGame();
        getContentPane().removeAll();
    }

    // MODIFIES: this
    // EFFECTS: Prints out the game lost event clears the board brings user back to main menu
    private void gameOver() {
        String lose = "You lose!\nScore: " + game.getScore() + "\nMoves: " + game.getMoves();

        // Audio from: https://soundbible.com/1830-Sad-Trombone.html
        playAudio("./data/Sad_Trombone.wav");

        JOptionPane.showMessageDialog(new JFrame(), lose);
        mainMenu.setVisible(true);
        this.setVisible(false);
        game = new NumberMergeGame();
        getContentPane().removeAll();
    }

    // EFFECTS: Plays the given audio file.
    private void playAudio(String fileName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File((fileName)));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            System.out.println("Failed to play Audio");
        }
    }

    // A key handler for the game to handle moves made by user
    private class KeyHandler extends KeyAdapter {

        // EFFECTS: updates the whole game frame and moves board based on key pressed
        @Override
        public void keyPressed(KeyEvent e) {
            game.keyPressed(e.getKeyCode());
            gamePanel.repaint();
            scorePanel.update();

            if (game.isGameWon()) {
                gameWon();
            } else if (game.isGameOver()) {
                gameOver();
            }
        }
    }

    // An Action listener for the game frames menu button
    private class MenuHandler implements ActionListener {

        // EFFECTS: Performs action based on which button was pressed in menu.
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == save) {
                saveGame();
            }
        }
    }
}
