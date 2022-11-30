package model;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/*
This class implements the methods of cyclists, the primary users of the program. All cyclists have an initially
empty list of bicycles, to which at least one bicycle should be added in order to access certain methods, and a
theft report list, which represents the theft reports they created.
 */

public class Cyclist extends User {
    private List<Bicycle> bicycles;
    private List<TheftReport> theftReports;

    // REQUIRES: name is not null
    // EFFECTS:  New Cyclist object is created with no registered bicycles.
    public Cyclist(String name) {
        this.name = name;
        this.bicycles = new ArrayList<>();
        this.theftReports = new ArrayList<>();
    }

    // Note: This function is used exclusively for parsing a Cyclist from a JSONObject. To create a new “blank” Cyclist
    // use the first constructor.
    // REQUIRES: name is not null
    // EFFECTS:  New Cyclist object is created with a list of registered bicycles and theft reports.
    public Cyclist(String name, List<Bicycle> bicycles, List<TheftReport> theftReports) {
        this.name = name;
        this.bicycles = bicycles;
        this.theftReports = theftReports;
    }

    // MODIFIES: this
    // EFFECTS:  Bicycle is added to the bicycles list.
    public void addBicycle(Bicycle bicycle) {
        if (bicycles.contains(bicycle)) {
            return;
        }
        bicycles.add(bicycle);
        EventLog.getInstance().logEvent(new Event("A bicycle of name " + bicycle.getName() + " has been added to "
                + name + "'s bicycles."));
    }

    // REQUIRES: bicycle is in bicycles
    // MODIFIES: this
    // EFFECTS:  bicycle is removed from bicycles list.
    public void removeBicycle(Bicycle bicycle) {
        bicycles.remove(bicycle);
        EventLog.getInstance().logEvent(new Event("A bicycle of name " + bicycle.getName() + " has been removed from "
                + name + "'s bicycles."));
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

    public List<Bicycle> getBicycles() {
        return bicycles;
    }

    public List<TheftReport> getTheftReports() {
        return theftReports;
    }


    // EFFECTS: Returns the JSONObject formatted version of a TheftReport
    @Override
    public JSONObject toJson() {
        JSONObject result = new JSONObject();
        result.put("name", name);
        result.put("bicycles", bicyclesToJson());
        result.put("theftReports", theftReportsToJson());
        return result;
    }

    // EFFECTS: Returns the JSONArray formatted version of the bicycles list.
    private JSONArray bicyclesToJson() {
        JSONArray result = new JSONArray();

        for (Bicycle b : bicycles) {
            result.put(b.toJson());
        }

        return result;
    }

    // EFFECTS: Returns the JSONArray formatted version of the theftReports list.
    private JSONArray theftReportsToJson() {
        JSONArray result = new JSONArray();

        for (TheftReport r : theftReports) {
            result.put(r.toJson());
        }

        return result;
    }
}
