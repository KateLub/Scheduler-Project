package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


// Class was adapted from EventLogTest class in: AlarmSystem
public class EventLogTest {
    EventForLogging e1;
    EventForLogging e2;
    EventForLogging e3;
    EventLog el;
	
	@BeforeEach
	public void setUp() {
		e1 = new EventForLogging("test1");
		e2 = new EventForLogging("test1");
		e3 = new EventForLogging("test1");
        el = EventLog.getInstance();

		el.logEvent(e1);
		el.logEvent(e2);
		el.logEvent(e3);
    }
	
	@Test
	public void testLoggingEvent() {
		List<EventForLogging> testList = new ArrayList<>();

		for (EventForLogging eventForLogging : el) {
			testList.add(eventForLogging);
		}
		
		assertTrue(testList.contains(e1));
		assertTrue(testList.contains(e2));
		assertTrue(testList.contains(e3));
	}

	@Test
	public void testClearLog() {
		el.clear();
		Iterator<EventForLogging> itr = el.iterator();
		assertTrue(itr.hasNext());
		assertEquals("Event log cleared.", itr.next().getDescription());
		assertFalse(itr.hasNext());
	}
}
