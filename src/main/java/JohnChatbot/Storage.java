package JohnChatbot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Storage {
    /**
     * Retrieves the save file if available and returns it, otherwise create a new one.
     *
     * @param filename Name of file to be created/found.
     * @return The result of the subtraction.
     */
    public static TaskList getOrCreateSave(String filename) {
        File file = new File(filename);

        if (file.exists() && file.length() > 0 && !file.isDirectory()) {
            try (FileInputStream fis = new FileInputStream(file);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {

                Object obj = ois.readObject();
                if (obj instanceof TaskList) {
                    return (TaskList) obj;
                } else {
                    System.out.println("Warning: Data in file is of the wrong type. Creating new list.");
                }

            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error reading TaskList from file, creating new one: " + e.getMessage());
            }
        }

        System.out.println("No saved data found or file is empty. Creating new task list.");
        TaskList newTaskList = new TaskList();
        saveToFile(newTaskList, filename);
        return newTaskList;
    }

    /**
     * Update stored TaskList data in filename to follow new one.
     *
     * @param taskList The new TaskList data to save.
     * @param filename The name of the stored TaskList file.
     * @return The result of the subtraction.
     */
    public static void saveToFile(TaskList taskList, String filename) {
        try {
            File file = new File(filename);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            try (FileOutputStream fos = new FileOutputStream(file);
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(taskList);
            }
        } catch (IOException e) {
            System.err.println("Error saving TaskList to file: " + filename); // The error message should print the actual filename
        }
    }
}