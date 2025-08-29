package seedu.JohnChatbot;

import JohnChatbot.Storage;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class StorageTest {
    private static final String testFilename = "./testFile.ser";

    @Before
    public void setup() {
        // This method runs before each test. It ensures the file doesn't exist before testing creation.
        File file = new File(testFilename);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void getOrCreateSave_nonExistentFile_createsNewFileAndReturnsTaskList() {
        TaskList taskList = Storage.getOrCreateSave(testFilename);
        File file = new File(testFilename);
        assertNotNull(taskList);
        assertTrue(file.exists());
    }

    @After
    public void tearDown() {
        // This method runs after each test to clean up the created file.
        File file = new File(testFilename);
        if (file.exists()) {
            file.delete();
        }
    }
}