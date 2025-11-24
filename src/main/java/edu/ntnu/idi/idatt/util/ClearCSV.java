package edu.ntnu.idi.idatt.util;
import java.io.File;

public class ClearCSV {
    /**
     * removes all files in entries folder ready to be written
     */
    public static void clear() {
        File folder = new File("src/main/resources/entries");
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }
}