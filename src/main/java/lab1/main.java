package lab1;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;


public class main extends JFrame {

    private static JFrame window;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new main();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static JLabel[][] labels;
    static Random rand = new Random();
    // tracking each column
    private static final List<Integer> rightfar = new ArrayList<>();
    private static final List<Integer> rightclose = new ArrayList<>();
    private static final List<Integer> leftfar = new ArrayList<>();
    private static final List<Integer> leftclose = new ArrayList<>();
    private static final List<Integer> gutters = new ArrayList<>();
    private static final List<Integer> guttersLeft = new ArrayList<>();

    private static final List<String> gutterBallPhrases = new ArrayList<>();
    private static final List<Integer> scoreList = new ArrayList<>();
    private static final List<Integer> pinLocations = new ArrayList<>();
    private static final List<Integer> notAllowPositions = new ArrayList<>();
    public static int mainCounter = 0;
    public static int rowDown = 0;
    public static int gameNumber = 1;
    public static int ballNumber = 1;
    public static Boolean gutterBall = false;
    public static Boolean strike = false;
    static ImageIcon bowlingPin = new ImageIcon("src/main/java/lab1/pictures/bowling_pin.png");
    static ImageIcon bowlingBall = new ImageIcon("src/main/java/lab1/pictures/bowling_ball.png");

    static int finalScore = 0;
    public static int position = 59;
    public static final int totalPositions = 62;

    public main() throws IOException {
        fillLists();
        window = new JFrame("Bowling Simulator 3000");
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.setSize(400, 800);
        window.setResizable(false);
        // custom BackgroundPanel
        BackgroundPanel bowlingAlleyPanel = new BackgroundPanel("src/main/java/lab1/pictures/bowling_alley.png");
        bowlingAlleyPanel.setLayout(new GridLayout(9, 7, 0, 0));
        labels = new JLabel[9][7];

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 7; col++) {
                labels[row][col] = new JLabel("", SwingConstants.CENTER);
                labels[row][col].setPreferredSize(new Dimension(10, 10));
                bowlingAlleyPanel.add(labels[row][col]);
            }
        }

        for (int n = 0; n < 10; n++) {
            changeCellImage(pinLocations.get(n), bowlingPin);
        }

        changeCellText(1, "Game:" + gameNumber);
        changeCellText(3, "throw:" + ballNumber);
        changeCellText(5, "Total Score:" + finalScore);

        changeCellImage(59, bowlingBall);

        JPanel buttonPanel = getJPanel();


        window.add(bowlingAlleyPanel, BorderLayout.CENTER);
        window.add(buttonPanel, BorderLayout.SOUTH);

        window.setSize(700, 900);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

    }

    private static JPanel getJPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton leftButton = new JButton("←");
        JButton rightButton = new JButton("→");
        JButton shootButton = new JButton("BOWL!!!");

        // move ball position left
        leftButton.addActionListener(_ -> {
            if (position > (totalPositions - 6)) {
                adjustPosition("left");
                updateBallPosition();
            }
        });

        // move ball position right
        rightButton.addActionListener(_ -> {
            if (position < totalPositions) {
                adjustPosition("right");
                updateBallPosition();
            }
        });

        shootButton.addActionListener(_ -> {
            final int[] count = {0};
            if (ballNumber <= 2) {
                ballNumber += 1;
                buttonPanel.remove(leftButton);
                buttonPanel.remove(rightButton);
                buttonPanel.remove(shootButton);
                buttonPanel.revalidate();

                Timer timer = new Timer(500, e -> {
                    curveLogic(); //this is what moves the ball mathematically
                    if (gutterBall) {
                        gutterBall = false;
                        resetPosition();
                    } else {
                        updateBallPosition();
                    }
                    pinHitCheck();

                    mainCounter++;
                    if (mainCounter > 9) {
                        ((Timer) e.getSource()).stop();
                        if (ballNumber > 2 || strike) {
                            /*
                            since the rest of the code still runs while the pin animations go and thread.sleep()
                            cannot be used with the Swing UI i'm mimicking the largest pin animations
                            time (strike) + 250 milliseconds here to stop any potential issues with the pins being
                            restarted during the animation. This does make the flow of the game kinda janky.
                             */
                            Timer delayTimer = new Timer(500, i -> {
                                count[0]++;
                                // the count is more of a backup for the other two conditions
                                if (count[0]==4) {
                                    try {
                                        resetGame();
                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                    count[0] = 0;
                                    buttonPanel.add(leftButton);
                                    buttonPanel.add(shootButton);
                                    buttonPanel.add(rightButton);
                                    buttonPanel.revalidate();
                                    buttonPanel.repaint();
                                    ((Timer) i.getSource()).stop();
                                }
                            });
                            delayTimer.setInitialDelay(500);
                            delayTimer.start();
                        } else {
                            //simulate delay like above to the largest delay (+ 250 milliseconds) possible without striking
                            Timer delayTimer = new Timer(500, i -> {
                                count[0]++;
                                if (count[0] == 3) {
                                    count[0] = 0;
                                    buttonPanel.add(leftButton);
                                    buttonPanel.add(shootButton);
                                    buttonPanel.add(rightButton);
                                    buttonPanel.revalidate();
                                    buttonPanel.repaint();
                                    resetPosition();
                                    ((Timer) i.getSource()).stop();
                                }
                            });
                            delayTimer.setInitialDelay(500);
                            delayTimer.start();
                            mainCounter = 0;
                        }
                    }
                });

                timer.setRepeats(true);
                timer.setInitialDelay(0);
                timer.start();
            }
        });

        buttonPanel.add(leftButton);
        buttonPanel.add(shootButton);
        buttonPanel.add(rightButton);
        return buttonPanel;
    }

    public static void resetPosition() {
        position = 59;
        updateBallPosition();
        changeCellText(3, "throw:" + ballNumber);
    }

    public static void resetGame() throws IOException {
        ballNumber = 1;
        gameNumber += 1;
        mainCounter = 0;
        pinLocations.removeAll(List.of(100)); //remove placeholder
        finalScore += (10 - pinLocations.size());
        scoreList.add(finalScore);
        strike = false;

        changeCellText(1, "Game:" + gameNumber);
        changeCellText(5, "Total Score:" + finalScore);
        pinLocations.clear();
        int pinNum = 2;
        for (int n = 0; n <= 24; n += pinNum) {
            pinLocations.add(n);
            if (n == 12) {
                pinNum += 2;
            } else if (n == 16) {
                pinNum -= 2;
            } else if (n == 18) {
                pinNum += 4;
            }
        }
        for (int n = 0; n < 10; n++) {
            changeCellImage(pinLocations.get(n), bowlingPin);
        }
        resetPosition();

        if (gameNumber > 10) {
            try {
                FileWriter myWriter = new FileWriter("src/main/java/lab1/score.txt");
                myWriter.write("your scores for each game and final score were: " + scoreList);
                myWriter.close();
                window.dispose();
            } catch (IOException e) {
                throw new IOException("A problem occurred while saving your  score");
            }
        }
    }

    //---------------------------------------------------| LANE MOVING LOGIC |----------------------------------------------
    public static void curveLogic() {
        int min = 33;
        int max = 100;
        int chanceToCurve = rand.nextInt((max - min) + 1) + min; // 33-100
        int chanceToStraight = rand.nextInt(100) + 1; // 1-100, reducing the straight chance
        int curveCheck = rand.nextInt(100) + 1;
        int variation = rand.nextInt(10) + 1; // 1-10

        if (gutters.contains(position)) {
            // column 1 and 7 (next to gutters)
            handleGutter(chanceToCurve, curveCheck, variation);
        } else if (leftfar.contains(position)) {
            // column 2
            handleLanePosition("leftfar", chanceToCurve, chanceToStraight, curveCheck, variation);
        } else if (leftclose.contains(position)) {
            // column 3
            handleLanePosition("leftclose", chanceToCurve, chanceToStraight, curveCheck, variation);
        } else if (rightclose.contains(position)) {
            // column 5
            handleLanePosition("rightclose", chanceToCurve, chanceToStraight, curveCheck, variation);
        } else if (rightfar.contains(position)) {
            // column 6
            handleLanePosition("leftfar", chanceToCurve, chanceToStraight, curveCheck, variation);
        } else { // column 4
            handleMiddleLane(chanceToStraight, variation);
        }
    }

    private static void handleGutter(int chanceToCurve, int curveCheck, int variation) {
        if (shouldFallIntoGutter(chanceToCurve, curveCheck, variation)) {
            applyGutterPenalty();
        } else {
            if (guttersLeft.contains(position)) {
                if (chanceToCurve > curveCheck) {
                    adjustPosition("upright");
                } else {
                    adjustPosition("up");
                }
            } else { // right side gutter
                if (chanceToCurve > curveCheck) {
                    adjustPosition("upleft");
                } else {
                    adjustPosition("up");
                }
            }
        }
    }

    private static boolean shouldFallIntoGutter(int chanceToCurve, int curveChance, int variation) {
        return (chanceToCurve + variation) < curveChance;
    }

    private static void applyGutterPenalty() {
        changeCellImage(position, null);
        mainCounter = 9;
        gutterBall = true;
        int phrase = rand.nextInt(gutterBallPhrases.size());
        System.out.println(gutterBallPhrases.get(phrase));
    }

    private static void handleLanePosition(String lane, int chanceToCurve, int straightChance, int curveChance, int variation) {
        switch (lane) {
            // far lanes are right before the gutter lanes and have a higher chance to go away from the gutter direction
            case "leftfar", "rightfar" -> {
                // away from gutter
                if ((chanceToCurve + variation) > curveChance) {
                    if (lane.equals("leftfar")) {
                        adjustPosition("upright");
                    } else {
                        adjustPosition("upleft");
                    }
                } else if (straightChance > curveChance) {
                    adjustPosition("up");
                } else {
                    //gutter direction
                    if (lane.equals("leftfar")) {
                        adjustPosition("upleft");
                    } else {
                        adjustPosition("upright");
                    }
                }
            }
            // close lanes are the lanes beside the middle and have increased chance to go towards the gutter direction
            case "leftclose", "rightclose" -> {

                if (chanceToCurve > (curveChance + variation)) {
                    if (lane.equals("leftclose")) {
                        adjustPosition("upright");
                    } else {
                        adjustPosition("upleft");
                    }
                } else if (straightChance > curveChance) {
                    adjustPosition("up");
                } else {
                    if (lane.equals("leftclose")) {
                        adjustPosition("upleft");
                    } else {
                        adjustPosition("upright");
                    }
                }
            }
        }
    }

    private static void handleMiddleLane(int straightChance, int variation) {
        int min = 10;
        int max = 100;
        int leftCurveChance = rand.nextInt((max - min) + 1) + min;
        int rightCurveChance = rand.nextInt((max - min) + 1) + min;
        if (position == 30 || position == 32) {
            //reduce chance to strike from tiles diagonal from front pin
            straightChance -= 15;
        }
        if (leftCurveChance > rightCurveChance) {
            if ((leftCurveChance + variation) > straightChance) {
                adjustPosition("upleft");
            } else {
                adjustPosition("up");
            }
        } else if (rightCurveChance > leftCurveChance) {
            if ((rightCurveChance + variation) > straightChance) {
                adjustPosition("upright");
            } else {
                adjustPosition("up");
            }
        } else {
            adjustPosition("up");
        }
    }

// ---------------------------------------------------------------------------------------------------------------------

    public static void pinHitCheck() {
        /*
        This controls the cascade effect the pins have when it, it also resets the ball position after a pin is hit
        and removes the hit pins from the pinLocation
         */
        //Thread.sleep does not work with the Swing UI
        if (pinLocations.contains(position)) {
            mainCounter = 9;
            rowDown = 0;
            int delay = 500; // milliseconds
            switch (position) {
                //left side first row
                case 0 -> {
                    changeCellImage(position, null);
                    //remove value and not index so second shot doesnt break game
                    pinLocations.removeAll(List.of(0));
                }
                //right side first row
                case 6 -> {
                    changeCellImage(position, null);
                    pinLocations.removeAll(List.of(6));
                }
                //left side second row
                case 8 -> {
                    Timer timer1 = new Timer(delay, e -> {
                        switch (rowDown) {

                            case 0 -> changeCellImage(position, null);

                            case 1 -> {
                                changeCellImage(0, null);
                                changeCellImage(2, null);
                            }
                            case 2 -> ((Timer) e.getSource()).stop();


                        }
                        rowDown++;
                    });
                    timer1.setInitialDelay(250);
                    timer1.start();
                    pinLocations.removeAll(Arrays.asList(0, 2, 8));
                }
                //right side second row
                case 12 -> {
                    Timer timer2 = new Timer(delay, e -> {
                        switch (rowDown) {

                            case 0 -> changeCellImage(position, null);

                            case 1 -> {
                                changeCellImage(4, null);
                                changeCellImage(6, null);
                            }
                            case 2 -> ((Timer) e.getSource()).stop();

                        }
                        rowDown++;
                    });
                    timer2.setInitialDelay(250);
                    timer2.start();
                    pinLocations.removeAll(Arrays.asList(4, 6, 12));
                }
                // left third row
                case 16 -> {
                    Timer timer3 = new Timer(delay, e -> {
                        switch (rowDown) {

                            case 0 -> changeCellImage(position, null);

                            case 1 -> {
                                changeCellImage(8, null);
                                changeCellImage(10, null);
                            }
                            case 2 -> {
                                changeCellImage(0, null);
                                changeCellImage(2, null);
                                changeCellImage(4, null);
                            }
                            case 3 -> ((Timer) e.getSource()).stop();


                        }
                        rowDown++;
                    });
                    timer3.setInitialDelay(250);
                    timer3.start();
                    pinLocations.removeAll(Arrays.asList(0, 2, 4, 8, 10, 16));
                }
                //right side third row
                case 18 -> {
                    Timer timer4 = new Timer(delay, e -> {
                        switch (rowDown) {

                            case 0 -> changeCellImage(position, null);

                            case 1 -> {
                                changeCellImage(10, null);
                                changeCellImage(12, null);
                            }
                            case 2 -> {
                                changeCellImage(2, null);
                                changeCellImage(4, null);
                                changeCellImage(6, null);
                            }
                            case 3 -> ((Timer) e.getSource()).stop();
                        }
                        rowDown++;
                    });
                    timer4.setInitialDelay(250);
                    timer4.start();
                    pinLocations.removeAll(Arrays.asList(2, 4, 6, 10, 12, 18));
                }
                // strike
                case 24 -> {
                    strike = true;
                    Timer timer5 = new Timer(delay, e -> {
                        switch (rowDown) {

                            case 0 -> changeCellImage(position, null);

                            case 1 -> {
                                changeCellImage(16, null);
                                changeCellImage(18, null);
                            }
                            case 2 -> {
                                changeCellImage(12, null);
                                changeCellImage(10, null);
                                changeCellImage(8, null);
                            }

                            case 3 -> {
                                changeCellImage(6, null);
                                changeCellImage(4, null);
                                changeCellImage(2, null);
                                changeCellImage(0, null);

                            }
                            case 4 -> ((Timer) e.getSource()).stop();

                        }
                        rowDown++;
                    });
                    timer5.setInitialDelay(250);
                    timer5.start();
                    pinLocations.clear();
                }
            }
        }
    }

    public static void updateBallPosition() {
        /*
        this originally only cleared the past position but there were issues with the ball image not being
        cleared sometimes so it's being brute forced now
         */

        int i = 7;

        while (i != 63) {
            if (!pinLocations.contains(i) && i != position) {
                changeCellImage(i, null);
            }
            i++;
        }
        changeCellImage(position, bowlingBall);
    }

    public static void adjustPosition(String direction) {
        int oldPosition = position;
        try {
            switch (direction) {
                // x axis
                case "left" -> position -= 1;
                case "right" -> position += 1;
                // y axis
                case "upleft" -> position -= 8;
                case "upright" -> position -= 6;
                case "up" -> position -= 7;
                case null, default -> throw new IllegalArgumentException("Not a valid direction");

            }
            if (notAllowPositions.contains(position)) {
                if (leftclose.contains(position) || leftfar.contains(position)) {
                    adjustPosition("left");
                } else if (rightclose.contains(position) || rightfar.contains(position)) {
                    adjustPosition("right");
                } else { // middle
                    if (leftclose.contains(oldPosition)) {
                        adjustPosition("left");
                    } else {
                        adjustPosition("right");
                    }

                }
            }

        } catch (Exception e) {
            throw new ArrayIndexOutOfBoundsException("You scared the ball and it run off screen.");
        }
    }

    public static void changeCellImage(int cell, Icon newIcon) {
        int cols = 7;
        int totalCells = 63;
        if (cell < 0 || cell >= totalCells) {
            /* after some pins have been removed the ball might go past all pins to the top
            this is what catching those
             */
            position = 59;
            gutterBall = true;
        } else {
            int row = cell / cols;
            int col = cell % cols;

            labels[row][col].setIcon(newIcon);
        }
    }

    public static void changeCellText(int cell, String text) {
        int cols = 7;
        int totalCells = 63;
        if (cell < 0 || cell >= totalCells) {
            throw new IllegalArgumentException("Cell number is out of bounds.");
        }
        int row = cell / cols;
        int col = cell % cols;

        labels[row][col].setText(text);
    }

    public static void fillLists() {
        // column 1
        for (int n = 0; n <= 63; n += 7) {
            guttersLeft.add(n);
            gutters.add(n);
        }

        // columns 2 and 3
        for (int n = 1; n <= 63; n += 7) {
            leftfar.add(n);
            leftclose.add(n + 1);
        }

        // column 4 doesn't need to be tracked since its the only one not tracked it can be inferred

        // columns 5 and 6
        for (int n = 4; n <= 63; n += 7) {
            rightclose.add(n);
            rightfar.add(n + 1);
        }

        // column 7
        for (int n = 6; n <= 63; n += 7) {
            gutters.add(n);
        }

        // setting locations for pins
        int pinNum = 2;
        for (int n = 0; n <= 24; n += pinNum) {
            pinLocations.add(n);
            if (n == 12) {
                pinNum += 2;
            } else if (n == 16) {
                pinNum -= 2;
            } else if (n == 18) {
                pinNum += 4;
            }
        }
        // These are the spaces between the pins tht we dont want to ball going to
        int emptySpace = 2;
        for (int n = 1; n <= 17; n += emptySpace) {
            notAllowPositions.add(n);
            if (n == 5) {
                emptySpace += 2;
            } else if (n == 9) {
                emptySpace -= 2;
            } else if (n == 11) {
                emptySpace += 4;
            }
        }

        //display messages for gutter balls
        gutterBallPhrases.add("WHO HIT A GUTTER BALL?? YOU HIT A GUTTER BALL!!!");
        gutterBallPhrases.add("Today's looking like a good day to be a pin.");
        gutterBallPhrases.add("Who needs the pins anyway?");
        gutterBallPhrases.add("uhh, you were aiming for the edge, right?");
        gutterBallPhrases.add("I’ve seen more accurate throws from a toddler playing catch.");
        gutterBallPhrases.add("I've seen better bowling from my wheelchair-bound grandma.");
        gutterBallPhrases.add("Some people are good at bowling, you're not \"some people\"");
        gutterBallPhrases.add("Next time, try to aim for the pins... or at least the lane?");
        gutterBallPhrases.add("With gutters like these who needs strikes?");
        gutterBallPhrases.add("Somewhere out there, you just made a bowling pin smile.");
        gutterBallPhrases.add("I think that ball hates you. Maybe you should try golf instead?");
        gutterBallPhrases.add("It’s not a gutter ball. It’s an *artistic* representation of failure.");
        gutterBallPhrases.add("I think you missed the memo: You’re supposed to hit the pins!");
        gutterBallPhrases.add("Gutter ball: Where your dreams go to die.");
        gutterBallPhrases.add("you must be (g)utterly disappointed right now");
        gutterBallPhrases.add("If we scored this like golf, you’d be a pro!");
        gutterBallPhrases.add("Bowling tip #1: Pins are not in the gutter.");
        gutterBallPhrases.add("Congratulations! You’ve mastered the art of gutter balls.");
        gutterBallPhrases.add("Forget the pins, at least the gutter likes you!");
        gutterBallPhrases.add("Bowling pro tip: Pins are typically located in the lane, not the gutters.");

    }


}


