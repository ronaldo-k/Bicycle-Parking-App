package model;

import java.time.LocalDateTime;

/*
This class represents a theft report. It includes the stolen bicycle, the parking spot from which it was stolen and
an approximate time of theft. In order to store theft reports after the bicycle was recovered, the theft report is
not deleted upon recovery, rather it is marked as recovered by setting wasRecovered to true.
*/

public class TheftReport {
    private Bicycle bicycle;
    private ParkingSpot parkingSpot;
    private LocalDateTime dateTime;
    private boolean wasRecovered;

    // REQUIRES: bicycle and parkingSpot are not null.
    // EFFECTS:  Creates a theft report.
    public TheftReport(Bicycle bicycle, ParkingSpot parkingSpot, LocalDateTime dateTime) {
        this.bicycle = bicycle;
        this.parkingSpot = parkingSpot;
        this.dateTime = dateTime;
        this.wasRecovered = false;
    }

    public Bicycle getBicycle() {
        return bicycle;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public boolean wasRecovered() {
        return wasRecovered;
    }

    // MODIFIES: this
    // EFFECTS:  Indicates bicycle was recovered.
    public void recover() {
        wasRecovered = true;
    }
}
