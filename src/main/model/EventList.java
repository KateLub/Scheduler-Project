package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistance.Writable;

import java.util.ArrayList;
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
    }

    //MODIFIES: this
    //EFFECTS: if Event e not in List (events), adds e to list and returns true
    //         if e already in list, returns false and changes nothing
    public boolean addEventToList(Event e) {
        for (Event ev : events) {
            if (ev.equals(e)) {
                return false;
            }
        }
        events.add(e);
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
        return null;
    }

    //MODIFIES: this
    //EFFECTS: if findEvent method finds matching Event to eventName, event removed and returns true
    //         if findEvent method returns null (no event w/t that name) return false
    public boolean removeEventFromList(String eventName) {
        Event result = findEvent(eventName);
        if (result == null) {
            return false;
        } else {
            events.remove(result);
            return true;
        }
    }

    //MODIFIES: this
    //EFFECTS: sorts list in chronological order according to date of each Event
    public void sortEventList() {
        for (int i = 0; i < (events.size() - 1); i++) {
            Event e = events.get(i);
            Event e2 = events.get(i + 1);
            if (e.getEventDueDate().isAfter(e2.getEventDueDate())) {
                events.remove(e);
                events.add(e);
            }
        }
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

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray eventsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Event e : events) {
            jsonArray.put(e.toJson());
        }

        return jsonArray;
    }
}
