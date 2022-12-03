package model;

import java.util.Calendar;
import java.util.Date;


// Class was adapted from Event class in: AlarmSystem
// Represents a system event.

public class EventForLogging {
    private static final int HASH_CONSTANT = 13;
    private Date dateLogged;
    private String description;

    // Creates an event with the given description
    // and the current date/time stamp.
    public EventForLogging(String description) {
        dateLogged = Calendar.getInstance().getTime();
        this.description = description;
    }

    //EFFECTS: returns date and time of this event for logging
    public Date getDate() {
        return dateLogged;
    }

    //EFFECTS: returns description of this event for logging
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        EventForLogging otherEvent = (EventForLogging) other;

        return (this.dateLogged.equals(otherEvent.dateLogged)
                && this.description.equals(otherEvent.description));
    }

    @Override
    public int hashCode() {
        return (HASH_CONSTANT * dateLogged.hashCode() + description.hashCode());
    }

    @Override
    public String toString() {
        return dateLogged.toString() + "\n" + description;
    }
}

