package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class EventTest {

    Event e1;
    Event e2;
    Event e3;
    Event e4;

    @BeforeEach
    public void setup(){
        e1 = new Event("Com Sci Project", "2022-12-07", "create an app in Java");
        e2 = new Event("English Final Essay", "2022-11-11",
                "write an essay on the book \"A Hero of Our Time\" by Mikhail Lermontov");
        e3 = new Event("Math homework", "2022-10-28", "worksheet on derivatives");
        e4 = new Event("Maria's birthday", "2022-07-01", "M's birthday" +
                "at the Starbucks on Brentwood");
    }


    @Test
    public void testConstructor(){
        assertEquals(e1.getEventName(), "Com Sci Project" );
        assertEquals(e2.getEventDueDate(), LocalDate.parse("2022-11-11"));
        assertEquals(e3.getEventDescription(), "worksheet on derivatives");
        assertFalse(e4.getIsCompleted());
    }

    @Test
    public void testChangeEventName(){
        e1.changeEventName("Com Sci End-Of-Term Project");
        assertEquals(e1.getEventName(), "Com Sci End-Of-Term Project" );
        e1.changeEventName("CPSC 210 - Personal Java Project");
        assertEquals(e1.getEventName(), "CPSC 210 - Personal Java Project");

        assertEquals(e2.getEventName(), "English Final Essay");
    }

    @Test
    public void testChangeEventDueDate(){
        e2.changeEventDueDate("2021-12-11");
        assertEquals(e2.getEventDueDate(), LocalDate.parse("2021-12-11"));
        e2.changeEventDueDate("2022-12-11");
        assertEquals(e2.getEventDueDate(), LocalDate.parse("2022-12-11"));
    }

    @Test
    public void testChangeEventDescription(){
        e3.changeEventDescription("complete at least half of worksheet on derivatives, to go over w/t tutor");
        assertEquals(e3.getEventDescription(),
                "complete at least half of worksheet on derivatives, to go over w/t tutor");
    }

    @Test
    public void testCompleteEvent(){
        assertFalse(e1.getIsCompleted());
        assertFalse(e3.getIsCompleted());
        e3.completeEvent();
        assertTrue(e3.getIsCompleted());
        assertFalse(e4.getIsCompleted());
        assertFalse(e1.getIsCompleted());
    }
}