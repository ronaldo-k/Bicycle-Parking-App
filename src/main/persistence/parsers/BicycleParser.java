package persistence.parsers;

import model.Bicycle;
import org.json.JSONObject;
import persistence.Saveable;
import persistence.parsers.SaveableParser;

/*
Helper class used to parse a JSONObject as a bicycle (returned as a Saveable)
 */

public class BicycleParser extends SaveableParser {
    // EFFECTS: Creates a BicycleParser, which stores no variables.
    public BicycleParser() {

    }

    // EFFECTS: Parses a JSONObject as a bicycle and returns the bicycle.
    @Override
    public Saveable parseSaveable(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String brand = jsonObject.getString("brand");
        String model = jsonObject.getString("model");
        String description = jsonObject.getString("description");
        String serialNumber = jsonObject.getString("serialNumber");
        return new Bicycle(name, brand, model, description, serialNumber);
    }
}
