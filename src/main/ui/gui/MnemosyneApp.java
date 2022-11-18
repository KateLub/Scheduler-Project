package ui.gui;

import model.Event;
import model.EventList;
import persistance.JsonReadFromFile;
import persistance.JsonWriteToFile;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MnemosyneApp {

    private static final String JSON_STORE = "./data/myFile.json";
    private JsonWriteToFile jsonWriter;
    private JsonReadFromFile jsonReader;

    private String[] columns;
    private JLabel lblHeading;
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
        list = new EventList("New Event List");

        lblHeading = new JLabel(list.getListName());
        lblHeading.setHorizontalAlignment(JLabel.CENTER);
        lblHeading.setFont(new Font("Times New Roman", Font.PLAIN, 24));

        setUpFrame();

        setUpEventTable();

        setUpMakeEventPanel();

        appEventListGuiSetUp();
    }

    public void setUpFrame() {
        frame = new JFrame("Mnemosyne App");
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(lblHeading, BorderLayout.PAGE_START);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(frame, "Would you lke to save your work?");
                if (option == JOptionPane.YES_OPTION) {
                    saveEventList();
                    System.exit(0);
                } else if (option == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
            }
        });
        frame.setSize(550, 200);
        frame.setVisible(true);
    }

    public void setUpEventTable() {
        columns = new String[]{"Event Name", "Due Date", "Description", "Completed"};
        model = new DefaultTableModel();
        table = new JTable(model);
        model.setColumnIdentifiers(columns);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        popupMenu = new JPopupMenu();
        deleteEvent();
        completeEvent();
        changeEvent();
        table.setComponentPopupMenu(popupMenu);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

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

        jsonWriter = new JsonWriteToFile(JSON_STORE);
        jsonReader = new JsonReadFromFile(JSON_STORE);
    }

    //MODIFIES: this
    //EFFECTS: prints welcome message and checks whether user wants to load previous work

    public void appEventListGuiSetUp() {
        loadEventList();
        appPopUpIntroInstr();
        if (list.getListName().equals("New Event List") && list.getEvents().isEmpty()) {
            namingList();
        } else {
            if (decideIfUsePreviousWork() == JOptionPane.YES_OPTION) {
                setUpTableWithSavedEvents();
            } else {
                namingList();
            }
        }
        changeHeading(list.getListName());
    }

    //MODIFIES: this
    //EFFECTS: completes naming of new Event List
    public void namingList() {
        list.setListName(nameEventList());
        JOptionPane.showMessageDialog(frame, "The name of your list is now " + list.getListName());
    }

    // The following two methods were adapted from WorkRoomApp class in:
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    public void changeHeading(String label) {
        lblHeading.setText(label);
    }

    public String nameEventList() {
        return JOptionPane.showInputDialog("What would you like to name this list?");
    }

    public int decideIfUsePreviousWork() {
        return JOptionPane.showConfirmDialog(frame, "Would you lke to Load your previous work?");
    }

    public void appPopUpIntroInstr() {
        JOptionPane.showMessageDialog(frame, "Welcome to Mnemosyne!"
                + "\n To make a new event for your list, enter the list details "
                + "\n in the bottom panel and click \"make event\". To delete, change or complete "
                + "\n an event, right-click on the event and choose the option you prefer. "
                + "\n Then, follow the prompts to complete your desired action!");
    }

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

    public void addEvent(Event e) {
        if (list.addEventToList(e)) {
            list.sortEventList();
            model.insertRow(list.getEvents().indexOf(e),
                    new Object[]{e.getEventName(), e.getEventDueDate(), e.getEventDescription(), "no"});
        }
    }

    public void deleteEvent() {
        JMenuItem deleteItem = new JMenuItem("Delete");
        deleteItem.addActionListener(e -> {
            list.removeEventFromList((String) model.getValueAt(table.getSelectedRow(), 0));
            model.removeRow(table.getSelectedRow());
            JOptionPane.showMessageDialog(frame, "Right-click performed on table and choose DELETE");
        });
        popupMenu.add(deleteItem);
    }

    public void changeEvent() {
        JMenuItem changeItem = new JMenuItem("Change");
        changeItem.addActionListener(e -> {
            String[] options = {"Name", "Date", "Description"};
            int returnValue = JOptionPane.showOptionDialog(frame,
                    "What would you like to change about this event?", "Change Event",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            changeEventDetails(options[returnValue]);
        });
        popupMenu.add(changeItem);
    }

    public void changeEventDetails(String eventDetail) {
        String input = JOptionPane.showInputDialog(frame,
                "What would you like to change the " + eventDetail + " to?");
        Event tempEvent = list.findEvent((String) model.getValueAt(table.getSelectedRow(), 0));

        if (eventDetail.equals("Name")) {
            tempEvent.changeEventName(input);
            model.setValueAt(input, table.getSelectedRow(), 0);
        } else if (eventDetail.equals("Date")) {
            tempEvent.changeEventDueDate(input);
            model.setValueAt(input, table.getSelectedRow(), 1);
        } else {
            tempEvent.changeEventDescription(input);
            model.setValueAt(input, table.getSelectedRow(), 2);
        }
    }

    public void completeEvent() {
        JMenuItem completeEvent = new JMenuItem("Complete");
        completeEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                list.findEvent((String) model.getValueAt(table.getSelectedRow(), 0)).completeEvent();
                model.setValueAt("yes", table.getSelectedRow(), 3);
                JOptionPane.showMessageDialog(frame, "Right-click performed on table and choose Complete");
            }
        });
        popupMenu.add(completeEvent);
    }


    // EFFECTS: saves the eventList to file
    protected void saveEventList() {
        try {
            jsonWriter.open();
            jsonWriter.write(list);
            jsonWriter.close();
            System.out.println(list.getListName() + " and all its' events has been saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads eventList from file
    private void loadEventList() {
        try {
            list = jsonReader.read();
            list.sortEventList();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}

