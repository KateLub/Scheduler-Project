# Mnemosyne

## Personal Scheduling Application

The purpose of this application is to simplify
the *creation*, *scheduling*, and *updating*
of events or deadlines, by allowing a user to 
enter a series of errands, a short description, 
and the date each must be completed by. The 
application then sorts them in order from the 
closest due date to the farthest, and allows the 
tasks to be searched using several filters (such 
as due date, description keywords, or title), to 
have any details changed, or marked as completed.
In this way, Mnemosyne can be used by anyone 
who could use some help in keeping track of their
**day-to-day activities**, or for more **long-term
planning** and assistance in **prioritizing tasks**. 
This can be especially helpful to **students**, who 
have frequent small assignments, exams, projects,
and events to keep track of during the school year.
As someone who has used to-do lists, calendars, 
and planners to varying degrees of success throughout
my academic journey, I am highly invested and 
interested in this project, as I want to make 
something that I will ultimately be able to use
later on, and which I believe will be very helpful
in scheduling for several months in advance.

## User Stories for Phase 0

* As a user, I want to be able to add events to my list of events
* As a user, I want to be able to see all events added to the list
* As a user, I want all events to be sorted from the closest date they 
are due, to the furthest
* As a user, I want to be able to update/change details of the events 
without making a new event
* As a user, I want to be able to mark certain events as complete

## User Stories for Phase 2

* As a user, I want to be able to save all my events to file
* As a user, I want to be able to load all my events from file
* As a user, I want to get the option of saving or not the recent changes(since the last downloaded version) whenever 
I quit the application
* As a user, I want to get the option of opening the last downloaded version from file,
whenever I start the application


## Instructions for Grader for Phase 3

- You can generate the first required event related to adding Xs to a Y by clicking "ok" to the Welcome
popup that contains instructors for the user (will be the first thing to appear), then selecting "no" when 
the application asks if you want to load previous events. Finally, it will ask you to name your list (can be anything), 
after which you may input the name, due date, and (optionally) a description for the event and 
click "make event", your event will then show up on the screen.
- You can generate the second required event related to adding Xs to a Y by inputting the name, due-date, and 
description for an event in the bottom panel of the application and clicking "make event"
- My visual component is located in the message pane that pops up (titled "Welcome to Mnemosyne!")
when the application is run
- You can save the state of my application by closing the application through the button in the top left corner,
and clicking "yes" when it asks whether you would like to save your events
- You can reload the state of my application by selecting "yes" when you first open the 
application and it asks if you want to load your events (this option will only appear if there are
any events saved in myFile, otherwise, it will lead user straight to making a new list)

## Phase 4: Task 2
    Fri Dec 02 17:58:30 PST 2022
        Event List name set to UBC Due-Dates
    Fri Dec 02 17:58:55 PST 2022
        Event Midterm2 was added to list
    Fri Dec 02 17:59:15 PST 2022
        Event Co-op application was added to list
    Fri Dec 02 17:59:35 PST 2022
        Event Final Exam was added to list
    Fri Dec 02 17:59:47 PST 2022
        Event Final Exam was added to list
    Fri Dec 02 18:00:13 PST 2022
        Event Final Project was added to list
    Fri Dec 02 18:00:18 PST 2022
        Event Co-op application is completed
    Fri Dec 02 18:00:22 PST 2022
        Event Midterm2 is completed
    Fri Dec 02 18:00:34 PST 2022
        Event Final Exam description changed to English 110
    Fri Dec 02 18:00:54 PST 2022
        Event name changed to Physics Final
    Fri Dec 02 18:01:05 PST 2022
        Event Physics Final date changed to 2022-12-17
    Fri Dec 02 18:01:14 PST 2022
        Attempt to remove Event Co-op application was successfull

## Phase 4: Task 2
* I'd say my project has very little coupling apart from the fact that
both the Event and the EventList class implement the Writable interface.
I would thus consider removing the interface as part of refactoring this project,
considering that it only contains the toJson() method, which is overriden either way
in the classes currently implementing it. However, I feel that this decision
would also heavily depend on how the application is changed in the future, 
as it may prove a better design choice to maintain the distinction between the responsibility
of the classes working with Events, and classes responsible for persistance.
* On the other hand, I feel like there is low cohesion, particularly since
the MnemosyneApp class is responsible for absolutely everything to do with 
operating the application. When refactoring, I would want to separate the class according to
methods that are responsible for setting up the framework of the GUI (such as instantiating
and establishing the work of the frame, panels, buttons, the JTable and other such things). Another 
class could be delegated to handle the operation of the Events List in the scope
of the GUI, and so would contain all the methods for adding, removing, changing,
and influencing the state of the Events table as presented to the user.
* I believe that in terms of design patterns, the Composite Pattern would likely be least helpful,
since this application has a very simple hierarchy with one node (the Event list) containing an
undetermined number of children (the events). However, it could be useful if the
application was adapted to allow for different lists to be saved as part of a
bigger list (aka a main list of "Things to complete today" containing a grocery list,
a to-do list that another family member is responsible for, and several reminders). 
That way, there would be a Component class, an Event class which would be the leaf,
and an EventList class which would be the composite.
* The Observer Pattern also doesn't make much sense in the context of the 
application, as it neither requires to update something when an Event or EventList
changes state, nor does it really need to be notified of changing states of something else.
* I think that the Singleton Pattern would be most useful for this application,
since there is only one Event List at any given point in time, which could be refactored into a 
Singleton class. As it stands currently, if the Mnemosyne class is separated into several classes
(to increase cohesion), it could become necessary to pass the Event List instance
between the classes, which would increase the risk for errors. However, if Event List is 
a Singleton, the Event List would always only have one instance, and have a global point
of access. 