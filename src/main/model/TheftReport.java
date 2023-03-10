package model;

import org.json.JSONObject;
import persistence.Saveable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*
This class represents a theft report. It includes the stolen bicycle, the parking spot from which it was stolen and
a date of theft. In order to store theft reports after the bicycle was recovered, the theft report is
not deleted upon recovery, rather it is marked as recovered by setting wasRecovered to true.
*/

public class TheftReport implements Saveable {
    private Bicycle bicycle;
    private ParkingSpot parkingSpot;
    private LocalDate date;
    private boolean wasRecovered;

    // REQUIRES: No null parameters
    // EFFECTS:  Creates a theft report.
    public TheftReport(Bicycle bicycle, ParkingSpot parkingSpot, LocalDate date) {
        this.bicycle = bicycle;
        this.parkingSpot = parkingSpot;
        this.date = date;
        this.wasRecovered = false;
    }

    // EFFECTS: Returns a formatted description of the bicycle.
    public String getFormattedDescription() {
        /* This description is formatted as follows:
        (Address)
         - Type: (Type)
         - Price: $(Price)/Period
         - Is it covered? (True or False)
         - Is its access restricted? (True or False)
         - Does it require a lock? (True or False)
         - Number of theft reports registered? (Number of theft reports)
         - Details: (Description)
         */
        return this.bicycle.getName() + " was stolen from " + this.parkingSpot.getAddress().getFormattedAddress()
                + " on " + this.date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public Bicycle getBicycle() {
        return bicycle;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean wasRecovered() {
        return wasRecovered;
    }

    // MODIFIES: this
    // EFFECTS:  Indicates bicycle was recovered.
    public void recover() {
        wasRecovered = true;
    }

    // EFFECTS: Returns the JSON formatted version of a TheftReport
    @Override
    public JSONObject toJson() {
        JSONObject result = new JSONObject();
        result.put("bicycle", bicycle.toJson());
        result.put("parkingSpot", parkingSpot.toJson());
        result.put("dateDay", date.getDayOfMonth());
        result.put("dateMonth", date.getMonthValue());
        result.put("dateYear", date.getYear());
        result.put("wasRecovered", wasRecovered);
        return result;
    }
}
