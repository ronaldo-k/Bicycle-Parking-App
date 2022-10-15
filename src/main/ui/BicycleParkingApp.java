package ui;

import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BicycleParkingApp {
    private List<ParkingSpot> parkingSpotMap;
    private List<Cyclist> cyclists;
    private List<TheftReport> theftReports;
    private Cyclist currentCyclist;
    private Scanner scanner;
    private final String quitCommand = "Q";

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

    // EFFECTS: Instantiates the program with an empty list of cyclists, theftReports and parking spots, adds the
    // sample set of parking spots, and creates a Scanner object.
    public BicycleParkingApp() {
        cyclists = new ArrayList<>();
        theftReports = new ArrayList<>();
        parkingSpotMap = new ArrayList<>();
        scanner = new Scanner(System.in);
        addSampleParkingSpots();

        while (true) {
            cyclistAccountSelection();
            while (true) {
                int menuReturnValue = cyclistMainMenu();
                if (menuReturnValue == 6) {
                    break; // Returns to the outer while loop (i.e. the account selection prompt).
                } else if (menuReturnValue == 7) {
                    return; // Terminates the program.
                } else {
                    continue; // Returns to the main menu
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS:  Adds sample set of parkingSpots to parkingSpotMap
    private void addSampleParkingSpots() {
        parkingSpotMap.add(computerScienceXWingRack);
        parkingSpotMap.add(forestSciencesRack);
        parkingSpotMap.add(hughDempsterRack);
        parkingSpotMap.add(thunderbirdResidenceSelkirkRack);
        parkingSpotMap.add(osborneCentreUnit1Rack);
        parkingSpotMap.add(thunderbirdParkadeCage);
    }

    // This may be moved to its own account selection class.
    private void cyclistAccountSelection() {
        int input;
        if (cyclists.isEmpty()) {
            input = createCyclistAccount();
            // The following code is repeated to skip the "Choose an account" prompt when first starting the program.
            currentCyclist = cyclists.get(input - 1);
            System.out.printf("Welcome, %s!\n", currentCyclist.getName());
            return;
        }

        System.out.println("\nCHOOSE AN ACCOUNT");
        System.out.println("Please type the account's number, or type [0] to create an account:");
        for (int i = 0; i < cyclists.size(); i++) {
            System.out.printf("\t[%d] %s\n", i + 1, cyclists.get(i).getName());
        }
        input = scanner.nextInt();

        if (input == 0) {
            scanner.nextLine();
            input = createCyclistAccount();
            currentCyclist = cyclists.get(input - 1);
            System.out.printf("Welcome, %s!\n", currentCyclist.getName());
        } else {
            currentCyclist = cyclists.get(input - 1);
            System.out.printf("Welcome back, %s!\n", currentCyclist.getName());
        }

        return;
    }

    // REQUIRES: A prior scanner.nextLine() to avoid skipping wherever applicable.
    // MODIFIES: this
    // EFFECTS:  Creates cyclist account, inserts it in the list of cyclists and returns the newly added cyclist's
    // index in the list.
    // This may be moved to its own account selection class.
    private int createCyclistAccount() {
        String inputName;
        System.out.printf("\nCREATE AN ACCOUNT\n \tPlease input your name: ");
        inputName = scanner.nextLine();

        cyclists.add(new Cyclist(inputName));

        currentCyclist = cyclists.get(cyclists.size() - 1);
        return cyclists.size();
    }

    // EFFECTS: Prints main menu and waits for user's input
    private int cyclistMainMenu() {
        int input;
        System.out.println("\nMAIN MENU\nPlease enter the number of one of the options below:\n"
                + "\t[1] Add bicycle \n\t[2] Remove bicycle \n"
                + "\t[3] Search for a parking spot \n\t[4] File theft report \n"
                + "\t[5] View user profile, theft reports and bicycles \n"
                + "\t[6] Change user \n\t[7] Quit");
        input = scanner.nextInt();
        switch (input) {
            case 1: addBicycle();
                break;
            case 2: removeBicycle();
                break;
            case 3: searchParkingSpots();
                break;
            case 4: addTheftReport();
                break;
            case 5: viewUserProfile();
                break;
            default: return input;
        }
        return 0;
    }

    // EFFECTS: Obtains the user's input to create and return a bicycle object.
    private Bicycle createBicycle() {
        String inputName;
        String inputBrand;
        String inputModel;
        String inputDescription;
        String inputSerialNumber;
        Bicycle bicycle;
        scanner.nextLine(); // Workaround to prevent skipping the subsequent nextLine command.

        System.out.printf("First, enter a name to identify your bicycle.\n\tName: ");
        inputName = scanner.nextLine();
        System.out.printf("Now, supply the following information:\n\tBrand: ");
        inputBrand = scanner.nextLine();
        System.out.printf("\tModel: ");
        inputModel = scanner.nextLine();
        System.out.printf("Add a description of any identifying details, such as accessories, decals, etc.: \n"
                + "(Optional. Press enter to skip)\n");
        inputDescription = scanner.nextLine();
        System.out.printf("Lastly, enter the bicycle's serial number: \n(This can usually be found under the "
                + "bottom bracket)\n");
        inputSerialNumber = scanner.nextLine();

        bicycle = new Bicycle(inputName, inputBrand, inputModel, inputDescription, inputSerialNumber);

        return bicycle;
    }

    // MODIFIES: currentCyclist
    // EFFECTS:  Creates new bicycle according to the user's input and adds it to the currentCyclist's list of bicycles.
    private void addBicycle() {
        System.out.printf("\nADD A BICYCLE\n");
        Bicycle bicycle = createBicycle();
        currentCyclist.addBicycle(bicycle);
        System.out.println("< Your bicycle has been successfully added! > You can view it by entering [5] in the main"
                + " menu.");
    }

    // EFFECTS: Prints out a list of the currentCyclist's bicycles.
    private void viewBicycles() {
        System.out.println("\nYou currently have " + currentCyclist.getBicycles().size() + " bicycles registered.");
        for (int i = 0; i < currentCyclist.getBicycles().size(); i++) {
            System.out.printf("\t[%d] %s\n", i + 1, currentCyclist.getBicycles().get(i).getFormattedDescription("\t"));
        }
    }

    // MODIFIES: currentCyclist
    // EFFECTS:  Removes the bicycle chosen from currentCyclist's list of bicycles.
    private void removeBicycle() {
        int inputBicycleIndex;
        Bicycle bicycle;

        System.out.printf("\nREMOVE A BICYCLE");
        viewBicycles();
        if (currentCyclist.getBicycles().isEmpty()) {
            return;
        }
        System.out.println("Which bicycle would you like to remove?");

        inputBicycleIndex = scanner.nextInt();
        inputBicycleIndex--;

        bicycle = currentCyclist.getBicycles().get(inputBicycleIndex);

        System.out.printf("< \"%s\" has been removed from your list. >\n", bicycle.getName());
        currentCyclist.removeBicycle(bicycle);
    }

    // EFFECTS: Prints out a list of bicycle parking spots available in the provided inputPostalCode.
    private List<ParkingSpot> viewParkingSpots(String inputPostalCode) {
        List<ParkingSpot> searchResults = new ArrayList<>();

        for (ParkingSpot parkingSpot : parkingSpotMap) {
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

    // EFFECTS: Outputs a list of parking spots according to the user's input postal code.
    private void searchParkingSpots() {
        String inputPostalCode;
        scanner.nextLine(); // Workaround to prevent skipping the subsequent nextLine command.

        System.out.printf("\nSEARCH FOR A PARKING SPOT\n\tWhat postal code would you like to search? ");
        inputPostalCode = scanner.nextLine();
        viewParkingSpots(inputPostalCode);
    }

    // EFFECTS: Gets a date from the user and returns it.
    private LocalDate getDateFromUser() {
        int day;
        int month;
        int year;

        System.out.printf("\n(Please input the following information as numbers only)"
                + "\n\tDay: ");
        day = scanner.nextInt();
        System.out.printf("\tMonth: ");
        month = scanner.nextInt();
        System.out.printf("\tYear: ");
        year = scanner.nextInt();

        return LocalDate.of(year, month, day);
    }

    // MODIFIES: this
    // EFFECTS:  Adds a theft report with user-provided details to the list of theftReports.
    private void addTheftReport() {
        Bicycle bicycle;
        LocalDate date;
        List<ParkingSpot> parkingSpotSearchResults;
        ParkingSpot parkingSpot;
        scanner.nextLine(); // Workaround to prevent skipping the subsequent nextLine command.

        System.out.printf("\nFILE A THEFT REPORT\n");
        viewBicycles();
        System.out.println("Which bicycle has been stolen? ");

        bicycle = currentCyclist.getBicycles().get(scanner.nextInt() - 1);

        System.out.println("Please input the postal code of the parking spot from which your bicycle was stolen: ");
        scanner.nextLine(); // Workaround to prevent skipping the subsequent nextLine command.
        parkingSpotSearchResults = viewParkingSpots(scanner.nextLine());

        System.out.println("From which of these parking spots has your bicycle been stolen?");
        parkingSpot = parkingSpotSearchResults.get(scanner.nextInt() - 1);

        System.out.printf("When was your bicycle stolen?");
        date = getDateFromUser();

        TheftReport theftReport = new TheftReport(bicycle, parkingSpot, date);
        theftReports.add(theftReport);
        currentCyclist.addTheftReport(theftReport);
        System.out.println("< Your theft report has been filed. > You may view it by entering [5] in the main menu.");
    }

    // EFFECTS: Prints out a list of theft reports filed by the currentCyclist.
    private void viewTheftReports() {
        System.out.println("You currently have " + currentCyclist.getTheftReports().size() + " theft reports filed.");
        for (int i = 0; i < currentCyclist.getTheftReports().size(); i++) {
            System.out.printf("\t[%d] %s\n", i + 1, currentCyclist.getTheftReports().get(i).getFormattedDescription());
        }
    }

    // EFFECTS: Prints out the currentCyclist's bicycles and filed theft reports.
    private void viewUserProfile() {
        System.out.println("Your name is: " + currentCyclist.getName());
        viewBicycles();
        System.out.printf("\n");
        viewTheftReports();
    }

}
