# Bicycle Parking Facilities Map Application

## Proposal

The application is intended to provide cyclists with a map of nearby bicycle parking facilities, such as racks, lockers
and parkades, given a specified address and to inform them of user-provided statistics regarding the security of each
facility, including reports of theft of bicycles stationed at a given parking spot. Although cyclists will be the
primary users, both those that use their bicycles for commuting and for sport, those that plan to install bicycle
parking (such as business owners and transit and governmental agencies) can also use the application to register their
facilities into the database.

Cyclists in large cities often find themselves discouraged to incorporate their bicycle as a means of 
transport for their daily activities due to the apparent high levels of theft and the inconsistent presence and 
security of end of trip and parking facilities. 
Taking this into consideration, this application's goal is to be a proof of concept of a piece of software that, if 
integrated with an actively updated database (which is outside the scope of the current project), could demonstrate 
the current condition of bicycle parking in major cities, motivating the adoption of cycling as a reliable, 
sustainable and healthy means of transport and the action on behalf of local government agencies to promote secure 
cycling and parking.

## User Stories

- As a user, I want to be able to add my bicycle to my list of bicycles.
- As a user, I want to be able to search for parking facilities of a certain type near a given address.
- As a user, I want to be able to see reports on the incidence of bicycle theft for a given parking facility.
- As a user, I want to be able to report that one of my bikes were stolen from a given parking facility.
- As a user, I want to be able to save my user profiles with their respective bicycles and theft reports.
- As a user, I want to be able to view and add to my user profiles, their respective bicycles and theft reports after 
  closing and reopening the program. 

## Instructions for grader

- The program prompts the user with a dialog asking whether they want to load the saved data upon startup. Then, the 
  user selection prompt is shown in the terminal.
- In the main menu (GUI frame), you can access the required “Add multiple Xs to Y” events by pressing the “View and 
  Edit Bicycles button”. This opens a dialog.
  - The first required event is adding bicycles. It can be accessed with the “Add Bicycle” button on the dialog. This 
    prompts the user with another dialog with which the user can insert information about the bicycle being added. After 
    pressing “Confirm”, the new bicycle should be visible, along with the user's other bicycles, in the first dialog.
  - The second required event is editing bicycles. It can be accessed with the “Edit Bicycle” button on the dialog, 
    which shows a prompt similar to that used to add bicycles.
- The graphical component can be seen using the dialog that opens when pressing “View Parking Spots”. Input a postal 
  code in the “Postal Code” field and press search to search for parking spots within the postal code. Select one of 
  the listed parking spots and press more information. This opens a dialog containing the graphical component (a 
  photo of the parking spot) together with information regarding the selected parking spot.  
  - Note: The following postal codes have registered parking spots: V6T1Z3, V6T1Z4, V6T2A1, V6T2G9.
- The program prompts the user with a dialog asking whether they want to save their changes upon closing.

## Logging

The following log has been obtained in an execution of the program where “My bicycle” was removed and “new bicycle” 
was added to User1's bicycles. The following user actions were taken:

- Upon startup, the option to load the saved data (from data/cyclists.json and data/parkingSpots.json) was selected.
- In the terminal, User1 was chosen from a list of two users. User1 has a single bicycle called “My bicycle” already 
  registered.
- In the main menu, the “View and Edit Bicycles” item was selected.
- In the “View and Edit Bicycles” prompt, the “Show All Bicycles” button was pressed once.
- “My bicycle” was selected in the list of bicycles and removed with the “Remove Bicycle” button. The program would 
  show here the current state of the list of bicycles if there were any, which would register as a series of “A 
  bicycle of name X has been displayed” events.
- The “Add Bicycle” button is clicked and a bicycle of name “New bicycle” is added. Then, the program automatically 
  shows all registered bicycles in the list, which prompts the last event to be listed.
- The buttons “Cancel”, “Quit” and “Do Not Save” are clicked, exiting the application.

The corresponding log has been copied below:

```
Tue Nov 29 19:23:53 PST 2022
A bicycle of name My bicycle has been displayed.
Tue Nov 29 19:24:13 PST 2022
A bicycle of name My bicycle has been removed from User1's bicycles.
Tue Nov 29 19:25:10 PST 2022
A bicycle of name New Bicycle has been added to User1's bicycles.
Tue Nov 29 19:25:10 PST 2022
A bicycle of name New Bicycle has been displayed.
```

## Planned design improvements

According to the UML diagram that represents the program, certain elements of the project could be refactored to 
improve its clarity, reduce coupling and increase cohesion. Of the many possible improvements, I highlight a more 
consistent employment of inheritance and a more precise separation between user interface and model classes.

Certain classes that have comparable functions, namely ArrayJsonWriter and the classes that extend ArrayJsonReader, 
have different inheritance patterns. Whereas ArrayJsonWriter can write objects of any type implementing the Saveable 
interface to .json files, given that classes that implement Saveable must implement a version of the toJson() method,
the use of an abstract ArrayJsonReader class that was extended by three classes specific to a certain object is not 
only inconsistent, but also implies that the code in question cannot be changed easily. This could be solved by 
including the declaration of a decoding fromJson() method, similar to the parseSaveable() method in the classes that 
extend SaveableParser, in the Saveable interface.

The ListManager classes, which are part of the ui package, connects the model classes to the ui by both maintaining 
a list of their designated type and interacting with the user. This implies that the ListManager classes have low 
cohesion as they group functions which would have been more appropriately split up between classes in both the model 
and ui packages. Furthermore, the classes in question have a significant number of methods in common, which could 
have been grouped into an AbstractListManager class.

Also of note are the User abstract class and the PointOfInterest interface. In the scope of a refactoring without 
the addition of other features to the program, it can be noted that these are redundant and could be removed with no 
changes to the current features. These were intended to accommodate user stories that were excluded from the 
project in the interest of time.