package persistence;

import model.Bicycle;
import model.Cyclist;
import model.TheftReport;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.ArrayJsonReader;
import persistence.Saveable;
import persistence.parsers.BicycleParser;
import persistence.parsers.TheftReportParser;

import java.util.ArrayList;
import java.util.List;

public class CyclistsJsonReader extends ArrayJsonReader {
    BicycleParser bicycleParser = new BicycleParser();
    TheftReportParser theftReportParser = new TheftReportParser();

    // EFFECTS: Builds a UserJsonReader as described in ArrayJsonReader
    public CyclistsJsonReader(String fileName) {
        super(fileName);
    }

    // EFFECTS: Parses a JSONObject into a Cyclist.
    @Override
    public Saveable parseSaveable(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        List<Bicycle> bicycles = parseBicycles(jsonObject.getJSONArray("bicycles"));
        List<TheftReport> theftReports = parseTheftReports(jsonObject.getJSONArray("theftReports"));
        return new Cyclist(name, bicycles, theftReports);
    }

    private List<Bicycle> parseBicycles(JSONArray jsonArray) {
        List<Bicycle> result = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            result.add((Bicycle) bicycleParser.parseSaveable(jsonArray.getJSONObject(i)));
        }
        return result;
    }

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
