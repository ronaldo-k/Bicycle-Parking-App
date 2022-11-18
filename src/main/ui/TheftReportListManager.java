package ui;

import model.Bicycle;
import model.Cyclist;
import ui.exceptions.NoBicyclesFoundException;
import ui.exceptions.NoParkingSpotsFoundException;
import model.ParkingSpot;
import model.TheftReport;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TheftReportListManager {
    List<TheftReport> theftReports;
    Cyclist cyclist;
    Scanner scanner;
    ParkingSpot outputFromParkingSpotDialog;

    ParkingSpotListManager parkingSpotListManager;
    BicycleListManager bicycleListManager;

    // EFFECTS: Creates a TheftReportListManager object for the passed cyclist with no registered theft reports.
    public TheftReportListManager(Cyclist cyclist) {
        theftReports = new ArrayList<>();
        scanner = new Scanner(System.in);
        this.cyclist = cyclist;
    }

    // EFFECTS: Gets a date from the user and returns it.
    private LocalDate getDateFromUser() {
        int day;
        int month;
        int year;
        System.out.printf("When was your bicycle stolen?");
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
    public void addTheftReport(ParkingSpotListManager parkingSpotListManager, BicycleListManager bicycleListManager) {
        Bicycle bicycle;
        LocalDate date;
        ParkingSpot parkingSpot = null;
        this.parkingSpotListManager = parkingSpotListManager;
        this.bicycleListManager = bicycleListManager;

        System.out.printf("\nFILE A THEFT REPORT\n");
        bicycle = getBicycleForTheftReport(bicycleListManager);
        parkingSpot = getParkingSpotForTheftReport(parkingSpotListManager);
        date = getDateFromUser();

        TheftReport theftReport = new TheftReport(bicycle, parkingSpot, date);
        theftReports.add(theftReport);
        cyclist.addTheftReport(theftReport);
        parkingSpot.incrementTheftReportNumber();
        System.out.println("< Your theft report has been filed. >");
    }

    // EFFECTS: Returns bicycle selected by the user with bicycleListManager's GUI
    private Bicycle getBicycleForTheftReport(BicycleListManager bicycleListManager) {
        bicycleListManager.initializeViewBicyclesWindow("Please select which bicycle was stolen", "Confirm");
        return bicycleListManager.getOutput();
    }

    // EFFECTS: Returns parkingSpot selected by the user with parkingSpotListManager's GUI
    private ParkingSpot getParkingSpotForTheftReport(ParkingSpotListManager parkingSpotListManager) {
        parkingSpotListManager.initializeSearchParkingSpotsWindow("Please input the postal code of the"
                + "parking spot from which your bicycle was stolen", "Confirm");
        return parkingSpotListManager.getOutput();
    }

    // EFFECTS: Prints out a list of theft reports filed by the currentCyclist.
    public void viewTheftReports() {
        System.out.println("You currently have " + cyclist.getTheftReports().size() + " theft reports filed.");
        for (int i = 0; i < cyclist.getTheftReports().size(); i++) {
            System.out.printf("\t[%d] %s\n", i + 1, cyclist.getTheftReports().get(i).getFormattedDescription());
        }
    }

    public void setCyclist(Cyclist cyclist) {
        this.cyclist = cyclist;
    }
}
