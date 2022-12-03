package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistance.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// represents a list that has a name, and contains Events
public class EventList implements Writable {

    private String listName;
    private final List<Event> events;

    public EventList(String name) {
        listName = name;
        events = new ArrayList<>();
    }

    //getters
    public String getListName() {
        return listName;
    }

    public List<Event> getEvents() {
        return events;
    }

    public int getListSize() {
        return events.size();
    }

    //setters
    public void setListName(String name) {
        listName = name;
        EventLog.getInstance().logEvent(new EventForLogging("Event List name set to "
                + this.listName));
    }

    //MODIFIES: this
    //EFFECTS: if Event e not in List (events), adds e to list and returns true
    //         if e already in list, returns false and changes nothing
    public boolean addEventToList(Event e) {
        for (Event ev : events) {
            if (ev.equals(e)) {
                EventLog.getInstance().logEvent(new EventForLogging("Event " + e.getEventName()
                        + " was not added to list due to duplication error"));
                return false;
            }
        }
        events.add(e);
        EventLog.getInstance().logEvent(new EventForLogging("Event " + e.getEventName()
                + " was added to list"));
        return true;
    }

    //EFFECTS: if Event e with matching name to eventName is in List, returns e
    //         if e with matching name not found, returns null
    public Event findEvent(String eventName) {
        for (Event e : events) {
            if (e.getEventName().equals(eventName)) {
                return e;
            }
        }
        EventLog.getInstance().logEvent(new EventForLogging("Event " + eventName
                + " was searched for and not found in list"));
        return null;
    }

    //MODIFIES: this
    //EFFECTS: if findEvent method finds matching Event to eventName, event removed and returns true
    //         if findEvent method returns null (no event w/t that name) return false
    public boolean removeEventFromList(String eventName) {
        Event result = findEvent(eventName);
        if (result == null) {
            EventLog.getInstance().logEvent(new EventForLogging("Attempt to remove Event "
                    + eventName + " failed because it was not found"));
            return false;
        } else {
            events.remove(result);
            EventLog.getInstance().logEvent(new EventForLogging("Attempt to remove Event "
                    + eventName + " was successfull"));
            return true;
        }
    }

    //MODIFIES: this
    //EFFECTS: sorts list in chronological order according to date of each Event
    public void sortEventList() {
        Collections.sort(events, new Comparator<Event>() {
            @Override
            public int compare(Event e1, Event e2) {
                return e1.getEventDueDate().compareTo(e2.getEventDueDate());
            }
        });
    }

    // The following two methods were adapted from WorkRoom class in:
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("listName", listName);
        json.put("events", eventsToJson());
        return json;
    }

    // EFFECTS: returns things in this eventList as a JSON array
    private JSONArray eventsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Event e : events) {
            jsonArray.put(e.toJson());
        }
        return jsonArray;
    }
}
