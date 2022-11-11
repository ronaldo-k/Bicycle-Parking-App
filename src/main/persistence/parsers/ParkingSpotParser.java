package persistence.parsers;

import model.Address;
import model.ParkingSpot;
import org.json.JSONObject;
import persistence.Saveable;
import persistence.parsers.SaveableParser;

/*
Helper class used to parse a JSONObject as a parkingSpot (returned as a Saveable)
 */

public class ParkingSpotParser extends SaveableParser {

    // EFFECTS: Creates a ParkingSpotParser, which stores no variables.
    public ParkingSpotParser() {

    }

    // EFFECTS: Parses a JSONObject as a parkingSpot and returns the parkingSpot (as a Saveable)
    @Override
    public Saveable parseSaveable(JSONObject jsonObject) {
        Address address = parseAddress(jsonObject.getJSONObject("address"));
        String type = jsonObject.getString("type");
        int capacity = jsonObject.getInt("capacity");
        int price = jsonObject.getInt("price");
        int period = jsonObject.getInt("period");
        Boolean isCovered = jsonObject.getBoolean("isCovered");
        Boolean isRestrictedAccess = jsonObject.getBoolean("isRestrictedAccess");
        Boolean requiresLock = jsonObject.getBoolean("requiresLock");
        String description = jsonObject.getString("description");
        int theftReportNumber = jsonObject.getInt("theftReportNumber");

        ParkingSpot parkingSpot = new ParkingSpot(address, type, capacity, price, period, isCovered,
                isRestrictedAccess, requiresLock, description, theftReportNumber);
        return parkingSpot;
    }

    // EFFECTS: Parses a JSONObject into an Address. This method is implemented in ParkingSpotsJsonReader because
    // Addresses are never stored by themselves.
    public Address parseAddress(JSONObject jsonObject) {
        String buildingNumber = jsonObject.getString("buildingNumber");
        String streetName = jsonObject.getString("streetName");
        String city = jsonObject.getString("city");
        String postalCode = jsonObject.getString("postalCode");
        return new Address(buildingNumber, streetName, city, postalCode);
    }
}
