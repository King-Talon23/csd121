package lab3.ui;

import java.util.*;

public class Console {
    public static Integer currentPlayer = 1;
    static final List<String> availableCoords = new ArrayList<>();
    public static Map<String, String> tileMap = new HashMap<>();
    public static boolean endGame = false;
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
        if (endGame) {
            // this is here so the board gets displayed as full before the termination
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
            currentPlayer--;
        } else {
            currentPlayer = 1;
            throw new Exception("Unknown Player Turn (return to Player One)");
        }

        if (availableCoords.isEmpty()) {
            System.out.print("Tie Game!");
            endGame = true;
        }
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
