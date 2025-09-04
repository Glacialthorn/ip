package JohnChatbot;

import java.util.List;
import JohnChatbot.Tasks.Task;

public class Ui {
    private static final String line = "------------------------------------";

    public static String getSection(String msg) {
        return line + "\n" + msg + "\n" + line;
    }

    public static String getListInSection(List<Task> list, String msg) {
        StringBuilder sb = new StringBuilder();
        sb.append(line).append("\n").append(msg).append("\n");
        for (int i = 0; i < list.size(); i++) {
            sb.append((i + 1)).append(". ").append(list.get(i).toString()).append("\n");
        }
        sb.append(line);
        return sb.toString();
    }

    public static String getStringListInSection(List<String> list, String msg) {
        StringBuilder sb = new StringBuilder();
        sb.append(line).append("\n").append(msg).append("\n");
        for (String item : list) {
            sb.append(item).append("\n");
        }
        sb.append(line);
        return sb.toString();
    }
}