package lab3.game;

import java.util.*;

import static lab3.game.winRecord.callScore;

public class Board {
    private final Map<Coordinates, String> tileMap = new HashMap<>();
    private final List<Coordinates> availableCoords = new ArrayList<>();

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

    private static final winRecord winRecord = new winRecord(0, 0);

    public Board() {
        for (Coordinates coord : Coordinates.values()) {

            tileMap.put(coord, " ");
            availableCoords.add(coord);
        }
    }
//new stuff------------------------
    public int getWinScore(Integer player) {
        int currentWinnerScore;
        if (player == 1) {
            callScore().incrementXWins();
            currentWinnerScore = callScore().XWins();
        } else {
            callScore().incrementOWins();
            currentWinnerScore = callScore().OWins();
        }
        return currentWinnerScore;
    }
//--------------------------------

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

    // Check if the game is a tie
    public boolean isTie() {
        return availableCoords.isEmpty();
        // no need to check for win since ties are checked for after wins
    }

    //Prompt a move on the board
    public boolean setMove(Coordinates move, String playerSymbol) {
        if (!availableCoords.contains(move)) {
            return false;
        }
        tileMap.put(move, playerSymbol);
        availableCoords.remove(move);
        return true;
    }



    // Display the board
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
