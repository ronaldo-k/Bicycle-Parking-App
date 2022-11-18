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