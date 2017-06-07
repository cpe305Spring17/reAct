# React
### *Unlock your potential*
###### spring2017-project-ndara
[![Build Status](https://travis-ci.org/cpe305Spring17/spring2017-project-ndara.svg?branch=master)](https://travis-ci.org/cpe305Spring17/spring2017-project-ndara)
My project for CPE305 in Spring 2017

Developer:  Nishanth Dara <br/>
Platform:   Android Application <br/>
IDE:        Android Studio <br/>

### React
React is an Android application where you may visualize your time. Users may create activities and log events for your activities. React will aggregate these events to provide user-friendly visualizations. Furthermore, with React's functionality allowing users to set goals, users may improve their productivity. Data motivate, goals motivate, therefore React motivates the user to unlock their potential.

### How to Get Started
React is still under production and has not yet reached the Play Store. If you'd like to try the beta version...
1. Download Android Studio.
2. Clone this repository onto your local machine.
3. Boot up Android Studio
4. Open the repository using Android Studio
5. Download an emulator on Android Studio
6. Press the 'Run' button on Android Studio and use the emulator you created

### Class Diagram

![Class Diagram](https://github.com/cpe305Spring17/spring2017-project-ndara/blob/master/images/Class%20Diagram.png?raw=true)

### Design Considerations
Design patterns in Java are incredibly powerful and useful if used properly. In a project as small as React, the need of design patterns is not extraordinary. Furthermore, React is backed by Android Studio. Just as the JDK already implements several patterns under the hood, the Android environment implements a variety as well. As a user of the Android environment, I needed to understand how the design patterns worked in Android and use them appropriately. 

##### Singleton
Database is a singleton class. There is only one object of the database to access. This design consideration was taken to uphold the ACID (atomicity, consistency, isolation, durability) constraints. No 2 asynchronous calls should be made to the database at the same time to ensure that the database is at a stable state before any transaction.

##### Observer
The observer pattern is a pattern created by the Android framework in the form of Intents. I utilized this pattern by alerting the view to reload data from the database when a new activity or event has been added. Essentially, through Android's framework, the view observes the model and the model alerts the view when changes are made. Using this pattern allows the view to be automatically updated when the model is updated.

##### State
I plan to utilize the state pattern in order to transition the way buttons are handled. Actions that are active will be a green color; inactive actions are purple. Instead of my current if-else operation that decides what color the button should turn to on click, I plan to use a state pattern. The state pattern will have 2 states, active, and inactive. The state pattern is useful because I can call a function and it will act appropriately. This allows the client to disregard the state of the button and focus on implementation.

### Analytics
[SonarQube](https://sonarcloud.io/dashboard?id=spring2017-project-ndara)

### Favorite Part
I enjoyed the idea of being able to design a modern application. I was able to place an emphasis on simplicity both in UI and UX. A user may access any part of core functionality with 2 or fewer clicks. This allows an application like React which depends on user usage to be properly utilized.

### What I Learned
I learned how useful design patterns can be in large projects. More importantly, I learned that one must be able to adapt design patterns to fit their use best. It's not usually the best idea to take directly from the Gang of 4 design patterns and staple them to your project. Careful consideration must be taken when attempting to make an appropriate design choice and apply modifications as necessary.

I also learned plenty of Android development. I was fairly new to Android coming into the project but I have definitely increased my understanding of Android Studio and application development.

### Screenshots

##### Main Menu
![Main Menu](https://github.com/cpe305Spring17/spring2017-project-ndara/blob/master/images/Main%20Menu.png?raw=true)

##### Graphs
![Graphs](https://github.com/cpe305Spring17/spring2017-project-ndara/blob/master/images/Graphs.png?raw=true)

##### Graph of Activity
![GraphActivity](https://github.com/cpe305Spring17/spring2017-project-ndara/blob/master/images/GraphActivity.png?raw=true)

##### Goals
![Goals](https://github.com/cpe305Spring17/spring2017-project-ndara/blob/master/images/Goals.png?raw=true)

##### Settings
![Settings](https://github.com/cpe305Spring17/spring2017-project-ndara/blob/master/images/Settings.png?raw=true)

##### Sample Notification
![Notification](https://github.com/cpe305Spring17/spring2017-project-ndara/blob/master/images/Notification.png?raw=true)



