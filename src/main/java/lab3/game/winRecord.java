package lab3.game;

/**
 * A record that keeps tracks of both players wins over multiple games
 *
 * @param XWins The number of Player X's wins
 * @param OWins The number of Player O's wins
 */
public record winRecord(int XWins, int OWins) {
    private static winRecord gameScore = new winRecord(0, 0);


    /**
     * Retrieves the current game score
     *
     * @return the current winRecord
     */
    public static winRecord callScore() {
        return gameScore;
    }

    /**
     * Adds a point for X to the games score
     */
    public void gameWonX() {
        gameScore = new winRecord(this.XWins + 1, this.OWins);
    }

    /**
     * Adds a point for O to the games score
     */
    public void gameWonO() {
        gameScore = new winRecord(this.XWins,this.OWins + 1);
    }
}


