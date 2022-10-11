package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BicycleTest {
    Bicycle bicycle = new Bicycle("Green bike", "Brandname", "Model A",
            "Green bike, no accessories", "H81P1936784P0M");

    @Test
    public void constructorTest() {
        assertEquals("Green bike", bicycle.getName());
        assertEquals("Brandname", bicycle.getBrand());
        assertEquals("Model A", bicycle.getModel());
        assertEquals("Green bike, no accessories", bicycle.getDescription());
        assertEquals("H81P1936784P0M", bicycle.getSerialNumber());
    }
}
