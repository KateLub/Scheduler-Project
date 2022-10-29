package persistance;

import model.Event;
import model.EventList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// This test class was adapted from JsonReaderTest class in:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReadFromFileTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReadFromFile reader = new JsonReadFromFile("./data/noSuchFile.json");
        try {
            EventList el = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyEventList() {
        JsonReadFromFile reader = new JsonReadFromFile("./data/testEmptyEventList.json");
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
        JsonReadFromFile reader = new JsonReadFromFile("./data/testGeneralEventList.json");
        try {
            EventList el = reader.read();
            assertEquals("UBC", el.getListName());
            List<Event> events = el.getEvents();
            assertEquals(3, events.size());
            checkEvent("midterm", "2021-12-01", "sdf", true, events.get(0));
            checkEvent("midterm1", "2022-12-01", "sfg", false, events.get(1));
            checkEvent("project", "2022-11-06", "sfdkn", true, events.get(2));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
