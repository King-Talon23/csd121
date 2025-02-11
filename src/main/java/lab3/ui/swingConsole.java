package lab3.ui;

import lab3.game.Board;
import lab3.game.Coordinates;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;


import static lab3.game.Board.currentPlayerSymbol;
import static lab3.game.winRecord.callScore;

public class swingConsole {
    /**
     * This is Not Fully-Functional
     */
    private static Board board = new Board();
    public static int currentPlayer = 1;
    private static JFrame window;
    private static JLabel mainText;
    private static final JPanel buttonPanel = new JPanel();
    public static Integer rematch = 0; // 0 = placeholder, 1 = rematch, 2 = game end
    private static final Font labelFont = new Font("Ariel", Font.PLAIN, 200);

    public swingConsole(Board board) {
        swingConsole.board = board;
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
        window = new JFrame("Tic-Tac-Toe-inator 3000");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH); // this is what makes it maximize on start
        window.setSize(500, 600);

        fillButtonPanel();
        JPanel textPanel = getTextJLabel();

        window.add(buttonPanel, BorderLayout.CENTER);
        window.add(textPanel, BorderLayout.SOUTH);

        window.setVisible(true);
    }

    public static void clearPanel() {
        buttonPanel.removeAll();
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    public static void displayWin() {
        printToUI("PLAYER " + lastPlayerSymbol() + " HAS WON " +
                String.format("THE SCORE IS NOW %s (X) VS %s (O)", callScore().XWins(), callScore().OWins()));
        sleep(1500);
    }

    public static void displayTie() {
        printToUI("TIE GAME, NO ONE WINS! " +
                String.format("THE SCORE REMAINS %s (X) VS %s (O)", callScore().XWins(), callScore().OWins()));
        sleep(1500);
    }

    public void closeGame() {
        window.dispose();
    }

    public static void resetPanel() {
        clearPanel();
        currentPlayer = 1;
        fillButtonPanel();
        printToUI("");
        rematch = 0;
    }

    public void setRematch() {
        clearPanel();
        printToUI("Do both players wish to play again?");
        buttonPanel.setLayout(new GridLayout(1, 2));
        JButton yes = new JButton("");
        yes.setBorder(new LineBorder(Color.BLACK));
        yes.addActionListener(e -> rematch = 1);
        yes.setText("Yes");

        JButton no = new JButton("");
        no.setBorder(new LineBorder(Color.BLACK));
        no.addActionListener(e -> rematch = 2);
        no.setText("No");

        buttonPanel.add(yes);
        buttonPanel.add(no);
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    private static JButton createTileButton(String name) {
        JButton button = new JButton("");
        button.setBorder(new LineBorder(Color.BLACK));
        button.addActionListener(e -> handleButtonPress(button, name));

        return button;
    }

    public static void handleButtonPress(JButton button, String coordName) {
        swapButtonWithLabel(button, currentPlayerSymbol());
        Coordinates coord = Coordinates.valueOf(coordName);
        board.setMove(coord);
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
    }

    public static void swapButtonWithLabel(JButton button, String text) {
        JPanel parent = (JPanel) button.getParent();

        // Get the index of the button to remember where to put label
        int index = parent.getComponentZOrder(button);
        parent.remove(button);
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(labelFont);
        // put the label back at the same place of the button
        parent.add(label, index);
        parent.revalidate();
        parent.repaint();
    }


    public static void fillButtonPanel() {
        buttonPanel.setLayout(new GridLayout(3, 3));
        JButton a1 = createTileButton("A1");
        JButton a2 = createTileButton("A2");
        JButton a3 = createTileButton("A3");
        JButton b1 = createTileButton("B1");
        JButton b2 = createTileButton("B2");
        JButton b3 = createTileButton("B3");
        JButton c1 = createTileButton("C1");
        JButton c2 = createTileButton("C2");
        JButton c3 = createTileButton("C3");

        buttonPanel.add(a1);
        buttonPanel.add(a2);
        buttonPanel.add(a3);
        buttonPanel.add(b1);
        buttonPanel.add(b2);
        buttonPanel.add(b3);
        buttonPanel.add(c1);
        buttonPanel.add(c2);
        buttonPanel.add(c3);
    }

    public static String lastPlayerSymbol() {
        //since the player value it switched on button press we need the last player value for the winner
        return currentPlayer == 1 ? "O" : "X";
    }

    public static void sleep(Integer milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}