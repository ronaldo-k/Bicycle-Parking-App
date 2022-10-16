package model;

/*
Catch-all interface for points of interest, which may or may not (in future implementations) be ParkingSpots.
 */
public interface PointOfInterest {
    Address getAddress();

    void setAddress(Address address);
}
