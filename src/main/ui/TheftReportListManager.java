package ui;

import model.Bicycle;
import model.Cyclist;
import model.ParkingSpot;
import model.TheftReport;

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
    public void addTheftReport(ParkingSpotListManager parkingSpotListManager, BicycleListManager bicycleListManager) {
        Bicycle bicycle;
        LocalDate date;
        List<ParkingSpot> parkingSpotSearchResults;
        ParkingSpot parkingSpot;

        System.out.printf("\nFILE A THEFT REPORT\n");
        bicycleListManager.viewBicycles();
        System.out.println("Which bicycle has been stolen? ");

        bicycle = cyclist.getBicycles().get(scanner.nextInt() - 1);

        System.out.println("Please input the postal code of the parking spot from which your bicycle was stolen: ");
        scanner.nextLine(); // Workaround to prevent skipping the subsequent nextLine command.
        parkingSpotSearchResults = parkingSpotListManager.viewParkingSpots(scanner.nextLine());

        System.out.println("From which of these parking spots has your bicycle been stolen?");
        parkingSpot = parkingSpotSearchResults.get(scanner.nextInt() - 1);

        System.out.printf("When was your bicycle stolen?");
        date = getDateFromUser();

        TheftReport theftReport = new TheftReport(bicycle, parkingSpot, date);
        theftReports.add(theftReport);
        cyclist.addTheftReport(theftReport);
        parkingSpot.incrementTheftReportNumber();
        System.out.println("< Your theft report has been filed. > You may view it by entering [5] in the main menu.");
    }

    // EFFECTS: Prints out a list of theft reports filed by the currentCyclist.
    public void viewTheftReports() {
        System.out.println("You currently have " + cyclist.getTheftReports().size() + " theft reports filed.");
        for (int i = 0; i < cyclist.getTheftReports().size(); i++) {
            System.out.printf("\t[%d] %s\n", i + 1, cyclist.getTheftReports().get(i).getFormattedDescription());
        }
    }
}
