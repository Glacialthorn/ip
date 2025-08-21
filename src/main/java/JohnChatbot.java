import java.util.Scanner;

public class JohnChatbot {
    public static void main(String[] args) {
        String lineBreak = "\n____________________________________________________________\n";
        System.out.println(lineBreak + "Hello! I'm John Chatbot!\nWhat can I do for you?" + lineBreak);
        while(true) {
            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine();
            if(userInput.equals("bye")) {
                System.out.println(lineBreak + "Bye. Hope to see you again soon!" + lineBreak);
                break;
            } else {
                System.out.println(lineBreak + userInput + lineBreak);
            }
        }
    }
}
