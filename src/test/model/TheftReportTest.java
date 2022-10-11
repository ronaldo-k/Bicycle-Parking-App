package model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TheftReportTest {
    private Bicycle bicycle = new Bicycle("Green bike", "Brandname", "Model A",
            "Green bike, no accessories", "H81P1936784P0M");
    private Address parkingSpotAddress = new Address("2366", "Main Mall", "Vancouver", "V6T1Z4");
    private ParkingSpot parkingSpot = new ParkingSpot(parkingSpotAddress, "Rack", 14, 0, 0,
            false, false, true, "To the South of the X wing of the ICICS/CS building. Visible from the X wing " +
            "first floor lounge");
    private LocalDateTime dateTime = LocalDateTime.of(2022, 9, 19, 14, 15);

    private TheftReport theftReport = new TheftReport(bicycle, parkingSpot, dateTime);

    @Test
    public void constructorTest() {
        assertEquals(bicycle, theftReport.getBicycle());
        assertEquals(parkingSpot, theftReport.getParkingSpot());
        assertEquals(dateTime, theftReport.getDateTime());
        assertFalse(theftReport.wasRecovered());
    }

    @Test
    public void wasRecoveredTest() {
        theftReport.recover();
        assertTrue(theftReport.wasRecovered());
    }
}
