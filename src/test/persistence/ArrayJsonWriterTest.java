package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayJsonWriterTest {
    ArrayJsonWriter arrayJsonWriter;

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

    // A nonexistent file test does not apply here because ArrayJsonWriter always creates a new file if it does not
    // already exist.

    @Test
    public void writerOnSample1Test() {
        Cyclist cyclist = new Cyclist("John");
        cyclist.addBicycle(bicycle1);
        cyclist.addBicycle(bicycle2);
        cyclist.addTheftReport(theftReport);

        List<Saveable> cyclists = new ArrayList<>();
        cyclists.add(cyclist);

        try {
            arrayJsonWriter = new ArrayJsonWriter("./Data/test/Sample1.json");
            arrayJsonWriter.open();
            arrayJsonWriter.write(cyclists);
            arrayJsonWriter.close();
        } catch (IOException e) {
            fail("Unexpected IOException thrown.");
        }
    }

    @Test
    public void writeEmptySampleTest() {
        try {
            List<Saveable> emptyList = new ArrayList<>();
            arrayJsonWriter = new ArrayJsonWriter("./Data/test/EmptySample.json");
            arrayJsonWriter.open();
            arrayJsonWriter.write(emptyList);
            arrayJsonWriter.close();
        } catch (IOException e) {
            fail("Unexpected IOException thrown.");
        }
    }
}
