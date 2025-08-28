import java.util.List;

public class Ui {
    static final String lineBreak = "\n____________________________________________________________\n";

    public Ui() {

    }

    public static void printLineBreak() {
        System.out.println(lineBreak);
    }

    public static void printSection(String msg) {
        printLineBreak();
        System.out.println(msg);
        printLineBreak();
    }

    public static void printListInSection(List<?> ls) {
        printLineBreak();
        for (int i = 0; i < ls.size(); i++) {
            System.out.println((i + 1) + "." + ls.get(i).toString());
        }
        printLineBreak();
    }

    public static void printListInSection(List<?> ls, String msg) {
        printLineBreak();
        printList(ls, msg);
        printLineBreak();
    }

    public static void printList(List<?> ls, String msg) {
        System.out.println(msg);
        for (int i = 0; i < ls.size(); i++) {
            System.out.println((i + 1) + "." + ls.get(i).toString());
        }
    }

    public static void showLoadingError(Exception e) {
        System.out.println("An error has occurred while loading tasks: " + e.getMessage());
    }
}
