package persistence;

import model.Address;
import model.Bicycle;
import model.ParkingSpot;
import model.TheftReport;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.parsers.ParkingSpotParser;
import persistence.parsers.TheftReportParser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TheftReportJsonReaderTest {
    private Bicycle bicycle2 = new Bicycle("Old bike", "Coyote", "Voyage V2", "Black hybrid bike with pannier rack",
            "E63D0127491Y1T");
    private Address parkingSpotAddress = new Address("2366", "Main Mall", "Vancouver", "V6T1Z4");
    private ParkingSpot parkingSpot = new ParkingSpot(parkingSpotAddress, "Rack", 14, 0, 0,
            false, false, true, "To the South of the X wing of the ICICS/CS building. Visible from the X wing " +
            "first floor lounge");
    private TheftReport theftReport = new TheftReport(bicycle2, parkingSpot, LocalDate.of(2022, 10, 1));

    String source = "./Data/test/SampleTheftReports.json";
    List<Saveable> theftReports = new ArrayList<>();
    TheftReportsJsonReader theftReportsJsonReader = new TheftReportsJsonReader(source);

    @BeforeEach
    public void setup() {
        ArrayJsonWriter arrayJsonWriter;
        theftReports.add(theftReport);

        try {
            arrayJsonWriter = new ArrayJsonWriter(source);
            arrayJsonWriter.open();
            arrayJsonWriter.write(theftReports);
            arrayJsonWriter.close();
        } catch (IOException e) {
            fail("Setup failed: Unexpected IOException thrown.");
        }
    }

    // Important: Certify that NonexistentFile1.json does not exist in the data folder.
    @Test
    public void readFromNonexistentFileTest() {
        String nonexistentSource = "./data/test/NonexistentFile1.json";
        try {
            theftReportsJsonReader = new TheftReportsJsonReader(nonexistentSource);
            theftReportsJsonReader.read();
            fail("Expected IOException not thrown");
        } catch (IOException e) {
            // Test passes
        }
    }

    @Test
    public void readFromSampleParkingSpotsTest() {
        try {
            List<Saveable> actualList = theftReportsJsonReader.read();
            TheftReport actualTheftReport = (TheftReport) actualList.get(0);

            assertEquals("Old bike", actualTheftReport.getBicycle().getName());
            assertEquals("To the South of the X wing of the ICICS/CS building. Visible from the X wing " +
                    "first floor lounge", actualTheftReport.getParkingSpot().getDescription());
            assertEquals(LocalDate.of(2022, 10, 1), actualTheftReport.getDate());
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
            TheftReportParser theftReportParser = new TheftReportParser();
            String jsonData = readFile(source);
            JSONArray jsonArray = new JSONArray(jsonData);
            TheftReport expected = (TheftReport) theftReportParser.parseSaveable(jsonArray.getJSONObject(0));

            TheftReport actual = (TheftReport) theftReportsJsonReader.read().get(0);

            assertEquals(expected.getBicycle().getSerialNumber(), actual.getBicycle().getSerialNumber());
        } catch (IOException e) {
            fail("Unexpected IOException thrown");
        }
    }
}
