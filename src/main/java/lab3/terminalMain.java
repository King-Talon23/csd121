package lab3;

import lab3.game.Board;
import lab3.ui.terminalConsole;

/**
 * This is the main game loop for tic tac toe.
 * It initializes a game board and a terminal console interface.
 * It then alternates between each player continually prompting each to make a move.
 * When one player reaches a win condition, the game will end and both players will be prompted to play again.
 * If the players reach a tie, the game ends and both players are prompted to play again.
 * The game board is reset after each game is completed to allow for rematches
 */
public class terminalMain {
    /**
     * This is the main game loop for tic tac toe.
     * It initializes a game board and a terminal console interface.
     * It then alternates between each player continually prompting each to make a move.
     * When one player reaches a win condition, the game will end and both players will be prompted to play again.
     * If the players reach a tie, the game ends and both players are prompted to play again.
     * The game board is reset after each game is completed to allow for rematches
     */
    public static void main(String[] args) {

        Board board = new Board();
        terminalConsole game = new terminalConsole(board);

        while (true) {
            game.displayBoard();
            game.move();


            if (board.checkWin()) {
                Board.addPoints(Board.currentPlayerSymbol());
                game.displayBoard();
                terminalConsole.displayWin();
                if (game.rematch()) {
                    Board.resetGame();
                    Board.resetPlayers();
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
                    Board.resetPlayers();
                    continue;
                } else {
                    break;
                }
            }

            Board.switchPlayer();

        }
    }
}


