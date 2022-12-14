public class GameRecord {
    private String playerName;
    private  int score;

    public GameRecord()
    {

    }

    public GameRecord(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    public void setPlayerName(String playerName) {
        this.playerName=playerName;
    }

    public void setScore(int score) {
        this.score=score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }
}
