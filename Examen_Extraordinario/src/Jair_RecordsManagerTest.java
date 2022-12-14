import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
//import org.testng.annotations.Test;

public class Jair_RecordsManagerTest {

    private static final int MAX_RECORDS_IN_FILE = 5;

    private final Path fileCreated = Paths.get("test.rec");

    @AfterEach
    public void cleanup() throws IOException {
        Files.deleteIfExists(fileCreated);
    }

    @Test
    public void givenAnEmptyRecord_whenSave_thenRecordIsSavedSuccessfully() {
        // Given:
        JairRecordsManager recordsManager = new JairRecordsManager("test.rec", MAX_RECORDS_IN_FILE);
        GameRecord record = new GameRecord();

        record.setPlayerName("Ivan");
        record.setScore(30);

        // When:
        recordsManager.save(record);
        List<GameRecord> records = recordsManager.getRecords();

        // Then:

        Assertions.assertTrue(Files.exists(fileCreated));
        Assertions.assertEquals(1, records.size());

        GameRecord stored = records.get(0);

        Assertions.assertEquals("Ivan", stored.getPlayerName());
        Assertions.assertEquals(30, stored.getScore());

    }

    @Test
    public void givenAnExistentRecord_whenSave_thenRecordIsSavedSuccessfully() {
        // Given:
        JairRecordsManager recordsManager = new JairRecordsManager("test.rec", MAX_RECORDS_IN_FILE);

        recordsManager.save(new GameRecord("Ivan", 30));
        recordsManager.save(new GameRecord("Israel", 20));
        recordsManager.save(new GameRecord("Juan", 50));

        // When:
        List<GameRecord> records = recordsManager.getRecords();

        // Then:

        Assertions.assertTrue(Files.exists(fileCreated));
        Assertions.assertEquals(3, records.size());

        Iterator<GameRecord> iterator = records.iterator();

        validateGameRecord(iterator, "Juan", 50);
        validateGameRecord(iterator, "Ivan", 30);
        validateGameRecord(iterator, "Israel", 20);
    }

    @Test
    public void givenAnFullRecord_whenSave_thenRecordIsReplacedSuccessfully() {
        // Given:
        JairRecordsManager recordsManager = new JairRecordsManager("test.rec", MAX_RECORDS_IN_FILE);

        recordsManager.save(new GameRecord("Ivan", 30));
        recordsManager.save(new GameRecord("Israel", 20));
        recordsManager.save(new GameRecord("Juan", 50));
        recordsManager.save(new GameRecord("Lupe", 80));
        recordsManager.save(new GameRecord("Iris", 10));

        // When:
        recordsManager.save(new GameRecord("Maria", 5));
        recordsManager.save(new GameRecord("Mario", 40));

        // Then:
        List<GameRecord> records = recordsManager.getRecords();
        Assertions.assertTrue(Files.exists(fileCreated));
        Assertions.assertEquals(MAX_RECORDS_IN_FILE, records.size());

        Iterator<GameRecord> iterator = records.iterator();

        validateGameRecord(iterator, "Lupe", 80);
        validateGameRecord(iterator, "Juan", 50);
        validateGameRecord(iterator, "Mario", 40);
        validateGameRecord(iterator, "Ivan", 30);
        validateGameRecord(iterator, "Israel", 20);
    }

    private void validateGameRecord(Iterator<GameRecord> iterator, String playerName, int score) {
        GameRecord record;

        Assertions.assertTrue(iterator.hasNext());

        record = iterator.next();

        Assertions.assertEquals(playerName, record.getPlayerName());
        Assertions.assertEquals(score, record.getScore());
    }

}
