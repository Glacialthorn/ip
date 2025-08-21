import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class JohnChatbot {
    public static void main(String[] args) {
        List<String> userInputs = new ArrayList<>();

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
                    for(int i = 0; i < userInputs.size(); i++){
                        System.out.println((i + 1) + ". " + userInputs.get(i) + "\n");
                    }
                    System.out.println(lineBreak);
                    break;
                default:
                    System.out.println(lineBreak + "added: " + userInput + lineBreak);
                    userInputs.add(userInput);
                    break;
            }
        }
    }
}
