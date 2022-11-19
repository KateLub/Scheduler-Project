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