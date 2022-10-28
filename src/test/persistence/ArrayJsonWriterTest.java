package persistence;

import model.*;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
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

    Cyclist cyclist;
    List<Saveable> cyclists;

    @BeforeEach
    public void setup() {
        cyclist = new Cyclist("John");
        cyclist.addBicycle(bicycle1);
        cyclist.addBicycle(bicycle2);
        cyclist.addTheftReport(theftReport);

        cyclists = new ArrayList<>();
        cyclists.add(cyclist);
    }

    @Test
    // This test creates a new file called NonexistentFile.json. Certify that this file does not exist in ./data/test
    // before running the test
    public void openNonexistentFileTest() {
        try {
            arrayJsonWriter = new ArrayJsonWriter("./data/test/NonexistentFile.json");
            arrayJsonWriter.open();
        } catch (FileNotFoundException e) {
            fail("Unexpected FileNotFoundException thrown");
        }
    }

    @Test
    public void writerOnSample1Test() {
        String source = "./data/test/Sample1.json";
        try {
            arrayJsonWriter = new ArrayJsonWriter(source);
            arrayJsonWriter.open();
            arrayJsonWriter.write(cyclists);
            arrayJsonWriter.close();

            CyclistsJsonReader cyclistsJsonReader = new CyclistsJsonReader(source);
            List<Saveable> actual = cyclistsJsonReader.read();

            for (int i = 0; i < actual.size(); i++) {
                Cyclist actualCyclist = (Cyclist) actual.get(i);
                Cyclist expectedCyclist = (Cyclist) cyclists.get(i);
                assertEquals(expectedCyclist.getName(), expectedCyclist.getName());
            }
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

    @Test
    public void closeTest() {
        String source = "./Data/test/EmptySample.json";
        arrayJsonWriter = new ArrayJsonWriter(source);
        try {
            arrayJsonWriter.open();
        } catch (FileNotFoundException e) {
            fail("Unexpected FileNotFoundException thrown");
        }
        arrayJsonWriter.close();
        arrayJsonWriter.write(cyclists);
        CyclistsJsonReader cyclistsJsonReader = new CyclistsJsonReader(source);
        List<Saveable> actual = new ArrayList<>();

        try {
            actual = cyclistsJsonReader.read();
        } catch (IOException e) {
            fail("Unexpected IOException thrown");
        } catch (JSONException e) {
            // Test passes.
        }

        assertEquals(0, actual.size());
    }
}
