package persistance;

import model.Event;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

// This test class was adapted from JsonTest class in:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonTest {

    protected void checkEvent(String eventName, String eventDueDate, String description, Boolean isCompleted, Event event) {
        assertEquals(eventName, event.getEventName());
        assertEquals(LocalDate.parse(eventDueDate), event.getEventDueDate());
        assertEquals(description, event.getEventDescription());
        assertEquals(isCompleted, event.getIsCompleted());
    }
}
