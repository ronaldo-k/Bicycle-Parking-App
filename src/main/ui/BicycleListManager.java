package ui;

/*
A class that maintains, alters and displays a list of bicycles registered by cyclist.
 */

import model.Bicycle;
import model.Cyclist;
import ui.dialogs.BicycleEditorDialog;
import ui.exceptions.NoBicyclesFoundException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BicycleListManager implements ActionListener, ListSelectionListener {
    Cyclist cyclist;
    Scanner scanner;
    Bicycle output;

    // EFFECTS: Creates a BicycleListManager that maintains cyclist's list of bicycles.
    public BicycleListManager(Cyclist cyclist) {
        this.cyclist = cyclist;
        scanner = new Scanner(System.in);
        constructGUI();
    }

    // GUI dialog and related components
    private JDialog dialog;
    private JPanel panel; // Dialog's main panel
    private JPanel inputPanel; // Panel that contains the following two fields and buttons.
    private JLabel instructionLabel;
    private JTextField entry;
    private JLabel entryLabel;
    private JButton searchButton;
    private JButton showAllButton;
    private JPanel listPanel; // Panel that contains the following JList
    private JList queryList;
    private JPanel optionPanel; // Panel that contains the following three buttons
    private JButton addBicycleButton;
    private JButton editBicycleButton;
    private JButton removeBicycleButton;
    private JButton proceedButton;
    private List<Bicycle> searchResults; // Stores user query's search results

    // MODIFIES: All swing objects in this class
    // EFFECTS:  Instantiates all swing objects and adds
    private void constructGUI() {
        dialog = new JDialog();
        dialog.setModal(true);
        dialog.setMinimumSize(new Dimension(640, 440));
        panel = new JPanel(new BorderLayout());
        instructionLabel = new JLabel();

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
        entryLabel = new JLabel("Search by serial number:");
        entry = new JTextField(12);

        searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        searchButton.setActionCommand("search");

        showAllButton = new JButton("Show All Bicycles");
        showAllButton.addActionListener(this);
        showAllButton.setActionCommand("showAllBicycles");

        inputPanel.add(entryLabel);
        inputPanel.add(entry);
        inputPanel.add(searchButton);
        inputPanel.add(showAllButton);

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
        queryList.addListSelectionListener(this);

        listPanel.add(queryList);

        listPanel.setVisible(true);
        panel.add(listPanel, BorderLayout.CENTER);
    }

    // MODIFIES: optionPanel, addBicycleButton, editBicycleButton and removeBicycleButton
    // EFFECTS:  Instantiates and adds all elements of the third (options) row of the GUI to panel.
    private void constructOptionPanel() {
        optionPanel = new JPanel(new GridLayout(1,3));
        addBicycleButton = new JButton("Add Bicycle");
        editBicycleButton = new JButton("Edit Bicycle");
        removeBicycleButton = new JButton("Remove Bicycle");
        proceedButton = new JButton();

        addBicycleButton.addActionListener(this);
        editBicycleButton.addActionListener(this);
        removeBicycleButton.addActionListener(this);
        proceedButton.addActionListener(this);

        editBicycleButton.setEnabled(false);
        removeBicycleButton.setEnabled(false);

        addBicycleButton.setActionCommand("addBicycle");
        editBicycleButton.setActionCommand("editBicycle");
        removeBicycleButton.setActionCommand("removeBicycle");
        proceedButton.setActionCommand("proceed");

        addOptionButtonsToPanel();
    }

    // MODIFIES: optionPanel and panel
    // EFFECTS:  Adds buttons to optionPanel, which is then added to panel
    private void addOptionButtonsToPanel() {
        optionPanel.add(addBicycleButton);
        optionPanel.add(editBicycleButton);
        optionPanel.add(removeBicycleButton);
        optionPanel.add(proceedButton);
        optionPanel.setVisible(true);
        panel.add(optionPanel, BorderLayout.PAGE_END);
    }

    // MODIFIES: dialog
    // EFFECTS:  Displays a panel that shows a list of the user's bicycles.
    public void initializeViewBicyclesWindow(String instructionLabelString, String proceedButtonString) {
        output = null;
        instructionLabel.setText(instructionLabelString);
        proceedButton.setText(proceedButtonString);
        dialog.setVisible(true);
    }

    // EFFECTS:  Obtains the user's input to create and return a bicycle object.
    private Bicycle createBicycle() {
        String inputName;
        String inputBrand;
        String inputModel;
        String inputDescription;
        String inputSerialNumber;
        Bicycle bicycle;

        System.out.printf("First, enter a name to identify your bicycle.\n\tName: ");
        inputName = scanner.nextLine();
        System.out.printf("Now, supply the following information:\n\tBrand: ");
        inputBrand = scanner.nextLine();
        System.out.printf("\tModel: ");
        inputModel = scanner.nextLine();
        System.out.printf("Add a description of any identifying details, such as accessories, decals, etc.: \n"
                + "(Optional – Press enter to skip)\n");
        inputDescription = scanner.nextLine();
        System.out.printf("Lastly, enter the bicycle's serial number: \n(This can usually be found under the "
                + "bottom bracket)\n");
        inputSerialNumber = scanner.nextLine();

        bicycle = new Bicycle(inputName, inputBrand, inputModel, inputDescription, inputSerialNumber);

        return bicycle;
    }

    // MODIFIES: cyclist
    // EFFECTS:  Creates new bicycle according to the user's input and adds it to the currentCyclist's list of bicycles.
    public void addBicycle() {
        Bicycle bicycle;
        System.out.printf("\nADD A BICYCLE\n");
        bicycle = createBicycle();
        cyclist.addBicycle(bicycle);
        System.out.println("< Your bicycle has been successfully added! > You can view it by entering [5] in the main"
                + " menu.");
    }

    // REQUIRES: As required by the definition of the Bicycle class, serialNumbers are unique.
    // MODIFIES: searchResults
    // EFFECTS:  Adds the bicycle whose serial number matches the serialNumber parameter.
    // Note: If serialNumber is null, all bicycles are added to searchResults.
    public void viewBicycles(String serialNumber) throws NoBicyclesFoundException {
        searchResults = new ArrayList<>();

        if (cyclist.getBicycles().size() == 0) {
            throw new NoBicyclesFoundException();
        }
        if (serialNumber == null) {
            searchResults = cyclist.getBicycles();
        }

        for (Bicycle bicycle : cyclist.getBicycles()) {
            if (bicycle.getSerialNumber().equals(serialNumber)) {
                searchResults.add(bicycle);
                return;
            }
        }
    }

    // MODIFIES: cyclist
    // EFFECTS:  Removes the bicycle chosen from cyclist's list of bicycles.
    public void removeBicycle() {
        int inputBicycleIndex;
        Bicycle bicycle;

        System.out.printf("\nREMOVE A BICYCLE");
        try {
            viewBicycles(null);
        } catch (NoBicyclesFoundException e) {
            System.out.println("\nThis user currently has no registered bicycles.");
        }
        if (cyclist.getBicycles().isEmpty()) {
            return;
        }
        System.out.println("Which bicycle would you like to remove?");

        inputBicycleIndex = scanner.nextInt();
        inputBicycleIndex--;

        bicycle = cyclist.getBicycles().get(inputBicycleIndex);

        System.out.printf("< \"%s\" has been removed from your list. >\n", bicycle.getName());
        cyclist.removeBicycle(bicycle);
    }

    private void setSearchResultsAndQueryList(Boolean getAll) {
        List<String> formattedSearchResults = new ArrayList<>();

        try {
            if (getAll) {
                viewBicycles(null);
            } else {
                viewBicycles(entry.getText());
            }
            for (Bicycle bicycle: searchResults) {
                formattedSearchResults.add(bicycle.getName());
            }
        } catch (NoBicyclesFoundException exception) {
            formattedSearchResults.add("No parking spots with this postal code were found.");
        }

        queryList.setListData(formattedSearchResults.toArray());
    }

    public void setCyclist(Cyclist cyclist) {
        this.cyclist = cyclist;
    }

    public Bicycle getOutput() {
        return output;
    }

    public List<Bicycle> getSearchResults() {
        return searchResults;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if (actionCommand.equals("search")) {
            setSearchResultsAndQueryList(false);
        } else if (actionCommand.equals("showAllBicycles")) {
            setSearchResultsAndQueryList(true);
        } else if (actionCommand.equals("addBicycle")) {
            BicycleEditorDialog bicycleEditorDialog = new BicycleEditorDialog("Add a bicycle");
            bicycleEditorDialog.initializeDialog(new Bicycle());
            cyclist.addBicycle(bicycleEditorDialog.getBicycle());
            setSearchResultsAndQueryList(true);
        } else if (actionCommand.equals("editBicycle")) {
            Bicycle selectedBicycle = searchResults.get(queryList.getSelectedIndex());
            List<String> defaultFieldValues = new ArrayList<>();

            defaultFieldValues.add(selectedBicycle.getName());
            defaultFieldValues.add(selectedBicycle.getBrand());
            defaultFieldValues.add(selectedBicycle.getModel());
            defaultFieldValues.add(selectedBicycle.getDescription());
            defaultFieldValues.add(selectedBicycle.getSerialNumber());

            BicycleEditorDialog bicycleEditorDialog = new BicycleEditorDialog("Edit “"
                    + selectedBicycle.getName() + "”", defaultFieldValues);
            bicycleEditorDialog.initializeDialog(selectedBicycle);
            // Because a pointer of selectedBicycle is passed, the bicycle object is modified directly from the
            // bicycleEditorDialog
            setSearchResultsAndQueryList(true);
        }
    }

    // EFFECTS:  Sets
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!queryList.isSelectionEmpty()) {
            editBicycleButton.setEnabled(true);
            removeBicycleButton.setEnabled(true);
        } else {
            editBicycleButton.setEnabled(false);
            removeBicycleButton.setEnabled(false);
        }
    }
}
