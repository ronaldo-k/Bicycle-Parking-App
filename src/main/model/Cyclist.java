package model;

import java.util.ArrayList;
import java.util.List;

public class Cyclist implements User {
    private String name;
    private List<Bicycle> bicycles;

    // REQUIRES: username not null
    // EFFECTS:  New Cyclist object is created with no registered bicycles.
    public Cyclist(String name) {
        this.name = name;
        this.bicycles = new ArrayList<>();
    }

    // REQUIRES: bicycle's serial number does not match that of any already in the list
    // MODIFIES: This
    // EFFECTS:  Bicycle is removed from bicycles list.
    public void addBicycle(Bicycle bicycle) {
        bicycles.add(bicycle);
    }

    // REQUIRES: bycicle is in bicycles (matching is done by serial number)
    // MODIFIES: This
    // EFFECTS:  bicycle is removed from bicycles list.
    public void removeBicycle(Bicycle bicycle) {
        bicycles.remove(bicycle);
    }

    @Override
    public String getName() {
        return name;
    }

}
