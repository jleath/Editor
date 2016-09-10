package editor;

import java.io.*;

public class TextFileManager {
    /**
     * A simple utility class for reading from and writing to disk. Deals only with strings and has no
     * knowledge of textbuffers or rendering. Does not handle any exceptions, instead just passes them
     * along to the client.
     */
    public static String loadFromDisk(String filePath) throws IOException {
        File inputFile = new File(filePath);
        String result = "";
        if (!inputFile.exists()) {
            return result;
        }
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        int intRead;
        while ((intRead = reader.read()) != -1) {
            result += (char) intRead;
        }
        reader.close();
        return result;
    }

    public static void saveToDisk(String contents, String filePath) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        writer.write(contents);
        writer.close();
    }
}
