package persistence.parsers;

import model.Bicycle;
import model.ParkingSpot;
import model.TheftReport;
import org.json.JSONObject;
import persistence.Saveable;

import java.time.LocalDate;

/*
Helper class used to parse a JSONObject as a theftReport (returned as a Saveable)
 */

public class TheftReportParser extends SaveableParser {
    BicycleParser bicycleParser = new BicycleParser();
    ParkingSpotParser parkingSpotParser = new ParkingSpotParser();

    // EFFECTS: Parses a JSONObject as a theftReport and returns the theftReport (as a Saveable)
    @Override
    public Saveable parseSaveable(JSONObject jsonObject) {
        Bicycle bicycle = (Bicycle) bicycleParser.parseSaveable(jsonObject.getJSONObject("bicycle"));
        ParkingSpot parkingSpot =
                (ParkingSpot) parkingSpotParser.parseSaveable(jsonObject.getJSONObject("parkingSpot"));

        int day = jsonObject.getInt("dateDay");
        int month = jsonObject.getInt("dateMonth");
        int year = jsonObject.getInt("dateYear");

        LocalDate date = LocalDate.of(year, month, day);
        return new TheftReport(bicycle, parkingSpot, date);
    }

}
