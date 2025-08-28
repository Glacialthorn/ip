import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Storage {
    public static SaveData getOrCreateSave(String filename) {
        File file = new File(filename);

        if (file.exists() && file.length() > 0) {
            try (FileInputStream fis = new FileInputStream(file);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {

                Object obj = ois.readObject();
                if (obj instanceof SaveData) {
                    return (SaveData) obj;
                } else {
                    System.out.println("Warning: TaskList in file is of the wrong type. Creating a new one.");
                }

            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error reading TaskList from file, creating new: " + e.getMessage());
            }
        }

        SaveData newSave = new SaveData();
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(newSave);
        } catch (IOException e) {
            System.out.println("Error writing new TaskList to file: " + e.getMessage());
        }
        return newSave;
    }

    public static void saveToFile(SaveData saveData, String filename) {
        try (FileOutputStream fos = new FileOutputStream(filename);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(saveData);
        } catch (IOException e) {
            System.out.println("Error saving TaskList to file: " + filename);
        }
    }
}
