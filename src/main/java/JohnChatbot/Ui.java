package JohnChatbot;

import java.util.List;

public class Ui {
    static final String lineBreak = "\n____________________________________________________________\n";

    public static void printLineBreak() {
        System.out.println(lineBreak);
    }

    public static void printSection(String msg) {
        printLineBreak();
        System.out.println(msg);
        printLineBreak();
    }

    public static void printListInSection(List<?> list) {
        printLineBreak();
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + "." + list.get(i).toString());
        }
        printLineBreak();
    }

    public static void printListInSection(List<?> list, String msg) {
        printLineBreak();
        printList(list, msg);
        printLineBreak();
    }

    public static void printList(List<?> list, String msg) {
        System.out.println(msg);
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + "." + list.get(i).toString());
        }
    }

    public static void showLoadingError(Exception e) {
        System.out.println("An error has occurred while loading tasks: " + e.getMessage());
    }
}
