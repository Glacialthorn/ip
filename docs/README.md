### JohnChatbot

JohnChatbot is a simple, GUI-based chatbot designed to help you manage your daily tasks. It allows you to create, list, mark, unmark, delete, and find tasks, with all data automatically saved to a file for persistent storage.

!(/docs/Ui.png)

---

### Features

* **GUI Interface**: The chatbot features a user-friendly graphical interface built with JavaFX, making task management intuitive and easy.
* **Task Management**: The application supports three distinct types of tasks:
  * `todo`: A basic task with just a description.
  * `deadline`: A task with a description and a due date.
  * `event`: A task that includes a description along with a start and end time.
* **Task Operations**: You can perform the following commands:
  * `list`: Displays all your outstanding tasks.
  * `mark <index>`: Marks a specified task as complete.
  * `unmark <index>`: Marks a specified task as incomplete.
  * `delete <index>`: Removes a task from your list permanently.
  * `find <keyword>`: Searches for and lists all tasks containing a specific word or phrase.
* **Data Persistence**: The task list is automatically saved to a serialized file named `JohnChatbotSave.ser` and is loaded when the application starts, so you never lose your data.

---

### Getting Started

#### Prerequisites

* **Java Development Kit (JDK)**: Version 11 or later is required.
* **JavaFX SDK**: The project relies on JavaFX for its GUI components.
* **IDE**: An IDE such as IntelliJ IDEA or Eclipse is recommended for easy setup and running.

#### How to Run

1.  **Download the source files**: Get all the `.java` files from the project.
2.  **Open the project in your IDE**: The files are structured within the `JohnChatbot` package.
3.  **Configure JavaFX**: Ensure your IDE is set up with the JavaFX SDK.
4.  **Run `Launcher.java`**: This class contains the `main` method and is the entry point for the application. Running it will launch the GUI for JohnChatbot.

---

### Command Guide

Here are the commands you can use to interact with JohnChatbot:

#### General Commands

* `list`: View all tasks.
* `bye`: Exit the chatbot.

#### Task Creation

* **Todo**: `todo <description>`
  * **Example**: `todo read book`
* **Deadline**: `deadline <description> /by <date/time>`
  * **Example**: `deadline submit report /by 2025-10-25 1800`
* **Event**: `event <description> /from <start date/time> /to <end date/time>`
  * **Example**: `event team meeting /from 2025-10-26 0900 /to 2025-10-26 1000`

#### Task Modification

* `mark <index>`: Mark a task as done.
  * **Example**: `mark 1` (Marks the first task)
* `unmark <index>`: Mark a task as not done.
  * **Example**: `unmark 1`
* `delete <index>`: Delete a task.
  * **Example**: `delete 3` (Deletes the third task)

#### Task Search

* `find <keyword>`: Find tasks containing the keyword.
  * **Example**: `find report`

#### Date and Time Formatting

JohnChatbot accepts the following date and time formats for deadline and event tasks:

* `yyyy-MM-dd HHmm`
* `HHmm yyyy-MM-dd`

**Examples**: `2025-10-25 1800` or `1800 2025-10-25`

---

### Project Structure

* `Main.java`: Initializes the JavaFX GUI and the chatbot instance.
* `JohnChatbot.java`: Contains the main logic for processing user commands and managing the task list.
* `Parser.java`: A utility class for parsing command strings and handling date/time conversions.
* `Storage.java`: Manages reading from and writing to the task list save file.
* `TaskList.java`: Holds the `ArrayList` of tasks.
* `Ui.java`: Provides methods for formatting and displaying user-facing messages.
* `MainWindow.java`: The controller for the main chat window GUI.
* `DialogBox.java`: A custom JavaFX component that creates and styles chat bubbles for the user and chatbot.
* `Launcher.java`: A workaround class to correctly launch the JavaFX application.
* `JohnChatbotException.java`: A custom exception for handling specific application errors.