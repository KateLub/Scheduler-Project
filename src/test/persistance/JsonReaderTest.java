package persistance;

import model.Event;
import model.EventList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Methods and comments were adapted from JsonReaderTest class in:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            EventList el = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyEventList() {
        JsonReader reader = new JsonReader("./data/testEmptyEventList.json");
        try {
            EventList el = reader.read();
            assertEquals("UBC", el.getListName());
            assertEquals(0, el.getListSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralEventList() {
        JsonReader reader = new JsonReader("./data/testGeneralEventList.json");
        try {
            EventList el = reader.read();
            assertEquals("UBC", el.getListName());
            List<Event> events = el.getEvents();
            assertEquals(3, events.size());
            checkEvent("midterm", "2021-12-01", "sdf", events.get(0));
            checkEvent("midterm1", "2022-12-01", "sfg", events.get(1));
            checkEvent("project", "2022-11-06", "sfdkn", events.get(2));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
