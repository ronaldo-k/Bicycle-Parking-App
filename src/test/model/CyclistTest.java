package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CyclistTest {
    private Cyclist cyclist;
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
    TheftReport theftReport2 = new TheftReport(bicycle3, parkingSpot, LocalDate.of(2021, 4, 23));

    @BeforeEach
    public void setup() {
        cyclist = new Cyclist(cyclistName);
        EventLog.getInstance().clear();
    }

    @Test
    public void constructorTest() {
        assertEquals(cyclistName, cyclist.getName());
        assertEquals(0, cyclist.getBicycles().size());
    }

    @Test
    public void constructorForJsonTest() {
        Cyclist expected = new Cyclist(cyclistName);
        Cyclist actual;

        List<Bicycle> bicycles = new ArrayList<>();
        bicycles.add(bicycle1);
        bicycles.add(bicycle2);
        bicycles.add(bicycle3);

        List<TheftReport> theftReports = new ArrayList<>();
        theftReports.add(theftReport);

        actual = new Cyclist(cyclistName, bicycles, theftReports);

        expected.addTheftReport(theftReport);
        expected.addBicycle(bicycle1);
        expected.addBicycle(bicycle2);
        expected.addBicycle(bicycle3);

        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getBicycles(), actual.getBicycles());
        assertEquals(expected.getTheftReports(), actual.getTheftReports());
    }

    @Test
    public void addBicycleTest() {
        cyclist.addBicycle(bicycle1);
        assertEquals(1, cyclist.getBicycles().size());
        assertEquals(bicycle1, cyclist.getBicycles().get(0));

        cyclist.addBicycle(bicycle2);
        assertEquals(2, cyclist.getBicycles().size());
        assertEquals(bicycle1, cyclist.getBicycles().get(0));
        assertEquals(bicycle2, cyclist.getBicycles().get(1));
    }

    @Test
    public void addBicycleLogTest() {
        cyclist.addBicycle(bicycle1);
        cyclist.addBicycle(bicycle2);
        String[] expectedDescriptions = {"Event log cleared.", "A bicycle of name E-bike has been added to Adam's "
                + "bicycles.", "A bicycle of name Old bike has been added to Adam's bicycles."};
        int i = 0;
        for (Event event : EventLog.getInstance()) {
            assertEquals(expectedDescriptions[i], event.getDescription());
            i++;
        }
    }

    @Test
    public void addBicycleMultipleAttemptsTest() {
        cyclist.addBicycle(bicycle1);
        cyclist.addBicycle(bicycle1);
        assertEquals(1, cyclist.getBicycles().size());
        assertEquals(bicycle1, cyclist.getBicycles().get(0));

        cyclist.addBicycle(bicycle2);
        assertEquals(2, cyclist.getBicycles().size());
        assertEquals(bicycle1, cyclist.getBicycles().get(0));
        assertEquals(bicycle2, cyclist.getBicycles().get(1));

        cyclist.addBicycle(bicycle2);
        assertEquals(2, cyclist.getBicycles().size());
        assertEquals(bicycle1, cyclist.getBicycles().get(0));
        assertEquals(bicycle2, cyclist.getBicycles().get(1));
    }

    @Test
    public void removeBicycleTest() {
        // Important: This assumes addBicycle() works correctly
        cyclist.addBicycle(bicycle1);
        cyclist.addBicycle(bicycle2);

        cyclist.removeBicycle(bicycle1);
        assertEquals(1, cyclist.getBicycles().size());
        assertEquals(bicycle2, cyclist.getBicycles().get(0));

        cyclist.removeBicycle(bicycle2);
        assertEquals(0, cyclist.getBicycles().size());
    }

    @Test
    public void removeBicycleLogTest() {
        cyclist.addBicycle(bicycle1);
        cyclist.addBicycle(bicycle2);
        cyclist.removeBicycle(bicycle2);
        cyclist.removeBicycle(bicycle1);
        String[] expectedDescriptions = {"Event log cleared.",
                "A bicycle of name E-bike has been added to Adam's bicycles.",
                "A bicycle of name Old bike has been added to Adam's bicycles.",
                "A bicycle of name Old bike has been removed from Adam's bicycles.",
                "A bicycle of name E-bike has been removed from Adam's bicycles."};
        int i = 0;
        for (Event event : EventLog.getInstance()) {
            assertEquals(expectedDescriptions[i], event.getDescription());
            i++;
        }
    }

    @Test
    public void addGetTheftReportTest() {
        cyclist.addTheftReport(theftReport);
        assertEquals(1, cyclist.getTheftReports().size());
        assertEquals(theftReport, cyclist.getTheftReports().get(0));

        cyclist.addTheftReport(theftReport2);
        assertEquals(2, cyclist.getTheftReports().size());
        assertEquals(theftReport, cyclist.getTheftReports().get(0));
        assertEquals(theftReport2, cyclist.getTheftReports().get(1));

        cyclist.addTheftReport(theftReport);

        assertEquals(2, cyclist.getTheftReports().size());
        assertEquals(theftReport, cyclist.getTheftReports().get(0));
        assertEquals(theftReport2, cyclist.getTheftReports().get(1));
    }

    @Test
    public void toJsonTest() {
        cyclist.addBicycle(bicycle1);
        cyclist.addBicycle(bicycle2);
        cyclist.addTheftReport(theftReport);

        assertEquals(cyclistName, cyclist.toJson().getString("name"));
        assertEquals(bicycle1.getName(),
                cyclist.toJson().getJSONArray("bicycles").getJSONObject(0).getString("name"));
        assertEquals(bicycle2.getBrand(),
                cyclist.toJson().getJSONArray("bicycles").getJSONObject(1).getString("brand"));
        assertEquals(bicycle1.getModel(),
                cyclist.toJson().getJSONArray("bicycles").getJSONObject(0).getString("model"));
        assertEquals(bicycle2.getDescription(),
                cyclist.toJson().getJSONArray("bicycles").getJSONObject(1).getString("description"));
        assertEquals(bicycle1.getSerialNumber(),
                cyclist.toJson().getJSONArray("bicycles").getJSONObject(0).getString("serialNumber"));

        assertEquals(theftReport.getBicycle().getName(),
                cyclist.toJson().getJSONArray("theftReports").getJSONObject(0).getJSONObject("bicycle").getString(
                        "name"));
        assertEquals(theftReport.getParkingSpot().getCapacity(),
                cyclist.toJson().getJSONArray("theftReports").getJSONObject(0).getJSONObject("parkingSpot").getInt(
                        "capacity"));
    }

}
