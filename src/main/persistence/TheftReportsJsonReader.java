package persistence;

import org.json.JSONObject;
import persistence.parsers.TheftReportParser;

/*
Reader class that reads a JSONArray and parses it as a List<TheftReport> (Returned as a List<Saveable>).
 */

public class TheftReportsJsonReader extends ArrayJsonReader {
    TheftReportParser theftReportParser = new TheftReportParser();

    // EFFECTS: Constructs a ParkingSpotsJsonReader that reads from the file named fileName.
    public TheftReportsJsonReader(String fileName) {
        super(fileName);
    }

    // EFFECTS: Parses a JSONObject into a TheftReport (Returned as a Saveable)
    @Override
    public Saveable parseSaveable(JSONObject jsonObject) {
        return theftReportParser.parseSaveable(jsonObject);
    }
}
