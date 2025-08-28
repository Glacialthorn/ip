package JohnChatbot;

import JohnChatbot.Tasks.Task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TaskList implements Serializable {
    private final List<Task> taskList = new ArrayList<>();

    public List<Task> getTaskList() {
        return taskList;
    }
}