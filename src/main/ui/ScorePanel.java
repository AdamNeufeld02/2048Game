package ui;


import model.NumberMergeGame;

import javax.swing.*;
import java.awt.*;

// Score Panel of the number merge game. Contains the score and moves made by player.
public class ScorePanel extends JPanel {
    private static final String SCORE_TEXT = "Score: ";
    private static final String MOVES_TEXT = "Moves: ";
    private NumberMergeGame game;
    private JLabel movesLabel;
    private JLabel scoreLabel;

    // EFFECTS: Constructs a new ScorePanel with the score and moves.
    public ScorePanel(NumberMergeGame game) {
        this.game = game;
        setBackground(Color.WHITE);
        movesLabel = new JLabel(MOVES_TEXT + game.getMoves());
        scoreLabel = new JLabel(SCORE_TEXT + game.getScore());
        add(scoreLabel);
        add(movesLabel);
    }

    // MODIFIES: this
    // EFFECTS: updates the score and moves text of the labels
    public void update() {
        movesLabel.setText(SCORE_TEXT + game.getScore());
        scoreLabel.setText(MOVES_TEXT + game.getMoves());
        repaint();
    }

}
