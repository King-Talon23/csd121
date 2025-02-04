package lab3.ui;

import lab3.game.wins.*;

import java.util.*;

public class Console {
    public static Integer currentPlayer = 1;
    static final List<String> availableCoords = new ArrayList<>();
    public static Map<String, String> tileMap = new HashMap<>();
    public static boolean tieGame = false;
    public static boolean playerOneWin = false;
    public static boolean playerTwoWin = false;
    private static final List<winCondition> winConditions = List.of(
            new winCondition("a1", "a2", "a3"),
            new winCondition("b1", "b2", "b3"),
            new winCondition("c1", "c2", "c3"),
            new winCondition("a1", "b1", "c1"),
            new winCondition("a2", "b2", "c2"),
            new winCondition("a3", "b3", "c3"),
            new winCondition("a1", "b2", "c3"),
            new winCondition("a3", "b2", "c1")
    );

    /*
    I think the way im handling the win condition might be able to be done better, same with the game terminations in
    the method below can probably be improved. I think the hashmap and the way im handling the board is the best, or
    atleast pretty high up there on the list of way to do it. Some of these need to get moved to the game package and be
     called in the main file.
     */

    public static void boardDisplay() {
        String boardString = String.format(
                """
                        - |__1__|__2__|__3__| _ \s
                        A |  %1$s  |  %2$s  |  %3$s  |
                        - | --- + --- + --- + -
                        B |  %4$s  |  %5$s  |  %6$s  |
                        - | --- + --- + --- + -
                        C |  %7$s  |  %8$s  |  %9$s  |
                        - | --- + --- + --- + -
                        """, tileMap.get("a1"), tileMap.get("a2"), tileMap.get("a3"), tileMap.get("b1"),
                tileMap.get("b2"), tileMap.get("b3"), tileMap.get("c1"), tileMap.get("c2"), tileMap.get("c3"));
        System.out.printf(boardString);

        // these are here so the board gets displayed before the termination
        if (playerOneWin) {
            System.out.print("PLAYER ONE HAS WON");
            System.exit(0);

        }
        if (playerTwoWin) {
            System.out.print("PLAYER TWO HAS WON");
            System.exit(0);

        }
        if (tieGame) {
            System.out.print("Tie Game!);");
            System.exit(0);
        }
    }

    public static void fillCoordList() {
        availableCoords.add("a1");
        availableCoords.add("a2");
        availableCoords.add("a3");
        availableCoords.add("b1");
        availableCoords.add("b2");
        availableCoords.add("b3");
        availableCoords.add("c1");
        availableCoords.add("c2");
        availableCoords.add("c3");
    }

    public static void fillHashMap() {
        tileMap.put("a1", " ");
        tileMap.put("a2", " ");
        tileMap.put("a3", " ");
        tileMap.put("b1", " ");
        tileMap.put("b2", " ");
        tileMap.put("b3", " ");
        tileMap.put("c1", " ");
        tileMap.put("c2", " ");
        tileMap.put("c3", " ");
    }


    public static void move() throws Exception {
        Scanner scanner = new Scanner(System.in);

        if (currentPlayer == 1) {
            while (true) {
                System.out.print("Player One: Please Select a Square: ");
                String input = scanner.nextLine();
                input = input.replaceAll(" ", "").toLowerCase();
                if (checkInput(input)) {
                    availableCoords.remove(input);
                    setMove(input, "X");
                    break;
                } else {
                    System.out.print("Tile not available. Please enter new coordinates\n" +
                            "e.x: a1(top left), b2(middle), c3(bottom right)");
                }
            }
            if (checkWin()) {
                playerOneWin = true;
            }
            currentPlayer++;
        } else if (currentPlayer == 2) {
            while (true) {
                System.out.print("Player Two: Please Select a Square: ");
                String input = scanner.nextLine();
                input = input.replaceAll(" ", "").toLowerCase();
                if (checkInput(input)) {
                    availableCoords.remove(input);
                    setMove(input, "O");
                    break;
                } else {
                    System.out.println("\nTile not available. Please enter new coordinates\n" +
                            "e.x: a1 -> top left, b2 -> middle, c3 -> bottom right");
                }
            }
            if (checkWin()) {
                playerTwoWin = true;
            }
            currentPlayer--;
        } else {
            currentPlayer = 1;
            throw new Exception("Unknown Player Turn (return to Player One)");
        }

        if (availableCoords.isEmpty()) {
            tieGame = true;
        }
    }

    public static boolean checkWin() {
        for (winCondition wc : winConditions) {
            if (tileMap.get(wc.coord1()).equals(tileMap.get(wc.coord2())) &&
                    tileMap.get(wc.coord2()).equals(tileMap.get(wc.coord3())) &&
                    !tileMap.get(wc.coord1()).equals(" ")) {
                return true;
            }
        }
        return false;
    }


    // i'm like 98% sure these go into the game package but i'm just keeping everything in here for now
    public static boolean checkInput(String userInput) {
        return availableCoords.contains(userInput);
    }

    public static void setMove(String move, String value) {
        tileMap.put(move, value);
    }


    // TODO: create a CLASS method to display a tictactoe board

    // TODO: create a CLASS method to get the next move from a player
}
