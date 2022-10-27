package model;

import org.json.JSONObject;
import persistance.Writable;

import java.time.LocalDate;

// represents an Event with an event name, due-date, description, and whether it is completed or not
public class Event implements Writable {

    String eventName;
    LocalDate eventDueDate;
    String eventDescription;
    Boolean isCompleted;

    //REQUIRES: String date must be in format "YYYY-MM-DD"
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
    //MODIFIES: this
    //EFFECT: change eventName to given name
    public void changeEventName(String name) {
        eventName = name;
    }

    //REQUIRES: String date passed in by user MUST be in format "YYYY-MM-DD"
    //MODIFIES: this
    //EFFECT: change eventDueDate to given date
    public void changeEventDueDate(String date) {
        eventDueDate = LocalDate.parse(date);
    }

    //MODIFIES: this
    //EFFECT: change eventDescription to given description
    public void changeEventDescription(String description) {
        eventDescription = description;
    }

    //MODIFIES: this
    //EFFECT: change status of isCompleted to true
    public void completeEvent() {
        isCompleted = true;
    }

    // The following method was adapted from Thingy class in:
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("eventName", eventName);
        json.put("dueDate", eventDueDate);
        json.put("eventDescription", eventDescription);
        return json;
    }
}
