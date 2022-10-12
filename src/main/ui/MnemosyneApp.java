package ui;

import model.Event;
import model.EventList;

import java.util.Scanner;

public class MnemosyneApp {

    private final Scanner input;
    private final EventList list;
    private boolean isProgramRunning;
    private String userInput;

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: runs Mnemosyne application and processes user input
    public MnemosyneApp() {
        this.input = new Scanner(System.in);
        this.list = new EventList("New Events List");
        this.isProgramRunning = true;
        appIntro();
        while (isProgramRunning) {
            appIntroInstr();
            userInput = input.nextLine();

            if (!userInput.equals("cancel")) {
                System.out.println("This is great news!");
                provideResponse(userInput);
            } else {
                System.out.println("See you next time!");
                isProgramRunning = false;
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: prints welcome message and completes naming of new Event List
    public void appIntro() {
        System.out.println("Welcome to Mnemosyne!");
        System.out.println("What would you like to call this " + list.getListName() + " ?");
        userInput = input.nextLine();
        list.setListName(userInput);
        System.out.println("The name of your list is now " + list.getListName());
    }

    //EFFECTS:displays all app instructions/options for user
    public void appIntroInstr() {
        System.out.println("What would you like to do?");
        System.out.println("To add a new event, type \"make new event\"");
        System.out.println("To remove an event, type \"delete event\"");
        System.out.println("To change information on an existing event, type \"change event event_name\"");
        System.out.println("To see all of your events, type \"see event list\"");
        System.out.println("To leave the program, type \"cancel\"");
    }


    //MODIFIES: this
    //EFFECTS: processes the option user chose through their input
    public void provideResponse(String userInput) {
        if (userInput.equals("make new event")) {
            makeNewEvent();
        } else if (userInput.equals("delete event")) {
            deleteEvent();
        } else if ((userInput.contains("change event"))) {
            changeEvent(userInput);
        } else if ((list.getListSize() > 0) & userInput.equals("see event list")) {
            System.out.println("Here are your events:");
            printList(list);
        } else {
            isProgramRunning = false;
            System.out.println("Oops, there seems to have been an error");
            System.out.println("Please make sure you are typing in the correct commands,");
            System.out.println("and the correct name for any events");
        }
    }


    //EFFECTS: prints all the events in Event List to screen
    public void printList(EventList list) {
        for (int i = 0; i < list.getListSize(); i++) {
            Event e = list.getEvents().get(i);
            System.out.println(e.getEventName() + " due at " + e.getEventDueDate() + " which has the description "
                    + e.getEventDescription());
        }
    }

    //MODIFIES: this
    //EFFECTS: creates a new Event in Event List according to user specifications
    public void makeNewEvent() {
        System.out.println("Give this event a name:");
        String eventName = input.nextLine();
        System.out.println("When does this event take place?");
        String eventDate = input.nextLine();
        System.out.println("(Optional) Add a short description to help you remember this event:");
        String eventDescription = input.nextLine();
        list.addEventToList(new Event(eventName, eventDate, eventDescription));
    }

    //MODIFIES: this
    //EFFECTS: deletes the Event with the name supplied by the user
    public void deleteEvent() {
        System.out.println("No problem, what was the name of the event?:");
        String eventName = input.nextLine();
        list.removeEventFromList(eventName);
    }

    //MODIFIES: this
    //EFFECTS: changes name, date, or description of an Event according to user specifications
    public void changeEvent(String userInput) {
        System.out.println(
                "What would you like to change about the event? the name, the date, or" + " the description?");
        String answer = input.nextLine();
        Event e = list.findEvent((userInput.split("change event ", 2))[1]);
        if (answer.equals("name")) {
            System.out.println("What is the new name?");
            String name = input.nextLine();
            e.changeEventName(name);
        } else if (answer.equals("date")) {
            System.out.println("What is the new date?");
            String date = input.nextLine();
            e.changeEventDueDate(date);
        } else {
            System.out.println("What is the new description?");
            String description = input.nextLine();
            e.changeEventDescription(description);
        }
    }
}
