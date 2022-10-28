package persistence;

import model.*;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CyclistsJsonReaderTest {
    CyclistsJsonReader cyclistsJsonReader;

    private String cyclistName = "Adam";
    private Bicycle bicycle1 = new Bicycle("E-bike", "Ampere", "eCommute 500","Green e-bike with bronze bell",
            "D82H0439187H3P");
    private Bicycle bicycle2 = new Bicycle("Old bike", "Coyote", "Voyage V2", "Black hybrid bike with pannier rack",
            "E63D0127491Y1T");
    private Bicycle bicycle3 = new Bicycle("Pink bike", "Crosstown", "Rider 1000", "Pink bike with plastic fenders",
            "G73M1065883P3N");
    // Note: No real bicycle models are used in these tests.

    Address parkingSpotAddress = new Address("2366", "Main Mall", "Vancouver", "V6T1Z4");
    ParkingSpot parkingSpot = new ParkingSpot(parkingSpotAddress, "Rack", 14, 0, 0,
            false, false, true, "To the South of the X wing of the ICICS/CS building. Visible from the X wing " +
            "first floor lounge");
    TheftReport theftReport = new TheftReport(bicycle2, parkingSpot, LocalDate.of(2022, 10, 1));

    // Important: Certify that NonexistentFile1.json does not exist in the data/test folder.
    @Test
    public void readFromNonexistentFileTest() {
        String source = "./data/test/NonexistentFile1.json";
        try {
            cyclistsJsonReader = new CyclistsJsonReader(source);
            cyclistsJsonReader.read();
            fail("Expected IOException not thrown");
        } catch (IOException e) {
            // Test passes
        }
    }

    @Test
    public void readFromSample1Test() {
        String source = "./data/test/Sample1.json";

        Cyclist cyclist = new Cyclist("John");
        cyclist.addBicycle(bicycle1);
        cyclist.addBicycle(bicycle2);
        cyclist.addTheftReport(theftReport);

        List<Cyclist> cyclists = new ArrayList<>();
        cyclists.add(cyclist);

        cyclistsJsonReader = new CyclistsJsonReader(source);
        try {
            List<Saveable> actualList = cyclistsJsonReader.read();
            Cyclist actualCyclist = (Cyclist) actualList.get(0);

            assertEquals(2, actualCyclist.getBicycles().size());
            assertEquals("E-bike", actualCyclist.getBicycles().get(0).getName());
            assertEquals("Coyote", actualCyclist.getBicycles().get(1).getBrand());
            assertTrue(actualCyclist.getTheftReports().get(0).getParkingSpot().requiresLock());
        } catch (IOException e) {
            fail("Unexpected IOException thrown");
        }
    }

    @Test
    public void readFromEmptySampleTest() {
        String source = "./data/test/EmptySample.json";
        cyclistsJsonReader = new CyclistsJsonReader(source);
        try {
            List<Saveable> actualList = cyclistsJsonReader.read();
            assertEquals(0, actualList.size());
        } catch (IOException e) {
            fail("Unexpected IOException thrown");
        }
    }
}
