package lab3;

import lab3.game.Board;
import lab3.ui.Console;

public class Main {
    public static void main(String[] args) {

        Board board = new Board();
        Console game = new Console(board);

        while (true) {
            game.displayBoard();
            game.move();


            if (board.checkWin()) {

                game.displayBoard();
                System.out.println("PLAYER " + game.getCurrentPlayer() + " HAS WON!");
                break;
            }

            if (board.isTie()) {
                game.displayBoard();
                System.out.println("Tie Game!");
                break;
            }

            game.switchPlayer();
        }
    }
}
