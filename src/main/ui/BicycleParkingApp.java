package ui;

import model.*;
import ui.exceptions.NoBicyclesFoundException;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
 Class that represents an instance of the bicycle parking program. A BicycleParkingApp object is declared in
 the Main class.
 */

public class BicycleParkingApp extends JFrame implements ActionListener {
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;

    private CyclistListManager cyclistListManager;
    private ParkingSpotListManager parkingSpotListManager;
    private BicycleListManager bicycleListManager;
    private TheftReportListManager theftReportListManager;
    private Cyclist currentCyclist;
    private Scanner scanner;

    // GUI Components
    // Note: The main menu buttons are displayed as a ButtonGroup and stored as a map. buttonCommands stores the
    // action commands as received by the ActionListener (this), and buttonTitles stores the titles for each button as
    // displayed to the user. For clarity, the key for a button is its buttonCommand + "Button". buttonCommands and
    // buttonTitles should not be changed and must have the same length (number of elements).
    private Map<String, JButton> buttons = new HashMap<>();
    private static final String[] buttonCommands = {"addBicycle", "removeBicycle", "searchParkingSpots",
            "addTheftReport",
            "viewUserProfile", "changeUser", "quit"};
    private static final String[] buttonTitles = {"Add Bicycle", "Remove Bicycle", "Search Parking Spots",
            "File a Theft Report", "View User Profile", "Change User", "Quit"};

    // EFFECTS: Instantiates the program with an empty list of cyclists, theftReports and parking spots, adds the
    // sample set of parking spots, and creates a Scanner object.
    public BicycleParkingApp() {
        super("Bicycle Parking");

        Boolean loadSavedData = loadSavedDataPrompt();

        cyclistListManager = new CyclistListManager(loadSavedData);
        parkingSpotListManager = new ParkingSpotListManager();
        scanner = new Scanner(System.in);

        currentCyclist = cyclistListManager.selectCyclist();

        bicycleListManager = new BicycleListManager(currentCyclist);
        theftReportListManager = new TheftReportListManager(currentCyclist);

        initializeGraphics();
    }

    // EFFECTS: Shows dialog to ask whether the user wants to load their saved data. Returns true if the saved data
    // should be loaded, false otherwise.
    private Boolean loadSavedDataPrompt() {
        String[] options = {"Load", "Do Not Load"};

        int result = JOptionPane.showOptionDialog(this,
                "Would you like to load your saved data?", "Load saved data",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (result) {
            case 0: return true;
            default: return false;
        }
    }

    // MODIFIES: this and buttons
    // EFFECTS:  Initializes the graphic main menu
    private void initializeGraphics() {
        setLayout(new GridLayout(0,1));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        initializeMainMenu();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    // MODIFIES: this and buttons
    // EFFECTS:  Inserts label and buttons into main menu and
    private void initializeMainMenu() {
        JLabel welcomeLabel = new JLabel("Hello, " + currentCyclist.getName() + "!");
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        add(welcomeLabel, BorderLayout.PAGE_START);

        for (int i = 0; i < buttonCommands.length; i++) {
            JButton currentButton;
            buttons.put(buttonCommands[i] + "Button", new JButton(buttonTitles[i]));

            currentButton = buttons.get(buttonCommands[i] + "Button");
            currentButton.addActionListener(this);
            currentButton.setActionCommand(buttonCommands[i]);
            add(currentButton);
        }
    }

    // MODIFIES: this
    // EFFECTS:  Saves all data (unrelated to tests) to JSON files from the list managers.
    private void saveData() {
        cyclistListManager.saveCyclists();
        return;
    }

    // TODO: Review according to what is not implemented in the GUI.
    // MODIFIES: May modify currentCyclist, depending on the chosen option.
    // EFFECTS: Prints main menu and waits for user's input.
//    private int cyclistMainMenu() {
//        int input;
//        System.out.println("\nMAIN MENU\nPlease enter the number of one of the options below:\n"
//                + "\t[1] Add bicycle \n\t[2] Remove bicycle \n"
//                + "\t[3] Search for a parking spot \n\t[4] File theft report \n"
//                + "\t[5] View user profile, theft reports and bicycles \n"
//                + "\t[6] Change user \n\t[7] Save changes and quit");
//        input = scanner.nextInt();
//        switch (input) {
//            case 1: bicycleListManager.addBicycle();
//                break;
//            case 2: bicycleListManager.removeBicycle();
//                break;
//            case 3: parkingSpotListManager.searchParkingSpots();
//                break;
//            case 4: theftReportListManager.addTheftReport(parkingSpotListManager, bicycleListManager);
//                break;
//            case 5: viewUserProfile();
//                break;
//            default:
//                return input;
//        }
//        return 0;
//    }

    // EFFECTS: Prints out the currentCyclist's bicycles and filed theft reports.
    private void viewUserProfile() {
        System.out.println("Your name is: " + currentCyclist.getName());
        try {
            bicycleListManager.viewBicycles();
        } catch (NoBicyclesFoundException e) {
            System.out.println("You currently have no registered bicycles.");
        }
        System.out.printf("\n");
        theftReportListManager.viewTheftReports();
    }

    private void saveAndQuitPrompt() {
        String[] options = {"Save", "Do Not Save", "Cancel"};

        int result = JOptionPane.showOptionDialog(this,
                "Would you like to save your changes?", "Save and Quit",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (result) {
            case 0: // "Save"
                saveData();
                System.exit(0);
                break;
            case 1: // "Do Not Save"
                System.exit(0);
                break;
            default: // "Cancel"
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("addBicycle")) {
            bicycleListManager.addBicycle();
        } else if (command.equals("removeBicycle")) {
            bicycleListManager.removeBicycle();
        } else if (command.equals("searchParkingSpots")) {
            parkingSpotListManager.initializeSearchParkingSpotsWindow("", "Cancel");
        } else if (command.equals("addTheftReport")) {
            theftReportListManager.addTheftReport(parkingSpotListManager, bicycleListManager);
        } else if (command.equals("viewUserProfile")) {
            viewUserProfile();
        } else if (command.equals("changeUser")) {
            cyclistListManager.selectCyclist();
        } else if (command.equals("quit")) {
            saveAndQuitPrompt(); // something else
        }
    }
}
