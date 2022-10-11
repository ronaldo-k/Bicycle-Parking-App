package model;

/*
Catch-all interface for points of interest, which may or may not be ParkingSpots.
 */
public interface PointOfInterest {
    public Address getAddress();

    public void setAddress(Address address);
}
