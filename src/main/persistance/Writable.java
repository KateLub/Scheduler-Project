package persistance;

import org.json.JSONObject;

// This class was taken from Writable interface in:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
