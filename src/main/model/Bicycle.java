package model;

/*
This class represents bicycles, which carry a name (defined by the Cyclist that creates the bicycle), a brand, a
model, a user defined description and a serial number (which is represented as a String to accommodate alphanumeric
serial numbers). Of all fields, only serialNumber cannot be null, as it will be used as a unique identifier for each
bicycle.
*/

public class Bicycle {
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
}
