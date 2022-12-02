package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


// Class was adapted from EventLog class in AlarmSystem
// Represents a log of system events.

public class EventLog implements Iterable<EventForLogging> {

    // the only EventLog in the system (Singleton Design Pattern)
    private static EventLog theLog;
    private Collection<EventForLogging> events;

    // Prevent external construction.
    private EventLog() {
        events = new ArrayList<EventForLogging>();
    }


     // MODIFIES: this
     // EFFECTS: Gets instance of EventLog - creates it
     // if it doesn't already exist.
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }
        return theLog;
    }

    // MODIFIES: this
    // EFFECTS: Adds an event to the event log.
    public void logEvent(EventForLogging e) {
        events.add(e);
    }

    // MODIFIES: this
    // EFFECTS: Clears the event log and logs the event.
    public void clear() {
        events.clear();
        logEvent(new EventForLogging("Event log cleared."));
    }

    @Override
    public Iterator<EventForLogging> iterator() {
        return events.iterator();
    }
}
