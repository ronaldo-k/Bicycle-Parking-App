package model;

/*
This class represents bicycle parking spots, which carry an address, a type, a capacity, a price per period (as two
separate values), and a description with special boolean variables for three basic attributes: whether the parking
spot is sheltered, whether its access is restricted and whether it requires the use of a lock. While the address does
not need to be unique, there should not be two different ParkingSpot objects with all variables matching.
 */

import org.json.JSONObject;
import persistence.Saveable;

public class ParkingSpot implements PointOfInterest, Saveable {
    private Address address;
    private String type;
    private int capacity;
    private int price;
    private int period;
    private boolean isCovered;
    private boolean isRestrictedAccess;
    private boolean requiresLock;
    private String description;
    private int theftReportNumber;
    /** Variable conventions:
     * - type is one of: "Rack", "Locker", "Locking rack" or "Parkade"
     * - price is given in cents. This is the price that must be paid per period.
     * - period is given in hours.
     * (Note: Free to use parking spots have price = period = 0)
     * - isCovered is true if the parking spot is sheltered from the elements.
     * - isRestrictedAccess is true when a permit is required to access the parking spot. (e.g. bicycle cages at UBC
     * have isRestrictedAccess = true, given that it requires a parking permit or UBC card)
     * - requiresLock is true only if the bicycle can only be attached to the rack by means of a lock. requiresLock
     * being false means that a lock is not required, but it does not mean a lock cannot be used.
     * - description is a short description of the parking spot, which should be focused on relevant characteristics
     * of the parking spot not specified by the other variables.
     */

    // REQUIRES: address and type are not null, capacity > 0.
    // EFFECTS:  Creates ParkingSpot of a certain type with a certain capacity on a given address with zero
    // associated theft reports
    public ParkingSpot(Address address, String type, int capacity, int price, int period, boolean isCovered,
                       boolean isRestrictedAccess, boolean requiresLock, String description) {
        this.address = address;
        this.type = type;
        this.capacity = capacity;
        this.price = price;
        this.period = period;
        this.isCovered = isCovered;
        this.isRestrictedAccess = isRestrictedAccess;
        this.requiresLock = requiresLock;
        this.description = description;
        this.theftReportNumber = 0;
    }

    // Note: This function is used exclusively for parsing a ParkingSpot from a JSONObject. To create a new “blank”
    // ParkingSpot, use the first constructor.
    // REQUIRES: address and type are not null, capacity > 0.
    // EFFECTS:  Creates ParkingSpot of a certain type with a certain capacity and number of theft reports on a given
    // address
    public ParkingSpot(Address address, String type, int capacity, int price, int period, boolean isCovered,
                       boolean isRestrictedAccess, boolean requiresLock, String description, int theftReportNumber) {
        this.address = address;
        this.type = type;
        this.capacity = capacity;
        this.price = price;
        this.period = period;
        this.isCovered = isCovered;
        this.isRestrictedAccess = isRestrictedAccess;
        this.requiresLock = requiresLock;
        this.description = description;
        this.theftReportNumber = theftReportNumber;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getPrice() {
        return price;
    }

    public int getPeriod() {
        return period;
    }

    public String getDescription() {
        return description;
    }

    public int getTheftReportNumber() {
        return theftReportNumber;
    }

    // EFFECTS: Returns a formatted description of the parkingSpot.
    public String getFormattedDescription(String pretab) {
        /* This description is formatted as follows:
        (Address)
         - Type: (Type)
         - Price: $(Price)/Period
         - Is it covered? (True or False)
         - Is its access restricted? (True or False)
         - Does it require a lock? (True or False)
         - Number of theft reports registered? (Number of theft reports)
         - Details: (Description)
         Note: pretab is a sequence of tab (i.e. \t) characters placed before each new line.
         */
        return this.address.getFormattedAddress().toUpperCase() + "\n"
                + pretab + "\tType: " + this.type + "\n"
                + pretab + "\tCapacity: " + this.capacity + "\n"
                + pretab + "\tPrice: $" + this.price / 100 + "." + this.price % 100 + " / " + this.period + " h\n"
                + pretab + "\tIs it covered? " + this.isCovered + "\n"
                + pretab + "\tIs its access restricted? " + this.isRestrictedAccess + "\n"
                + pretab + "\tDoes it require a lock? " + this.requiresLock + "\n"
                + pretab + "\tNumber of theft reports registered: " + this.theftReportNumber + "\n"
                + pretab + "\tDetails: " + this.description;
    }

    public boolean isCovered() {
        return isCovered;
    }

    public boolean isRestrictedAccess() {
        return isRestrictedAccess;
    }

    public boolean requiresLock() {
        return requiresLock;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void incrementTheftReportNumber() {
        theftReportNumber++;
    }


    // EFFECTS: Returns the JSON formatted version of a ParkingSpot
    @Override
    public JSONObject toJson() {
        JSONObject result = new JSONObject();
        result.put("address", address.toJson());
        result.put("type", type);
        result.put("capacity", capacity);
        result.put("price", price);
        result.put("period", period);
        result.put("isCovered", isCovered);
        result.put("isRestrictedAccess", isRestrictedAccess);
        result.put("requiresLock", requiresLock);
        result.put("description", description);
        result.put("theftReportNumber", theftReportNumber);
        return result;
    }
}
