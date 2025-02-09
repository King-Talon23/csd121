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
                Board.addPoints(swingConsole.lastPlayerSymbol());
                swingConsole.displayWin();
                game.setRematch();
                Board.resetGame();
                game.resetPlayers();
                break;
            }

            if (board.isTie()) {
                swingConsole.displayTie();
                game.setRematch();
                Board.resetGame();
                game.resetPlayers();
                break;
            }
        }
    }

}






