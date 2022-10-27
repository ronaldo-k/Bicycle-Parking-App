package ui;

import model.*;

import java.util.Scanner;

/*
 Class that represents an instance of the bicycle parking program. A BicycleParkingApp object is declared in
 the Main class.
 */

public class BicycleParkingApp {
    private CyclistListManager cyclistListManager;
    private ParkingSpotListManager parkingSpotListManager;
    private BicycleListManager bicycleListManager;
    private TheftReportListManager theftReportListManager;
    private Cyclist currentCyclist;
    private Scanner scanner;

    // EFFECTS: Instantiates the program with an empty list of cyclists, theftReports and parking spots, adds the
    // sample set of parking spots, and creates a Scanner object.
    public BicycleParkingApp() {
        cyclistListManager = new CyclistListManager();
        parkingSpotListManager = new ParkingSpotListManager();
        scanner = new Scanner(System.in);

        while (true) {
            currentCyclist = cyclistListManager.selectCyclist();
            bicycleListManager = new BicycleListManager(currentCyclist);
            theftReportListManager = new TheftReportListManager(currentCyclist);
            while (true) {
                int menuReturnValue = cyclistMainMenu();
                if (menuReturnValue == 6) {
                    break; // Returns to the outer while loop (i.e. the account selection prompt).
                } else if (menuReturnValue == 7) {
                    return; // Terminates the program.
                } // Else, it returns to the main menu.
            }
        }
    }

    // MODIFIES: May modify currentCyclist, depending on the chosen option.
    // EFFECTS: Prints main menu and waits for user's input.
    // TODO: Add a function to allow the addition of parkingSpots (this is a feature not intended to be viewed by the
    //  end user, and instead exists to allow for easier addition of parkingSpots to the file that stores them in
    //  JSON format.
    private int cyclistMainMenu() {
        int input;
        System.out.println("\nMAIN MENU\nPlease enter the number of one of the options below:\n"
                + "\t[1] Add bicycle \n\t[2] Remove bicycle \n"
                + "\t[3] Search for a parking spot \n\t[4] File theft report \n"
                + "\t[5] View user profile, theft reports and bicycles \n"
                + "\t[6] Change user \n\t[7] Quit");
        input = scanner.nextInt();
        switch (input) {
            case 1: bicycleListManager.addBicycle();
                break;
            case 2: bicycleListManager.removeBicycle();
                break;
            case 3: parkingSpotListManager.searchParkingSpots();
                break;
            case 4: theftReportListManager.addTheftReport(parkingSpotListManager, bicycleListManager);
                break;
            case 5: viewUserProfile();
                break;
            default: return input;
        }
        return 0;
    }

    // EFFECTS: Prints out the currentCyclist's bicycles and filed theft reports.
    private void viewUserProfile() {
        System.out.println("Your name is: " + currentCyclist.getName());
        bicycleListManager.viewBicycles();
        System.out.printf("\n");
        theftReportListManager.viewTheftReports();
    }

}
