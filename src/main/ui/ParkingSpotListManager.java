package ui;

import model.Address;
import model.Cyclist;
import persistence.ArrayJsonWriter;
import persistence.ParkingSpotsJsonReader;
import persistence.Saveable;
import ui.exceptions.NoParkingSpotsFoundException;
import model.ParkingSpot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import static java.awt.event.KeyEvent.*;

/*
A class that maintains and displays a list of parking spots.
 */

public class ParkingSpotListManager implements ActionListener {
    private List<ParkingSpot> parkingSpots;
    Scanner scanner;
    String source = "./data/parkingSpots.json";
    String imagesFolder = "./data/images/";

    // GUI dialog and related components
    JDialog dialog;
    JPanel panel; // Dialog's main panel
    JPanel inputPanel; // Panel that contains the following two fields and a button.
    JTextField entry;
    JLabel entryLabel;
    JButton searchButton;
    JPanel listPanel; // Panel that contains the following JList
    JList queryList;
    JPanel optionPanel; // Panel that contains the following two buttons
    JButton moreInformationButton;
    JButton proceedButton;
    List<ParkingSpot> searchResults; // Stores user query's search results


    // EFFECTS: Creates an instance of ParkingSpotListManager with the sample set of parking spots.
    public ParkingSpotListManager() {
        parkingSpots = new ArrayList<>();
        scanner = new Scanner(System.in);
        readParkingSpots();
        constructGUI();
    }

    // MODIFIES: this
    // EFFECTS:  Reads parkingSpots from parkingSpots.json and inserts them into the internal parkingSpots list.
    public void readParkingSpots() {
        ParkingSpotsJsonReader reader = new ParkingSpotsJsonReader(source);

        try {
            List<Saveable> saveableParkingSpots = reader.read();

            for (Saveable saveable : saveableParkingSpots) {
                parkingSpots.add((ParkingSpot) saveable);
            }
        } catch (IOException e) {
            System.out.println("[Error - IOException] An unexpected error occurred while attempting to read "
                    + "parkingSpots.json.");
        }
    }

    // MODIFIES: data/parkingSpots.json
    // EFFECTS:  Saves parkingSpots to parkingSpots.json
    public void saveParkingSpots() {
        ArrayJsonWriter writer = new ArrayJsonWriter(source);
        List<Saveable> saveables = new ArrayList<>();

        for (ParkingSpot parkingSpot : parkingSpots) {
            saveables.add(parkingSpot);
        }

        try {
            writer.open();
            writer.write(saveables);
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("[Error - FileNotFoundException] parkingSpots.json file not found.");
        }
    }

    // MODIFIES: All swing objects in this class
    // EFFECTS:  Instantiates all swing objects and adds
    private void constructGUI() {
        dialog = new JDialog();
        dialog.setMinimumSize(new Dimension(640, 440));
        panel = new JPanel(new BorderLayout());

        constructInputPanel();
        constructListPanel();
        constructOptionPanel();

        panel.setVisible(true);
        dialog.add(panel);
    }

    // MODIFIES: inputPanel, entryLabel, entry and panel
    // EFFECTS:  Instantiates and adds all elements of the first (input) row of the GUI to panel.
    private void constructInputPanel() {
        inputPanel = new JPanel(new FlowLayout());
        entryLabel = new JLabel("Postal code:");
        entry = new JTextField(12);

        searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        searchButton.setActionCommand("search");

        inputPanel.add(entryLabel);
        inputPanel.add(entry);
        inputPanel.add(searchButton);

        inputPanel.setVisible(true);
        panel.add(inputPanel, BorderLayout.PAGE_START);
    }

    // MODIFIES: listPanel, queryList and panel
    // EFFECTS:  Instantiates and adds all elements of the second (list) row of the GUI to panel.
    private void constructListPanel() {
        listPanel = new JPanel(new BorderLayout());
        queryList = new JList<>();
        queryList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        queryList.setLayoutOrientation(JList.VERTICAL);

        listPanel.add(queryList);

        listPanel.setVisible(true);
        panel.add(listPanel, BorderLayout.CENTER);
    }

    // MODIFIES: optionPanel, moreInformationButton, proceedButton and panel
    // EFFECTS:  Instantiates and adds all elements of the third (options) row of the GUI to panel.
    private void constructOptionPanel() {
        optionPanel = new JPanel(new GridLayout(1,2));
        moreInformationButton = new JButton("More Information");
        proceedButton = new JButton("Cancel");

        moreInformationButton.addActionListener(this);
        proceedButton.addActionListener(this);
        moreInformationButton.setActionCommand("moreInformation");
        proceedButton.setActionCommand("proceed");

        optionPanel.add(moreInformationButton);
        optionPanel.add(proceedButton);
        optionPanel.setVisible(true);
        panel.add(optionPanel, BorderLayout.PAGE_END);
    }

    // MODIFIES: dialog
    // EFFECTS:  Displays a panel that shows a list of parking spots according to the user's input postal code.
    public void initializeSearchParkingSpotsWindow() {
        dialog.setVisible(true);
    }

    private void generateMoreInformationDialog(int index) {
        JDialog moreInformationDialog = new JDialog();
        moreInformationDialog.setSize(new Dimension(640, 560));

        JPanel moreInformationPanel = new JPanel(new FlowLayout());

        JTextArea moreInformationText = new JTextArea(14, 48);
        moreInformationText.setEditable(false);
        String text =
                searchResults.get(index).getFormattedDescription("") + "\n" + searchResults.get(index).getUniqueID();

        ImageIcon image = new ImageIcon(imagesFolder + searchResults.get(index).getUniqueID() + ".jpg");
        JLabel moreInformationPicture = new JLabel(image);

        moreInformationText.setLineWrap(true);
        moreInformationText.setWrapStyleWord(true);
        moreInformationText.setText(text);

        moreInformationPanel.add(moreInformationPicture);
        moreInformationPanel.add(moreInformationText);
        moreInformationPanel.setVisible(true);

        moreInformationDialog.add(moreInformationPanel);
        moreInformationDialog.setVisible(true);
    }

    // EFFECTS: Prints out a list of bicycle parking spots available in the provided inputPostalCode.
    public List<ParkingSpot> viewParkingSpots(String inputPostalCode) throws NoParkingSpotsFoundException {
        searchResults = new ArrayList<>();

        for (ParkingSpot parkingSpot : parkingSpots) {
            if (inputPostalCode.equalsIgnoreCase(parkingSpot.getAddress().getPostalCode())) {
                searchResults.add(parkingSpot);
            }
        }
        if (searchResults.size() == 0) {
            throw new NoParkingSpotsFoundException();
        }

        // The code below is related to terminal-based displaying of parking spots. It has been commented out for the
        // Parking Spots GUI implementation.
//        System.out.println("The following parking spots with the postal code " + inputPostalCode
//                + " have been found:");
//        for (int i = 0; i < searchResults.size(); i++) {
//            System.out.printf("\t[%d] %s\n", i + 1, searchResults.get(i).getFormattedDescription("\t"));
//        }
        return searchResults;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<String> formattedSearchResults = new ArrayList<>();

        if (e.getActionCommand().equals("search")) {
            try {
                searchResults = viewParkingSpots(entry.getText());
                for (ParkingSpot parkingSpot : searchResults) {
                    formattedSearchResults.add(parkingSpot.getAddress().getFormattedAddress());
                }
            } catch (NoParkingSpotsFoundException exception) {
                formattedSearchResults.add("No parking spots with this postal code were found.");
            }
            queryList.setListData(formattedSearchResults.toArray());
        } else if (e.getActionCommand().equals("moreInformation")) {
            generateMoreInformationDialog(queryList.getSelectedIndex());
        }
    }
}
