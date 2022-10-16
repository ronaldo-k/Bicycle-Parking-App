package ui;

import model.Address;
import model.ParkingSpot;

import java.util.List;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
A class that maintains and displays a list of parking spots.
 */

public class ParkingSpotListManager {
    private List<ParkingSpot> parkingSpots;
    Scanner scanner;

    // The following is a sample set of parking spots and their addresses
    Address computerScienceAddress = new Address("2366", "Main Mall", "Vancouver", "V6T1Z4");
    Address forestSciencesAddress = new Address("2424", "Main Mall", "Vancouver", "V6T1Z4");
    Address hughDempsterAddress = new Address("6245", "Agronomy Road", "Vancouver", "V6T1Z4");
    Address thunderbirdResidenceSelkirkAddress = new Address("6335", "Thunderbird Crescent", "Vancouver", "V6T2G9");
    Address osborneCentreUnit1Address = new Address("6108", "Thunderbird Boulevard", "Vancouver", "V6T2A1");
    Address thunderbirdParkadeAddress = new Address("6085", "Thunderbird Boulevard", "Vancouver", "V6T1Z3");

    ParkingSpot computerScienceXWingRack = new ParkingSpot(computerScienceAddress, "Rack", 14, 0, 0,
            false, false, true, "To the South of the X wing of the ICICS/CS building, facing Agronomy Road. "
            + "Visible from the X wing first floor lounge.");
    ParkingSpot forestSciencesRack = new ParkingSpot(forestSciencesAddress, "Rack", 30, 0, 0,
            true, false, true, "To the West of the Forest Sciences building, facing Main Mall.");
    ParkingSpot hughDempsterRack = new ParkingSpot(hughDempsterAddress, "Rack", 12, 0, 0,
            true, false, true, "To the West of the Forest Sciences building, facing Engineering Road.");
    ParkingSpot thunderbirdResidenceSelkirkRack = new ParkingSpot(thunderbirdResidenceSelkirkAddress, "Rack", 7,
            0, 0, false, false, true, "To the East of the Selkirk block of the Thunderbird Student Residence, "
            + "facing Thunderbird Crescent");
    ParkingSpot osborneCentreUnit1Rack = new ParkingSpot(osborneCentreUnit1Address, "Rack", 32,
            0, 0, false, false, true, "To the East of Unit 1 of the Robert F. Osborne Centre, facing a parking "
            + "lot.");
    ParkingSpot thunderbirdParkadeCage = new ParkingSpot(thunderbirdParkadeAddress, "Parkade", 40,
            0, 0, true, true, true, "At the Northwest corner of Thunderbird Parkade. Access to this bicycle cage "
            + "is restricted to students, faculty, staff and affiliated members of the University of British "
            + "Columbia (UBC) only. These may obtain a bicycle cage permit in the UBC parking website with a "
            + "UBCcard.");

    public ParkingSpotListManager() {
        parkingSpots = new ArrayList<>();
        scanner = new Scanner(System.in);
        addSampleParkingSpots();
    }

    // MODIFIES: this
    // EFFECTS:  Adds sample set of parkingSpots to parkingSpotMap
    private void addSampleParkingSpots() {
        parkingSpots.add(computerScienceXWingRack);
        parkingSpots.add(forestSciencesRack);
        parkingSpots.add(hughDempsterRack);
        parkingSpots.add(thunderbirdResidenceSelkirkRack);
        parkingSpots.add(osborneCentreUnit1Rack);
        parkingSpots.add(thunderbirdParkadeCage);
    }

    // EFFECTS: Outputs a list of parking spots according to the user's input postal code.
    public void searchParkingSpots() {
        String inputPostalCode;

        System.out.printf("\nSEARCH FOR A PARKING SPOT\n\tWhat postal code would you like to search? ");
        inputPostalCode = scanner.nextLine();
        viewParkingSpots(inputPostalCode);
    }

    // EFFECTS: Prints out a list of bicycle parking spots available in the provided inputPostalCode.
    public List<ParkingSpot> viewParkingSpots(String inputPostalCode) {
        List<ParkingSpot> searchResults = new ArrayList<>();

        for (ParkingSpot parkingSpot : parkingSpots) {
            if (inputPostalCode.equalsIgnoreCase(parkingSpot.getAddress().getPostalCode())) {
                searchResults.add(parkingSpot);
            }
        }
        if (searchResults.size() == 0) {
            System.out.println("\tNo parking spots with the postal code " + inputPostalCode + " have been found.");
            return null;
        }

        System.out.println("The following parking spots with the postal code " + inputPostalCode
                + " have been found:");
        for (int i = 0; i < searchResults.size(); i++) {
            System.out.printf("\t[%d] %s\n", i + 1, searchResults.get(i).getFormattedDescription("\t"));
        }
        return searchResults;
    }
}
