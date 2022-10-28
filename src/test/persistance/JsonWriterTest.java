package persistance;

import model.Event;
import model.EventList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Methods and comments were adapted from JsonWriterTest class in:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriterTest extends JsonTest{

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyEventList() {
        try {
            EventList el = new EventList("UBC");
            JsonWriter writer = new JsonWriter("./data/testEmptyEventList.json");
            writer.open();
            writer.write(el);
            writer.close();

            JsonReader reader = new JsonReader("./data/testEmptyEventList.json");
            el = reader.read();
            assertEquals("UBC", el.getListName());
            assertEquals(0, el.getListSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralEventList() {
        try {
            EventList el = new EventList("UBC");
            el.addEventToList(new Event("midterm", "2021-12-01", "sdf"));
            el.addEventToList(new Event("midterm1", "2022-12-01", "sfg"));
            el.addEventToList(new Event("project", "2022-11-06", "sfdkn"));

            JsonWriter writer = new JsonWriter("./data/testGeneralEventList.json");
            writer.open();
            writer.write(el);
            writer.close();

            JsonReader reader = new JsonReader("./data/testGeneralEventList.json");
            el = reader.read();
            assertEquals("UBC", el.getListName());

            assertEquals(3, el.getListSize());
            checkEvent("midterm", "2021-12-01", "sdf", el.getEvents().get(0));
            checkEvent("midterm1", "2022-12-01", "sfg", el.getEvents().get(1));
            checkEvent("project", "2022-11-06", "sfdkn", el.getEvents().get(2));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
