package lab3.ui;

import java.util.Scanner;

public class Console {
    public static String a1 = " ";
    public static String a2 = " ";
    public static String a3 = " ";
    public static String b1 = " ";
    public static String b2 = " ";
    public static String b3 = " ";
    public static String c1 = " ";
    public static String c2 = " ";
    public static String c3 = " ";
    public static Integer currentPlayer = 1;
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
                        """, a1, a2, a3, b1, b2, b3, c1, c2, c3);
        System.out.printf(boardString);
    }

    public static void move() throws Exception {
        Scanner scanner = new Scanner(System.in);

        if (currentPlayer == 1){
            while (true) {
                System.out.print("Please Select a Square: ");
                String input = scanner.nextLine();

                //removing spaces and making letters lowercase so verifying should be easier
                input = input.replaceAll(" ", "").toLowerCase();

                System.out.println("You selected: " + input);

                break;
            }
            currentPlayer++;

        } else if (currentPlayer == 2){
            while (true) {
                System.out.print("Please Select a Square: ");
                String input = scanner.nextLine();
                input = input.replaceAll(" ", "").toLowerCase();

                System.out.println("You selected: " + input);

                break;
            }
            currentPlayer--;
        } else {
            currentPlayer = 1;
            throw new Exception("Unknown Player Turn (return to Player One)");
        }
        scanner.close();
    }

    public static boolean checkInput(String userInput) {
        switch (userInput) {
            case "a1" -> {
                return true;
            }
            case "a2" -> {
                return true;
            }
            case "a3" -> {
                return true;
            }
        }
        return false;
    }
    // TODO: create a CLASS method to display a tictactoe board

    // TODO: create a CLASS method to get the next move from a player
}
