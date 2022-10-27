package persistence.parsers;

import model.Bicycle;
import org.json.JSONObject;
import persistence.Saveable;
import persistence.parsers.SaveableParser;

public class BicycleParser extends SaveableParser {

    public BicycleParser() {

    }

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
