package persistence;

import org.json.JSONObject;
import persistence.parsers.ParkingSpotParser;

/*
Reader class that reads a JSONArray and parses it as a List<ParkingSpot> (Returned as a List<Saveable>).
 */

public class ParkingSpotsJsonReader extends ArrayJsonReader {
    ParkingSpotParser parkingSpotParser = new ParkingSpotParser();

    // EFFECTS: Constructs a ParkingSpotsJsonReader that reads from the file named fileName.
    public ParkingSpotsJsonReader(String fileName) {
        super(fileName);
    }

    // EFFECTS: Parses a JSONObject into a ParkingSpot (Returned as a Saveable)
    @Override
    public Saveable parseSaveable(JSONObject jsonObject) {
        return parkingSpotParser.parseSaveable(jsonObject);
    }


}
