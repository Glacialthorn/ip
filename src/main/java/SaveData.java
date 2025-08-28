import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SaveData implements Serializable {
    private final List<Task> taskList = new ArrayList<>();

    public List<Task> getTaskList() {
        return taskList;
    }
}