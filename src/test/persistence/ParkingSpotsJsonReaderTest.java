package persistence;

import model.Address;
import model.Cyclist;
import model.ParkingSpot;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.parsers.ParkingSpotParser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


import static org.junit.jupiter.api.Assertions.*;

public class ParkingSpotsJsonReaderTest {
    // The following is a sample set of parking spots and their addresses
    private Address computerScienceAddress = new Address("2366", "Main Mall", "Vancouver", "V6T1Z4");
    private Address forestSciencesAddress = new Address("2424", "Main Mall", "Vancouver", "V6T1Z4");
    private Address hughDempsterAddress = new Address("6245", "Agronomy Road", "Vancouver", "V6T1Z4");
    private Address thunderbirdResidenceSelkirkAddress = new Address("6335", "Thunderbird Crescent", "Vancouver",
            "V6T2G9");
    private Address osborneCentreUnit1Address = new Address("6108", "Thunderbird Boulevard", "Vancouver", "V6T2A1");
    private Address thunderbirdParkadeAddress = new Address("6085", "Thunderbird Boulevard", "Vancouver", "V6T1Z3");

    private ParkingSpot computerScienceXWingRack = new ParkingSpot(computerScienceAddress, "Rack", 14, 0, 0,
            false, false, true, "To the South of the X wing of the ICICS/CS building, facing Agronomy Road. "
            + "Visible from the X wing first floor lounge.");
    private ParkingSpot forestSciencesRack = new ParkingSpot(forestSciencesAddress, "Rack", 30, 0, 0,
            true, false, true, "To the West of the Forest Sciences building, facing Main Mall.");
    private ParkingSpot hughDempsterRack = new ParkingSpot(hughDempsterAddress, "Rack", 12, 0, 0,
            true, false, true, "To the West of the Forest Sciences building, facing Engineering Road.");
    private ParkingSpot thunderbirdResidenceSelkirkRack = new ParkingSpot(thunderbirdResidenceSelkirkAddress, "Rack", 7,
            0, 0, false, false, true, "To the East of the Selkirk block of the Thunderbird Student Residence, "
            + "facing Thunderbird Crescent");
    private ParkingSpot osborneCentreUnit1Rack = new ParkingSpot(osborneCentreUnit1Address, "Rack", 32,
            0, 0, false, false, true, "To the East of Unit 1 of the Robert F. Osborne Centre, facing a parking "
            + "lot.");
    private ParkingSpot thunderbirdParkadeCage = new ParkingSpot(thunderbirdParkadeAddress, "Parkade", 40,
            0, 0, true, true, true, "At the Northwest corner of Thunderbird Parkade. Access to this bicycle cage "
            + "is restricted to students, faculty, staff and affiliated members of the University of British "
            + "Columbia (UBC) only. These may obtain a bicycle cage permit in the UBC parking website with a "
            + "UBCcard.");

    String source = "./Data/test/SampleParkingSpots.json";
    List<Saveable> parkingSpots = new ArrayList<>();
    ParkingSpotsJsonReader parkingSpotsJsonReader = new ParkingSpotsJsonReader(source);

    @BeforeEach
    public void setup() {
        ArrayJsonWriter arrayJsonWriter;
        addSampleParkingSpots();

        try {
            arrayJsonWriter = new ArrayJsonWriter(source);
            arrayJsonWriter.open();
            arrayJsonWriter.write(parkingSpots);
            arrayJsonWriter.close();
        } catch (IOException e) {
            fail("Setup failed: Unexpected IOException thrown.");
        }
    }

    // MODIFIES: this
    // EFFECTS:  Adds sample set of ParkingSpots to parkingSpots
    private void addSampleParkingSpots() {
        parkingSpots.add(computerScienceXWingRack);
        parkingSpots.add(forestSciencesRack);
        parkingSpots.add(hughDempsterRack);
        parkingSpots.add(thunderbirdResidenceSelkirkRack);
        parkingSpots.add(osborneCentreUnit1Rack);
        parkingSpots.add(thunderbirdParkadeCage);
    }

    // Important: Certify that NonexistentFile1.json does not exist in the data folder.
    @Test
    public void readFromNonexistentFileTest() {
        String nonexistentSource = "./data/test/NonexistentFile1.json";
        try {
            parkingSpotsJsonReader = new ParkingSpotsJsonReader(nonexistentSource);
            parkingSpotsJsonReader.read();
            fail("Expected IOException not thrown");
        } catch (IOException e) {
            // Test passes
        }
    }

    @Test
    public void readFromSampleParkingSpotsTest() {
        try {
            List<Saveable> actualList = parkingSpotsJsonReader.read();
            ParkingSpot actualParkingSpot1 = (ParkingSpot) actualList.get(1);
            ParkingSpot actualParkingSpot2 = (ParkingSpot) actualList.get(2);

            assertEquals("2424 Main Mall, Vancouver, V6T1Z4", actualParkingSpot1.getAddress().getFormattedAddress());
            assertEquals("To the West of the Forest Sciences building, facing Engineering Road.",
                    actualParkingSpot2.getDescription());
        } catch (IOException e) {
            fail("Unexpected IOException thrown");
        }
    }

    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    @Test
    public void parseSaveableTest() {
        try {
            ParkingSpotParser parkingSpotParser = new ParkingSpotParser();
            String jsonData = readFile(source);
            JSONArray jsonArray = new JSONArray(jsonData);
            ParkingSpot expected = (ParkingSpot) parkingSpotParser.parseSaveable(jsonArray.getJSONObject(0));

            ParkingSpot actual = (ParkingSpot) parkingSpotsJsonReader.read().get(0);

            assertEquals(expected.getDescription(), actual.getDescription());
        } catch (IOException e) {
            fail("Unexpected IOException thrown");
        }
    }
}
