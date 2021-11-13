# Taskmaster

## Overview

This repository includes an Android app that will be the main focus of the second half of the 401 course. over time this will grow to be a fully-featured application.

## Architecture

The programing language used to build this project is java and using the Android Studio to complete it.


## Lab: 26 - Beginning TaskMaster

This lab is an introduction to Android and how to use the Android Studio. In this lab I create three activities, The Main Activity, Add Task Activity and All Task Activity.

### Main Activity

It is the home page which contains an image and two buttons each one starts a new activity when click on it.

![Home page](./app/src/screenshots/img1.PNG)

### Add Task Activity

When the user click on `ADD TASK` button from home page it will take The user to this page(*Add Task Page*). In this page the user can add a task title and a description and after that can click on th `ADD TASK` button.

![Add task page](./app/src/screenshots/img2.PNG)

When the user click on the `ADD TASK` button it will show a message that the task is submitted.

![Submitted task](./app/src/screenshots/img4.PNG)

### All Task Activity

When the user click on `ALL TASK` button from home page it will take The user to this page(*All Task Page*). This page contains an image.

![All task page](./app/src/screenshots/img3.PNG)

## Lab: 27 - Data in TaskMaster

In This lab added three buttons on the main page, each button for one task that takes the user to the task detail page when the user clicks on it. Also, added a setting button on the home page that takes the user to the setting page.

### Setting Activity

This page contains a fild which the user can put his/her name and click on save buttons to save the name.

![setting page](./app/src/screenshots/lab27SS5.PNG)

### Main Activity

The home page contains new four buttons. The first three buttons for tasks, Each button takes the user to a different task detail page. and the fourth button for the setting page that I showed you above. Also, the name that the user wrote on the setting page appears at the top of the main page.

![main page](./app/src/screenshots/lab27SS1.PNG)

### Task Detail Activity

Task detail page appears when the user click on each task on the home page.

![Task1 detail page](./app/src/screenshots/lab27SS2.PNG)

![Task2 detail page](./app/src/screenshots/lab27SS3.PNG)

![Task3 detail page](./app/src/screenshots/lab27SS4.PNG)

## Lab: 28 - RecyclerView

In this lab I used the `RecyclerView` to view all tasks in the home page as the list. 

### Main Activity

I refactored the homepage to show all the tasks as the list and the user can scroll down or up to view the tasks.

![RecyclerView list](./app/src/screenshots/lab28SS1.PNG)

And also when the user clicks on any task, it will move him/her to the task details page.

![details page](./app/src/screenshots/lab28SS2.PNG)

## Lab: 29 - Room

In this lab, I added a Room database to save the tasks and the details of tasks and get data from it, and let the recycler view take the data from the room database. 

### Add Task Activity
On this page, Added a new field in which can the user writes the title and description and state of the task.

![Add Task](./app/src/screenshots/lab29SS2.PNG)

### Main Activity

 This is the home page and it conains tasks and each task has descreption and state

![home page](./app/src/screenshots/lab29SS1.PNG)



## Lab 31: Espresso and Polish

* In This lab I tets the code by using Espresso test.

* I created 3 test:

1. `elementsUI()`

To test the important UI elements are displayed on the Add Task page.

2. `addTaskFun()`

To test if you edit the user’s username, and then assert that it says the correct thing on the homepage.

3. `displayTaskName()`

To test when you tap on a task, and then assert that the resulting activity displays the title, body and state of that task in task detail page.


![EsspressoTest](./app/src/screenshots/lab31EspressoTest.PNG)

## Lab 32: Amplify and DynamoDB

In this lab I implemented **AWS amplify** to access the data in **DynamoDB** insted of **Room**.

### Add Task Activity

Now when the user add new task in the add task page, The task will stor in the **DynamoDb** data base and also in the room to reach it if the user offline.

### Main Activity

I refactored the homepage's **RecyclerView** to display all Task entities in **DynamoDB**.

![tasks In Homepage](./app/src/screenshots/lab32SS1.PNG)

* This is screenshot for the tasks in the **DynamoDb**

![tasks in DynamoDB](./app/src/screenshots/lab32SS2.PNG)

## Lab 33: Related Data

### Add Task Activity

Modify the Add Task form to include either a Spinner for which team that task belongs to.

![Add Task Page](./app/src/screenshots/lab33-SS3.PNG)


### Setting Activity

In addition to a username, allow the user to choose their team on the Settings page. Use that Team to display only that team’s tasks on the homepage.

![settings page]()

### ### Main Activity

When the user chooses the team, the tasks will display on the home page, and at the top of the page, the team name will display.
![Main Page](./app/src/screenshots/lab33-SS2.PNG)

* Picturs below for **DynamoDB**:

![teams Tabl](./app/src/screenshots/lab33-SS1.PNG)


