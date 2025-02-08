package lab3;

import lab3.game.Board;
import lab3.ui.terminalConsole;

import static lab3.game.winRecord.callScore;

public class terminalMain {
    public static void main(String[] args) {

        Board board = new Board();
        terminalConsole game = new terminalConsole(board);

        while (true) {
            game.displayBoard();
            game.move();


            if (board.checkWin()) {
                game.displayBoard();
                terminalConsole.displayWin(game.getCurrentPlayer());
                if (game.rematch()) {
                    Board.resetGame();
                    game.resetPlayers(game.getCurrentPlayer());
                    continue;
                } else {
                    break;
                }
            }
            if (board.isTie()) {
                game.displayBoard();
                terminalConsole.displayTie();

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


