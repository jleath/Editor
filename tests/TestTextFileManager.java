import editor.TextFileManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jaleath on 9/7/16.
 */
public class TestTextFileManager {
    private static final String DIRECTORY = "./";
    private static final String TEST_MESSAGE_1 = "This is a test of the emergency file writing system.";
    private static final String TEST_MESSAGE_2 = "Please remain calm. For the safety of yourself and others, please report to your block leader.";
    private static final String FILE_NAME = "testfile.out";

    @Test
    public void testSaving() {
        try {
            TextFileManager.saveToDisk(TEST_MESSAGE_1, DIRECTORY + FILE_NAME);
            Files.delete(Paths.get(DIRECTORY + FILE_NAME));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testLoading() {
        try {
            TextFileManager.saveToDisk(TEST_MESSAGE_1, DIRECTORY + FILE_NAME);
            String fileContents = TextFileManager.loadFromDisk(DIRECTORY + FILE_NAME);
            assertEquals(TEST_MESSAGE_1, fileContents);
            Files.delete(Paths.get(DIRECTORY + FILE_NAME));
        } catch (FileNotFoundException e) {
            assertTrue(false);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testOverwrite() {
        try {
            TextFileManager.saveToDisk(TEST_MESSAGE_1, DIRECTORY + FILE_NAME);
            String fileContents = TextFileManager.loadFromDisk(DIRECTORY + FILE_NAME);
            assertEquals(TEST_MESSAGE_1, fileContents);

            TextFileManager.saveToDisk(TEST_MESSAGE_2, DIRECTORY + FILE_NAME);
            fileContents = TextFileManager.loadFromDisk(DIRECTORY + FILE_NAME);
            assertEquals(TEST_MESSAGE_2, fileContents);

            Files.delete(Paths.get(DIRECTORY + FILE_NAME));
        } catch (IOException e) {
            assertTrue(false);
        }
    }
}
