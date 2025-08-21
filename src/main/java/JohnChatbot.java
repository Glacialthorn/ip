import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class JohnChatbot {
    public static void main(String[] args) {
        List<Task> userInputs = new ArrayList<>();

        String lineBreak = "\n____________________________________________________________\n";
        System.out.println(lineBreak + "Hello! I'm John Chatbot!\nWhat can I do for you?" + lineBreak);
        while(true) {
            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine();
            switch (userInput) {
                case "bye":
                    System.out.println(lineBreak + "Bye. Hope to see you again soon!" + lineBreak);
                    break;
                case "list":
                    System.out.println(lineBreak);
                    System.out.println("Here are the outstanding tasks in your list: \n");
                    for(int i = 0; i < userInputs.size(); i++) {
                        System.out.println((i + 1) + "." +
                                            userInputs.get(i).getDescription());
                    }
                    System.out.println(lineBreak);
                    break;
                default:
                    checkCommand(userInput, userInputs);
            }
        }
    }

    public static String getStatus(Task task) {
        if (task.isDone) {
            return "[X] ";
        } else {
            return "[ ] ";
        }

    }

    public static boolean isMarkCommand(String input) {
        String frontPart = input.substring(0, Math.min(input.length(), 4));
        if (frontPart.contains("mark")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isUnmarkCommand(String input) {
        String frontPart = input.substring(0, Math.min(input.length(), 6));
        return frontPart.contains("unmark");
    }

    public static boolean isTodoCommand(String input) {
        String frontPart = input.substring(0, Math.min(input.length(), 4));
        return frontPart.contains("todo");
    }
    public static boolean isDeadlineCommand(String input) {
        String frontPart = input.substring(0, Math.min(input.length(), 8));
        return frontPart.contains("deadline");
    }
    public static boolean isEventCommand(String input) {
        String frontPart = input.substring(0, Math.min(input.length(), 5));
        return frontPart.contains("event");
    }

    public static void checkCommand(String userInput, List<Task> userInputs) {
        String lineBreak = "\n____________________________________________________________\n";
        if (isDeadlineCommand(userInput)) {
            Deadline deadlineAdded = new Deadline(userInput);
            userInputs.add(deadlineAdded);
            System.out.println(lineBreak + "Got it. I've added:\n" + deadlineAdded.getDescription() +
                    "\n Now you have " + userInputs.size() + " tasks in the list."
                    + lineBreak);
        } else if (isUnmarkCommand(userInput)) {
            int taskIndex = Integer.parseInt(userInput.substring(7)) - 1;
            userInputs.get(taskIndex).markAsUndone();
            System.out.println(lineBreak + "OK, I've marked this task as not done yet: \n" +
                    getStatus(userInputs.get(taskIndex)) +
                    userInputs.get(taskIndex).getDescription() + lineBreak);
        } else if (isEventCommand(userInput)) {
            Event eventAdded = new Event(userInput);
            userInputs.add(eventAdded);
            System.out.println(lineBreak + "Got it. I've added:\n" + eventAdded.getDescription() +
                    "\n Now you have " + userInputs.size() + " tasks in the list."
                    + lineBreak);
        } else if (isTodoCommand(userInput)) {
            Todo todoAdded = new Todo(userInput);
            userInputs.add(todoAdded);
            System.out.println(lineBreak + "Got it. I've added:\n" + todoAdded.getDescription() +
                    "\n Now you have " + userInputs.size() + " tasks in the list."
                    + lineBreak);
        } else if (isMarkCommand(userInput)) {
            int taskIndex = Integer.parseInt(userInput.substring(5)) - 1;
            userInputs.get(taskIndex).markAsDone();
            System.out.println(lineBreak + "Nice! I've marked this task as done: \n" +
                    getStatus(userInputs.get(taskIndex)) +
                    userInputs.get(taskIndex).getDescription() + lineBreak);
        } else {
            System.out.println(lineBreak + "I have no idea what that means" + lineBreak);
        }
    }
}
