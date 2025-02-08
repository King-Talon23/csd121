package lab3;

import lab3.game.Board;
import lab3.ui.swingConsole;

import javax.swing.*;
import java.awt.*;

public class swingMain {
    public static void main(String[] args) {
        Board board = new Board();
        swingConsole game = new swingConsole(board);
        game.createWindow();

        while (true) {
            game.move();


            if (board.checkWin()) {
                swingConsole.displayWin(game.getCurrentPlayer());
                if (game.rematch()) {
                    Board.resetGame();
                    game.resetPlayers(game.getCurrentPlayer());
                    continue;
                } else {
                    break;
                }
            }

            if (board.isTie()) {
                swingConsole.displayTie();

                if (game.rematch()) {
                    Board.resetGame();
                    game.resetPlayers(game.getCurrentPlayer());
                    continue;
                } else {
                    break;
                }
            }

            game.switchPlayer();

        }

    }
}


