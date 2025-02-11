package lab3;

import lab3.game.Board;
import lab3.ui.swingConsole;

import javax.swing.*;
import java.awt.*;

public class swingMain {
    /**
     * This is Not Fully-Functional
     */
    public static void main(String[] args) {
        Board board = new Board();
        swingConsole game = new swingConsole(board);
        game.createWindow();

        boolean gameRunning = true;

        while (gameRunning) {
            if (board.checkWin()) {
                Board.addPoints(swingConsole.lastPlayerSymbol());
                swingConsole.displayWin();
                game.setRematch();

                while (swingConsole.rematch == 0) {
                    swingConsole.sleep(1);
                } // waits for rematch input

                if (swingConsole.rematch == 1) {
                    Board.resetGame();
                    swingConsole.resetPanel();
                } else if (swingConsole.rematch == 2) {
                    gameRunning = false;
                }
            } else if (board.isTie()) {
                swingConsole.displayTie();
                game.setRematch();

                while (swingConsole.rematch == 0) {
                    swingConsole.sleep(1);
                }

                if (swingConsole.rematch == 1) {
                    Board.resetGame();
                    swingConsole.resetPanel();
                } else if (swingConsole.rematch == 2) {
                    gameRunning = false;
                }
            }
        }
        game.closeGame();
    }
}






