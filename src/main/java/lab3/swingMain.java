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

            if (board.checkWin()) {
                swingConsole.displayWin(game.getCurrentPlayer());
                game.setRematch();
                Board.resetGame();
                game.resetPlayers(game.getCurrentPlayer());
                break;
            }

            if (board.isTie()) {
                swingConsole.displayTie();
                game.setRematch();
                Board.resetGame();
                game.resetPlayers(game.getCurrentPlayer());
                break;
            }

            game.switchPlayer();
        }
    }

}






