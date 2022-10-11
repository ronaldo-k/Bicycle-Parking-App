package model;

/*
This is a generic user class that defines and implements the basic methods that a user can execute, that is,
those related to their name. This class is intended to support a future implementation of a business
owner/governmental agency user, that does not necessarily own bicycles but can file less specific theft reports
(that is, without all characteristics of a certain bicycle) and can create parkingSpots.
*/

public abstract class User {
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void addTheftReport(TheftReport theftReport);

    public abstract void removeTheftReport(TheftReport theftReport);
}
