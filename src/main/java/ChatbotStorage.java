import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class ChatbotStorage {
    String lineBreak = "\n____________________________________________________________\n";
    private final String filePath;

    public ChatbotStorage(String filePath) {
        this.filePath = filePath;
    }

    public List<Task> load() throws IOException {
        List<Task> tasks = new ArrayList<>();
        File f = new File(filePath);

        // Create the file and directory if it doesn't exist
        if (!f.exists()) {
            if (f.getParentFile().mkdirs())
                System.out.println("Directory created");
            else
                System.out.println("Directory already exists");
            if (f.createNewFile())
                System.out.println("File created");
            else
                System.out.println("File already exists");
                System.out.println(lineBreak);
        }

        try (Scanner s = new Scanner(f)) {
            while (s.hasNextLine()) {
                String line = s.nextLine();
                tasks.add(parseTaskFromFile(line));
            }
        }
        return tasks;
    }

    public void save(List<Task> tasks) throws IOException {
        try (FileWriter fw = new FileWriter(filePath)) {
            for (Task task : tasks) {
                fw.write(task.convertToString() + System.lineSeparator());
            }
        }
    }

    private Task parseTaskFromFile(String line) {
        try {
            String[] parts = line.split(" \\| ");
            String type = parts[0];
            boolean isDone = parts[1].equals("1");

            switch (type) {
            case "T":
                Todo todo = new Todo("todo " + parts[2]);
                if (isDone) todo.markAsDone();
                return todo;
            case "D":
                Deadline deadline = new Deadline("deadline " + parts[2]);
                if (isDone) deadline.markAsDone();
                return deadline;
            case "E":
                Event event = new Event("event " + parts[2]);
                if (isDone) event.markAsDone();
                return event;
            default:
                return null;
            }
        } catch (JohnChatbotException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
