package lab3.ui;

import lab3.game.Board;
import lab3.game.Coordinates;
import java.util.Scanner;

public class Console {
    private final Board board;
    private int currentPlayer = 1;

    public Console(Board board) {
        this.board = board;
    }

    public void displayBoard() {
        System.out.println(board);
    }
    public static void println(String message) {
        System.out.println(message);
    }

    public void move() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            println("Player " + currentPlayer + ": Please Select a Square: ");
            String input = scanner.nextLine().replaceAll(" ", "").toUpperCase();

            try {
                Coordinates coord = Coordinates.valueOf(input);
                if (board.setMove(coord, currentPlayer == 1 ? "X" : "O")) {
                    return;
                } else {
                    println("Tile not available. Please enter new coordinates.");
                }
            } catch (IllegalArgumentException e) {
                println("Invalid coordinate. Please enter a valid move (e.g., A1, B2, C3).");
            }
        }
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }
}
