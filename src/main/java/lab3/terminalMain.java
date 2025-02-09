package lab3;

import lab3.game.Board;
import lab3.ui.terminalConsole;


public class terminalMain {
    public static void main(String[] args) {

        Board board = new Board();
        terminalConsole game = new terminalConsole(board);

        while (true) {
            game.displayBoard();
            game.move();


            if (board.checkWin()) {
                Board.addPoints(terminalConsole.currentPlayerSymbol());
                game.displayBoard();
                terminalConsole.displayWin();
                if (game.rematch()) {
                    Board.resetGame();
                    game.resetPlayers();
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
                    game.resetPlayers();
                    continue;
                } else {
                    break;
                }
            }

            game.switchPlayer();

        }
    }
}


