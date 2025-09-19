package JohnChatbot;

import JohnChatbot.Tasks.Deadline;
import JohnChatbot.Tasks.Event;
import JohnChatbot.Tasks.Task;
import JohnChatbot.Tasks.Todo;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * The main class for the John Chatbot application.
 * This class handles user input, processes commands, and manages a list of tasks.
 */
public class JohnChatbot {
    private static TaskList taskList;
    private static final String saveFileName = "JohnChatbotSave.ser";

    /**
     * Constructs a new JohnChatbot instance and attempts to load tasks from a save file.
     * If no save file is found, a new, empty task list is created.
     */
    public JohnChatbot() {
        loadSave();
    }

    /**
     * Loads the task list from a serialized save file.
     * If the file does not exist, a new empty task list is initialized.
     */
    private static void loadSave() {
        taskList = Storage.getOrCreateSave(saveFileName);
    }

    /**
     * Generates a greeting message to be displayed at the start of the application.
     *
     * @return A string containing the welcome message.
     */
    public String greet() {
        return "Hello! I'm John Chatbot!\nWhat can I do for you?";
    }

    /**
     * Processes a user command and returns a response.
     * This method parses the input, identifies the command, and executes the corresponding logic.
     *
     * @param input The raw command string entered by the user.
     * @return A string containing the chatbot's response to the command.
     */
    public String getResponse(String input) {
        assert taskList != null;

        String cmd = input.split(" ", 2)[0].toLowerCase();
        String response = "";

        try {
            switch (cmd) {
            case "":
                break;
            case "bye":
                response = exit();
                System.exit(0);
                break;
            case "list":
                response = printTasks();
                break;
            case "mark":
                response = markTask(input);
                break;
            case "unmark":
                response = unmarkTask(input);
                break;
            case "deadline":
                response = addDeadline(input);
                break;
            case "event":
                response = addEvent(input);
                break;
            case "todo":
                response = addTodo(input);
                break;
            case "delete":
                response = deleteTask(input);
                break;
            case "find":
                response = findTask(input);
                break;
            default:
                response = "Invalid command :(";
                break;
            }
        } catch (IllegalArgumentException e) {
            response = "Error: " + e.getMessage();
        }
        return Ui.getSection(response);
    }

    /**
     * Searches for tasks that contain a specific keyword or phrase.
     *
     * @param input The user's input string, including the search keyword.
     * @return A formatted string listing all matching tasks. Returns a message if no tasks are found.
     * @throws IllegalArgumentException If the input does not contain a search description.
     */
    public String findTask(String input) {
        TaskList matchingTasks = new TaskList();
        String flag = "find";
        String description = Parser.getFlag(input, flag);
        if (description.isEmpty()) {
            throw new IllegalArgumentException("Add description after 'find' to search for tasks with the word!");
        }

        for (int i = 0; i < taskList.getTaskList().size(); i++) {
            if (taskList.getTaskList().get(i).toString().contains(description)) {
                matchingTasks.getTaskList().add(taskList.getTaskList().get(i));
            }
        }

        if (matchingTasks.getTaskList().isEmpty()) {
            return "No tasks match your search!";
        } else {
            return Ui.getListInSection(matchingTasks.getTaskList(),
                    "Here are the tasks that match your search of " + description);
        }
    }

    /**
     * Persists the current task list to the save file.
     */
    public static void updateSaveDataFile() {
        Storage.saveToFile(taskList, saveFileName);
    }

    /**
     * Adds a new task to the task list and updates the save file.
     *
     * @param task The task object to be added.
     * @return A string confirming the addition of the task.
     */
    public String addTask(Task task) {
        taskList.getTaskList().add(task);
        updateSaveDataFile();
        return "Added task to the list: " + task;
    }

    /**
     * Generates a formatted list of all outstanding tasks.
     *
     * @return A string containing the numbered list of tasks, or a message if the list is empty.
     */
    public String printTasks() {
        if (taskList.getTaskList().isEmpty()) {
            return "You have nothing to do!";
        } else {
            return Ui.getListInSection(taskList.getTaskList(), "Here are the outstanding tasks in your list: ");
        }
    }

    /**
     * Marks a task as complete based on its index.
     *
     * @param line The user's input string, containing the command and task index.
     * @return A string confirming the task has been marked as complete.
     * @throws IllegalArgumentException If the index is empty or not a valid number.
     * @throws IndexOutOfBoundsException If the index is out of the task list's range.
     */
    private String markTask(String line) {
        try {
            String flag = "mark";
            String index = Parser.getFlag(line, flag);
            if (index.isEmpty()) {
                throw new IllegalArgumentException("The task index cannot be empty!");
            }
            Task taskToMark = taskList.getTaskList().get(Integer.parseInt(index) - 1);
            taskToMark.markAsDone();
            updateSaveDataFile();
            return "Task has been marked as complete: " + taskToMark.getDescription();
        } catch (IndexOutOfBoundsException e) {
            return "The index provided is out of bounds";
        }
    }


    /**
     * Marks a task as incomplete based on its index.
     *
     * @param line The user's input string, containing the command and task index.
     * @return A string confirming the task has been marked as incomplete.
     * @throws IllegalArgumentException If the index is empty or not a valid number.
     * @throws IndexOutOfBoundsException If the index is out of the task list's range.
     */
    private String unmarkTask(String line) {
        try {
            String flag = "unmark";
            String index = Parser.getFlag(line, flag);
            if (index.isEmpty()) {
                throw new IllegalArgumentException("The task index cannot be empty!");
            }
            Task taskToMark = taskList.getTaskList().get(Integer.parseInt(index) - 1);
            taskToMark.markAsUndone();
            updateSaveDataFile();
            return "Task has been marked as incomplete: " + taskToMark.getDescription();
        } catch (IndexOutOfBoundsException e) {
            return "The index provided is out of bounds";
        }
    }

    /**
     * Creates and adds a new Todo task to the list.
     *
     * @param line The user's input string, containing the todo description.
     * @return A string confirming the addition of the new todo.
     * @throws IllegalArgumentException If the description is empty.
     * @throws JohnChatbotException If an error occurs during the task creation.
     */
    private String addTodo(String line) {
        try {
            String flag = "todo";
            String description = Parser.getFlag(line, flag);
            if (description.isEmpty()) {
                throw new IllegalArgumentException("The description of a todo cannot be empty!");
            }
            Task task = new Todo(description);
            return addTask(task);
        } catch (JohnChatbotException e) {
            return Ui.getSection(e.getMessage());
        }
    }

    /**
     * Creates and adds a new Deadline task to the list.
     *
     * @param line The user's input string, containing the deadline description and due date.
     * @return A string confirming the addition of the new deadline.
     * @throws IllegalArgumentException If the description or due date is empty.
     * @throws DateTimeParseException If the provided date/time string is in an invalid format.
     * @throws JohnChatbotException If an error occurs during task creation.
     */
    private String addDeadline(String line) {
        String flag = "deadline";
        String byFlag = "/by";
        String description = Parser.getFlag(line, flag);
        String by = Parser.getFlag(line, byFlag);
        if (description.isEmpty()) {
            throw new IllegalArgumentException("The description of a deadline cannot be empty!");
        }
        if (by.isEmpty()) {
            throw new IllegalArgumentException("The " + byFlag + " for a deadline cannot be empty!");
        }
        try {
            LocalDateTime byDate = Parser.parseDateTime(by);
            Task task = new Deadline(description, byDate);
            return addTask(task);
        } catch (DateTimeParseException e) {
            return Ui.getStringListInSection(Parser.formatList,
                    "Wrong date time format: " + e + "\nAccepted date formats: ");
        } catch (JohnChatbotException e) {
            return Ui.getSection(e.getMessage());
        }
    }

    /**
     * Creates and adds a new Event task to the list.
     *
     * @param line The user's input string, including the event description, start time, and end time.
     * @return A string confirming the addition of the new event.
     * @throws IllegalArgumentException If the description, start time, or end time is empty.
     * @throws DateTimeParseException If the provided date/time string is in an invalid format.
     * @throws JohnChatbotException If an error occurs during task creation.
     */
    private String addEvent(String line) {
        String flag = "event";
        String fromFlag = "/from";
        String toFlag = "/to";
        String description = Parser.getFlag(line, flag);
        String from = Parser.getFlag(line, fromFlag);
        String to = Parser.getFlag(line, toFlag);
        if (description.isEmpty()) {
            throw new IllegalArgumentException("The description of an event cannot be empty!");
        }
        if (from.isEmpty()) {
            throw new IllegalArgumentException("The " + fromFlag + " of a event cannot be empty!");
        }
        if (to.isEmpty()) {
            throw new IllegalArgumentException("The " + toFlag + " of a event cannot be empty!");
        }
        try {
            LocalDateTime fromDate = Parser.parseDateTime(from);
            LocalDateTime toDate = Parser.parseDateTime(to);
            Task task = new Event(description, fromDate, toDate);
            return addTask(task);
        } catch (DateTimeParseException e) {
            String msg = "Wrong date time format!: " + e + "/nAccepted date formats: ";
            return Ui.getStringListInSection(Parser.formatList, msg);
        } catch (JohnChatbotException e) {
            return Ui.getSection(e.getMessage());
        }
    }

    /**
     * Deletes a task from the list based on its index.
     *
     * @param input The user's input string, including the command and task index.
     * @return A string confirming the task has been removed.
     * @throws IllegalArgumentException If the index is empty or not a valid number.
     * @throws IndexOutOfBoundsException If the index is out of the task list's range.
     */
    public String deleteTask(String input) {
        try {
            String flag = "delete";
            String index = Parser.getFlag(input, flag);
            if (index.isEmpty()) {
                throw new IllegalArgumentException("The task number of a delete cannot be empty!");
            }

            int indexToRemove = Integer.parseInt(index) - 1;
            Task taskToRemove = taskList.getTaskList().get(indexToRemove);
            taskList.getTaskList().remove(indexToRemove);
            updateSaveDataFile();
            return "Task has been removed: " + taskToRemove;
        } catch (IndexOutOfBoundsException e) {
            return "The index provided is out of bounds";
        }
    }

    /**
     * Generates a farewell message to be displayed upon exiting the chatbot.
     *
     * @return A string containing the exit message.
     */
    public String exit() {
        return "bye bye!";
    }
}