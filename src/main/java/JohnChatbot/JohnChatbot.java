package JohnChatbot;

import JohnChatbot.Tasks.Deadline;
import JohnChatbot.Tasks.Event;
import JohnChatbot.Tasks.Task;
import JohnChatbot.Tasks.Todo;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class JohnChatbot {
    private static TaskList taskList;
    private static final String saveFileName = "JohnChatbotSave.ser";

    public static void main(String[] args) {
        loadSave();
        greet();
        run();
        exit();
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
    public static void greet() {
        Ui.printSection("Hello! I'm John Chatbot!\nWhat can I do for you?");
    }

    /**
     * Constantly checks for inputs that are commands from user.
     * @throws IllegalArgumentException if the input does not contain a valid command word.
     * @see #printTasks() #markTask(string) #unmarkTask(string) #addDeadline(string)
     * #addEvent(string) #addTodo(string)
     */
    private static void run() {
        java.util.Scanner sc = new java.util.Scanner(System.in);
        boolean ongoing = true;

        while (ongoing) {
            String input = sc.nextLine().trim();
            String cmd = input.split(" ", 2)[0].toLowerCase();
            try {
                switch (cmd) {
                case "":
                    break;
                case "bye":
                    ongoing = false;
                    break;
                case "list":
                    printTasks();
                    break;
                case "mark":
                    markTask(input);
                    break;
                case "unmark":
                    unmarkTask(input);
                    break;
                case "deadline":
                    addDeadline(input);
                    break;
                case "event":
                    addEvent(input);
                    break;
                case "todo":
                    addTodo(input);
                    break;
                case "delete":
                    deleteTask(input);
                    break;
                case "find":
                    findTask(input);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid command :(");
                }
            } catch (IllegalArgumentException e) {
                Ui.printSection((e.toString()));
            }
        }
    }

    public static void findTask(String input) {
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
            Ui.printSection("No tasks match your search!");
        } else {
            Ui.printListInSection(matchingTasks.getTaskList(),
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
    public static void addTask(Task task) {
        taskList.getTaskList().add(task);
        updateSaveDataFile();
        Ui.printSection("Added task to the list: " + task);
    }

    /**
     * Print the list of tasks that the user has added to their list.
     */
    public static void printTasks() {
        if (taskList.getTaskList().isEmpty()) {
            Ui.printSection("You have nothing to do!");
        } else {
            Ui.printListInSection(taskList.getTaskList(), "Here are the outstanding tasks in your list: ");
        }
    }

    /**
     * Marks the chosen task as complete.
     * @param line The input command from the user.
     */
    private static void markTask(String line) {
        try {
            String flag = "mark";
            String index = Parser.getFlag(line, flag);
            if (index.isEmpty()) {
                throw new IllegalArgumentException("The task index cannot be empty!");
            }
            //setting task in list as done
            Task taskToMark = taskList.getTaskList().get(Integer.parseInt(index) - 1);
            taskToMark.markAsDone();
            //print feedback so user can see
            Ui.printSection("Task has been marked as complete: " + taskToMark.getDescription());
        } catch (IndexOutOfBoundsException e) {
            Ui.printSection("The index provided is out of bounds");
        }
    }


    /**
     * Marks the chosen task as incomplete.
     * @param line The input command from the user.
     */
    private static void unmarkTask(String line) {
        try {
            String flag = "unmark";
            String index = Parser.getFlag(line, flag);
            if (index.isEmpty()) {
                throw new IllegalArgumentException("The task index cannot be empty!");
            }
            //setting task in list as done
            Task taskToMark = taskList.getTaskList().get(Integer.parseInt(index) - 1);
            taskToMark.markAsUndone();
            //print feedback so user can see
            Ui.printSection("JohnChatbot.Tasks.Task has been marked as incomplete: " + taskToMark.getDescription());
        } catch (IndexOutOfBoundsException e) {
            Ui.printSection("The index provided is out of bounds");
        }
    }

    /**
     * Adds a Todo Task to the list.
     * @param line The input command from the user.
     */
    private static void addTodo(String line) {
        try {
            String flag = "todo";
            String description = Parser.getFlag(line, flag);
            if (description.isEmpty()) {
                throw new IllegalArgumentException("The description of a todo cannot be empty!");
            }
            Task task = new Todo(description);
            addTask(task);
        } catch (JohnChatbotException e) {
            Ui.printSection("Something went wrong: " + e.getMessage());
        }
    }

    /**
     * Adds a Deadline Task to the list.
     * @param line The input command from the user.
     */
    private static void addDeadline(String line) {
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
            addTask(task);
        } catch (DateTimeParseException e) {
            Ui.printListInSection(Parser.formatList,
                            "Wrong date time format: " + e + "\nAccepted date formats: ");
        } catch (JohnChatbotException e) {
            Ui.printSection("Something went wrong: " + e.getMessage());
        }
    }

    /**
     * Adds an Event Task to the list.
     * @param line The input command from the user.
     */
    private static void addEvent(String line) {
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
            addTask(task);
        } catch (DateTimeParseException e) {
            String msg = "Wrong date time format!: " + e + "/nAccepted date formats: ";
            Ui.printListInSection(Parser.formatList, msg);
        } catch (JohnChatbotException e) {
            Ui.printSection("Something went wrong: " + e.getMessage());
        }
    }

    /**
     * Removes the chosen task from the list based on index provided.
     * @param input The input command from the user.
     */
    public static void deleteTask(String input) {
        try {
            String flag = "delete";
            String index = Parser.getFlag(input, flag);
            if (index.isEmpty()) {
                throw new IllegalArgumentException("The task number of a delete cannot be empty!");
            }

            int indexToRemove = Integer.parseInt(index) - 1;
            Task taskToRemove = taskList.getTaskList().get(indexToRemove);
            taskList.getTaskList().remove(indexToRemove);
            Ui.printSection("Task has been removed: " + taskToRemove);
            updateSaveDataFile();
        } catch (IndexOutOfBoundsException e) {
            Ui.printSection("The index provided is out of bounds");
        }
    }

    /**
     * Closes the chatbot and ends program.
     */
    public static void exit() {
        Ui.printSection("bye bye!");
    }
}