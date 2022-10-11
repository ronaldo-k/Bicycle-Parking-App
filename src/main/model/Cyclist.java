package model;

import java.util.ArrayList;
import java.util.List;

/* This class implements the methods of cyclists, the primary users of the program. All cyclists have an initially
empty list of bicycles, to which at least one bicycle should be added in order to access certain methods, and a
theft report list, which represents the theft reports they created.
* */

public class Cyclist extends User {
    private List<Bicycle> bicycles;
    private List<TheftReport> theftReports;

    // REQUIRES: username not null
    // EFFECTS:  New Cyclist object is created with no registered bicycles.
    public Cyclist(String name) {
        this.name = name;
        this.bicycles = new ArrayList<>();
        this.theftReports = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS:  Bicycle is added to the bicycles list.
    public void addBicycle(Bicycle bicycle) {
        if (bicycles.contains(bicycle)) {
            return;
        }
        bicycles.add(bicycle);
    }

    // REQUIRES: bicycle is in bicycles
    // MODIFIES: this
    // EFFECTS:  bicycle is removed from bicycles list.
    public void removeBicycle(Bicycle bicycle) {
        bicycles.remove(bicycle);
    }

    // MODIFIES: this
    // EFFECTS:  Adds theftReport to the theftReports list.
    @Override
    public void addTheftReport(TheftReport theftReport) {
        if (theftReports.contains(theftReport)) {
            return;
        }
        theftReports.add(theftReport);
    }

    // REQUIRES: theftReport is in theftReports
    // MODIFIES: this
    // EFFECTS:  Removes theftReport to the theftReports list.
    @Override
    public void removeTheftReport(TheftReport theftReport) {
        theftReports.remove(theftReport);
    }

    public List<Bicycle> getBicycles() {
        return bicycles;
    }

    public List<TheftReport> getTheftReports() {
        return theftReports;
    }
}
