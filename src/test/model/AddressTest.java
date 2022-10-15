package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AddressTest {
    private Address address;

    @Test
    public void constructorTest() {
        address = new Address("2366", "Main Mall", "Vancouver", "V6T1Z4");
        assertEquals("2366", address.getBuildingNumber());
        assertEquals("Main Mall", address.getStreetName());
        assertEquals("Vancouver", address.getCity());
        assertEquals("V6T1Z4", address.getPostalCode());
    }

    @Test
    public void getFormattedAddressTest() {
        // Note: Assumes that the Address constructor works correctly
        address = new Address("2366", "Agronomy Road", "Vancouver", "V6T1Z4");
        assertEquals("2366 Agronomy Road, Vancouver, V6T1Z4", address.getFormattedAddress());
    }
}
