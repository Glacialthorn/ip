package JohnChatbot;

import java.util.List;

public class Ui {
    static final String lineBreak = "\n____________________________________________________________\n";

    public static void printLineBreak() {
        System.out.println(lineBreak);
    }
    /**
     * Prints message sandwiched between 2 lines.
     *
     * @param msg The message to be printed.
     */
    public static void printSection(String msg) {
        printLineBreak();
        System.out.println(msg);
        printLineBreak();
    }

    /**
     * Prints each element in the provided list.
     *
     * @param list The list to be printed.
     */
    public static void printListInSection(List<?> list) {
        printLineBreak();
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + "." + list.get(i).toString());
        }
        printLineBreak();
    }

    /**
     * Prints each element in the provided list, with a message before the list.
     *
     * @param list The list to be printed.
     */
    public static void printListInSection(List<?> list, String msg) {
        printLineBreak();
        printList(list, msg);
        printLineBreak();
    }

    private static void printList(List<?> list, String msg) {
        System.out.println(msg);
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + "." + list.get(i).toString());
        }
    }
}
