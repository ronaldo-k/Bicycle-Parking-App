package persistence.parsers;

import org.json.JSONObject;
import persistence.Saveable;

/*
Abstract class from which JSONObject parsers extend. This is intended to reduce repetition of parsers in code
related to reading data from JSON files.
 */

public abstract class SaveableParser {
    // EFFECTS: Parses JSONObject as a Saveable and returns the Saveable.
    public abstract Saveable parseSaveable(JSONObject jsonObject);
}
