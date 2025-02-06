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


    //new stuff ------------------------------------------------------
    public static void displayWin(Integer player, Integer winScore) {
        String displayName;
        if (player == 1) {
            displayName = "ONE";
        } else {
            displayName = "TWO";
        }
        println("PLAYER " + displayName + " HAS WON");
        println("PLAYER " + displayName + "S SCORE IS " + winScore);

    }

    public static void displayTie() {
        println("TIE GAME!!!");
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
                        if (playerX.equals("y") && playerO.equals("y")) {
                            return true;
                        }
                        else {
                            return false;
                        }

                    }
                }
            }
        }
    }

    public void resetPlayers(Integer player) {
        if (player != 1) {
            currentPlayer = 1;
        }
    }
    //---------------------
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