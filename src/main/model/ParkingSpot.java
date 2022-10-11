package model;

public class ParkingSpot implements PointOfInterest {
    private Address address;
    private String type;
    private int capacity;
    private int price;
    private int period;
    private boolean isCovered;
    private boolean isRestrictedAccess;
    private boolean requiresLock;
    private String description;
    /** Variable conventions:
     * - type is one of: "Rack", "Locker", "Locking rack" or "Parkade"
     * - price is given in cents. This is the price that must be paid per period.
     * - period is given in hours.
     * > Free to use parking spots have price = period = 0.
     * - isCovered is true if the parking spot is sheltered from the elements.
     * - isRestrictedAccess is true when a permit is required to access the parking spot. (e.g. bicycle cages at UBC
     * have isRestrictedAccess = true, given that it requires a parking permit or UBC card)
     * - requiresLock is true only if the bicycle can only be attached to the rack by means of a lock. requiresLock
     * being false means that a lock is not required, but it does not mean a lock cannot be used.
     * - description is a short description of the parking spot, which should be focused on relevant characteristics
     * of the parking spot not specified by the other variables.
     */

    // REQUIRES: address and type are not null, capacity > 0.
    // EFFECTS:  Creates ParkingSpot of a certain type with a certain capacity on a given address. All spots are
    // initially unoccupied.
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

    public void setType(String type) {
        this.type = type;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
