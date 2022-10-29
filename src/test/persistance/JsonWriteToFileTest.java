package persistance;

import model.Event;
import model.EventList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// This test class was adapted from JsonWriterTest class in:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriteToFileTest extends JsonTest{

    @Test
    void testWriteAnInvalidFile() {
        try {
            JsonWriteToFile writer = new JsonWriteToFile("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriteWithAnEmptyEventList() {
        try {
            EventList el = new EventList("UBC");
            JsonWriteToFile writer = new JsonWriteToFile("./data/testEmptyEventList.json");
            writer.open();
            writer.write(el);
            writer.close();

            JsonReadFromFile reader = new JsonReadFromFile("./data/testEmptyEventList.json");
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
            Event e1 = new Event("midterm", "2021-12-01", "sdf");
            e1.completeEvent();
            Event e2 = new Event("midterm1", "2022-12-01", "sfg");
            Event e3 = new Event("project", "2022-11-06", "sfdkn");
            e3.completeEvent();
            el.addEventToList(e1);
            el.addEventToList(e2);
            el.addEventToList(e3);

            JsonWriteToFile writer = new JsonWriteToFile("./data/testGeneralEventList.json");
            writer.open();
            writer.write(el);
            writer.close();

            JsonReadFromFile reader = new JsonReadFromFile("./data/testGeneralEventList.json");
            el = reader.read();
            assertEquals("UBC", el.getListName());

            assertEquals(3, el.getListSize());
            checkEvent("midterm", "2021-12-01", "sdf", true, el.getEvents().get(0));
            checkEvent("midterm1", "2022-12-01", "sfg", false, el.getEvents().get(1));
            checkEvent("project", "2022-11-06", "sfdkn", true, el.getEvents().get(2));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
