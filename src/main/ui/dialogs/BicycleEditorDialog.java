package ui.dialogs;

import model.Bicycle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Class that creates a dialog to add or edit a bicycle.

public class BicycleEditorDialog implements ActionListener {
    private JDialog dialog;
    private JPanel panel;
    private JPanel entriesPanel;
    private static final String[] labels = {"Name: ", "Brand: ", "Model: ", "Description: ", "Serial number: "};
    private List<JLabel> entryLabels;
    private List<JTextField> entries;
    private JButton confirmButton;
    private Bicycle bicycle;

    // REQUIRES: defaultValues.size() == 5.
    // MODIFIES: All swing components.
    // EFFECTS:  Creates a dialog with each entry containing the values specified in defaultValues. Dialog is
    // initially not visible.
    public BicycleEditorDialog(String dialogTitle, List<String> defaultValues) {
        bicycle = null;

        if (defaultValues.isEmpty()) {
            for (int i = 0; i < 5; i++) {
                defaultValues.add("");
            }
        }

        dialog = new JDialog();
        dialog.setTitle(dialogTitle);
        dialog.setMinimumSize(new Dimension(640, 200));
        dialog.setAlwaysOnTop(true);
        dialog.setModal(true);

        panel = new JPanel(new BorderLayout());
        entriesPanel = new JPanel(new GridLayout(0, 2));
        entryLabels = new ArrayList<>();
        entries = new ArrayList<>();

        confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(this);
        confirmButton.setActionCommand("confirm");

        addAndSetElementsVisible();
        constructEntries(defaultValues);
    }

    // MODIFIES: All swing components.
    // EFFECTS:  Creates a dialog with all fields empty.
    public BicycleEditorDialog(String dialogTitle) {
        this(dialogTitle, new ArrayList<>());
    }

    // MODIFIES: entryLabels and entries
    // EFFECTS:  Constructs entries and their respective labels and sets each entry to their corresponding default value
    private void constructEntries(List<String> defaultValues) {
        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            JTextField entry = new JTextField(defaultValues.get(i));

            entriesPanel.add(label);
            entriesPanel.add(entry);

            entryLabels.add(label);
            entries.add(entry);
        }
    }

    // MODIFIES: panel, dialog and entriesPanel
    // EFFECTS:  Adds entriesPanel and confirmButton to panel, adds panel to dialog, and set panels to visible.
    public void addAndSetElementsVisible() {
        panel.add(entriesPanel, BorderLayout.CENTER);
        panel.add(confirmButton, BorderLayout.PAGE_END);
        dialog.add(panel);

        entriesPanel.setVisible(true);
        panel.setVisible(true);
    }

    // MODIFIES: bicycle and dialog
    // EFFECTS:  Shows this dialog and sets this.bicycle (the bicycle being edited) to bicycle (the parameter).
    public void initializeDialog(Bicycle bicycle) {
        this.bicycle = bicycle;
        dialog.setVisible(true);
    }

    public Bicycle getBicycle() {
        return bicycle;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("confirm")) {
            bicycle.setName(entries.get(0).getText());
            bicycle.setBrand(entries.get(1).getText());
            bicycle.setModel(entries.get(2).getText());
            bicycle.setDescription(entries.get(3).getText());
            bicycle.setSerialNumber(entries.get(4).getText());

            dialog.setVisible(false);
        }
    }
}
