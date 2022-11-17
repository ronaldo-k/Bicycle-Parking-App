package model;

/*
This class represents bicycles, which carry a name (defined by the Cyclist that creates the bicycle), a brand, a
model, a user defined description and a serial number (which is represented as a String to accommodate alphanumeric
serial numbers). Of all fields, only serialNumber cannot be null, as it will be used as a unique identifier for each
bicycle.
*/

import org.json.JSONObject;
import persistence.Saveable;

public class Bicycle implements Saveable {
    private String name;
    private String brand;
    private String model;
    private String description;
    private String serialNumber;

    // REQUIRES: serialNumber is not null
    // EFFECTS:  Creates bicycle with their corresponding name, type, brand, model, description and serialNumber.
    public Bicycle(String name, String brand, String model, String description, String serialNumber) {
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.description = description;
        this.serialNumber = serialNumber;
    }

    // REQUIRES: serialNumber must be set afterwards with bicycle.setSerialNumber.
    // EFFECTS:  Creates a bicycle with all fields empty.
    public Bicycle() {
        new Bicycle("", "", "", "", "");
    }

    // EFFECTS: Returns a formatted description of the bicycle.
    public String getFormattedDescription(String pretab) {
        /* This description is formatted as follows:
        (Name)
            Brand: (Brand)
            Model: (Model)
            Description: (Description)
            Serial number: (Serial number)
         Note: pretab is a sequence of tab (i.e. \t) characters placed before each new line.
         */
        return this.name.toUpperCase() + "\n"
                + pretab + "\tBrand: " + this.brand + "\n"
                + pretab + "\tModel: " + this.model + "\n"
                + pretab + "\tDescription: " + this.description + "\n"
                + pretab + "\tSerial number: " + this.serialNumber;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getDescription() {
        return description;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    // EFFECTS: Returns the JSONArray formatted version of a bicycle.
    @Override
    public JSONObject toJson() {
        JSONObject result = new JSONObject();
        result.put("name", name);
        result.put("brand", brand);
        result.put("model", model);
        result.put("description", description);
        result.put("serialNumber", serialNumber);
        return result;
    }
}
