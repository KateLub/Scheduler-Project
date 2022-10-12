package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class EventListTest {

    Event e1;
    Event e2;
    Event e3;
    Event e4;
    EventList list1;
    List<Event> testList;

    @BeforeEach
    public void setup(){
        e1 = new Event("Com Sci Project", "2022-12-07", "create an app in Java");
        e2 = new Event("English Final Essay", "2022-11-11",
                "write an essay on the book \"A Hero of Our Time\" by Mikhail Lermontov");
        e3 = new Event("Math homework", "2022-10-28", "worksheet on derivatives");
        e4 = new Event("Maria's birthday", "2022-07-01", "M's birthday" +
                "at the Starbucks on Brentwood");
        list1 = new EventList("Important School Dates and Others");
        testList = new ArrayList<>();
    }

    @Test
    public void testConstructor(){
        assertEquals(list1.getListName(), "Important School Dates and Others");
        list1.setListName("SchoolWork due Dates & Birthdays");
        assertEquals(list1.getListName(), "SchoolWork due Dates & Birthdays");
    }

    @Test
    public void testGetListSize(){
        assertEquals(list1.getListSize(), 0);
        list1.addEventToList(e1);
        assertEquals(list1.getListSize(), 1);

        list1.addEventToList(e1);
        list1.addEventToList(e2);
        list1.addEventToList(e3);
        assertEquals(list1.getListSize(), 3);
    }

    @Test
    public void testAddEvent(){
        assertTrue(list1.addEventToList(e1));
        testList.add(e1);
        assertEquals(list1.getEvents(), testList);
    }

    @Test
    public void testAddSameEventTwice(){
        assertTrue(list1.addEventToList(e1));
        testList.add(e1);
        assertEquals(list1.getEvents(), testList);

        assertTrue(list1.addEventToList(e2));
        testList.add(e2);
        assertEquals(list1.getEvents(), testList);

        assertFalse(list1.addEventToList(e1));
        assertEquals(list1.getEvents(), testList);
    }

    @Test
    public void testRemoveEventInList(){
        assertTrue(list1.addEventToList(e1));
        testList.add(e1);
        assertEquals(list1.getEvents(), testList);

        assertTrue(list1.addEventToList(e2));
        testList.add(e2);
        assertEquals(list1.getEvents(), testList);

        assertTrue(list1.removeEventFromList(e1.getEventName()));
        testList.remove(e1);
        assertEquals(list1.getEvents(), testList);
    }

    @Test
    public void testRemoveEventNotInList(){
        assertTrue(list1.addEventToList(e1));
        testList.add(e1);
        assertEquals(list1.getEvents(), testList);

        assertFalse(list1.removeEventFromList(e2.getEventName()));
        assertEquals(list1.getEvents(), testList);
    }

    @Test
    public void testFindEventNotInList(){
        assertTrue(list1.addEventToList(e1));
        testList.add(e1);
        assertEquals(list1.getEvents(), testList);

        assertNull(list1.findEvent(e2.getEventName()));
        assertEquals(list1.getEvents(), testList);
    }

    @Test
    public void testSortEventList(){
        assertTrue(list1.addEventToList(e1));
        assertTrue(list1.addEventToList(e4));
        assertTrue(list1.addEventToList(e3));
        assertTrue(list1.addEventToList(e2));
        testList.add(e1);
        testList.add(e4);
        testList.add(e3);
        testList.add(e2);
        assertEquals(list1.getEvents(), testList);

        List<Event> testList2 = new ArrayList<>();
        testList2.add(e4);
        testList2.add(e3);
        testList2.add(e2);
        testList2.add(e1);
        list1.sortEventList();
        assertEquals(list1.getEvents(),testList2);
    }

}
