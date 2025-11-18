package main.java.edu.ntnu.idi.idatt;
import java.io.File;

public class ClearCSV {
    
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