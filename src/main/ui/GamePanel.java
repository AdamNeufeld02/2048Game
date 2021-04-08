package ui;

import exceptions.IndexException;
import model.Board;
import model.Cell;
import model.NumberMergeGame;

import javax.swing.*;
import java.awt.*;

// The game panel of the number merge game. contains the board which the game is played on
public class GamePanel extends JPanel {

    public static final int CELL_SIZE = 100;
    public static final int CELL_ARC = 40;
    public static final int SPACER = 10;
    public static final Font FONT = new Font(Font.SERIF, Font.BOLD, 25);
    private NumberMergeGame game;

    // EFFECTS: Constructs the GamePanel and initializes the size and fonts
    public GamePanel(NumberMergeGame game) {
        this.game = game;
        setPreferredSize(new Dimension(calculateWidth(), calculateWidth()));
        setBackground(Color.GRAY);
        setFont(FONT);
    }

    // EFFECTS: Calculates the current game width based on the size of the board
    public int calculateWidth() {
        return CELL_SIZE * game.getBoard().getSize() + SPACER * (game.getBoard().getSize() - 1);
    }

    // Effects: paints all components of the game panel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawBoard(g);
    }

    // MODIFIES: G
    // EFFECTS: Draws the board onto g
    private void drawBoard(Graphics g) {
        Board board = game.getBoard();
        int size = board.getSize();
        Cell cell;
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                try {
                    cell = board.getCellAt(row * size + column);
                    drawCell(g, cell, row, column);
                } catch (IndexException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // MODIFIES: G
    // EFFECTS: Draws the Cell onto g
    private void drawCell(Graphics g, Cell cell, int row, int column) {
        if (!cell.isEmpty()) {
            int x = column * CELL_SIZE + column * SPACER;
            int y = row * CELL_SIZE + row * SPACER;
            String val = String.valueOf(cell.getValue());
            Color savedCol = g.getColor();
            g.setColor(Color.ORANGE);
            g.fillRoundRect(x, y, CELL_SIZE, CELL_SIZE, CELL_ARC, CELL_ARC);
            g.setColor(Color.BLACK);
            g.drawRoundRect(x, y, CELL_SIZE, CELL_SIZE, CELL_ARC, CELL_ARC);
            drawValue(g, val, x, y);
            g.setColor(savedCol);
        }
    }


    // MODIFIES: G
    // EFFECTS: Draws the given string centered in the given coordinates onto g
    private void drawValue(Graphics g, String value, int x, int y) {
        FontMetrics fm = g.getFontMetrics();
        int height = fm.getHeight();
        int width = fm.stringWidth(value);
        g.drawString(value, x + CELL_SIZE / 2 - width / 2, y + CELL_SIZE / 2 + height / 4);
    }
}
