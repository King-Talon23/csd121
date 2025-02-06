package lab3.game;

public record winRecord(int XWins, int OWins) {
    private static winRecord gameScore = new winRecord(0, 0);

    public static winRecord callScore() {
        return gameScore;
    }

    public winRecord incrementXWins() {
        gameScore = new winRecord(this.XWins + 1, this.OWins);
        return gameScore;
    }

    public winRecord incrementOWins() {
        gameScore = new winRecord(this.XWins, this.OWins + 1);
        return gameScore;
    }
}


