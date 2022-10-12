package model;

import java.time.LocalDate;

// represents an Event with different attributes
public class Event {

    String eventName;
    LocalDate eventDueDate;
    String eventDescription;
    Boolean isCompleted;

    //EFFECTS: eventName set to name, eventDueDate set to date, eventDescription set to description
    public Event(String name, String date, String description) {
        eventName = name;
        eventDueDate = LocalDate.parse(date);
        eventDescription = description;
        isCompleted = false;
    }

    //getters
    public String getEventName() {
        return eventName;
    }

    public LocalDate getEventDueDate() {
        return eventDueDate;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

    //setters
    public void changeEventName(String name) {
        eventName = name;
    }

    public void changeEventDueDate(String date) {
        eventDueDate = LocalDate.parse(date);
    }

    public void changeEventDescription(String description) {
        eventDescription = description;
    }

    //EFFECT: change status of isCompleted to true;
    public void completeEvent() {
        isCompleted = true;
    }

}
