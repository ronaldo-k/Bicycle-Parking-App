package model;

/*
Helper class that stores an address as internally separate variables for building number, street name, city and postal
code. The building number is stored as a string in order to accommodate buildings with alphanumeric building numbers.
 */

import org.json.JSONObject;
import persistence.Saveable;

public class Address implements Saveable {
    private String buildingNumber;
    private String streetName;
    private String city;
    private String postalCode;

    // REQUIRES: No null parameters
    // EFFECTS:  Creates an Address object with a given building number, street name, city and postal code.
    public Address(String buildingNumber, String streetName, String city, String postalCode) {
        this.buildingNumber = buildingNumber;
        this.streetName = streetName;
        this.city = city;
        this.postalCode = postalCode;
    }

    // EFFECTS: Outputs string with the address in the format: "buildingNumber streetName, city, postalCode"
    public String getFormattedAddress() {
        return buildingNumber + " " + streetName + ", " + city + ", " + postalCode;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    // EFFECTS: Returns the JSONObject formatted version of an address.
    @Override
    public JSONObject toJson() {
        JSONObject result = new JSONObject();
        result.put("buildingNumber", buildingNumber);
        result.put("streetName", streetName);
        result.put("city", city);
        result.put("postalCode", postalCode);
        return result;
    }
}
