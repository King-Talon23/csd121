package lab3.ui;

import lab3.game.Board;
import lab3.game.Coordinates;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Scanner;

import static lab3.game.winRecord.callScore;

public class swingConsole {
    private final Board board;
    private int currentPlayer = 1;
    private static JFrame window;
    private static JLabel mainText;

    public swingConsole(Board board) {
        this.board = board;
    }

    public static void printToUI(String message) {
        mainText.setText(message);
    }

    public static JPanel getTextJLabel() {
        JPanel textPanel = new JPanel(new FlowLayout());
        mainText = new JLabel("");
        textPanel.add(mainText);
        return textPanel;
    }

    public void createWindow() {
        window = new JFrame("Tic-Tac-Toe Simulator 3000");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(300, 200);

        JPanel buttonPanel = getButtonJPanel();
        JPanel textPanel = getTextJLabel();


        window.add(buttonPanel, BorderLayout.CENTER);
        window.add(textPanel, BorderLayout.SOUTH);


        window.setVisible(true);
    }

    private void handleButtonPress(JButton button, String coordName) {
        try {
            Coordinates coord = Coordinates.valueOf(coordName);
            String playerSymbol = (currentPlayer == 1 ? "X" : "O");

            if (board.setMove(coord, playerSymbol)) {
                swapButtonWithLabel(button, playerSymbol);
            } else {
                printToUI("Tile not available. Please enter new coordinates.");
            }
        } catch (IllegalArgumentException e) {
            printToUI("Invalid coordinate. Please enter a valid move (e.g., A1, B2, C3).");
        }
        switchPlayer();
    }

    private static void swapButtonWithLabel(JButton button, String text) {
        JPanel parent = (JPanel) button.getParent();
        parent.remove(button);

        JLabel label = new JLabel(text, SwingConstants.CENTER);
        parent.add(label);

        parent.revalidate();
        parent.repaint();
    }

    public JPanel getButtonJPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(3, 3));
        JButton a1 = new JButton("");
        a1.setBorder(new LineBorder(Color.BLACK));
        JButton a2 = new JButton("");
        a2.setBorder(new LineBorder(Color.BLACK));
        JButton a3 = new JButton("");
        a3.setBorder(new LineBorder(Color.BLACK));

        JButton b1 = new JButton("");
        b1.setBorder(new LineBorder(Color.BLACK));
        JButton b2 = new JButton("");
        b2.setBorder(new LineBorder(Color.BLACK));
        JButton b3 = new JButton("");
        b3.setBorder(new LineBorder(Color.BLACK));

        JButton c1 = new JButton("");
        c1.setBorder(new LineBorder(Color.BLACK));
        JButton c2 = new JButton("");
        c2.setBorder(new LineBorder(Color.BLACK));
        JButton c3 = new JButton("");
        c3.setBorder(new LineBorder(Color.BLACK));


        a1.addActionListener(_ -> handleButtonPress(a1, "a1"));
        a2.addActionListener(_ -> handleButtonPress(a2, "a2"));
        a3.addActionListener(_ -> handleButtonPress(a3, "a3"));

        b1.addActionListener(_ -> handleButtonPress(b1, "b1"));
        b2.addActionListener(_ -> handleButtonPress(b2, "b2"));
        b3.addActionListener(_ -> handleButtonPress(b3, "b3"));

        c1.addActionListener(_ -> handleButtonPress(c1, "c1"));
        c2.addActionListener(_ -> handleButtonPress(c2, "c2"));
        c3.addActionListener(_ -> handleButtonPress(c3, "c3"));

        buttonPanel.add(a1);
        buttonPanel.add(a2);
        buttonPanel.add(a3);
        buttonPanel.add(b1);
        buttonPanel.add(b2);
        buttonPanel.add(b3);
        buttonPanel.add(c1);
        buttonPanel.add(c2);
        buttonPanel.add(c3);
        return buttonPanel;
    }


    public static void displayWin(Integer winningPlayer) {
        String winnerName;
        if (winningPlayer == 1) {
            winnerName = "X";
        } else {
            winnerName = "O";
        }
        printToUI("PLAYER " + winnerName + " HAS WON");
        printToUI(String.format("THE SCORE IS NOW %s (X) VS %s (O)", callScore().XWins(), callScore().OWins()));
    }

    public static void displayTie() {
        printToUI("TIE GAME, NO ONE WINS!");
        printToUI(String.format("THE SCORE REMAINS %s (X) VS %s (O)", callScore().XWins(), callScore().OWins()));
    }

    public void closeGame() {
        window.dispose();
    }



    public boolean rematch() {
        Scanner scanner = new Scanner(System.in);
        printToUI("Do both players wish to play again? (y/n)");
        while (true) {
            printToUI("Player X?");
            String playerX = scanner.nextLine().replaceAll(" ", "").toLowerCase();
            if (!playerX.equals("y") && !playerX.equals("n")) {
                printToUI("please enter Y or N to continue.");
            } else {
                while (true) {
                    printToUI("Player O?");
                    String playerO = scanner.nextLine().replaceAll(" ", "").toLowerCase();
                    if (!playerO.equals("y") && !playerO.equals("n")) {
                        printToUI("please enter Y or N to continue.");
                    } else {
                        return playerX.equals("y") && playerO.equals("y");
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
            printToUI("Player " + currentPlayer + ": Please Select a Square: ");
            String input = scanner.nextLine().replaceAll(" ", "").toUpperCase();

            try {
                Coordinates coord = Coordinates.valueOf(input);
                if (board.setMove(coord, currentPlayer == 1 ? "X" : "O")) {
                    return;
                } else {
                    printToUI("Tile not available. Please enter new coordinates.");
                }
            } catch (IllegalArgumentException e) {
                printToUI("Invalid coordinate. Please enter a valid move (e.g., A1, B2, C3).");
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