package persistance;

import model.Event;
import model.EventList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Methods and comments were adapted from JsonReader class in:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Represents a reader that reads EventList from JSON data stored in file
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from myFile
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads EventList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public EventList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);

        return parseEventList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses event list from JSON object and returns it
    private EventList parseEventList(JSONObject jsonObject) {
        String listName = jsonObject.getString("listName");
        EventList el = new EventList(listName);
        addAllEventsFromList(el, jsonObject);
        return el;
    }

    // MODIFIES: el
    // EFFECTS: parses events from JSON object and adds them to event list
    private void addAllEventsFromList(EventList el, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("events");
        for (Object json : jsonArray) {
            JSONObject nextEvent = (JSONObject) json;
            addEachEventFromList(el, nextEvent);
        }
    }

    // MODIFIES: el
    // EFFECTS: parses event from JSON object and adds them to event list
    private void addEachEventFromList(EventList el, JSONObject jsonObject) {
        String eventName = jsonObject.getString("eventName");
        String eventDueDate = jsonObject.getString("dueDate");
        String eventDescription = jsonObject.getString("eventDescription");
        Event event = new Event(eventName, eventDueDate, eventDescription);
        el.addEventToList(event);
    }
}
