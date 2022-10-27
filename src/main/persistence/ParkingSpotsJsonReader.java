package persistence;

import org.json.JSONObject;
import persistence.parsers.ParkingSpotParser;

public class ParkingSpotsJsonReader extends ArrayJsonReader {
    ParkingSpotParser parkingSpotParser = new ParkingSpotParser();

    // EFFECTS: Constructs a ParkingSpotsJsonReader that reads from the file named fileName.
    public ParkingSpotsJsonReader(String fileName) {
        super(fileName);
    }

    @Override
    public Saveable parseSaveable(JSONObject jsonObject) {
        return parkingSpotParser.parseSaveable(jsonObject);
    }


}
