package ui;

/*
A class that maintains, alters and displays a list of bicycles registered by cyclist.
 */

import model.Bicycle;
import model.Cyclist;
import ui.exceptions.NoBicyclesFoundException;

import java.util.Scanner;

public class BicycleListManager {
    Cyclist cyclist;
    Scanner scanner;

    // EFFECTS: Creates a BicycleListManager that maintains cyclist's list of bicycles.
    public BicycleListManager(Cyclist cyclist) {
        this.cyclist = cyclist;
        scanner = new Scanner(System.in);
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

    // EFFECTS: Prints out a list of the cyclist's bicycles.
    public void viewBicycles() throws NoBicyclesFoundException {
        if (cyclist.getBicycles().size() == 0) {
            throw new NoBicyclesFoundException();
        }
        System.out.println("\nYou currently have " + cyclist.getBicycles().size() + " bicycles registered.");
        for (int i = 0; i < cyclist.getBicycles().size(); i++) {
            System.out.printf("\t[%d] %s\n", i + 1, cyclist.getBicycles().get(i).getFormattedDescription("\t"));
        }
    }

    // MODIFIES: cyclist
    // EFFECTS:  Removes the bicycle chosen from cyclist's list of bicycles.
    public void removeBicycle() {
        int inputBicycleIndex;
        Bicycle bicycle;

        System.out.printf("\nREMOVE A BICYCLE");
        try {
            viewBicycles();
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

}
