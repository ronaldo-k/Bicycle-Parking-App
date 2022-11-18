package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BicycleTest {
    Bicycle bicycle;

    @BeforeEach
    public void setup() {
        bicycle = new Bicycle("Green bike", "Brandname", "Model A",
                "None", "ABC1234");
    }

    @Test
    public void constructorTest() {
        assertEquals("Green bike", bicycle.getName());
        assertEquals("Brandname", bicycle.getBrand());
        assertEquals("Model A", bicycle.getModel());
        assertEquals("None", bicycle.getDescription());
        assertEquals("ABC1234", bicycle.getSerialNumber());
    }

    @Test
    public void noParametersConstructorTest() {
        Bicycle bicycle1 = new Bicycle();
        assertNull(bicycle1.getName());
        assertNull(bicycle1.getBrand());
        assertNull(bicycle1.getModel());
        assertNull(bicycle1.getDescription());
        assertNull(bicycle1.getSerialNumber());
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
    public void setNameTest() {
        String newField = "new name";
        bicycle.setName(newField);
        assertEquals(newField, bicycle.getName());
    }

    @Test
    public void setBrandTest() {
        String newField = "new brand";
        bicycle.setBrand(newField);
        assertEquals(newField, bicycle.getBrand());
    }

    @Test
    public void setModelTest() {
        String newField = "new model";
        bicycle.setModel(newField);
        assertEquals(newField, bicycle.getModel());
    }

    @Test
    public void setDescriptionTest() {
        String newField = "new description";
        bicycle.setDescription(newField);
        assertEquals(newField, bicycle.getDescription());
    }

    @Test
    public void setSerialNumberTest() {
        String newField = "new serial number";
        bicycle.setSerialNumber(newField);
        assertEquals(newField, bicycle.getSerialNumber());
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
