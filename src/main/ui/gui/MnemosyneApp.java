package ui.gui;

import model.Event;
import model.EventList;
import persistance.JsonReadFromFile;
import persistance.JsonWriteToFile;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;


// represents the Mnemosyne app, setting up the GUI, and handling all the app's manipulations and interactions
// with the EventList and Event class
public class MnemosyneApp {

    private static final String JSON_STORE = "./data/myFile.json";
    private JsonWriteToFile jsonWriter;
    private JsonReadFromFile jsonReader;

    private JLabel tableListNameHeading;
    private DefaultTableModel model;
    private JTable table;

    private EventList list;

    private JTextField eventName;
    private JTextField eventDate;
    private JTextField eventDesc;
    private JButton makeNewEventButton;

    private JFrame frame;
    private JPopupMenu popupMenu;


    //MODIFIES: this
    //EFFECTS: runs Mnemosyne application
    public MnemosyneApp() {
        jsonWriter = new JsonWriteToFile(JSON_STORE);
        jsonReader = new JsonReadFromFile(JSON_STORE);
        appPopUpIntroInstr();
        list = new EventList("New Event List");

        setUpFrame();
        setUpEventTable();
        setUpMakeEventPanel();
        appEventListGuiSetUp();

    }

    //MODIFIES: this
    //EFFECTS: all GUI features responsible for making the main frame of the app
    public void setUpFrame() {
        frame = new JFrame("Mnemosyne App");
        frame.getContentPane().setLayout(new BorderLayout());

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(frame, "Would you like to save your work?");
                if (option == JOptionPane.YES_OPTION) {
                    saveEventList();
                    System.exit(0);
                } else if (option == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
            }
        });
        frame.setSize(1000, 600);
        frame.setVisible(true);
    }

    //EFFECTS: creates visual for Table List-Name Heading
    private void setUpEventTableHeading() {
        tableListNameHeading = new JLabel(list.getListName());
        tableListNameHeading.setHorizontalAlignment(JLabel.CENTER);
        tableListNameHeading.setFont(new Font("Times New Roman", Font.PLAIN, 24));

        frame.getContentPane().add(tableListNameHeading, BorderLayout.PAGE_START);
    }

    //MODIFIES: this
    //EFFECTS: all GUI features responsible for table representing Event List
    public void setUpEventTable() {
        setUpEventTableHeading();

        String[] columns = new String[]{"Event Name", "Due Date", "Description", "Completed"};
        model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);
        table = new JTable(model);

        table.getTableHeader().setFont(new Font("Courier", Font.BOLD, 15));
        table.setFont(new Font("Times", Font.PLAIN, 12));
        table.setSelectionBackground(new Color(72,105,134));

        JScrollPane scrollPane = new JScrollPane(table);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        popupMenu = new JPopupMenu();
        deleteEvent();
        completeEvent();
        changeEvent();
        table.setComponentPopupMenu(popupMenu);
    }

    //MODIFIES: this
    //EFFECTS: entire bottom panel in app, all buttons/text fields for making a new Event
    public void setUpMakeEventPanel() {
        JPanel buttonPanel = new JPanel();
        eventName = new JTextField("Name");
        eventDate = new JTextField("yyyy-mm-dd");
        eventDesc = new JTextField("Optional Description");
        makeNewEventButton = new JButton("Make Event");

        makeNewEventButton.addActionListener(e -> addEvent(
                new Event(eventName.getText(), eventDate.getText(), eventDesc.getText())));

        buttonPanel.add(eventName);
        buttonPanel.add(eventDate);
        buttonPanel.add(eventDesc);
        buttonPanel.add(makeNewEventButton);
        frame.getContentPane().add(buttonPanel, BorderLayout.AFTER_LAST_LINE);
    }

    //MODIFIES: this
    //EFFECTS: prints welcome message and checks whether user wants to load previous work
    public void appEventListGuiSetUp() {
        loadEventList();
        if (list.getListName().equals("New Event List") && list.getEvents().isEmpty()) {
            namingList();
        } else {
            if (decideIfUsePreviousWork() == JOptionPane.YES_OPTION) {
                tableListNameHeading.setText(list.getListName());
                setUpTableWithSavedEvents();
            } else {
                namingList();
                list = new EventList(list.getListName());
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: changes list name and table header name to user input,
    // and shows the user a message confirming the change
    public void namingList() {
        list.setListName(nameEventList());
        tableListNameHeading.setText(list.getListName());
        JOptionPane.showMessageDialog(frame, "The name of your list is now " + list.getListName());
    }

    //MODIFIES: this
    //EFFECTS: asks user for a name for the Event List
    public String nameEventList() {
        return JOptionPane.showInputDialog("What would you like to name this list?");
    }

    //MODIFIES: this
    //EFFECTS: asks user if should load Event List from file
    public int decideIfUsePreviousWork() {
        return JOptionPane.showConfirmDialog(frame, "Would you like to Load your previous work?");
    }

    //MODIFIES: this
    //EFFECTS: provides pop-up message with app logo when starting app, w/t instr for how it works
    public void appPopUpIntroInstr() {
        ImageIcon logo = new ImageIcon("./data/MnemosyneLogo.png");
        Image image = logo.getImage(); // transform it
        Image newImg = image.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH);
        logo = new ImageIcon(newImg);

        JOptionPane.showMessageDialog(frame, "To make a new event for your list, enter the list details "
                + "\nin the bottom panel and click \"Make Event\". To Delete, Change, or Complete "
                + "\nan event, right-click on the event in the table and choose the option you prefer. "
                + "\nThen, follow the prompts to complete your desired action!", "Welcome to Mnemosyne!",
                JOptionPane.OK_OPTION, logo);
    }

    //MODIFIES: this
    //EFFECTS: all events transferred from list into empty table, in the correct format
    public void setUpTableWithSavedEvents() {
        String complete;

        for (Event e : list.getEvents()) {
            if (e.getIsCompleted()) {
                complete = "yes";
            } else {
                complete = "no";
            }
            model.addRow(new Object[]{e.getEventName(), e.getEventDueDate(), e.getEventDescription(), complete});
        }
    }

    //MODIFIES: this
    //EFFECTS: when user makes an event, event is added to the list, and to the table in correct format
    public void addEvent(Event e) {
        if (list.getListSize() == 0) {
            list.addEventToList(e);
            model.addRow(new Object[]{e.getEventName(), e.getEventDueDate(), e.getEventDescription(), "no"});
        } else if (list.addEventToList(e)) {
            list.sortEventList();
            model.insertRow(list.getEvents().indexOf(e),
                    new Object[]{e.getEventName(), e.getEventDueDate(), e.getEventDescription(), "no"});
        }
    }

    //MODIFIES: this
    //EFFECTS: "Delete" option added to pop-up for each row/event on table,
    // when user deletes an event, event is removed from list and from the table,
    // and change confirmed with message
    public void deleteEvent() {
        JMenuItem deleteItem = new JMenuItem("Delete");
        deleteItem.addActionListener(e -> {
            list.removeEventFromList((String) model.getValueAt(table.getSelectedRow(), 0));
            model.removeRow(table.getSelectedRow());
            JOptionPane.showMessageDialog(frame, "You have chosen to DELETE this event");
        });
        popupMenu.add(deleteItem);
    }

    //MODIFIES: this
    //EFFECTS: "Change" option added to pop-up for each row/event on table,
    // which creates another pop-up for user to specifically choose what part of Event they want to change
    public void changeEvent() {
        JMenuItem changeItem = new JMenuItem("Change");
        changeItem.addActionListener(e -> {
            String[] options = {"Name", "Date", "Description"};
            int returnValue = JOptionPane.showOptionDialog(frame,
                    "What would you like to change about this event?", "Change Event",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (returnValue != -1) {
                changeEventDetails(options[returnValue]);
            }
        });
        popupMenu.add(changeItem);
    }

    //MODIFIES: this
    //EFFECTS: using user input from changeEvent(), a pop-up is produced asking user what they want
    // to change that part of Event to, and that Event is changed accordingly in the list, and the table
    public void changeEventDetails(String eventDetail) {
        String input = JOptionPane.showInputDialog(frame,
                "What would you like to change the " + eventDetail + " to?");
        Event tempEvent = list.findEvent((String) model.getValueAt(table.getSelectedRow(), 0));

        if (eventDetail.equals("Name")) {
            tempEvent.changeEventName(input);
            model.setValueAt(input, table.getSelectedRow(), 0);
        } else if (eventDetail.equals("Date")) {
            tempEvent.changeEventDueDate(input);
            list.sortEventList();
            String complete = (String) model.getValueAt(table.getSelectedRow(), 3);
            model.removeRow(table.getSelectedRow());
            model.insertRow(list.getEvents().indexOf(tempEvent),
                    new Object[]{tempEvent.getEventName(),
                            tempEvent.getEventDueDate(), tempEvent.getEventDescription(), complete});
        } else {
            tempEvent.changeEventDescription(input);
            model.setValueAt(input, table.getSelectedRow(), 2);
        }
    }

    //MODIFIES: this
    //EFFECTS: "Complete" option added to pop-up for each row/event on table,
    // which will change the value in the "Completed" column of that row/event to "yes" from "no",
    // and confirms that change with message
    public void completeEvent() {
        JMenuItem completeEvent = new JMenuItem("Complete");
        completeEvent.addActionListener(e -> {
            list.findEvent((String) model.getValueAt(table.getSelectedRow(), 0)).completeEvent();
            model.setValueAt("yes", table.getSelectedRow(), 3);
            JOptionPane.showMessageDialog(frame, "You have chosen to COMPLETE this event");
        });
        popupMenu.add(completeEvent);
    }

    // The following two methods were adapted from WorkRoomApp class in:
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    // EFFECTS: saves the eventList to file
    protected void saveEventList() {
        try {
            jsonWriter.open();
            jsonWriter.write(list);
            jsonWriter.close();
            JOptionPane.showMessageDialog(frame,
                    list.getListName() + " and all its' events has been saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(frame,
                    "Unable to write to file: " + JSON_STORE, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads eventList from file
    private void loadEventList() {
        try {
            list = jsonReader.read();
            list.sortEventList();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame,
                    "Unable to read from file: " + JSON_STORE, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

