package lab3.game;

import java.util.*;

public class Board {
    private final Map<Coordinates, String> tileMap = new HashMap<>();
    private final List<Coordinates> availableCoords = new ArrayList<>();

    private static final List<wins.winCondition> winConditions = List.of(
            new wins.winCondition(Coordinates.A1, Coordinates.A2, Coordinates.A3),
            new wins.winCondition(Coordinates.B1, Coordinates.B2, Coordinates.B3),
            new wins.winCondition(Coordinates.C1, Coordinates.C2, Coordinates.C3),
            new wins.winCondition(Coordinates.A1, Coordinates.B1, Coordinates.C1),
            new wins.winCondition(Coordinates.A2, Coordinates.B2, Coordinates.C2),
            new wins.winCondition(Coordinates.A3, Coordinates.B3, Coordinates.C3),
            new wins.winCondition(Coordinates.A1, Coordinates.B2, Coordinates.C3),
            new wins.winCondition(Coordinates.A3, Coordinates.B2, Coordinates.C1)
    );


    public Board() {

        for (Coordinates coord : Coordinates.values()) {

            tileMap.put(coord, " ");
            availableCoords.add(coord);
        }
    }


    public boolean checkWin() {

        for (wins.winCondition wc : winConditions) {

            Coordinates c1 = wc.coord1();
            Coordinates c2 = wc.coord2();
            Coordinates c3 = wc.coord3();

            if (tileMap.get(c1).equals(tileMap.get(c2)) &&
                    tileMap.get(c2).equals(tileMap.get(c3)) &&
                    !tileMap.get(c1).equals(" ")) {
                return true;
            }
        }
        return false;
    }

    // Check if the game is a tie
    public boolean isTie() {
        return availableCoords.isEmpty() && !checkWin();
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
    public void displayBoard() {
        System.out.printf(
                """
                - |__1__|__2__|__3__|  
                A |  %s  |  %s  |  %s  |
                - | --- + --- + --- +
                B |  %s  |  %s  |  %s  |
                - | --- + --- + --- +
                C |  %s  |  %s  |  %s  |
                - | --- + --- + --- +
                """,
                tileMap.get(Coordinates.A1), tileMap.get(Coordinates.A2), tileMap.get(Coordinates.A3),
                tileMap.get(Coordinates.B1), tileMap.get(Coordinates.B2), tileMap.get(Coordinates.B3),
                tileMap.get(Coordinates.C1), tileMap.get(Coordinates.C2), tileMap.get(Coordinates.C3)
        );
    }
}
