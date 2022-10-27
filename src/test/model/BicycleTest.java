package model;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BicycleTest {
    Bicycle bicycle = new Bicycle("Green bike", "Brandname", "Model A",
            "None", "ABC1234");

    @Test
    public void constructorTest() {
        assertEquals("Green bike", bicycle.getName());
        assertEquals("Brandname", bicycle.getBrand());
        assertEquals("Model A", bicycle.getModel());
        assertEquals("None", bicycle.getDescription());
        assertEquals("ABC1234", bicycle.getSerialNumber());
    }

    @Test
    public void getFormattedDescriptionNoPretabTest() {
        assertEquals("GREEN BIKE\n\tBrand: Brandname\n\tModel: Model A" +
                "\n\tDescription: None\n\tSerial number: ABC1234", bicycle.getFormattedDescription(""));
    }

    @Test
    public void getFormattedDescriptionWithPretabTest() {
        assertEquals("GREEN BIKE\n\t\tBrand: Brandname\n\t\tModel: Model A" +
                "\n\t\tDescription: None\n\t\tSerial number: ABC1234", bicycle.getFormattedDescription("\t"));
    }

    @Test
    public void toJsonTest() {
        JSONObject expected = new JSONObject();
        expected.put("name", "Green bike");
        expected.put("brand", "Brandname");
        expected.put("model", "Model A");
        expected.put("description", "None");
        expected.put("serialNumber", "ABC1234");

        assertEquals(expected.getString("name"), bicycle.toJson().getString("name"));
        assertEquals(expected.getString("brand"), bicycle.toJson().getString("brand"));
        assertEquals(expected.getString("model"), bicycle.toJson().getString("model"));
        assertEquals(expected.getString("description"), bicycle.toJson().getString("description"));
        assertEquals(expected.getString("serialNumber"), bicycle.toJson().getString("serialNumber"));
    }
}
