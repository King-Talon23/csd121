package lab3.game;

import java.util.*;

import static lab3.game.winRecord.callScore;
import static lab3.ui.swingConsole.currentPlayer;

public class Board {
    private static final Map<Coordinates, String> tileMap = new HashMap<>();
    private static final List<Coordinates> availableCoords = new ArrayList<>();


    /**
     * This class represents the board of a tic-tac-toe game
     */
    private static final List<lab3.game.winConditions> winConditions = List.of(
            new winConditions(Coordinates.A1, Coordinates.A2, Coordinates.A3),
            new winConditions(Coordinates.B1, Coordinates.B2, Coordinates.B3),
            new winConditions(Coordinates.C1, Coordinates.C2, Coordinates.C3),
            new winConditions(Coordinates.A1, Coordinates.B1, Coordinates.C1),
            new winConditions(Coordinates.A2, Coordinates.B2, Coordinates.C2),
            new winConditions(Coordinates.A3, Coordinates.B3, Coordinates.C3),
            new winConditions(Coordinates.A1, Coordinates.B2, Coordinates.C3),
            new winConditions(Coordinates.A3, Coordinates.B2, Coordinates.C1)
    );

    static {
        new winRecord(0, 0);
    }

    /**
     * Initializes a game board for tic-tac-toe
     * Consists of a tileMap which holds info on what areas are taken up by each player.
     * Also contains a list array that tracks each move available to be made
     */
    public Board() {
        for (Coordinates coord : Coordinates.values()) {

            tileMap.put(coord, " ");
            availableCoords.add(coord);
        }
    }

    /**
     *Updates the score based on which player has won the most recent round
     * @param player the player symbol of the player who won the round (either X or O)
     */
    public static void addPoints(String player) {
        if (Objects.equals(player, "X")) {
            callScore().gameWonX();
        } else {
            callScore().gameWonO();
        }
    }

    /**
     * Resets the game by clearing available coordinates and re-initializes the board
     * All tiles are set to empty and all coordinates are marked as available again.
     */
    public static void resetGame() {
        availableCoords.clear();

        for (Coordinates coord : Coordinates.values()) {

            tileMap.put(coord, " ");
            availableCoords.add(coord);
        }
    }

    /**
     * Checks if there is a winning condition on the board.
     * Player will win if they have matched three of their symbols in a row (Horizontal, Vertical, Diagonal)
     * @return true if player has met a win condition.
     */
    public boolean checkWin() {

        for (lab3.game.winConditions wc : winConditions) {

            Coordinates cond1 = wc.coord1();
            Coordinates cond2 = wc.coord2();
            Coordinates cond3 = wc.coord3();

            if (tileMap.get(cond1).equals(tileMap.get(cond2)) &&
                    tileMap.get(cond2).equals(tileMap.get(cond3)) &&
                    !tileMap.get(cond1).equals(" ")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks the available coordinates list to see if there are no more moves to make.
     * @return true if no moves available.
     */
    public boolean isTie() {
        return availableCoords.isEmpty();
        // no need to check for win since ties are checked for after wins
    }

    /**
     * Places a player's move on the board.
     *
     *
     * @param move The coordinate the player has inputted for their move
     * @return true if move is successful, false if the move has already been taken
     */
    public boolean setMove(Coordinates move) {
        if (!availableCoords.contains(move)) {
            return false;
        }
        tileMap.put(move, currentPlayerSymbol());
        availableCoords.remove(move);
        return true;
    }

    /**
     * Sets the current player to  1
     */
    public static void resetPlayers() {
        currentPlayer = 1;
    }

    /**
     * Switches which is the current player to play their next turn
     */
    public static void switchPlayer() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
    }

    /**
     * checks for the current players symbol (either X or O)
     * @return a string containing either 'X' or 'O'
     */
    public static String currentPlayerSymbol() {
        return currentPlayer == 1 ? "X" : "O";
    }



    /**
     *
     * @return Returns a string representation of the board
     */
    @Override
    public String toString() {
        return String.format(
                """
                - |__1__|__2__|__3__| \s
                A |  %s  |  %s  |  %s  |
                - | --- + --- + --- +
                B |  %s  |  %s  |  %s  |
                - | --- + --- + --- +
                C |  %s  |  %s  |  %s  |
                - | --- + --- + --- +
               \s""",
                tileMap.get(Coordinates.A1), tileMap.get(Coordinates.A2), tileMap.get(Coordinates.A3),
                tileMap.get(Coordinates.B1), tileMap.get(Coordinates.B2), tileMap.get(Coordinates.B3),
                tileMap.get(Coordinates.C1), tileMap.get(Coordinates.C2), tileMap.get(Coordinates.C3)
        );
    }
}
