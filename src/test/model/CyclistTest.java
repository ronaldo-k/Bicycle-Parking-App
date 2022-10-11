package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CyclistTest {
    private Cyclist cyclist;
    private String cyclistName = "";
    private Bicycle bicycle1 = new Bicycle("E-bike", "Ampere", "eCommute 500","Green e-bike with bronze bell",
            "D82H0439187H3P");
    private Bicycle bicycle2 = new Bicycle("Old bike", "Coyote", "Voyage V2", "Black hybrid bike with pannier rack",
            "E63D0127491Y1T");
    private Bicycle bicycle3 = new Bicycle("Pink bike", "Crosstown", "Rider 1000", "Pink bike with plastic fenders",
            "G73M1065883P3N");
    // Note: No real bicycle models are used in these tests.

    @BeforeEach
    public void setup() {
        cyclist = new Cyclist(cyclistName);
    }

    @Test
    public void constructorTest() {
        assertEquals(cyclistName, cyclist.getName());
        assertEquals(0, cyclist.getBicycles().size());
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

}
