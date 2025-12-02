package edu.ntnu.idi.idatt.service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import edu.ntnu.idi.idatt.objects.Author;
import edu.ntnu.idi.idatt.objects.Day;
import edu.ntnu.idi.idatt.util.ClearCSV;

public class Save {
    /**
     * handles writing to csv. calls function clear csv to remove old save date
     * writes header with usename and pin
     * writes date entry an rating per lin seperated by "|"
     * @param authors
     */
    public void saveToCSV(List<Author> authors) {
        ClearCSV.clear();
        
        String directoryPath = "src/main/resources/entries/";
        try {
            Files.createDirectories(Paths.get(directoryPath));
        } catch (IOException e) {
            System.out.println("Failed to create directory: " + e.getMessage());
            return;
        }
        
        for (Author author : authors) {
            String filename = directoryPath + author.getName() + ".csv";
            
            try (FileWriter writer = new FileWriter(filename, false)) {
                // Write header with author's PIN
                String header = "# Author: " + author.getName() + ", PIN: " + author.getPin() + "\n";
                writer.write(header);
                
                List<Day> days = author.getSortDays(author.getListDays());
                
                for (Day day : days) {
                    // Format: date|content (pipe separator is less common in text)
                    String contentEscaped = day.getContent() == null ? "" : day.getContent().replace("\n", "\\n");
                    String line = day.getDate() + "|" +
                                 contentEscaped + "|" + 
                                 day.getRating() + "|" + 
                                 day.getTitle() + "\n";

                    writer.write(line);
                }
                
                writer.flush();
                
            } catch (IOException e) {
                System.out.println("Error saving data for " + author.getName() + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}