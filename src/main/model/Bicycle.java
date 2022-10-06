package model;

public class Bicycle {
    private String name;
    private String type;
    private String brand;
    private String model;
    private String description;
    private String serialNumber;

    public Bicycle(String name, String type, String brand, String model, String description, String serialNumber) {
        this.name = name;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.description = description;
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
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
