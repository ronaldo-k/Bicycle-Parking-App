package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingSpotTest {
    private Address parkingSpotAddress1;
    private ParkingSpot parkingSpot1;
    private ParkingSpot parkingSpot2;

    @BeforeEach
    public void setup() {
        parkingSpotAddress1 = new Address("2366", "Main Mall", "Vancouver", "V6T1Z4");
        parkingSpot1 = new ParkingSpot(parkingSpotAddress1, "Rack", 14, 0, 0,
                false, false, true, "To the South of the X wing of the ICICS/CS building. Visible from the X wing " +
                "first floor lounge");
        parkingSpot2 = new ParkingSpot(parkingSpotAddress1, "Parkade", 20, 10, 1, true, true, false, "description");
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

    @Test
    public void constructorForJsonTest() {
        ParkingSpot parkingSpot = new ParkingSpot(parkingSpotAddress1, "Rack", 14, 0, 0, false, false,true,
                "To the South of the X wing of the ICICS/CS building. Visible from the X wing first floor lounge", 3);

        for (int i = 0; i < 3; i++) {
            parkingSpot1.incrementTheftReportNumber();
        }

        assertEquals(parkingSpot1.getTheftReportNumber(),parkingSpot.getTheftReportNumber());
    }

    @Test
    public void incrementTheftReportNumberTest() {
        for (int i = 0; i < 5; i++) {
            parkingSpot1.incrementTheftReportNumber();
        }

        assertEquals(5, parkingSpot1.getTheftReportNumber());
    }

    @Test
    public void getDescriptionTest() {
        assertEquals("To the South of the X wing of the ICICS/CS building. Visible from the X wing " +
                "first floor lounge", parkingSpot1.getDescription());
    }

    @Test
    public void getFormattedDescriptionTest() {
        String expected1 = "2366 MAIN MALL, VANCOUVER, V6T1Z4\n\nType: Rack\nCapacity: 14\nPrice: Free\nIs" +
                " " +
                "it covered? No\nIs its access restricted? No\nDoes it require a lock? Yes\nNumber of " +
                "theft reports registered: 0\nDetails: To the South of the X wing of the ICICS/CS building. Visible" +
                " from the X wing first floor lounge";
        String expected2 = "2366 MAIN MALL, VANCOUVER, V6T1Z4\n\nType: Parkade\nCapacity: 20\nPrice: $0.10 / 1 h\nIs" +
                " " +
                "it covered? Yes\nIs its access restricted? Yes\nDoes it require a lock? No\nNumber of " +
                "theft reports registered: 0\nDetails: description";

        assertEquals(expected1, parkingSpot1.getFormattedDescription(""));
        assertEquals(expected2, parkingSpot2.getFormattedDescription(""));
    }

    @Test
    public void getUniqueIDTest() {
        String expected1 = "2366MainMallVancouverV6T1Z4Rack1400001";
        String expected2 = "2366MainMallVancouverV6T1Z4Parkade20101110";

        assertEquals(expected1, parkingSpot1.getUniqueID());
        assertEquals(expected2, parkingSpot2.getUniqueID());
    }
}
