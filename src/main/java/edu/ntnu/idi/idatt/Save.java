package edu.ntnu.idi.idatt;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Save {
    public void saveToCSV(List<Author> authors) {
        ClearCSV.clear();
        
        // Ensure the directory exists
        String directoryPath = "src/main/resources/entries/";
        try {
            Files.createDirectories(Paths.get(directoryPath));
        } catch (IOException e) {
            System.out.println("Failed to create directory: " + e.getMessage());
            return;
        }
        
        for (Author author : authors) {
            String filename = directoryPath + author.getName() + ".csv";
            System.out.println("Attempting to save to: " + filename); // Debug line
            
            try (FileWriter writer = new FileWriter(filename, false)) { // false to overwrite, not append
                // Write header with author's PIN
                String header = "# Author: " + author.getName() + ", PIN: " + author.getPin() + "\n";
                writer.write(header);
                
                List<Day> days = author.getSortDays(author.getListDays()); // Use sorted days
                
                for (Day day : days) {
                    String line = day.getDate() + "," + day.getContent() + "\n";
                    writer.write(line);
                }
                
                writer.flush(); // Force write to disk
                System.out.println("Successfully saved " + days.size() + " entries for " + author.getName()); // Debug line
                
            } catch (IOException e) {
                System.out.println("Error saving data for " + author.getName() + ": " + e.getMessage());
                e.printStackTrace(); // This will show the full error
            }
        }
    }
}