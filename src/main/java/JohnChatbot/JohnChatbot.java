package JohnChatbot;

import JohnChatbot.Tasks.Deadline;
import JohnChatbot.Tasks.Event;
import JohnChatbot.Tasks.Task;
import JohnChatbot.Tasks.Todo;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Chatbot Logic
 */
public class JohnChatbot {
    private static TaskList taskList;
    private static final String saveFileName = "JohnChatbotSave.ser";

    public JohnChatbot() {
        loadSave();
    }

    /**
     * Loads save file if available, otherwise creates a new one.
     */
    private static void loadSave() {
        taskList = Storage.getOrCreateSave(saveFileName);
    }

    /**
     * Outputs greeting message on bot startup.
     */
    public String greet() {
        return "Hello! I'm John Chatbot!\nWhat can I do for you?";
    }

    /**
     * Constantly checks for inputs that are commands from user.
     * @throws IllegalArgumentException if the input does not contain a valid command word.
     */
    public String getResponse(String input) {
        String cmd = input.split(" ", 2)[0].toLowerCase();
        String response = "";

        try {
            switch (cmd) {
            case "":
                break;
            case "bye":
                response = exit();
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
     * Searches for tasks
     * @param input String to be searched for
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
     * Updates the stored save file to match the current one's contents.
     */
    public static void updateSaveDataFile() {
        Storage.saveToFile(taskList, saveFileName);
    }

    /**
     * Adds a task to the list of tasks and updates the user when it does.
     *
     * @param task The task to be added.
     */
    public String addTask(Task task) {
        taskList.getTaskList().add(task);
        updateSaveDataFile();
        return "Added task to the list: " + task;
    }

    /**
     * Print the list of tasks that the user has added to their list.
     */
    public String printTasks() {
        if (taskList.getTaskList().isEmpty()) {
            return "You have nothing to do!";
        } else {
            return Ui.getListInSection(taskList.getTaskList(), "Here are the outstanding tasks in your list: ");
        }
    }

    /**
     * Marks the chosen task as complete.
     * @param line The input command from the user.
     */
    private String markTask(String line) {
        try {
            String flag = "mark";
            String index = Parser.getFlag(line, flag);
            if (index.isEmpty()) {
                throw new IllegalArgumentException("The task index cannot be empty!");
            }
            //setting task in list as done
            Task taskToMark = taskList.getTaskList().get(Integer.parseInt(index) - 1);
            taskToMark.markAsDone();
            updateSaveDataFile();
            //return feedback so user can see
            return "Task has been marked as complete: " + taskToMark.getDescription();
        } catch (IndexOutOfBoundsException e) {
            return "The index provided is out of bounds";
        }
    }


    /**
     * Marks the chosen task as incomplete.
     * @param line The input command from the user.
     */
    private String unmarkTask(String line) {
        try {
            String flag = "unmark";
            String index = Parser.getFlag(line, flag);
            if (index.isEmpty()) {
                throw new IllegalArgumentException("The task index cannot be empty!");
            }
            //setting task in list as done
            Task taskToMark = taskList.getTaskList().get(Integer.parseInt(index) - 1);
            taskToMark.markAsUndone();
            updateSaveDataFile();
            //return feedback so user can see
            return "Task has been marked as incomplete: " + taskToMark.getDescription();
        } catch (IndexOutOfBoundsException e) {
            return "The index provided is out of bounds";
        }
    }

    /**
     * Adds a Todo Task to the list.
     * @param line The input command from the user.
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
     * Adds a Deadline Task to the list.
     * @param line The input command from the user.
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
     * Adds an Event Task to the list.
     * @param line The input command from the user.
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
     * Removes the chosen task from the list based on index provided.
     * @param input The input command from the user.
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
     * Closes the chatbot and ends program.
     */
    public String exit() {
        return "bye bye!";
    }
}