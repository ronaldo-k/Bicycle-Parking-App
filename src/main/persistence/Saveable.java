package persistence;

import org.json.*;

/*
Basic interface for anything that can be converted to and saved as a JSON object.
 */
public interface Saveable {
    JSONObject toJson();
}
