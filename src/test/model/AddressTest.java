package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AddressTest {
    private Address address;

    @BeforeEach
    public void setup() {
        address = new Address("2366", "Agronomy Road", "Vancouver", "V6T1Z4");
    }

    @Test
    public void constructorTest() {
        assertEquals("2366", address.getBuildingNumber());
        assertEquals("Agronomy Road", address.getStreetName());
        assertEquals("Vancouver", address.getCity());
        assertEquals("V6T1Z4", address.getPostalCode());
    }

    @Test
    public void getFormattedAddressTest() {
        // Note: Assumes that the Address constructor works correctly
        assertEquals("2366 Agronomy Road, Vancouver, V6T1Z4", address.getFormattedAddress());
    }

    @Test
    public void getAddressForUniqueIDTest() {
        assertEquals("2366Agronomy RoadVancouverV6T1Z4", address.getAddressForUniqueID());
    }
}
