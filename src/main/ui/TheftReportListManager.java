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
    // TODO: Reduce method length
    public void addTheftReport(ParkingSpotListManager parkingSpotListManager, BicycleListManager bicycleListManager) {
        Bicycle bicycle;
        LocalDate date;
        ParkingSpot parkingSpot;

        System.out.printf("\nFILE A THEFT REPORT\n");
        try {
            bicycle = getBicycleForTheftReport(bicycleListManager);
        } catch (NoBicyclesFoundException e) {
            System.out.println("No registered bicycles found. Please register a bicycle before filing a theft report.");
            return;
        }

        try {
            parkingSpot = getParkingSpotForTheftReport(parkingSpotListManager);
        } catch (NoParkingSpotsFoundException e) {
            return;
        }

        System.out.printf("When was your bicycle stolen?");
        date = getDateFromUser();

        TheftReport theftReport = new TheftReport(bicycle, parkingSpot, date);
        theftReports.add(theftReport);
        cyclist.addTheftReport(theftReport);
        parkingSpot.incrementTheftReportNumber();
        System.out.println("< Your theft report has been filed. > You may view it by entering [5] in the main menu.");
    }

    // TODO: DOCUMENTATION FOR THIS METHOD
    private Bicycle getBicycleForTheftReport(BicycleListManager bicycleListManager) throws NoBicyclesFoundException {
        bicycleListManager.viewBicycles();
        System.out.println("Which bicycle has been stolen? ");
        return cyclist.getBicycles().get(scanner.nextInt() - 1);
    }

    // TODO: DOCUMENTATION FOR THIS METHOD
    private ParkingSpot getParkingSpotForTheftReport(ParkingSpotListManager parkingSpotListManager)
            throws NoParkingSpotsFoundException {
        List<ParkingSpot> parkingSpotSearchResults;
        System.out.println("Please input the postal code of the parking spot from which your bicycle was stolen: ");
        scanner.nextLine(); // Workaround to prevent skipping of the subsequent nextLine command.

        parkingSpotSearchResults = parkingSpotListManager.viewParkingSpots(scanner.nextLine());
        System.out.println("From which of these parking spots has your bicycle been stolen?");
        return parkingSpotSearchResults.get(scanner.nextInt() - 1);
    }

    // EFFECTS: Prints out a list of theft reports filed by the currentCyclist.
    public void viewTheftReports() {
        System.out.println("You currently have " + cyclist.getTheftReports().size() + " theft reports filed.");
        for (int i = 0; i < cyclist.getTheftReports().size(); i++) {
            System.out.printf("\t[%d] %s\n", i + 1, cyclist.getTheftReports().get(i).getFormattedDescription());
        }
    }
}
