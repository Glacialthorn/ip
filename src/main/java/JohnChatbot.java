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
                    for(int i = 0; i < userInputs.size(); i++) {
                        System.out.println((i + 1) + "." + getStatus(userInputs.get(i)) +
                                            userInputs.get(i).getDescription() + "\n");
                    }
                    System.out.println(lineBreak);
                    break;
                default:
                    if (isMarkCommand(userInput)) {
                        int taskIndex = Integer.parseInt(userInput.substring(5)) - 1;
                        userInputs.get(taskIndex).markAsDone();
                        System.out.println(lineBreak + "Nice! I've marked this task as done: \n" +
                                            getStatus(userInputs.get(taskIndex)) +
                                            userInputs.get(taskIndex).getDescription() + lineBreak);
                        break;
                    } else if (isUnmarkCommand(userInput)) {
                        int taskIndex = Integer.parseInt(userInput.substring(7)) - 1;
                        userInputs.get(taskIndex).markAsUndone();
                        System.out.println(lineBreak + "OK, I've marked this task as not done yet: \n" +
                                getStatus(userInputs.get(taskIndex)) +
                                userInputs.get(taskIndex).getDescription() + lineBreak);
                        break;
                    }
                    System.out.println(lineBreak + "added: " + userInput + lineBreak);
                    userInputs.add(new Task(userInput));
                    break;
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
}
