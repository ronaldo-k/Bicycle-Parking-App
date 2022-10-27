package persistence.parsers;

import org.json.JSONObject;
import persistence.Saveable;

/*
Abstract class from which parsers of JSON objects extend. This is intended to reduce repetition of parsers in code
related to reading data from JSON files.
 */

public abstract class SaveableParser {
    Saveable saveable;

    public abstract Saveable parseSaveable(JSONObject jsonObject);
}
