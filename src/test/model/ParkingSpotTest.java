package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingSpotTest {
    private Address parkingSpotAddress1;
    private ParkingSpot parkingSpot1;

    @BeforeEach
    public void setup() {
        parkingSpotAddress1 = new Address("2366", "Main Mall", "Vancouver", "V6T1Z4");
        parkingSpot1 = new ParkingSpot(parkingSpotAddress1, "Rack", 14, 0, 0,
                false, false, true, "To the South of the X wing of the ICICS/CS building. Visible from the X wing " +
                "first floor lounge");
    }

    @Test
    public void constructorTest() {
        assertEquals(parkingSpotAddress1, parkingSpot1.getAddress());
        assertEquals("Rack", parkingSpot1.getType());
        assertEquals(14, parkingSpot1.getCapacity());
        assertEquals(0, parkingSpot1.getPrice());
        assertEquals(0, parkingSpot1.getPeriod());
        assertFalse(parkingSpot1.isCovered());
        assertFalse(parkingSpot1.isRestrictedAccess());
        assertTrue(parkingSpot1.requiresLock());
    }
}
