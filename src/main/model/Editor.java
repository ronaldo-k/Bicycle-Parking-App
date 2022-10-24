package model;

/*
An Editor is a type of user that does not own Bicycles but can add ParkingSpots to the parkingSpotList.

 */

import java.util.List;

public class Editor extends User {
    private List<ParkingSpot> addedParkingSpots;
    // TODO: COMPLETE EDITOR'S IMPLEMENTATION

    // MODIFIES: this
    // EFFECTS:  Adds a parkingSpot to the editor's list of added parking spots;
    public void addParkingSpot(ParkingSpot parkingSpot) {

    }

    @Override
    public void addTheftReport(TheftReport theftReport) {

    }

    @Override
    public void removeTheftReport(TheftReport theftReport) {

    }
}
