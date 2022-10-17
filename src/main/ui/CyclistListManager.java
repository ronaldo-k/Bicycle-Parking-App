package ui;

import model.Cyclist;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
A class that maintains, alters and displays a list of cyclists.
 */

public class CyclistListManager {
    BicycleListManager bicycleListManager;
    List<Cyclist> cyclists;
    Scanner scanner;

    // EFFECTS: Creates a CyclistListManager object with no registered cyclists.
    public CyclistListManager() {
        cyclists = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    // MODIFIES: this
    // EFFECTS:  Adds cyclist to the list of cyclists according to the user's input and returns added cyclist.
    public Cyclist createCyclist(Boolean isInitialCall) {
        String inputName;
        Cyclist newCyclist;

        // Workaround to prevent skipping of the next scanner.nextLine() call. Only necessary if another account is
        // created by pressing [6] on the main menu and [0] on the account selection menu.
        if (!isInitialCall) {
            scanner.nextLine();
        }

        System.out.printf("\nCREATE AN ACCOUNT\n \tPlease input your name: ");
        inputName = scanner.nextLine();

        newCyclist = new Cyclist(inputName);
        cyclists.add(newCyclist);
        System.out.printf("Welcome, %s!\n", newCyclist.getName());
        return newCyclist;
    }

    // MODIFIES: this (if a new cyclist is created)
    // EFFECTS:  Selects a cyclist according to user input and creates a cyclist if the user requests it or if the
    // cyclists list is empty.
    public Cyclist selectCyclist() {
        Cyclist currentCyclist;
        int input;

        if (cyclists.isEmpty()) {
            currentCyclist = createCyclist(true);
            return currentCyclist;
        }

        System.out.println("\nCHOOSE AN ACCOUNT");
        System.out.println("Please type the account's number, or type [0] to create an account:");
        this.getFormattedList();

        input = scanner.nextInt();

        if (input == 0) {
             // Workaround to prevent skipping of createCyclist()'s scanner.nextLine() call.
            currentCyclist = createCyclist(false);
            return currentCyclist;
        }

        currentCyclist = cyclists.get(input - 1);
        System.out.printf("Welcome back, %s!\n", currentCyclist.getName());
        return currentCyclist;
    }

    // EFFECTS: Prints a formatted list of cyclists.
    public void getFormattedList() {
        for (int i = 0; i < cyclists.size(); i++) {
            System.out.printf("\t[%d] %s\n", i + 1, cyclists.get(i).getName());
        }
    }
}
