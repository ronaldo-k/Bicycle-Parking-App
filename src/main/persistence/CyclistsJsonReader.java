package persistence;

import model.Bicycle;
import model.Cyclist;
import model.TheftReport;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.parsers.BicycleParser;
import persistence.parsers.TheftReportParser;

import java.util.ArrayList;
import java.util.List;

/*
Reader class that reads a JSONArray and parses it as a List<Cyclists> (Returned as a List<Saveable>).
 */

public class CyclistsJsonReader extends ArrayJsonReader {
    BicycleParser bicycleParser = new BicycleParser();
    TheftReportParser theftReportParser = new TheftReportParser();

    // EFFECTS: Builds a UserJsonReader as described in ArrayJsonReader.
    public CyclistsJsonReader(String fileName) {
        super(fileName);
    }

    // EFFECTS: Parses a JSONObject into a Cyclist (Returned as a Saveable).
    @Override
    public Saveable parseSaveable(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        List<Bicycle> bicycles = parseBicycles(jsonObject.getJSONArray("bicycles"));
        List<TheftReport> theftReports = parseTheftReports(jsonObject.getJSONArray("theftReports"));
        return new Cyclist(name, bicycles, theftReports);
    }

    // EFFECTS: Parses a JSONArray of bicycles and returns it as a List<Bicycle>.
    private List<Bicycle> parseBicycles(JSONArray jsonArray) {
        List<Bicycle> result = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            result.add((Bicycle) bicycleParser.parseSaveable(jsonArray.getJSONObject(i)));
        }
        return result;
    }

    // EFFECTS: Parses a JSONArray of theftReports and returns it as a List<TheftReport>.
    private List<TheftReport> parseTheftReports(JSONArray jsonArray) {
        List<TheftReport> result = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject j = jsonArray.getJSONObject(i);
            Saveable l = theftReportParser.parseSaveable(j);
            result.add((TheftReport) l);
        }
        return result;
    }

}
