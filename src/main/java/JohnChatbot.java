import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class JohnChatbot {
    public static void main(String[] args) {
        List<Task> userInputs = new ArrayList<>();
        String lineBreak = "\n____________________________________________________________\n";
        System.out.println(lineBreak + "Hello! I'm John Chatbot!\nWhat can I do for you?" + lineBreak);
        Scanner scanner = new Scanner(System.in);
        while(true) {
            String userInput = scanner.nextLine();
            try {
                // Check for commands that don't need a task to be added
                if (userInput.equals("bye")) {
                    System.out.println(lineBreak + "Bye. Hope to see you again soon!" + lineBreak);
                    break;
                } else if (userInput.equals("list")) {
                    System.out.println(lineBreak);
                    System.out.println("Here are the outstanding tasks in your list: \n");
                    for(int i = 0; i < userInputs.size(); i++) {
                        System.out.println((i + 1) + "." +
                                getStatus(userInputs.get(i)) +
                                userInputs.get(i).getDescription());
                    }
                    System.out.println(lineBreak);
                } else {
                    checkCommand(userInput, userInputs);
                }
            } catch (JohnChatbotException e) {
                // Catch and handle all custom exceptions from checkCommand
                System.out.println(lineBreak + e.getMessage() + lineBreak);
            }
        }
        scanner.close();
    }

    public static String getStatus(Task task) {
        if (task.isDone) {
            return "[X] ";
        } else {
            return "[ ] ";
        }
    }

    public static boolean isMarkCommand(String input) {
        return input.startsWith("mark");
    }

    public static boolean isUnmarkCommand(String input) {
        return input.startsWith("unmark");
    }

    public static boolean isTodoCommand(String input) {
        return input.startsWith("todo");
    }
    public static boolean isDeadlineCommand(String input) {
        return input.startsWith("deadline");
    }
    public static boolean isEventCommand(String input) {
        return input.startsWith("event");
    }

    public static void checkCommand(String userInput, List<Task> userInputs) throws JohnChatbotException {
        String lineBreak = "\n____________________________________________________________\n";

        if (isDeadlineCommand(userInput)) {
            String[] parts = userInput.split(" ", 2);
            if (parts.length < 2 || parts[1].trim().isEmpty()) {
                throw new JohnChatbotException("The description of a deadline cannot be empty.");
            }
            // You will need to add more checks for '/by' as well
            Deadline deadlineAdded = new Deadline(userInput);
            userInputs.add(deadlineAdded);
            System.out.println(lineBreak + "Got it. I've added:\n" + deadlineAdded.getDescription() +
                    "\nNow you have " + userInputs.size() + " tasks in the list." + lineBreak);
        } else if (isUnmarkCommand(userInput)) {
            try {
                int taskIndex = Integer.parseInt(userInput.substring(7).trim()) - 1;
                if (taskIndex < 0 || taskIndex >= userInputs.size()) {
                    throw new JohnChatbotException("The task number is out of bounds. Please enter a number from 1 to " + userInputs.size() + ".");
                }
                userInputs.get(taskIndex).markAsUndone();
                System.out.println(lineBreak + "OK, I've marked this task as not done yet: \n" +
                        getStatus(userInputs.get(taskIndex)) +
                        userInputs.get(taskIndex).getDescription() + lineBreak);
            } catch (NumberFormatException e) {
                throw new JohnChatbotException("The unmark command requires a valid task number after 'unmark'.");
            }
        } else if (isEventCommand(userInput)) {
            String[] parts = userInput.split(" ", 2);
            if (parts.length < 2 || parts[1].trim().isEmpty()) {
                throw new JohnChatbotException("The description of an event cannot be empty.");
            }
            // You will need to add more checks for '/from' and '/to' as well
            Event eventAdded = new Event(userInput);
            userInputs.add(eventAdded);
            System.out.println(lineBreak + "Got it. I've added:\n" + eventAdded.getDescription() +
                    "\nNow you have " + userInputs.size() + " tasks in the list." + lineBreak);
        } else if (isTodoCommand(userInput)) {
            String[] parts = userInput.split(" ", 2);
            if (parts.length < 2 || parts[1].trim().isEmpty()) {
                throw new JohnChatbotException("The description of a todo cannot be empty.");
            }
            Todo todoAdded = new Todo(parts[1].trim());
            userInputs.add(todoAdded);
            System.out.println(lineBreak + "Got it. I've added:\n" + todoAdded.getDescription() +
                    "\nNow you have " + userInputs.size() + " tasks in the list." + lineBreak);
        } else if (isMarkCommand(userInput)) {
            try {
                int taskIndex = Integer.parseInt(userInput.substring(5).trim()) - 1;
                if (taskIndex < 0 || taskIndex >= userInputs.size()) {
                    throw new JohnChatbotException("The task number is out of bounds. Please enter a number from 1 to " + userInputs.size() + ".");
                }
                userInputs.get(taskIndex).markAsDone();
                System.out.println(lineBreak + "Nice! I've marked this task as done: \n" +
                        getStatus(userInputs.get(taskIndex)) +
                        userInputs.get(taskIndex).getDescription() + lineBreak);
            } catch (NumberFormatException e) {
                throw new JohnChatbotException("The mark command requires a valid task number after 'mark'.");
            }
        } else {
            throw new JohnChatbotException("No idea what you mean");
        }
    }
}