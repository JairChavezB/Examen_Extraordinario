import java.util.LinkedList;
import java.util.List;

public class JairRecordsManager {
    private static final int MAX_RECORDS_IN_FILE = 5;
    private String text;
    private int score;
    private List<GameRecord> gameRecords = new LinkedList<>();
    public JairRecordsManager(String text, int score) {
        this.text=text;
        this.score=score;
    }

    public void save(GameRecord gameRecord) {
        if(gameRecords.size()<MAX_RECORDS_IN_FILE)
            gameRecords.add(gameRecord);
    }

    public List<GameRecord> getRecords(){
        return gameRecords;
    }
}
