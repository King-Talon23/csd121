package lab3.ui;

import lab3.game.Board;
import lab3.game.Coordinates;

import java.util.Scanner;

import static lab3.game.winRecord.callScore;

public class terminalConsole {
    private final Board board;
    private static int currentPlayer = 1;

    public terminalConsole(Board board) {
        this.board = board;
    }

    public void displayBoard() {
        System.out.println(board);
    }

    public static void println(String message) {
        System.out.println(message);
    }


    public static void displayWin() {
        println("PLAYER " + currentPlayerSymbol() + " HAS WON");
        println(String.format("THE SCORE IS NOW %s (X) VS %s (O)", callScore().XWins(), callScore().OWins()));
    }

    public static void displayTie() {
        println("TIE GAME, NO ONE WINS!");
        println(String.format("THE SCORE REMAINS %s (X) VS %s (O)", callScore().XWins(), callScore().OWins()));
    }

    public boolean rematch() {
        Scanner scanner = new Scanner(System.in);
        println("Do both players wish to play again? (y/n)");
        while (true) {
            println("Player X?");
            String playerX = scanner.nextLine().replaceAll(" ", "").toLowerCase();
            if (!playerX.equals("y") && !playerX.equals("n")) {
                println("please enter Y or N to continue.");
            } else {
                while (true) {
                    println("Player O?");
                    String playerO = scanner.nextLine().replaceAll(" ", "").toLowerCase();
                    if (!playerO.equals("y") && !playerO.equals("n")) {
                        println("please enter Y or N to continue.");
                    } else {
                        return playerX.equals("y") && playerO.equals("y");
                    }
                }
            }
        }
    }

    public void move() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            println("Player " + currentPlayerSymbol() + ": Please Select a Square: ");
            String input = scanner.nextLine().replaceAll(" ", "").toUpperCase();

            try {
                Coordinates coord = Coordinates.valueOf(input);
                if (board.setMove(coord, currentPlayerSymbol())) {
                    return;
                } else {
                    println("Tile not available. Please enter new coordinates.");
                }
            } catch (IllegalArgumentException e) {
                println("Invalid coordinate. Please enter a valid move (e.g., A1, B2, C3).");
            }
        }
    }

    public void resetPlayers() {
        currentPlayer = 1;
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
    }

    public static String currentPlayerSymbol() {
        return currentPlayer == 1 ? "X" : "O";
    }

}
